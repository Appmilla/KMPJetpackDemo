package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import data.repositories.HelloWorldRepository
import data.repositories.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.reflect.KClass
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DetailViewModelFactory(
    private val scope: CoroutineScope,
    private val timerRepository: TimerRepository,
    private val helloWorldRepository: HelloWorldRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras,
    ): T {
        return DetailViewModel(
            viewModelScope = scope,
            helloWorldRepository = helloWorldRepository,
            timerRepository = timerRepository,
        ) as T
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        try {
            // Other teardown code
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun shouldInitializeTimerWithValueFromTimerRepository() =
        runTest {
            val initialValue = 10
            val timerRepository = FakeTimerRepository(initialValue)
            val helloWorldRepository = FakeHelloWorldRepository()

            val detailViewModel =
                DetailViewModel(
                    viewModelScope = this,
                    helloWorldRepository = helloWorldRepository,
                    timerRepository = timerRepository,
                )

            advanceTimeBy(100)

            assertEquals(initialValue, detailViewModel.timer.value)

            this.coroutineContext.cancelChildren()
        }

    @Test
    fun shouldIncrementTimerValueEverySecond() =
        runTest {
            val timerRepository = FakeTimerRepository(0)
            val helloWorldRepository = FakeHelloWorldRepository()

            val detailViewModel =
                DetailViewModel(
                    viewModelScope = this,
                    helloWorldRepository = helloWorldRepository,
                    timerRepository = timerRepository,
                )

            val values = mutableListOf<Int>()
            val job =
                launch {
                    detailViewModel.timer.collect { values.add(it) }
                }

            advanceTimeBy(3500)

            assertEquals(listOf(0, 1, 2, 3), values)
            job.cancel() // Clean up the collecting coroutine

            this.coroutineContext.cancelChildren()
        }

    @Test
    fun shouldSaveIncrementedTimerValueToTimerRepository() =
        runTest {
            val timerRepository =
                object : TimerRepository {
                    private val _timerValue = flow { emit(0) }
                    override val timerValue: Flow<Int> get() = _timerValue

                    override suspend fun getTimerValue(): Int {
                        return _timerValue.first()
                    }

                    var saveCalls = 0

                    override suspend fun saveTimerValue(value: Int) {
                        saveCalls++
                    }
                }
            val helloWorldRepository = FakeHelloWorldRepository()

            DetailViewModel(
                viewModelScope = this,
                helloWorldRepository = helloWorldRepository,
                timerRepository = timerRepository,
            )

            advanceTimeBy(2500)

            assertEquals(2, timerRepository.saveCalls)

            this.coroutineContext.cancelChildren()
        }

    @Test
    fun shouldCancelTimerWhenViewModelIsCleared() =
        runTest {
            val timerRepository = FakeTimerRepository(0)
            val helloWorldRepository = FakeHelloWorldRepository()

            val factory = DetailViewModelFactory(this, timerRepository, helloWorldRepository)
            val viewModelStore = ViewModelStore()
            val provider = ViewModelProvider.create(viewModelStore, factory)

            val detailViewModel = provider[DetailViewModel::class]

            val values = mutableListOf<Int>()
            val job =
                launch {
                    detailViewModel.timer.collect { values.add(it) }
                }

            advanceTimeBy(2500)

            this.coroutineContext.cancelChildren()
            advanceTimeBy(1000)

            assertEquals(listOf(0, 1, 2), values) // The timer should stop at 2 after being cleared
            job.cancel()
        }
}

class FakeTimerRepository(initialValue: Int) : TimerRepository {
    private val _timerValue = MutableStateFlow(initialValue)
    override val timerValue: Flow<Int> get() = _timerValue.asStateFlow()

    override suspend fun getTimerValue(): Int {
        return _timerValue.first()
    }

    override suspend fun saveTimerValue(value: Int) {
        _timerValue.value = value
    }
}

class FakeHelloWorldRepository : HelloWorldRepository {
    override fun getHelloWorldMessage(): String = "Hello, World!"
}

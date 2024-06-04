package viewmodels

import androidx.lifecycle.ViewModel
import data.repositories.HelloWorldRepository
import data.repositories.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DetailViewModel(
    private val viewModelScope: CoroutineScope,
    helloWorldRepository: HelloWorldRepository,
    private val timerRepository: TimerRepository,
) : ViewModel(viewModelScope) {
    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    init {
        timerRepository.timerValue
            .onEach { value ->
                _timer.value = value
                println("Collecting value from timerRepository: $value")
            }
            .launchIn(viewModelScope)
        startTimer()
        println(helloWorldRepository.getHelloWorldMessage())
    }

    private fun startTimer() {
        timerJob =
            viewModelScope.launch {
                while (isActive) {
                    delay(1000)
                    _timer.value++
                    timerRepository.saveTimerValue(_timer.value)
                    println("Timer fired, value from timerRepository: $_timer.value")
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

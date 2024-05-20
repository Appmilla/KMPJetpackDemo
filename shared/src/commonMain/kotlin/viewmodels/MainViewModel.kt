package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.HelloWorldRepository
import data.repository.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val coroutineScope: CoroutineScope,
    private val repository: HelloWorldRepository,
    private val timerRepository: TimerRepository,
): ViewModel() {

    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()

    init {
        timerRepository.timerValue
            .onEach { value ->
                _timer.value = value
            }
            .launchIn(viewModelScope)
        startTimer()
        println(repository.getHelloWorldMessage())
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
                timerRepository.saveTimerValue(_timer.value)
            }
        }
    }
}
package viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _message = MutableStateFlow("Welcome to Home")
    val message = _message.asStateFlow()
}

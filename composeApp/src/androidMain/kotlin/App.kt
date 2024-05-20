import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import viewmodels.MainViewModel

@Composable
@org.jetbrains.compose.ui.tooling.preview.Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val mainViewModel = koinViewModel<MainViewModel>()
            val timer by mainViewModel.timer.collectAsState()

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = timer.toString()
                )
            }
        }
    }
}
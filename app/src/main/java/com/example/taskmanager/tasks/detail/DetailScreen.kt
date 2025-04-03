
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.taskmanager.tasks.Utils
import com.example.taskmanager.tasks.detail.DetailUIState
import com.example.taskmanager.tasks.detail.DetailViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    taskId: String,
    onNavigate: () -> Unit
) {
    val detailUiState= detailViewModel?.detailUiState?: DetailUIState()

    val isFormsNotBlank= detailUiState.task.isNotBlank() && detailUiState.taskTitle.isNotBlank()

    val selectedColor by animateColorAsState(targetValue = Utils.colors[detailUiState.colorIndex])

    val isTaskIdNotBlank= taskId.isNotBlank()

    val icon= if(isFormsNotBlank) Icons.Default.Refresh else Icons.Default.Check

    LaunchedEffect(key1 = Unit){
        if(isTaskIdNotBlank){
            detailViewModel?.getTask(taskId)
        }else{
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState= rememberScaffoldState()

}
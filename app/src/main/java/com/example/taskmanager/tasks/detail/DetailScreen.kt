import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.taskmanager.tasks.Utils
import com.example.taskmanager.tasks.detail.DetailUIState
import com.example.taskmanager.tasks.detail.DetailViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    taskId: String,
    onNavigate: () -> Unit
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUIState()

    val isFormsNotBlank = detailUiState.task.isNotBlank() && detailUiState.taskTitle.isNotBlank()

    val selectedColor by animateColorAsState(targetValue = Utils.colors[detailUiState.colorIndex])

    val isTaskIdNotBlank = taskId.isNotBlank()

    val icon = if (isFormsNotBlank) Icons.Default.Refresh else Icons.Default.Check

    LaunchedEffect(key1 = Unit) {
        if (isTaskIdNotBlank) {
            detailViewModel?.getTask(taskId)
        } else {
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isTaskIdNotBlank) {
                    detailViewModel?.updateTask(taskId)
                } else {
                    detailViewModel?.addTask()
                }
            }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = selectedColor)
                .padding(padding)
        ) {
            if(detailUiState.taskAddedStatus){
                scope.launch{
                    scaffoldState.snackbarHostState.showSnackbar("Task has been added successfully")
                    detailViewModel?.resetNoteAddedStatus()
                    onNavigate.invoke()

                }
            }
            if(detailUiState.updateTaskStatus) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Task has been updated successfully")
                    detailViewModel?.resetNoteAddedStatus()
                    onNavigate.invoke()
                }
            }
        }
    }
}

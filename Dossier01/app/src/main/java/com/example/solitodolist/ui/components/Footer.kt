package com.example.solitodolist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.solitodolist.data.Task
import com.example.solitodolist.viewModel.TasksviewModel


@Composable
fun Footer(navController: NavController,tasksviewModel: TasksviewModel = viewModel()){
    val currentRoute =  navController.currentBackStackEntryAsState().value?.destination?.route
    if (currentRoute == Routes.Tasks.name)
    {
        val taskList by tasksviewModel.tasks.collectAsState()
        val tasks = remember { mutableStateOf<List<Task>>(emptyList()) }
        var tasksNonComplet by remember { mutableStateOf( taskList.filter { it.status == 0 }.size) }
        LaunchedEffect(taskList) {
            if (taskList.isNotEmpty()) {
                tasks.value = taskList
                tasksNonComplet = taskList.filter { it.status == 0 }.size
            }
        }
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Routes.AddTask.name)
                    })
                {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text(text= "Add Task")
                }
                Text(text = "$tasksNonComplet")

            }

        }
    }

}

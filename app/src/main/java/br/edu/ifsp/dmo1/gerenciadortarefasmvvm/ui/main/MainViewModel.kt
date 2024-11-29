package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.main

import android.accessibilityservice.AccessibilityService.TakeScreenshotCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.dao.TaskDao
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model.Task

class MainViewModel: ViewModel() {

    private val dao = TaskDao

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() {
            return _tasks
        }

    private val _insertTasks = MutableLiveData<Boolean>()
    val insertTask: LiveData<Boolean> = _insertTasks

    private val _updateTask = MutableLiveData<Boolean>()
    val updateTask: LiveData<Boolean>
        get() = _updateTask

    private val _tasksCompleted = MutableLiveData<List<Task>>()
    val tasksCompleted: LiveData<List<Task>>
        get(){
            return _tasksCompleted
        }

    private val _tasksNotCompleted = MutableLiveData<List<Task>>()
    val tasksNotCompleted: LiveData<List<Task>>
        get(){
            return _tasksNotCompleted
        }

    private val _tasksAll = MutableLiveData<List<Task>>()
    val tasksAll: LiveData<List<Task>>
        get(){
            return _tasksAll
        }

    init {
        load()
    }

    fun insertTask(description: String){
        val task = Task(description, false)
        dao.add(task)
        _insertTasks.value = true
        load()
    }

    fun updateTask(position:Int){
        val task = dao.getAll()[position]
        task.isCompleted = !task.isCompleted
        _updateTask.value = true
        load()
    }

    fun getCompletedTasks(){
        _tasksCompleted.value = dao.getIsCompleted()
    }

    fun getNotCompletedTasks(){
        _tasksNotCompleted.value = dao.getNotCompleted()
    }

    fun getAllTasks(){
        _tasksAll.value = dao.getAll()
    }

    private fun load() {
        _tasks.value = dao.getAll()
    }

}
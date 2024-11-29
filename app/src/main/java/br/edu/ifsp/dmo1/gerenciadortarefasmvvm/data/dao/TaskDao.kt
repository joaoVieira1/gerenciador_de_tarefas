package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.dao

import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.data.model.Task
import java.util.stream.Collectors

object TaskDao {
    private var tasks: MutableList<Task> = mutableListOf()

    fun add(task: Task){
        tasks.add(task)
    }

    fun getIsCompleted(): List<Task>{
        return tasks.stream().filter { t -> t.isCompleted == true }.collect(Collectors.toList())
    }

    fun getNotCompleted(): List<Task>{
        return tasks.stream().filter { t -> t.isCompleted == false }.collect(Collectors.toList())
    }

    fun getAll(): List<Task>{
        return tasks.sortedBy { it.isCompleted }
    }

    fun get(id: Long): Task?{
        return tasks.stream()
            .filter{t -> t.id == id}
            .findFirst()
            .orElse(null)
    }

}
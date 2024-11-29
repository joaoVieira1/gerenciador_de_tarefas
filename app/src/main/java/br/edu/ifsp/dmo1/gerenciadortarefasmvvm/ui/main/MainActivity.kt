package br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.R
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.databinding.DialogNewTaskBinding
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.adapter.TaskAdapter
import br.edu.ifsp.dmo1.gerenciadortarefasmvvm.ui.listener.TaskClickListener

class MainActivity : AppCompatActivity(), TaskClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        configListview()
        configOnClickListener()
        configObservers()
    }

    private fun configListview() {
        adapter = TaskAdapter(this, mutableListOf(), this)
        binding.listTasks.adapter = adapter
    }

    private fun configObservers() {
        viewModel.tasks.observe(this, Observer {
            adapter.updateTasks(it)
        })

        viewModel.insertTask.observe(this, Observer {
            val str: String = if (it) {
                getString(R.string.task_inserted_sucess)
            } else {
                getString(R.string.task_inserted_error)
            }
            Toast.makeText(this, str, Toast.LENGTH_LONG).show()
        })

        viewModel.updateTask.observe(this, Observer {
            if(it){
                Toast.makeText(this, getString(R.string.task_update_sucess), Toast.LENGTH_LONG).show()
            }
        })

        viewModel.tasksCompleted.observe(this, Observer{
            adapter.updateTasks(it)
        })

        viewModel.tasksNotCompleted.observe(this, Observer{
            adapter.updateTasks(it)
        })

        viewModel.tasksAll.observe(this, Observer {
            adapter.updateTasks(it)
        })
    }

    private fun configOnClickListener(){
        binding.buttonAddTask.setOnClickListener{
            openDialogNewTask()
        }

        binding.radiogroupUnits.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.radio_is_completed){
                viewModel.getCompletedTasks()
            }

            if(checkedId == R.id.radio_not_completed){
                viewModel.getNotCompletedTasks()
            }

            if(checkedId == R.id.radio_all_tasks){
                viewModel.getAllTasks()
            }
        }

    }

    private fun openDialogNewTask() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_new_task, null)
        val bindingDialog = DialogNewTaskBinding.bind(dialogView)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle(getString(R.string.add_new_task))
            .setPositiveButton(
                getString(R.string.save),
                DialogInterface.OnClickListener { dialog, which ->
                    val description = bindingDialog.editDescription.text.toString()
                    viewModel.insertTask(description)
                    dialog.dismiss()
                })
            .setNegativeButton(
                getString(R.string.cancel),
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })

        val dialog = builder.create()
        dialog.show()
    }

    override fun clickDone(position: Int) {
        viewModel.updateTask(position)
    }

}
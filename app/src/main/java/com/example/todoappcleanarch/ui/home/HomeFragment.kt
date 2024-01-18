package com.example.todoappcleanarch.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todoappcleanarch.R
import com.example.todoappcleanarch.databinding.FragmentHomeBinding
import com.example.todoappcleanarch.model.ToDoModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ToDoClickListener {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.toDoClickListener = this
        viewModel.toDoList.observe(viewLifecycleOwner) {
            Log.d("asdasd",it.toString())
        }
        binding.fragmentHomeFab.setOnClickListener {
            viewModel.insertToDo()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onToDoClick(id: Int) {
        TODO("Not yet implemented")
    }

    override fun onToDoChecked(toDoModel: ToDoModel) {
        TODO("Not yet implemented")
    }

}
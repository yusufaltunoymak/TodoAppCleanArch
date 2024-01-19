package com.example.todoappcleanarch.ui.newAndEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoappcleanarch.databinding.FragmentNewAndEditBinding
import com.example.todoappcleanarch.model.Priority
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewAndEditFragment : Fragment() {
    private var _binding:FragmentNewAndEditBinding? = null
    private val binding get() = _binding!!
    private var currentPriorityIndex = 0
    private val viewModel : NewAndEditViewModel by viewModels()
    private val args by navArgs<NewAndEditFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewAndEditBinding.inflate(inflater,container,false)
        if (args.toDoId == -1) {
           binding.newAndEditDeleteButton.isVisible = false
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "New To-Do"
        }
        else {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Edit To-Do"
            viewModel.getToDo(args.toDoId)
        }
        initializeView()
        viewModel.toDoModel.observe(viewLifecycleOwner) {
            binding.newAndEditTitleEditText.setText(it.title)
            binding.newAndEditDescriptionEditText.setText(it.description)
            binding.newAndEditAutoCompleteText.setText(it.priority?.name,false)
            binding.newAndEditCheckBox.isChecked = it.isChecked == true
            currentPriorityIndex = when(it.priority) {
                Priority.HIGH -> 0
                Priority.MEDIUM -> 1
                else -> 2
            }
        }

        return binding.root
    }

    private fun handleSaveButton() {
        val title = binding.newAndEditTitleEditText.text.toString()
        val description = binding.newAndEditDescriptionEditText.text.toString()

        val priority = when(currentPriorityIndex) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            else -> Priority.LOW
        }
        if(args.toDoId == -1) {
            viewModel.insertToDo(title,description,binding.newAndEditCheckBox.isChecked,priority)
        }
        else {
            viewModel.updateToDo(title,description,binding.newAndEditCheckBox.isChecked,priority)
        }
        Toast.makeText(requireContext(),"Successfully Saved",Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
    private fun initializeView() {
        binding.newAndEditSaveButton.setOnClickListener {
            handleSaveButton()
        }

        binding.newAndEditAutoCompleteText.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                arrayOf(Priority.HIGH.name,Priority.MEDIUM.name,Priority.LOW.name)
            )
        )
        binding.newAndEditAutoCompleteText.setOnItemClickListener { _,_,index,_ ->
            currentPriorityIndex = index
        }
        binding.newAndEditDeleteButton.setOnClickListener {
            viewModel.deleteToDo()
            Toast.makeText(requireContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.noteapp_wapip.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBinderMapper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.noteapp_wapip.R
import com.example.noteapp_wapip.databinding.FragmentCreateNoteBinding
import com.example.noteapp_wapip.db.NotesDatabase
import com.example.noteapp_wapip.mvvm.NotesFactoryViewModel
import com.example.noteapp_wapip.mvvm.NotesRepository
import com.example.noteapp_wapip.mvvm.NotesViewModel


class CreateNoteFragment : Fragment() {

    lateinit var binding : FragmentCreateNoteBinding
    lateinit var viewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        (activity as AppCompatActivity).supportActionBar?.setTitle("CREATE NOTE")



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_note, container, false)

        val dao = NotesDatabase.getInstance(requireContext()).notesDao
        val repostiry = NotesRepository(dao)
        val factory = NotesFactoryViewModel(repostiry)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.saveBtn.setOnClickListener {

            viewModel.addNotes()
            view?.findNavController()?.navigate(R.id.action_createNoteFragment2_to_homeFragment2)

        }

        return binding.root


    }










}
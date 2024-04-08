package com.example.noteapp_wapip.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp_wapip.R
import com.example.noteapp_wapip.databinding.FragmentHomeBinding
import com.example.noteapp_wapip.db.Notes
import com.example.noteapp_wapip.db.NotesDatmabase
import com.example.noteapp_wapip.mvvm.NotesFactoryViewModel
import com.example.noteapp_wapip.mvvm.NotesRepository
import com.example.noteapp_wapip.mvvm.NotesViewModel
import com.example.noteapp_wapip.ui.adapter.NotesAdapter

class HomeFragment : Fragment(), MenuProvider {

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: NotesAdapter
    lateinit var viewModel: NotesViewModel
     var searchNotes = arrayListOf<Notes>()
    var isopened = false

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        (activity as AppCompatActivity).supportActionBar?.setTitle("HOME")

        binding.fab.setOnClickListener {

            view?.findNavController()?.navigate(R.id.action_homeFragment2_to_createNoteFragment2)
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)

        setHasOptionsMenu(true)



        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        val dao = NotesDatabase.getInstance(requireContext()).notesDao
        val repostiry = NotesRepository(dao)
        val factory = NotesFactoryViewModel(repostiry)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        var placeHolder = listOf<Notes>()
        adapter = NotesAdapter(placeHolder)
        viewModel.displayAllnotes.observe(viewLifecycleOwner, Observer {

            searchNotes = it as ArrayList<Notes>
            placeHolder = it as List<Notes>
            adapter = NotesAdapter(placeHolder)

            binding.recyclerView.adapter = adapter


        })


        return binding.root
    }




    @Suppress("DEPRECATION")
    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        var menuItem = menu.findItem(R.id.app_bar_search)
        val searchView  = menuItem.actionView as SearchView



        searchView.setOnSearchClickListener {

            val menuItem = menu.findItem(R.id.delete)
            menuItem.setVisible(false)
            isopened = true


        }

        searchView.setOnCloseListener(SearchView.OnCloseListener { isopened
            val menuItem = menu.findItem(R.id.delete)
            menuItem.setVisible(true)

            isopened.not()

        })











        searchView.queryHint = "Search Notes"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                notesFilter(p0)

                return true

            }


        })



        super.onCreateOptionsMenu(menu, inflater)



    }

    private fun notesFilter(p0: String?) {

        val newfilteredlist = arrayListOf<Notes>()

        for (i in searchNotes){

            if (i.title.contains(p0!!) || i.subtitle.contains(p0)){

                newfilteredlist.add(i)
            }
        }

        adapter.filteredList(newfilteredlist)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {


        if (menuItem.itemId == R.id.delete) {

            viewModel.clearAll()

            Toast.makeText(context, "DELETD ALL NOTES", Toast.LENGTH_SHORT).show()
        }


        return true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                requireActivity().finish()
                requireActivity().moveTaskToBack(true);

            }
        })
    }



}






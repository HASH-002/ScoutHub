package com.android.project.scouthub.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.project.scouthub.R
import com.android.project.scouthub.activity.ScoutHubActivity
import com.android.project.scouthub.adapter.UsersAdapter
import com.android.project.scouthub.databinding.FragmentHomeBinding
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.viewModel.UsersViewModel
import com.android.project.scouthub.viewModel.viewModelFactory.UsersViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject lateinit var usersRepository: UsersRepository
    @Inject lateinit var viewModelFactory: UsersViewModelProviderFactory
    private lateinit var binding: FragmentHomeBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        binding.fabButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(UsersViewModel::class.java)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = usersAdapter.differ.currentList[position]
                viewModel.deleteUser(user)
                view?.let {
                    Snackbar.make(it, "Successfully deleted user", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo"){
                            viewModel.saveUser(user)
                        }
                        show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerViewHome)
        }

        viewModel.getSavedUsers().observe(viewLifecycleOwner, Observer { users ->
            usersAdapter.differ.submitList(users)
        })
    }

    private fun setupRecyclerView(view: View) {
        usersAdapter = UsersAdapter()
        binding.recyclerViewHome.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        val imm = (activity as ScoutHubActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
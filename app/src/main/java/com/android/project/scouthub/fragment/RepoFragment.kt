package com.android.project.scouthub.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.project.scouthub.adapter.RepoAdapter
import com.android.project.scouthub.databinding.FragmentRepoBinding
import com.android.project.scouthub.model.RepoItem
import com.android.project.scouthub.repository.RepoRepository
import com.android.project.scouthub.util.Resource
import com.android.project.scouthub.viewModel.RepoViewModel
import com.android.project.scouthub.viewModel.viewModelFactory.ReposViewModelProviderFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject


class RepoFragment : BaseFragment() {

    @Inject lateinit var repoRepository: RepoRepository
    @Inject lateinit var viewModelFactory: ReposViewModelProviderFactory
    private lateinit var binding: FragmentRepoBinding
    private lateinit var viewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter
    private lateinit var allRepoList: MutableList<RepoItem>

    val TAG = "RepoFragment"

    private val searchJob: Job by lazy { Job() }
    private val searchScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Default + searchJob) }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,viewModelFactory).get(RepoViewModel::class.java)

        setupRecyclerView(view)

        var login = ""
        val bundle = arguments
        //the data passing with bundle and serializable
        if (bundle != null)
            login = bundle.getSerializable("login").toString()

        viewModel.getUserRepos(login)

        viewModel.getUserRepos.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { reposResponse ->
                        repoAdapter.differ.submitList(reposResponse.toList())
                        allRepoList = repoAdapter.differ.currentList
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (!query.isNullOrEmpty()) {
                    performSearch(query.toLowerCase(Locale.getDefault()).trim())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    performSearch(newText.toLowerCase(Locale.getDefault()).trim())
                }else{
                    repoAdapter.differ.submitList(allRepoList)
                }
                return false
            }
        })

    }

    private fun performSearch(query: String) {
        searchScope.launch {

            // Show the progress bar in Main
            withContext(Dispatchers.Main) {
                showProgressBar()
            }

            val filteredList = filterList(query)

            // Update the RecyclerView in the main thread
            withContext(Dispatchers.Main) {
                repoAdapter.differ.submitList(filteredList)
                hideProgressBar()
            }
        }
    }

    private fun filterList(query: String): List<RepoItem> {
        val resultList = mutableListOf<RepoItem>()
        allRepoList.forEach { item ->
            if (item.name?.toLowerCase(Locale.getDefault())?.contains(query) == true) {
                resultList.add(item)
            }
        }
        return resultList
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob.cancel()
    }

    private fun setupRecyclerView(view: View) {
        repoAdapter = RepoAdapter()
        binding.recyclerViewRepo.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.repoSpinner.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.repoSpinner.visibility = View.VISIBLE
    }
}



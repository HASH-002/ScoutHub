package com.android.project.scouthub.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.project.scouthub.R
import com.android.project.scouthub.activity.ScoutHubActivity
import com.android.project.scouthub.databinding.FragmentSearchBinding
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.util.Resource
import com.android.project.scouthub.viewModel.SearchViewModel
import com.android.project.scouthub.viewModel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject lateinit var usersRepository: UsersRepository
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentSearchBinding
    private lateinit var paginationProgress: ProgressBar
    val TAG = "SearchFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(SearchViewModel::class.java)

        paginationProgress = view.findViewById(R.id.searchSpinner)
        binding.apply {
            searchButton.setOnClickListener {
                if (searchBar.text.toString().isNotEmpty()) {
                    viewModel.searchUser(searchBar.text.toString())
                }

            }
        }

        viewModel.searchUser.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                    setCardVisibility(View.GONE,View.GONE)
                }

                is Resource.Success -> {
                    hideProgressBar()
                    setCardVisibility(View.VISIBLE,View.GONE)
                    response.data?.let { user ->
                        Glide.with(this).load(user.avatar_url).into(binding.searchUserImage)
                        binding.apply {
                            searchUserFullName.text = user.name
                            searchUserBio.text = user.bio
                            searchUserUsername.text = user.login
                        }

                        binding.apply {
                            saveButton.setOnClickListener{
                                viewModel.saveUser(user)

                                val imm = (activity as ScoutHubActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view.windowToken, 0)

                                setCardVisibility(View.GONE,View.GONE)
                                searchBar.text.clear()
                                Snackbar.make(view, "User saved successfully", Snackbar.LENGTH_SHORT).show()
                            }
                            cancelButton.setOnClickListener{
                                setCardVisibility(View.GONE,View.GONE)
                                searchBar.text.clear()
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    setCardVisibility(View.GONE,View.VISIBLE)
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
            }
        })
    }

    // Do no load previous state
    override fun onResume() {
        super.onResume()
        setCardVisibility(View.GONE, View.GONE)
    }
    private fun setCardVisibility(success: Int, error: Int ){
        binding.apply {
            userSearchCardViewSuccess.visibility = success
            userSearchCardViewError.visibility = error
        }
    }

    private fun hideProgressBar() {
        paginationProgress.visibility = View.GONE
    }

    private fun showProgressBar() {
        paginationProgress.visibility = View.VISIBLE
    }
}
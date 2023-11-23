package com.android.project.scouthub.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.project.scouthub.R
import com.android.project.scouthub.ScoutHubActivity
import com.android.project.scouthub.databinding.FragmentSearchBinding
import com.android.project.scouthub.util.Resource
import com.android.project.scouthub.viewModel.UsersViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var paginationProgress: ProgressBar
    val TAG = "SearchFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ScoutHubActivity).viewModel

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
package com.android.project.scouthub.fragment

import androidx.fragment.app.Fragment
import com.android.project.scouthub.activity.BaseActivity

open class BaseFragment : Fragment() {
    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}
package com.android.project.scouthub.dagger

import com.android.project.scouthub.dagger.modules.ViewModelModule
import com.android.project.scouthub.dagger.scope.PresentationScope
import com.android.project.scouthub.fragment.HomeFragment
import com.android.project.scouthub.fragment.RepoFragment
import com.android.project.scouthub.fragment.SearchFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [ViewModelModule::class])
interface PresentationComponent {
    fun inject(fragment: RepoFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: HomeFragment)
}
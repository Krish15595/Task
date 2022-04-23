package com.d2k.task.di

import com.d2k.task.ui.dashboard.userlistview.UserListViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GraphViewFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeCustomerFragmentInjector():UserListViewFragment
}
package com.d2k.task.di

import com.d2k.task.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivityInjector():LoginActivity
}
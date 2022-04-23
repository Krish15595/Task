package com.d2k.task.di

import com.d2k.task.ui.MyAppiclation
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    LoginActivityModule::class,
    GraphViewFragmentModule::class,
    AppModule::class
])
interface AppComponent {
    fun inject(application:MyAppiclation)
}
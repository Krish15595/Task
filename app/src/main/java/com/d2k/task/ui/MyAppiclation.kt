package com.d2k.task.ui

import android.app.Application
import com.d2k.task.di.AppModule
import com.d2k.task.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyAppiclation : Application(),HasAndroidInjector {
    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInjector
    }
}
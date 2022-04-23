package com.d2k.task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import com.d2k.task.R
import com.d2k.task.databinding.ActivitySplashBinding
import com.d2k.task.ui.carousel.CarouselActivity
import com.d2k.task.ui.login.LoginActivity
import com.d2k.task.util.Constants.Companion.FIRST_TIME_LOAD
import com.d2k.tmb.base.BaseActivity
import com.d2k.tmb.extension.launchActivity


class SplashActivity : BaseActivity() {
    lateinit var activitySplashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding =
            DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            activitySplashBinding.bankLoad.visibility = View.GONE
            activitySplashBinding.llMain.visibility = View.VISIBLE
            /*val aniSlide: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.silde_up)
            activitySplashBinding.ivLogo.startAnimation(aniSlide)*/
            Handler(Looper.getMainLooper()).postDelayed({
                if (prefUtils.getBoolean(FIRST_TIME_LOAD))
                    launchActivity<LoginActivity> { finish() }
                else
                    launchActivity<CarouselActivity> { finish() }
            }, 2000)
        }, 1000)


    }
}
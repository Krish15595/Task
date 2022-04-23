package com.d2k.task.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.d2k.task.databinding.ActivityLoginBinding
import com.d2k.task.ui.dashboard.DashboardActivity
import com.d2k.task.ui.login.model.LoginRequest
import com.d2k.task.ui.login.model.LoginResponse
import com.d2k.task.ui.login.viewmodel.LoginViewModel
import com.d2k.task.util.Constants
import com.d2k.task.util.Constants.Companion.USER_INFO
import com.d2k.task.util.Constants.Companion.USER_LOGGED_IN
import com.d2k.task.util.Status
import com.d2k.tmb.base.BaseActivity
import com.d2k.tmb.extension.launchActivity
import com.d2k.tmb.extension.showProgress
import com.d2k.tmb.extension.showToast
import com.google.gson.Gson
import dagger.android.AndroidInjection
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    lateinit var loginBinding: ActivityLoginBinding

    @Inject
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@LoginActivity)
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        loginBinding.viewmodel = loginViewModel
        setContentView(loginBinding.root)
        if (prefUtils.getBoolean(USER_LOGGED_IN)) {
            launchActivity<DashboardActivity> {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
            }
        }
        init()
    }

    private fun init() {
        val dialog = Dialog(this)
        loginBinding.etUsername.setOnFocusChangeListener { view, b ->
            if (loginBinding.etUsername.text.toString().length > 0) {
                loginBinding.tilUsername.setError(null)
            }
        }
        loginBinding.etPassword.setOnFocusChangeListener { view, b ->
            if (loginBinding.etPassword.text.toString().length > 0) {
                loginBinding.tilPassword.setError(null)
            }
        }
        loginBinding.btnLogin.setOnClickListener {
            if (loginBinding.etUsername.text.toString().isNullOrEmpty()) {
                loginBinding.tilUsername.setError("Please enter username")
                return@setOnClickListener
            }
            if (loginBinding.etPassword.text.toString().isNullOrEmpty()) {
                loginBinding.tilPassword.setError("Please enter password")
                return@setOnClickListener
            }
            loginViewModel.loginUsingFlow(
                LoginRequest(
                    loginBinding.etPassword.text.toString(),
                    loginBinding.etUsername.text.toString()
                )
            )

            /* launchActivity<DashboardActivity> {
                 addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                 finish()
             }*/
            lifecycleScope.launchWhenCreated {
                loginViewModel.loginUserFlow.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            showProgress(dialog,1)

                            if (it.data!=null) {
                                prefUtils.setString(
                                    Constants.ACCESS_TOKEN,
                                    it.data.token
                                )
                                showToast("Login successful")
                                launchActivity<DashboardActivity> {
                                    prefUtils.setBoolean(USER_LOGGED_IN, true)
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    finish()
                                }
                            }
                        }
                        Status.ERROR -> {
                            showProgress(dialog,1)
                            showToast("User Not Found")
                        }
                        Status.LOADING -> {
                            showProgress(dialog)
                        }
                    }
                }
            }
        }
    }
}

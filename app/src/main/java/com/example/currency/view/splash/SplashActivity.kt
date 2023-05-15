package com.example.currency.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currency.R
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserSharedPreferance
import com.example.currency.databinding.ActivitySplashBinding
import com.example.currency.view.main.MainActivity
import com.example.currency.view.register.RegisterActivity
import com.example.currency.viewmodel.register.RegisterViewModel
import com.example.currency.viewmodel.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    lateinit var activitySplashBinding: ActivitySplashBinding
     private  val  splashViewModel: SplashViewModel by viewModels()
     var userModel: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        //registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        activitySplashBinding.splashviewmodel = splashViewModel
        setContentView(activitySplashBinding.root)
        splashViewModel.UserSharedPreferanceMutableLiveData.observe(this, Observer {
            setSharedPreferance(it)
        })
    }

    fun setSharedPreferance(userSharedPreferance: UserSharedPreferance) {
        userModel = userSharedPreferance.Get_UserData(this@SplashActivity)
        Handler().postDelayed(Runnable { activitySplashBinding.logo.animate().rotation(360f).duration =
            1000 }, 2000)
        Handler().postDelayed(Runnable {
            if (userModel != null) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
                finish()
            }
        }, 3000)
    }
}
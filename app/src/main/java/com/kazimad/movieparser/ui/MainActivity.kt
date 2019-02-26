package com.kazimad.movieparser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.Profile
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.utils.ActivityUtils


class MainActivity : AppCompatActivity(), MainInterface {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.loginCheckerLiveData.observe(this, Observer<Boolean> { t -> onUserLoginCheck(t) })
        viewModel.profileLiveData.observe(this, Observer<Profile> { t -> onProfileReceived(t) })

        viewModel.checkIsUserLoginIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun onUserLoginCheck(userLogedIn: Boolean?) {
        userLogedIn?.let {
            if (userLogedIn) {
                viewModel.getCurrentProfile()
            } else {
                val loginFragment = LoginFragment()
                ActivityUtils.addFragmentToActivity(this, loginFragment)
            }
        }
    }

    private fun onProfileReceived(profile: Profile?) {
        profile?.let {
            ActivityUtils.addFragmentToActivity(
                this@MainActivity, MainFragment.newInstance(profile)
            )
        }
    }
}

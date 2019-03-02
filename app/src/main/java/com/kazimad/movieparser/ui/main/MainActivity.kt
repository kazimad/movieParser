package com.kazimad.movieparser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.facebook.Profile
import com.google.android.material.tabs.TabLayout
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.SectionsPagerAdapter
import com.kazimad.movieparser.ui.login.LoginFragment
import com.kazimad.movieparser.utils.ActivityUtils
import com.kazimad.movieparser.utils.Logger


class MainActivity : AppCompatActivity(), MainInterface {


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var loginContainer: FrameLayout
    private lateinit var viewPagerContainer: ViewPager
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.loginCheckerLiveData.observe(this, Observer<Boolean> { t -> checkUserLogin(t) })
        viewModel.profileLiveData.observe(this, Observer<Profile> { t -> onCurrentProfileReceived(t) })

        viewModel.checkIsUserLoginIn()
        loginContainer = findViewById(R.id.fragmentContainer)
        viewPagerContainer = findViewById(R.id.pager)
        imageView = findViewById(R.id.avatar)

        prepareTabLayoutAndViewPager()
    }

    override fun onResume() {
        super.onResume()
        lodAvatar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onUserLoggedIn() {
        super.onUserLoggedIn()
        checkUserLogin(true)
    }

    private fun checkUserLogin(userLoggedIn: Boolean?) {
        userLoggedIn?.let {
            if (userLoggedIn) {
                viewModel.getCurrentProfile()
            } else {
                loginContainer.visibility = View.VISIBLE
                val loginFragment = LoginFragment()
                ActivityUtils.addFragmentToActivity(this, loginFragment)
            }
        }
    }

    private fun onCurrentProfileReceived(profile: Profile?) {
        Logger.log("onCurrentProfileReceived ${profile?.name}")
        profile?.let {
            ActivityUtils.addFragmentToActivity(
                this@MainActivity, MainFragment.newInstance(profile)
            )
        }
    }

    private fun lodAvatar() {
        val imageUrl = "http://graph.facebook.com/" + Profile.getCurrentProfile().id + "/picture?type=small"
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun prepareTabLayoutAndViewPager() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(this.getString(R.string.activity_films)))
        tabLayout.addTab(tabLayout.newTab().setText(this.getString(R.string.activity_favorites)))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager>(R.id.pager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPagerContainer.adapter = mSectionsPagerAdapter
    }
}

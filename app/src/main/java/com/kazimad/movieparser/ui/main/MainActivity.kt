package com.kazimad.movieparser.ui.main

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.google.android.material.tabs.TabLayout
import com.kazimad.movieparser.InterfaceActivity
import com.kazimad.movieparser.InterfaceFragment
import com.kazimad.movieparser.R
import com.kazimad.movieparser.adapters.SectionsPagerAdapter
import com.kazimad.movieparser.utils.glide.Glider


class MainActivity : AppCompatActivity(), InterfaceActivity {

    private lateinit var viewPagerContainer: ViewPager
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerContainer = findViewById(R.id.pager)
        imageView = findViewById(R.id.avatar)

        prepareTabLayoutAndViewPager()
    }

    override fun onResume() {
        super.onResume()
        lodAvatar()
    }


    private fun lodAvatar() {
        if (Profile.getCurrentProfile() != null) {
            val imageUri = Profile.getCurrentProfile().getProfilePictureUri(400, 400)
            Glider.downloadAvatar(imageUri, imageView)
        } else {
            object : ProfileTracker() {
                override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                    this.stopTracking()
                    Profile.setCurrentProfile(currentProfile)
                    val imageUri = Profile.getCurrentProfile().getProfilePictureUri(400, 400)
                    Glider.downloadAvatar(imageUri, imageView)
                }
            }
        }
    }

    private fun prepareTabLayoutAndViewPager() {
        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPagerContainer.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(this.getString(R.string.activity_films)))
        tabLayout.addTab(tabLayout.newTab().setText(this.getString(R.string.activity_favorites)))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager>(R.id.pager)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                if (mSectionsPagerAdapter.getItem(tab.position) is InterfaceFragment) {
                    (mSectionsPagerAdapter.getItem(tab.position) as InterfaceFragment).onTabSelected()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onUserLoggedIn() {
    }
}

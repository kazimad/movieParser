package com.kazimad.movieparser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kazimad.movieparser.ui.favorite.FavoriteFragment
import com.kazimad.movieparser.ui.main.MainFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var mainFragment: MainFragment

    override fun getItem(position: Int): Fragment {

        when (position) {
            1 -> {
                return if (::favoriteFragment.isInitialized) {
                    favoriteFragment
                } else {
                    favoriteFragment = FavoriteFragment()
                    return favoriteFragment
                }
            }
            else -> {
                return if (::mainFragment.isInitialized) {
                    mainFragment
                } else {
                    mainFragment = MainFragment()
                    return mainFragment
                }
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
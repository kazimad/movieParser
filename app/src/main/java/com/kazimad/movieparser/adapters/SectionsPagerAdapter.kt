package com.kazimad.movieparser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kazimad.movieparser.ui.favorite.FavoriteFragment
import com.kazimad.movieparser.ui.main.MainFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MainFragment()
            }
            else -> {
                FavoriteFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
package com.kazimad.movieparser.adapters

import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kazimad.movieparser.ui.favorite.FavoriteFragment
import com.kazimad.movieparser.ui.main.MainFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    lateinit var favoriteFargmnet: FavoriteFragment
    lateinit var mainFragment: MainFragment
    override fun getItem(position: Int): Fragment {

        when (position) {
            1 -> {
                return if (::favoriteFargmnet.isInitialized) {
                    favoriteFargmnet
                } else {
                    favoriteFargmnet = FavoriteFragment()
                    return favoriteFargmnet
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

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
//        Logger.log("SectionsPagerAdapter destroyItem ")
    }

    override fun startUpdate(container: ViewGroup) {
        super.startUpdate(container)
//        Logger.log("SectionsPagerAdapter startUpdate ")

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        Logger.log("SectionsPagerAdapter instantiateItem ")
        return super.instantiateItem(container, position)

    }

    override fun finishUpdate(container: ViewGroup) {
        super.finishUpdate(container)
//        Logger.log("SectionsPagerAdapter finishUpdate ")
//        if (currentFragment is FavoriteFragment) {
//            (currentFragment as InterfaceFragment).onTabSelected()
//        }

    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        super.restoreState(state, loader)
//        Logger.log("SectionsPagerAdapter restoreState ")

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        Logger.log("SectionsPagerAdapter isViewFromObject ")
        return super.isViewFromObject(view, `object`)
    }

    override fun getItemId(position: Int): Long {
//        Logger.log("SectionsPagerAdapter getItemId ")
        return super.getItemId(position)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
//        Logger.log("SectionsPagerAdapter setPrimaryItem ")
    }

    override fun saveState(): Parcelable? {
//        Logger.log("SectionsPagerAdapter saveState ")
        return super.saveState()
    }


}
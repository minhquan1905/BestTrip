package com.example.minhquan.besttrip.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.login.view.Login
import com.example.minhquan.besttrip.login.view.SignUp


class ViewPagerAdapter(private var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            Login()
        } else {
            SignUp()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.login)
            1 -> context.getString(R.string.signup)
            else -> null
        }
    }


}

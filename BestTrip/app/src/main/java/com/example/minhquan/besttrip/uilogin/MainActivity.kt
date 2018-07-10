package com.example.minhquan.besttrip.uilogin

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.widget.Toast
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.login.presenter.LoginPresenter
import com.example.minhquan.besttrip.login.view.ViewItf
import kotlinx.android.synthetic.main.sign_up_fragment.*
import android.content.Intent
import com.example.minhquan.besttrip.route.RouteActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, RouteActivity::class.java))


        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open,
                R.string.close
        ){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)




    }


}

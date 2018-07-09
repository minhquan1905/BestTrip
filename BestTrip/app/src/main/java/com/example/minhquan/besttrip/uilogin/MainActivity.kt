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


class MainActivity : AppCompatActivity(),ViewItf {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        //btnSignUp.setOnClickListener {Toast.makeText(this,"abc", Toast.LENGTH_LONG).show()}

    }

    override fun Show_Login_Success(){
        Toast.makeText(this,"Login Success", Toast.LENGTH_LONG).show()
    }
    override fun Show_Login_Fail(){
        Toast.makeText(this,"Login Fail", Toast.LENGTH_LONG).show()
    }
    override fun Show_Sigin_Success(){
        Toast.makeText(this,"Sign up Success", Toast.LENGTH_LONG).show()
    }
    override fun Show_Sigin_Fail(){
        Toast.makeText(this,"Sign up Fail", Toast.LENGTH_LONG).show()
    }
}

package com.example.minhquan.besttrip.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginView: AppCompatActivity(),ViewItf {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //chueyn cho presenter
//        btn_signup.setOnClickListener { v -> LoginPresenter(this).signup(edt_mail,edt_password) }
//        btn_login.setOnClickListener {  v -> LoginPresenter(this).login(edt_mail,edt_password)  }
    }

    override fun Show_Login_Success(){
        Toast.makeText(this,"Login Success", Toast.LENGTH_LONG).show()
    }
    override fun Show_Login_Fail(){
        Toast.makeText(this,"Login Fail",Toast.LENGTH_LONG).show()
    }
    override fun Show_Sigin_Success(){
        Toast.makeText(this,"Sign up Success",Toast.LENGTH_LONG).show()
    }
    override fun Show_Sigin_Fail(){
        Toast.makeText(this,"Sign up Fail",Toast.LENGTH_LONG).show()
    }
}
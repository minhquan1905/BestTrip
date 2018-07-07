package com.example.minhquan.besttrip.login.presenter

import android.widget.EditText
import android.widget.Toast
import com.example.minhquan.besttrip.login.view.LoginView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.support.v7.app.AppCompatActivity
import android.R.attr.password
import android.R.attr.password






class LoginPresenter(var viewlogin: LoginView) : AppCompatActivity() {

    fun login(email: EditText, password: EditText) {
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                       viewlogin.Show_Login_Success()
                    } else {
                       viewlogin.Show_Login_Fail()
                    }
                }
    }

    fun signup(email:EditText, password: EditText){
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        viewlogin.Show_Sigin_Success()
                    } else {
                        viewlogin.Show_Sigin_Fail()
                    }
                }
    }

}
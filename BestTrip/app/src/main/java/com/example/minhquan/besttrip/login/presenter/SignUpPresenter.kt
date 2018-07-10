package com.example.minhquan.besttrip.login.presenter

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.besttrip.login.view.SignUp


class SignUpPresenter(var viewlogin: SignUp) : AppCompatActivity() {


    fun signUp(email: EditText, password: EditText) {
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        viewlogin.showSignUpSuccess()
                    } else {
                        viewlogin.showSignUpFail()
                    }
                }
    }

}
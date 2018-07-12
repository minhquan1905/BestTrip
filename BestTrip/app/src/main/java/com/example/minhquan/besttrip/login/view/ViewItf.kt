package com.example.minhquan.besttrip.login.view

import com.google.firebase.auth.FirebaseUser

interface ViewItf {
    interface SignUpItf{
        fun showSignUpSuccess()
        fun showSignUpFail()
    }
    interface LoginItf{
        fun showLoginSuccess()
        fun showLoginFail()

    }

}
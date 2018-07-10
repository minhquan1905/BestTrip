package com.example.minhquan.besttrip.login.view

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
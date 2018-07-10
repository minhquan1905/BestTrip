package com.example.minhquan.besttrip.login.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.login.presenter.LoginPresenter
import com.example.minhquan.besttrip.login.presenter.SignUpPresenter
import com.example.minhquan.besttrip.login.view.ViewItf
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUp : Fragment(),ViewItf.SignUpItf {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener {  SignUpPresenter(this).signup(edtEmailSignUp,edtPassWordSignUp) }

        // check null when sign up
        btnSignUp.setOnClickListener {
            var check = true
            if (TextUtils.isEmpty(edtFullName.text)){
                edtFullName.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtEmailSignUp.text)){
                edtEmailSignUp.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtPassWordSignUp.text)){
                edtPassWordSignUp.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtPhoneNumber.text)){
                edtPhoneNumber.error = "The value cannot be empty!"
                check = false
            }
            if(check)
                SignUpPresenter(this).signup(edtEmailSignUp,edtPassWordSignUp)
        }

    }

    override fun showSignUpSuccess() {
        Toast.makeText(activity,"Sign up Success", Toast.LENGTH_LONG).show()
    }

    override fun showSignUpFail() {
        Toast.makeText(activity,"Sign up Fail", Toast.LENGTH_LONG).show()
    }


}

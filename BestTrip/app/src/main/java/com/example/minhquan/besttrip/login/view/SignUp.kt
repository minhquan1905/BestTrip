package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.datafirebase.User
import com.example.minhquan.besttrip.getsetdata.presenter.SetDataPresenter
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.example.minhquan.besttrip.login.presenter.SignUpPresenter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUp : Fragment(),ViewItf.SignUpItf {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener {  SignUpPresenter(this).signUp(edtEmailSignUp,edtPassWordSignUp) }

        // check null when sign up
        btnSignUp.setOnClickListener {
            var check = true
            if (TextUtils.isEmpty(edtFullNameSignUp.text)){
                edtFullNameSignUp.error = "The value cannot be empty!"
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
            if (TextUtils.isEmpty(edtPhoneNumberSignUp.text)){
                edtPhoneNumberSignUp.error = "The value cannot be empty!"
                check = false
            }
            if(check)
                SignUpPresenter(this).signUp(edtEmailSignUp,edtPassWordSignUp)
        }

    }

    override fun showSignUpSuccess() {
        Toast.makeText(activity,"Sign up Success", Toast.LENGTH_LONG).show()
        // Create user
        SetDataPresenter().createNewUserFireBase(edtEmailSignUp, edtFullNameSignUp, edtPassWordSignUp, edtPhoneNumberSignUp)


        //Start Activity Home when Success
        val intent = Intent(context, Home::class.java)
        intent.putExtra("emailUser",edtEmailSignUp.text.toString())
        startActivity(intent)
        activity?.finish()
    }

    override fun showSignUpFail() {
        Toast.makeText(activity,"Sign up Fail", Toast.LENGTH_LONG).show()
    }


}

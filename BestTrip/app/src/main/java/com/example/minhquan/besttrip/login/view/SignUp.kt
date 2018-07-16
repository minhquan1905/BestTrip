package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.model.firebasedata.Client
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataSignUp
import com.example.minhquan.besttrip.getsetdata.presenter.SetDataSignUp
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewClientItf
import com.example.minhquan.besttrip.login.presenter.SignUpPresenter
import com.example.minhquan.besttrip.route.RouteActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUp : Fragment(),ViewItf.SignUpItf,GetDataViewClientItf {
    var emailUser : String = ""

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

    override fun showSignUpSuccess() {//....B1
        Toast.makeText(activity,"Sign up Success", Toast.LENGTH_LONG).show()
        emailUser = edtEmailSignUp.text.toString()
        // Create user
        SetDataSignUp().createNewUserFireBase(edtEmailSignUp, edtFullNameSignUp, edtPassWordSignUp, edtPhoneNumberSignUp)
        //Getdata Client from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataSignUp(this).getDataClient(database.child("Client"))//....B2
    }

    override fun showSignUpFail() {
        Toast.makeText(activity,"Sign up Fail", Toast.LENGTH_LONG).show()
    }
    override fun showDataClient(ob: Client) {//......B3
        // ShowData Client
        val user = GetDataSignUp(this).filterEmail(ob, this.emailUser)
        Log.d("DataUser",user[0].toString())

        //Start Activity Route when Success
        val intent = Intent(activity, RouteActivity::class.java)
        intent.putExtra("DataUser",user[0])
        startActivity(intent)
        activity!!.finish()
    }


}

package com.example.minhquan.besttrip.getsetdata.presenter

import android.widget.EditText
import com.example.minhquan.besttrip.datafirebase.User
import com.example.minhquan.besttrip.login.view.SignUp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SetDataPresenter() {
    fun createNewUserFireBase(edtEmail: EditText,edtFullName: EditText,edtPassWord: EditText,edtPhoneNumber: EditText){
        val database = FirebaseDatabase.getInstance().getReference()

        val user = User(edtEmail.text.toString(),null,null,edtFullName.text.toString(),
                edtPassWord.text.toString(),edtPhoneNumber.text.toString())
        database.child("Client").child(edtFullName.text.toString()).setValue(user)
    }
}
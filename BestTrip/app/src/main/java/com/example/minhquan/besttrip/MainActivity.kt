package com.example.minhquan.besttrip


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Api
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.absoluteValue
import android.R.attr.password
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import com.example.minhquan.besttrip.datafirebase.*
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.content.Intent
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.*


class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}

package com.example.minhquan.besttrip.signingoogle

import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Presenter(val view: View) : AppCompatActivity() {


    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, mAuth: FirebaseAuth?) {
        //Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth?.signInWithCredential(credential)?.
                addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        val user = mAuth.currentUser
                        Log.e("FireBase", "signInWithCredential: Success!" + user?.uid)
                        view.updateUI(user)
                    } else {
                        // Sign in fails
                        Log.w("FireBase", "signInWithCredential: Failed!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!",
                                Toast.LENGTH_SHORT).show()
                        view.updateUI(null)
                    }
                }
    }

}
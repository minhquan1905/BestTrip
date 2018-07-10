package com.example.minhquan.besttrip.login.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.minhquan.besttrip.login.view.Login
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginGooglePresenter(val view: Login) : AppCompatActivity() {


    fun fireBaseAuthWithGoogle(acct: GoogleSignInAccount?, mAuth: FirebaseAuth?) {
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
package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.login.presenter.LoginGooglePresenter
import com.example.minhquan.besttrip.login.presenter.LoginPresenter
import com.example.minhquan.besttrip.route.RouteActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_fragment.*

class Login : Fragment(),ViewItf.LoginItf,GoogleApiClient.OnConnectionFailedListener {

    private val TAG = "JSAGoogleSignIn"
    val REQUEST_CODE_SIGN_IN = 1234

    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // btnLogin.setOnClickListener {  LoginPresenter(this).login(edtEmailLogin,edtPasswordLogin) }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .enableAutoManage(FragmentActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


        mAuth= FirebaseAuth.getInstance()
        //gửi request
        sign_in_button.setOnClickListener{ signIn() }

        // check null when login
        btnLogin.setOnClickListener {
            var check = true
            if (TextUtils.isEmpty(edtEmailLogin.text)){
                edtEmailLogin.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtPasswordLogin.text)){
                edtPasswordLogin.error = "The value cannot be empty!"
                check = false
            }
            if(check)
                LoginPresenter(this).login(edtEmailLogin,edtPasswordLogin)
        }



    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
        val intent = Intent(context, RouteActivity::class.java)
        startActivity(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent();
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // successful -> authenticate with Firebase
                val account = result.signInAccount
                LoginGooglePresenter(this).fireBaseAuthWithGoogle(account,mAuth)
            } else {
                // failed -> update UI
                updateUI(null)
                Toast.makeText(context, "SignIn: failed!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(TAG, "onConnectionFailed(): $p0")
        Toast.makeText(context, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }


    override fun updateUI(user: FirebaseUser?) {
//        if (user != null) {
//            tvStatus.text = "Google User email: " + user.email
//            tvDetail.text =  "FB User ID: " + user.uid
//        } else {
//            tvStatus.text = "Signed Out"
//            tvDetail.text = null
//        }
    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    override fun showLoginSuccess() {
        Toast.makeText(activity,"Login Success", Toast.LENGTH_LONG).show()
        val intent = Intent(context, RouteActivity::class.java)
        startActivity(intent)
    }

    override fun showLoginFail() {
        Toast.makeText(activity,"Login Fail", Toast.LENGTH_LONG).show()
    }
}

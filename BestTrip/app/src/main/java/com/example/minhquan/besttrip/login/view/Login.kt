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
import com.example.minhquan.besttrip.model.datafirebase.Client
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataLogin
import com.example.minhquan.besttrip.getsetdata.presenter.SetDataLoginGoogle
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewClientItf
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.example.minhquan.besttrip.login.presenter.LoginGooglePresenter
import com.example.minhquan.besttrip.login.presenter.LoginPresenter
import com.example.minhquan.besttrip.route.RouteActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login_fragment.*

class Login : Fragment(),ViewItf.LoginItf,GoogleApiClient.OnConnectionFailedListener, GetDataViewClientItf {

    private val TAG = "JSAGoogleSignIn"
    val REQUEST_CODE_SIGN_IN = 1234

    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    //emailUser use to filter User from Client
    var emailUser : String = ""

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
        // Google button
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

    // Login with Google button
    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
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
                //Create User
                SetDataLoginGoogle().createNewUserFireBase(account?.email.toString(),account?.displayName.toString())
                //Getdata Client from FireBase
                emailUser = account?.email.toString()
                val database = FirebaseDatabase.getInstance().reference
                GetDataLogin(this).getDataClient(database.child("Client"))

            } else {
                // failed -> update UI
                Toast.makeText(context, "SignIn: failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(TAG, "onConnectionFailed(): $p0")
        Toast.makeText(context, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }

    override fun showLoginSuccess() {
        Toast.makeText(activity,"Login Success", Toast.LENGTH_LONG).show()
        emailUser = edtEmailLogin.text.toString()
        //Getdata Client from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataLogin(this).getDataClient(database.child("Client"))
    }
    override fun showLoginFail() {
        Toast.makeText(activity,"Login Fail", Toast.LENGTH_LONG).show()
    }

    override fun showDataClient(ob: Client){
        // ShowData Client
        val user = GetDataLogin(this).filterEmail(ob, this.emailUser)
        Log.d("DataUser",user[0].toString())

        val intent = Intent(activity, RouteActivity::class.java)
        intent.putExtra("DataUser",user[0])
        startActivity(intent)
        activity?.finish()
    }
}

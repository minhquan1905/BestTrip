package com.example.minhquan.besttrip.signingoogle

import com.google.firebase.auth.FirebaseUser

interface ViewItf {
    fun updateUI(user: FirebaseUser?)
}
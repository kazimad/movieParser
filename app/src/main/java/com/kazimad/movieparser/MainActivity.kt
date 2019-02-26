package com.kazimad.movieparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.facebook.CallbackManager






class MainActivity : AppCompatActivity() {
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loginFragment = LoginFragment()
        loginFragment.setCallLoginCallBack(callbackManager)
        ActivityUtils.addFragmentToActivity(this, loginFragment)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}

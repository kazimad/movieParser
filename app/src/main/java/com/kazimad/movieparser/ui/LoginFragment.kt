package com.kazimad.movieparser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R


class LoginFragment : Fragment(), MainInterface {

    private lateinit var activityContext: MainInterface
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = fragmentView.findViewById(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.fragment = this

        callbackManager?.let {
            loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                }

                override fun onCancel() {

                }

                override fun onError(exception: FacebookException) {

                }
            })
        }
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as MainInterface)
    }
}
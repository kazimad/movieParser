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
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.kazimad.movieparser.MainInterface
import com.kazimad.movieparser.R
import com.kazimad.movieparser.utils.Logger


class LoginFragment : Fragment(), MainInterface {

    private lateinit var activityContext: MainInterface
    private val facebookCallbackManager = CallbackManager.Factory.create()!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = fragmentView.findViewById(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.fragment = this


        LoginManager.getInstance().registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Logger.log("LoginFragment onSuccess ${result?.accessToken}")

            }

            override fun onCancel() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(error: FacebookException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
//        loginButton.registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//
//            }
//
//            override fun onCancel() {
//
//            }
//
//            override fun onError(exception: FacebookException) {
//
//            }
//        })
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        activityContext.onUserLoggedIn()
        Logger.log("LoginFragment onActivityResult Profile.getCurrentProfile() is ${Profile.getCurrentProfile()}")


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as MainInterface)
    }
}
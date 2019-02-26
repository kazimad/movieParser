package com.kazimad.movieparser

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
import android.content.Intent




class LoginFragment : Fragment() {

    private lateinit var loginButton: com.facebook.login.widget.LoginButton
    private var loginManager: CallbackManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_login, container, false)


        loginButton = fragmentView.findViewById(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.fragment = this

        loginManager?.let {
            loginButton.registerCallback(loginManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Logger.log("LoginFragment onSuccess ")
                    System.out.println("LoginFragment onSuccess")
                }

                override fun onCancel() {
                    Logger.log("LoginFragment onCancel ")
                    System.out.println("LoginFragment onCancel")

                }

                override fun onError(exception: FacebookException) {
                    Logger.log("LoginFragment onError ${exception.message}")
                    System.out.println("LoginFragment onError ${exception.message}")

                }
            })
        }
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginManager?.onActivityResult(requestCode, resultCode, data)
    }
    fun setCallLoginCallBack(loginManager: CallbackManager) {
        this.loginManager = loginManager
    }
}
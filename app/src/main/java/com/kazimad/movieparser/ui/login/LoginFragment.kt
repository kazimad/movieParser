package com.kazimad.movieparser.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.kazimad.movieparser.InterfaceActivity
import com.kazimad.movieparser.InterfaceFragment
import com.kazimad.movieparser.R
import com.kazimad.movieparser.utils.Logger
import com.facebook.FacebookException
import com.facebook.Profile.getCurrentProfile
import com.facebook.ProfileTracker




class LoginFragment : Fragment() {

    private lateinit var activityContext: InterfaceActivity
    private val facebookCallbackManager = CallbackManager.Factory.create()!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = (context as InterfaceActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = fragmentView.findViewById(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.fragment = this

        LoginManager.getInstance().registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
            private var mProfileTracker: ProfileTracker? = null
            override fun onSuccess(loginResult: LoginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                            mProfileTracker?.stopTracking()

                        }
                    }
                }
            }

            override fun onCancel() {
            }

            override fun onError(e: FacebookException) {
            }
        })
        return fragmentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (facebookCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return
        }
    }
}
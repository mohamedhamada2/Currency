package com.example.currency.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.currency.databinding.ActivityLoginBinding
import com.example.currency.databinding.FragmentLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var callBackManager: CallbackManager
    lateinit var fragmentLoginBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)
        callBackManager = CallbackManager.Factory.create()
        // Inflate the layout for this fragment
        fragmentLoginBinding.loginButton.setReadPermissions(listOf("email","public_profile","user_gender","user_birthday"))
        fragmentLoginBinding.loginButton.registerCallback(callBackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() { // this method is invoked when the request is cancelled
                Toast.makeText(requireContext(),
                    "Cancelled",
                    Toast.LENGTH_SHORT).show() }

            override fun onError(error: FacebookException) {
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show() }
            //Incase of an error, the above message is displayed.
            override fun onSuccess(result: LoginResult) {
                val graphRequest = GraphRequest.newMeRequest(result?.accessToken){ jsonobject, _ ->
                    getFacebookData(jsonobject)
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,email,birthday,gender,name")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            } })
        return fragmentLoginBinding.root
    }

    private fun getFacebookData(jsonObject: JSONObject?) {
        val profilePic = "https://graph.facebook.com/${jsonObject
            ?.getString("id")}/picture?width=500&height=500"
        val name = jsonObject?.getString("name")
        val birthday = jsonObject?.getString("birthday")
        val gender = jsonObject?.getString("gender")
        val email = jsonObject?.getString("email")
        fragmentLoginBinding.userName.text = "Name: ${name}"
        fragmentLoginBinding.userEmail.text = "Email: ${email}"
        fragmentLoginBinding.userBDay.text = "Birthday: ${birthday}"
        fragmentLoginBinding.userGender.text = "Gender: ${gender}"
    }
}
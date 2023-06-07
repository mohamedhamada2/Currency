package com.example.currency.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.currency.R
import com.example.data.language.LocaleHelper
import com.example.currency.databinding.FragmentRegisterationBinding
import com.example.currency.view.main.MainActivity
import com.example.currency.viewmodel.auth.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class RegisterationFragment : Fragment() {
    lateinit var fragmentRegisterationBinding: FragmentRegisterationBinding
    private  val registerViewModel: RegisterViewModel by viewModels()
    lateinit var name:String
    lateinit var email:String
    lateinit var password:String
    lateinit var phone:String
    lateinit var language:String
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegisterationBinding = FragmentRegisterationBinding.inflate(inflater, container, false)
        fragmentRegisterationBinding.btnRegister.setOnClickListener(View.OnClickListener {
            validate()
        })
        registerViewModel.success_save_MutableLiveData.observe(viewLifecycleOwner, Observer {
            success_save(it)
        })
        fragmentRegisterationBinding.facebookImg.setOnClickListener {
            fragmentRegisterationBinding.root.findNavController().navigate(R.id.action_registerationFragment_to_loginFragment)
        }
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("233535470637-6epka0s4sa3vc6j7j3sild6jq6mano8s.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        fragmentRegisterationBinding.googleSignIn.setOnClickListener { // Initialize sign in intent
            val intent: Intent = googleSignInClient.signInIntent
            // Start activity for result
            startActivityForResult(intent, 100)
        }
        firebaseAuth = FirebaseAuth.getInstance()
        // Initialize firebase user
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
            startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        LoadLocal()
        return fragmentRegisterationBinding.root
    }

    private fun LoadLocal() {
        val sharedPreferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences?.getString("language","")!!
        LocaleHelper.setLocale(requireContext(), language)
        fragmentRegisterationBinding.txtSignUp.text = getString(R.string.sign_up);
        fragmentRegisterationBinding.etEmail.hint = getString(R.string.user_email);
        fragmentRegisterationBinding.etName.hint = getString(R.string.name);
        fragmentRegisterationBinding.etPhone.hint = getString(R.string.phone);
        fragmentRegisterationBinding.etPassword.hint = getString(R.string.user_password);
        fragmentRegisterationBinding.btnRegister.text = getString(R.string.sign_up);
    }

    private fun success_save(it: Boolean?) {
        if (it == true){
            Toast.makeText(requireContext(),"تم تسجيلك بنجاح", Toast.LENGTH_LONG).show()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validate() {
        name = fragmentRegisterationBinding.etName.text.toString()
        email = fragmentRegisterationBinding.etEmail.text.toString()
        phone = fragmentRegisterationBinding.etPhone.text.toString()
        password = fragmentRegisterationBinding.etPassword.text.toString()
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
            registerViewModel.save_data(name,phone,email,password,"")
            /*val userModel = UserModel(name,phone,email,password)
            userSharedPreferance.Create_Update_UserData(this,userModel)
            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
            startActivity(intent)*/
        }else{
            if (TextUtils.isEmpty(name)){
                fragmentRegisterationBinding.etName.error = "أدخل الإسم لو سمحت"
            }else{
                fragmentRegisterationBinding.etName.error = null
            }
            if (TextUtils.isEmpty(email)){
                fragmentRegisterationBinding.etEmail.error = "أدخل البريد الإلكتروني"
            }else{
                fragmentRegisterationBinding.etEmail.error = null
            }
            if (TextUtils.isEmpty(phone)){
                fragmentRegisterationBinding.etPhone.error = "أدخل رقم الهاتف"
            }else{
                fragmentRegisterationBinding.etPhone.error = null
            }
            if (TextUtils.isEmpty(password)){
                fragmentRegisterationBinding.etPassword.error = "أدخل كلمة المرور"
            }else{
                fragmentRegisterationBinding.etPassword.error = null
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            // check condition
            if (signInAccountTask.isSuccessful) {
                // When google sign in successful initialize string
                val s = "Google sign in successful"
                // Display Toast
                displayToast(s)
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    // Check condition
                    if (googleSignInAccount != null) {
                        registerViewModel.save_data(googleSignInAccount.displayName,googleSignInAccount.familyName,googleSignInAccount.email,"",googleSignInAccount.photoUrl.toString())
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                            googleSignInAccount.idToken, null
                        )
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(requireActivity()) { task ->
                                // Check condition
                                if (task.isSuccessful) {
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            MainActivity::class.java
                                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    )
                                    // Display Toast
                                    displayToast("Firebase authentication successful")
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast(
                                        "Authentication Failed :" + task.exception!!.message
                                    )
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun displayToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }
}
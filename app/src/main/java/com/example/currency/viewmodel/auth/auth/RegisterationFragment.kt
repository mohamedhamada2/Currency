package com.example.currency.viewmodel.auth.auth

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
import com.example.currency.data.language.LocaleHelper
import com.example.currency.databinding.FragmentRegisterationBinding
import com.example.currency.view.main.MainActivity
import com.example.currency.viewmodel.auth.RegisterViewModel
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
            registerViewModel.save_data(name,phone,email,password)
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
}
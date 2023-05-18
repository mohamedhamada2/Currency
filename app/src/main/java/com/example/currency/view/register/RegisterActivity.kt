package com.example.currency.view.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currency.R
import com.example.currency.data.language.LocaleHelper
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserSharedPreferance
import com.example.currency.databinding.ActivityRegisterBinding
import com.example.currency.view.main.MainActivity
import com.example.currency.viewmodel.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    lateinit var activityRegisterBinding: ActivityRegisterBinding
    private  val registerViewModel: RegisterViewModel by viewModels()
    lateinit var name:String
    lateinit var email:String
    lateinit var password:String
    lateinit var phone:String
    lateinit var language:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        setContentView(activityRegisterBinding.root)
        activityRegisterBinding.registerviewmodel = registerViewModel
        activityRegisterBinding.btnRegister.setOnClickListener(View.OnClickListener {
            validate()
        })
        registerViewModel.success_save_MutableLiveData.observe(this, Observer {
            success_save(it)
        })
        LoadLocal()
        /*registerViewModel.languageMutableLiveData.observe(this){
            init_language(it)
        }*/

    }


    private fun success_save(it: Boolean?) {
        if (it == true){
            Toast.makeText(this,"تم تسجيلك بنجاح",Toast.LENGTH_LONG).show()
            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validate() {
        name = activityRegisterBinding.etName.text.toString()
        email = activityRegisterBinding.etEmail.text.toString()
        phone = activityRegisterBinding.etPhone.text.toString()
        password = activityRegisterBinding.etPassword.text.toString()
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
            registerViewModel.save_data(name,phone,email,password)
            /*val userModel = UserModel(name,phone,email,password)
            userSharedPreferance.Create_Update_UserData(this,userModel)
            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
            startActivity(intent)*/
        }else{
            if (TextUtils.isEmpty(name)){
                activityRegisterBinding.etName.error = "أدخل الإسم لو سمحت"
            }else{
                activityRegisterBinding.etName.error = null
            }
            if (TextUtils.isEmpty(email)){
                activityRegisterBinding.etEmail.error = "أدخل البريد الإلكتروني"
            }else{
                activityRegisterBinding.etEmail.error = null
            }
            if (TextUtils.isEmpty(phone)){
                activityRegisterBinding.etPhone.error = "أدخل رقم الهاتف"
            }else{
                activityRegisterBinding.etPhone.error = null
            }
            if (TextUtils.isEmpty(password)){
                activityRegisterBinding.etPassword.error = "أدخل كلمة المرور"
            }else{
                activityRegisterBinding.etPassword.error = null
            }
        }
    }
    private fun LoadLocal() {
        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences.getString("language","")!!
        LocaleHelper.setLocale(this, language)
        activityRegisterBinding.txtSignUp.text = getString(R.string.sign_up);
        activityRegisterBinding.etEmail.hint = getString(R.string.user_email);
        activityRegisterBinding.etName.hint = getString(R.string.name);
        activityRegisterBinding.etPhone.hint = getString(R.string.phone);
        activityRegisterBinding.etPassword.hint = getString(R.string.user_password);
        activityRegisterBinding.btnRegister.text = getString(R.string.sign_up);
    }
}
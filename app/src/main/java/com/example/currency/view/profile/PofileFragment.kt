package com.example.currency.view.profile

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.currency.R
import com.example.currency.data.language.LocaleHelper
import com.example.currency.data.models.user.UserModel
import com.example.currency.databinding.FragmentPofileBinding
import com.example.currency.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class PofileFragment : Fragment() {
    lateinit var fragmentPofileBinding: FragmentPofileBinding
    lateinit var language: String
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //userSharedPreferance = UserSharedPreferance().getInstance()!!
        fragmentPofileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_pofile,container,false)
        fragmentPofileBinding.profileviewmodel = profileViewModel
        fragmentPofileBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_english -> setLocale("en")
                R.id.rb_arabic -> setLocale("ar")
            }

        }
        observeDataFromViewModel()
        //loadLocale()
        return fragmentPofileBinding.root
    }

    private fun loadLocale() {
        val sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences.getString("language","")!!
        if (language == "ar"){
            fragmentPofileBinding.rbArabic.isChecked = true

        }else{
            try {
                fragmentPofileBinding.rbEnglish.isChecked = true
            }catch (e:Exception){
                e.message?.let { Log.d("llll", it) }
            }

        }
    }

    private fun observeDataFromViewModel() {
        profileViewModel.userdata_MutableLiveData.observe(viewLifecycleOwner) {
            setData(it)
        }
        profileViewModel.languageMutableLiveData.observe(viewLifecycleOwner){
            //updateview(it)
        }
    }

    private fun setData(userModel: UserModel?) {
        fragmentPofileBinding.etName.setText(userModel!!.user_name)
        fragmentPofileBinding.etEmail.setText(userModel.user_email)
        fragmentPofileBinding.etPassword.setText(userModel.user_password)
        fragmentPofileBinding.etPhone.setText(userModel.user_phone)
        //Toast.makeText(requireContext(), userModel!!.user_name,Toast.LENGTH_LONG).show()
    }
    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config: Configuration = requireActivity().baseContext.resources.configuration
        config.locale = locale
        requireActivity().baseContext.resources.updateConfiguration(
            config, requireActivity().baseContext.resources.displayMetrics)
        val sharedPreference =  requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("language",language)
        editor.apply()
        //requireActivity().recreate()
        Toast.makeText(requireContext(),language,Toast.LENGTH_LONG).show()
        requireActivity().recreate()
        fragmentPofileBinding.root.findNavController().navigate(R.id.action_pofileFragment_self)
        /*fragmentPofileBinding.btnRegister.text = getString(R.string.edit_profile)
        fragmentPofileBinding.txtLanguage.text = getString(R.string.select_lang)
        fragmentPofileBinding.txtEdit.text = getString(R.string.edit_profile)*/


       /* Paper.book().write("language",language);
        updateview(Paper.book().read("language")!!)*/
        //restartActivity()
        //fragmentPofileBinding.root.findNavController().navigate(R.id.action_pofileFragment_self)
        //Toast.makeText(getActivity(), "ar-eEG", Toast.LENGTH_SHORT).show();
    }


    private fun restartActivity() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }
}

package com.example.currency.view.profile

import android.os.Bundle
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
import io.paperdb.Paper
import java.util.*


@AndroidEntryPoint
class PofileFragment : Fragment() {
    lateinit var fragmentPofileBinding: FragmentPofileBinding
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
        return fragmentPofileBinding.root
    }

    private fun observeDataFromViewModel() {
        profileViewModel.userdata_MutableLiveData.observe(viewLifecycleOwner) {
            setData(it)
        }
        profileViewModel.languageMutableLiveData.observe(viewLifecycleOwner){
            updateview(it)
        }
    }

    private fun setData(userModel: UserModel?) {
        fragmentPofileBinding.etName.setText(userModel!!.user_name)
        fragmentPofileBinding.etEmail.setText(userModel!!.user_email)
        fragmentPofileBinding.etPassword.setText(userModel!!.user_password)
        fragmentPofileBinding.etPhone.setText(userModel!!.user_phone)
        //Toast.makeText(requireContext(), userModel!!.user_name,Toast.LENGTH_LONG).show()
    }
    private fun setLocale(language: String) {
        Paper.book().write("language",language);
        updateview(Paper.book().read("language")!!)
        //restartActivity()
        //fragmentPofileBinding.root.findNavController().navigate(R.id.action_pofileFragment_self)
        //Toast.makeText(getActivity(), "ar-eEG", Toast.LENGTH_SHORT).show();
    }

    private fun updateview(language: String) {
        val context = LocaleHelper.setLocale(requireContext(), language);
        val resources = context?.resources
        if (resources != null) {
            fragmentPofileBinding.btnRegister.text = resources.getString(R.string.edit_profile)
            fragmentPofileBinding.txtLanguage.text = resources.getString(R.string.select_lang)
            fragmentPofileBinding.txtEdit.text = resources.getString(R.string.edit_profile)
            if (language == "ar"){
                fragmentPofileBinding.rbArabic.isChecked = true
            }else{
                fragmentPofileBinding.rbEnglish.isChecked = true
            }
        }

    }

    private fun restartActivity() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }
}

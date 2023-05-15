package com.example.currency.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currency.R
import com.example.currency.data.models.user.UserModel
import com.example.currency.data.models.user.UserSharedPreferance
import com.example.currency.databinding.FragmentPofileBinding
import com.example.currency.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        profileViewModel.userdata_MutableLiveData.observe(viewLifecycleOwner) {
            setData(it)
        }
        return fragmentPofileBinding.root
    }

    private fun setData(userModel: UserModel?) {
        fragmentPofileBinding.etName.setText(userModel!!.user_name)
        fragmentPofileBinding.etEmail.setText(userModel!!.user_email)
        fragmentPofileBinding.etPassword.setText(userModel!!.user_password)
        fragmentPofileBinding.etPhone.setText(userModel!!.user_phone)
        //Toast.makeText(requireContext(), userModel!!.user_name,Toast.LENGTH_LONG).show()
    }
}
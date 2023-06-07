package com.example.currency.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.currency.R
import com.example.domain.entity.user.UserModel
import com.example.data.local.UserSharedPreferance
import com.example.currency.databinding.FragmentSplashBinding
import com.example.currency.view.main.MainActivity
import com.example.currency.viewmodel.auth.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    lateinit var fragmentSplashBinding: FragmentSplashBinding
    private  val  splashViewModel: SplashViewModel by viewModels()
    var userModel: UserModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSplashBinding =  FragmentSplashBinding.inflate(inflater, container, false)
        splashViewModel.UserSharedPreferanceMutableLiveData.observe(viewLifecycleOwner, Observer {
            setSharedPreferance(it)
        })
        return fragmentSplashBinding.root

    }

    private fun setSharedPreferance(userSharedPreferance: UserSharedPreferance?) {
        userModel = userSharedPreferance?.Get_UserData(requireContext())
        Handler().postDelayed(Runnable { fragmentSplashBinding.logo.animate().rotation(360f).duration =
            1000 }, 2000)
        Handler().postDelayed(Runnable {
            if (userModel != null) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                fragmentSplashBinding.root.findNavController().navigate(R.id.action_splashFragment_to_registerationFragment)
            }
        }, 3000)
    }
}
package com.example.currency.view.gallery

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.currency.R
import com.example.data.language.LocaleHelper
import com.example.currency.databinding.FragmentImagesBinding
import com.example.currency.viewmodel.gallery.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ImagesFragment : Fragment() {

    lateinit var fragmentImagesBinding: FragmentImagesBinding
    lateinit var search:String
    lateinit var layoutManager: GridLayoutManager
    private var isloading = false
    private var pastvisibleitem = 0
    private  var visibleitemcount = 0
    private  var totalitemcount= 0
    private  var previous_total = 0
    var view_threshold  = 15
    var page = 1
    lateinit var language: String
    private val imagesViewModel: ImagesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentImagesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        val view = fragmentImagesBinding.root;
        //homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        //homeViewModel = HomeViewModel(requireContext(),this)
        //(activity?.application as MyApplication).getAppComponent()!!.inject(this)
        layoutManager = GridLayoutManager(activity,2)
        LoadLocal()

        /*fragmentHomeBinding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })*/
        fragmentImagesBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search = p0.toString();
                page = 1
                pastvisibleitem = 0
                visibleitemcount = 0
                totalitemcount= 0
                previous_total = 0
                view_threshold  = 15
                //search = fragmentHomeBinding.etSearch.text.toString()
                imagesViewModel.search_in_gallery(search,page)
                fragmentImagesBinding.imagesRecycler.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        visibleitemcount = layoutManager.childCount
                        totalitemcount = layoutManager.itemCount
                        pastvisibleitem = layoutManager.findFirstVisibleItemPosition()
                        if (dy>0) {
                            if (isloading) {
                                if (totalitemcount > previous_total) {
                                    isloading = false
                                    previous_total = totalitemcount
                                }
                            }
                            if (!isloading && totalitemcount - visibleitemcount <= pastvisibleitem + view_threshold) {
                                //HomeViewModelModule.provide_search(search)
                                page++
                                imagesViewModel.load_more_search_in_gallery(search,page)
                                isloading = true
                            }
                        }else{
                        }
                    }
                })
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        /*homeViewModel.galleryMutableLiveData.observe(viewLifecycleOwner, Observer {

            setRecyclerView(it.photos)
        })*/
        imagesViewModel.galleryadapterLiveData.observe(viewLifecycleOwner, Observer {
            setRecyclerView(it)
        })
        imagesViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it == 1){
                fragmentImagesBinding.progressBar.visibility= View.VISIBLE
            }else{
                fragmentImagesBinding.progressBar.visibility= View.INVISIBLE
            }
        })

        return view
    }


    private fun setRecyclerView(imagesAdapter: ImagesAdapter?) {
        fragmentImagesBinding.imagesRecycler.setHasFixedSize(true)
        fragmentImagesBinding.imagesRecycler.layoutManager = layoutManager
        fragmentImagesBinding.imagesRecycler.adapter = imagesAdapter
    }
    private fun LoadLocal() {
        val sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        language = sharedPreferences.getString("language","")!!
        LocaleHelper.setLocale(requireContext(),language)
        fragmentImagesBinding.etSearch.hint = getString(R.string.search)
        fragmentImagesBinding.txtAlboum.hint = getString(R.string.alboum)
    }

    private fun setLocal(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config: Configuration = requireActivity().baseContext.resources.configuration
        config.locale = locale
        requireActivity().baseContext.resources.updateConfiguration(
            config,requireActivity().baseContext.resources.displayMetrics)
    }

}
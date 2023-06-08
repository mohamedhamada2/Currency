package com.example.currency.viewmodel.gallery

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.constants.Constants
import com.example.currency.view.gallery.ImagesAdapter
import com.example.domain.usecase.gallery.GetGallery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ImagesViewModel @Inject constructor(private val getGallery: GetGallery, @Named("network_connection")val connect_network:Boolean):ViewModel() {
    var galleryadapterLiveData: MutableLiveData<ImagesAdapter> = MutableLiveData<ImagesAdapter>()
    var loading: MutableLiveData<Int> = MutableLiveData<Int>()
    lateinit var imagesAdapter: ImagesAdapter
    private var observable: Observable<com.example.domain.entity.gallery.GalleryModel> = BehaviorSubject.create()

    @SuppressLint("CheckResult")
    fun search_in_gallery(search: String, page: Int) {
        if (connect_network){
            observable.debounce(3,TimeUnit.SECONDS)
            observable = getGallery.get_gallery(Constants.gallery_key, search, page)
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())

            observable.subscribe(
                { o: com.example.domain.entity.gallery.GalleryModel? ->
                    if (o != null) {
                        if (!o.photos.isEmpty()) {
                            setAdapter(o)
                            loading.value = 1

                        } else {
                            setAdapter(o)
                            imagesAdapter = ImagesAdapter(o.photos as ArrayList<com.example.domain.entity.gallery.Photo>)
                            loading.value = 0

                        }

                        //homeFragment.setRecyclerView(imagesAdapter)
                        //load_more_search_in_gallery()
                        //imagesAdapter.addLoadingFooter(o.photos);

                    }
                },
                { e: Throwable -> Log.e("rrrr", e.message.toString()) })
        }

    }

    private fun setAdapter(o: com.example.domain.entity.gallery.GalleryModel) {
        imagesAdapter = ImagesAdapter(o.photos as ArrayList<com.example.domain.entity.gallery.Photo>)
        galleryadapterLiveData.value = imagesAdapter
    }

    @SuppressLint("CheckResult")
    fun load_more_search_in_gallery(search: String, page: Int) {
        if (connect_network){
            observable = getGallery.get_gallery(Constants.key, search, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            observable.subscribe(
                { o: com.example.domain.entity.gallery.GalleryModel? ->
                    if (o != null) {
                        if (!o.photos.isEmpty()) {
                            add_photo(o)
                            loading.value = 1
                        } else {
                            loading.value = 0
                        }

                    }
                },
                { e: Throwable -> Log.e("rrrr", e.message.toString()) })
        }
    }

    private fun add_photo(o: com.example.domain.entity.gallery.GalleryModel) {
        imagesAdapter.add_photo(o.photos);
    }
}

/*fun PerformPagination(search: String, page: Int, api: Api) {
    val observable: Observable<GalleryModel> = api.get_gallery(Constants.key,search,page)
        .subscribeOn(Schedulers.io())
        .debounce(6,TimeUnit.SECONDS)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
    observable.subscribe(
        { o: GalleryModel? -> if (o!!.page<= o.total_results){
            galleryMutableLiveData.value = o
        }
        },
        { e: Throwable -> Log.e("mmmm", e.message.toString()) })
}*/
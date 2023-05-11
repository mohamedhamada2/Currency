package com.example.currency.images

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.constants.Constants
import com.example.currency.main.CurrencyRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ImagesViewModel @Inject constructor(var currencyRepositoryImp: CurrencyRepositoryImp,@Named("network_connection")var connect_network:Boolean):ViewModel() {
    var galleryadapterLiveData: MutableLiveData<ImagesAdapter> = MutableLiveData<ImagesAdapter>()
    var loading: MutableLiveData<Int> = MutableLiveData<Int>()
    lateinit var imagesAdapter: ImagesAdapter

    private var observable: Observable<GalleryModel> = BehaviorSubject.create()


    @SuppressLint("CheckResult")
    fun search_in_gallery(search: String, page: Int) {
        if (connect_network){
            observable.debounce(3,TimeUnit.SECONDS)
            observable = currencyRepositoryImp.get_gallery(Constants.gallery_key, search, page)
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())

            observable.subscribe(
                { o: GalleryModel? ->
                    if (o != null) {
                        if (!o.photos.isEmpty()) {
                            setAdapter(o)

                            loading.value = 1

                        } else {
                            setAdapter(o)
                            imagesAdapter = ImagesAdapter(o.photos as ArrayList<Photo>)
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

    private fun setAdapter(o: GalleryModel) {
        imagesAdapter = ImagesAdapter(o.photos as ArrayList<Photo>)
        galleryadapterLiveData.value = imagesAdapter
    }

    fun load_more_search_in_gallery(search: String, page: Int) {
        if (connect_network){
            observable = currencyRepositoryImp.get_gallery(Constants.key, search, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            observable.subscribe(
                { o: GalleryModel? ->
                    if (o != null) {
                        if (!o.photos.isEmpty()) {
                            //Toast.makeText(mainActivity,currentPage.toString(),Toast.LENGTH_LONG).show()
                            //galleryMutableLiveData.value = o
                            imagesAdapter.add_photo(o.photos);
                            loading.value = 1
                            //galleryadapterLiveData.value = imagesAdapter
                            //imagesAdapter.addLoadingFooter(o.photos);
                        } else {
                            loading.value = 0
                        }

                    }
                },
                { e: Throwable -> Log.e("rrrr", e.message.toString()) })
        }
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
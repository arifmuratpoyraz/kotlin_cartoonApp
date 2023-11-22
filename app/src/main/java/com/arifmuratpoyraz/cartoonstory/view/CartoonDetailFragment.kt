package com.arifmuratpoyraz.cartoonstory.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arifmuratpoyraz.cartoonstory.R
import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import com.arifmuratpoyraz.cartoonstory.service.CartoonAPIService
import com.arifmuratpoyraz.cartoonstory.util.createPlaceHolder
import com.arifmuratpoyraz.cartoonstory.util.downloadImage
import com.arifmuratpoyraz.cartoonstory.viewmodel.CartoonDetailViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CartoonDetailFragment : Fragment() {

    private lateinit var viewModel: CartoonDetailViewModel
    private lateinit var cartoonDetailSafeTitle : TextView
    private lateinit var cartoonDetailAlt : TextView
    private lateinit var cartoonDetailTitle : TextView
    private lateinit var cartoonDetailYear : TextView
    private lateinit var cartoonDetailImage : ImageView
    private lateinit var cartoonDetailTranscript : TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartoon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartoonDetailSafeTitle = view.findViewById(R.id.cartoonDetailSafeTitle)
        cartoonDetailAlt = view.findViewById(R.id.cartoonDetailAlt)
        cartoonDetailTitle = view.findViewById(R.id.cartoonDetailTitle)
        cartoonDetailYear = view.findViewById(R.id.cartoonDetailYear)
        cartoonDetailImage = view.findViewById(R.id.cartoonDetailImage)
        cartoonDetailTranscript = view.findViewById(R.id.cartoonDetailTranscript)


        viewModel = ViewModelProvider(this).get(CartoonDetailViewModel::class.java)


        observeLiveData()
        viewModel.getData()
    }

    fun observeLiveData(){

        viewModel.cartoon.observe(viewLifecycleOwner, Observer {

            cartoonDetailSafeTitle.text = it.safe_title
            cartoonDetailTranscript.text = it.transcript
            cartoonDetailAlt.text = it.alt
            cartoonDetailTitle.text = it.title
            cartoonDetailYear.text = it.year
            cartoonDetailImage.downloadImage(it.img, createPlaceHolder(requireContext()))


        })

    }


}
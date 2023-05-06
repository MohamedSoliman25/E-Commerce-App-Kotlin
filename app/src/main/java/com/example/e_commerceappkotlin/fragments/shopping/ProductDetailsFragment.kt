package com.example.e_commerceappkotlin.fragments.shopping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.SpacingDecorator.HorizantalSpacingItemDecorator
import com.example.e_commerceappkotlin.adapters.ColorsAdapter
import com.example.e_commerceappkotlin.adapters.SizesAdapter
import com.example.e_commerceappkotlin.adapters.ViewPager2Images
import com.example.e_commerceappkotlin.data.Product
import com.example.e_commerceappkotlin.databinding.FragmentProductDetailsBinding
import com.example.e_commerceappkotlin.util.Constants.COLORS_TYPE
import com.example.e_commerceappkotlin.util.Constants.SIZES_TYPE
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.vejei.viewpagerindicator.indicator.CircleIndicator

class ProductDetailsFragment :Fragment() {

    val args by navArgs<ProductDetailsFragmentArgs>()
    val TAG = "ProductPreviewFragment"

    private lateinit var binding: FragmentProductDetailsBinding
    private val colorsAdapter by lazy { ColorsAdapter() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private  val viewPagerAdapter by lazy { ViewPager2Images() }
//    private lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = (activity as ShoppingActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.visibility = View.GONE

        val product = args.product

        setupViewpager()
        setupColorsRecyclerview()
        setupSizesRecyclerview()


        setProductInformation(product)

        onImageCloseClick()
//        onBtnAddToCartClick()

//        observeAddToCart()

        onColorClick()
        onSizeClick()
    }

    private var selectedSize: String = ""
    private fun onSizeClick() {
        sizesAdapter.onItemClick = { size ->
            selectedSize = size
            binding.tvSizeError.visibility = View.INVISIBLE

        }
    }

    private var selectedColor: Int = 0
    private fun onColorClick() {
        colorsAdapter.onItemClick = { color ->
            selectedColor = color
            binding.tvColorError.visibility = View.INVISIBLE
        }
    }


//    private fun observeAddToCart() {
//        viewModel.addToCart.observe(viewLifecycleOwner, Observer { response ->
//            val btn = binding.btnAddToCart
//            when (response) {
//                is Resource.Loading -> {
//                    startLoading()
//                    return@Observer
//                }
//
//                is Resource.Success -> {
//                    stopLoading()
//                    viewModel.addToCart.value = null
//                    return@Observer
//                }
//
//                is Resource.Error -> {
//                    Toast.makeText(activity, "Oops! error occurred", Toast.LENGTH_SHORT).show()
//                    viewModel.addToCart.value = null
//                    Log.e(TAG, response.message.toString())
//                }
//            }
//        })
//    }

    private fun stopLoading() {
        binding.apply {
            btnAddToCart.visibility = View.VISIBLE
            progressbar.visibility = View.INVISIBLE
        }
    }

    private fun startLoading() {
        binding.apply {
            btnAddToCart.visibility = View.INVISIBLE
            progressbar.visibility = View.VISIBLE
        }
    }


//    private fun onBtnAddToCartClick() {
//        binding.btnAddToCart.apply {
//            setOnClickListener {
//
//                if (selectedColor.isEmpty()) {
//                    binding.tvColorError.visibility = View.VISIBLE
//                    return@setOnClickListener
//                }
//
//                if (selectedSize.isEmpty()) {
//                    binding.tvSizeError.visibility = View.VISIBLE
//                    return@setOnClickListener
//                }
//
//                val product = args.product
//                val image = (product.images?.get(IMAGES) as List<String>)[0]
//                val cartProduct = CartProduct(
//                    product.id,
//                    product.title!!,
//                    product.seller!!,
//                    image,
//                    product.price!!,
//                    product.newPrice,
//                    1,
//                    selectedColor,
//                    selectedSize
//                )
//                viewModel.addProductToCart(cartProduct)
//                setBackgroundResource(R.color.g_black)
//            }
//        }
//    }

    private fun onImageCloseClick() {
        binding.imgClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setProductInformation(product: Product) {
        val imagesList = product.images
        val colors = product.colors!!
        val sizes = product.sizes!!
        binding.apply {
            viewPagerAdapter.differ.submitList(imagesList)
            if (colors.isNotEmpty())
                colorsAdapter.differ.submitList(colors)
            if (sizes.isNotEmpty() && sizes[0] != "")
                sizesAdapter.differ.submitList(sizes)
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvProductPrice.text = "$${product.price}"
            tvProductOfferPrice.visibility = View.GONE
//            product.newPrice?.let {
//                if (product.newPrice.isNotEmpty() && product.newPrice != "0") {
//                    tvProductPrice.paintFlags =
//                        tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    tvProductOfferPrice.text = "$${product.newPrice}"
//                    tvProductOfferPrice.visibility = View.VISIBLE
//                }
//            }

//            product.sizes?.let {
//                if (it.isNotEmpty()) {
//                    binding.tvSizeUnit.visibility = View.VISIBLE
//                    binding.tvSizeUnit.text = " ($it)"
//                }
//            }
            if (product.colors.isNullOrEmpty()){
                tvColor.visibility = View.INVISIBLE
            }
            if (product.sizes.isNullOrEmpty()){
                tvSize.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupSizesRecyclerview() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizantalSpacingItemDecorator(45))
        }
    }

    private fun setupColorsRecyclerview() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizantalSpacingItemDecorator(45))
        }
    }

    private fun setupViewpager() {
        binding.viewpager2Images.adapter = viewPagerAdapter
        binding.circleIndicator.setWithViewPager2(binding.viewpager2Images)
        binding.circleIndicator.itemCount = args.product.images.size
        binding.circleIndicator.setAnimationMode(CircleIndicator.AnimationMode.SLIDE)
    }
}
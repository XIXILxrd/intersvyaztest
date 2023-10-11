package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.intersvyaztest.presentation.feature.notification.Notification
import com.example.itntersvyaztest.R
import com.example.itntersvyaztest.databinding.FragmentCoinDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {
    private val args by navArgs<CoinDetailFragmentArgs>()
    private lateinit var viewModel: CoinViewModel

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        val fromSymbol = getSymbol() ?: args.fromSymbol

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) { coin ->
            with(binding) {
                tvPrice.text = coin.price
                tvMinPrice.text = coin.lowDay
                tvMaxPrice.text = coin.highDay
                tvLastMarket.text = coin.lastMarket
                tvLastUpdate.text = coin.lastUpdate
                tvFromSymbol.text = coin.fromSymbol
                tvToSymbol.text = coin.toSymbol
                Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
                swapFavoriteIcon(coin.isFavorite)

                addToFavoriteImageButton.setOnClickListener {
                    TODO()
                    //swapFavoriteIcon(coin.isFavorite)
                }

                reminderImageButton.setOnClickListener {
                    Notification.scheduleNotification(
                        requireContext(),
                        fromSymbol,
                        fromSymbol,
                        "It's time to check $fromSymbol",
                        Notification.TIME_1MIN
                    )

                    Snackbar.make(
                        requireView(),
                        getString(R.string.notification_create_snackbar_text),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun swapFavoriteIcon(isFavorite: Boolean) {
        binding.addToFavoriteImageButton.setImageResource(
            if (isFavorite) {
                R.drawable.baseline_favorite_40
            } else {
                R.drawable.baseline_favorite_border_40
            }
        )
    }

    private fun getSymbol(): String? {
        return arguments?.getString(EXTRA_FROM_SYMBOL, null)
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        fun newDetailFragment(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}
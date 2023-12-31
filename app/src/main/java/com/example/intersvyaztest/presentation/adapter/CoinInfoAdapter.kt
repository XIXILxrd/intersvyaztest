package com.example.intersvyaztest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.intersvyaztest.domain.CoinInfo
import com.example.itntersvyaztest.R
import com.example.itntersvyaztest.databinding.ItemCoinInfoBinding
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback) {

    var onCoinClickListener: OnCoinClickListener? = null
    var onLongCoinClickListener: OnLongCoinClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)

        with(holder.binding) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)

                symbolsTextView.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                priceTextView.text = price
                descriptionTextView.text = description
                favoriteImageView.visibility = if (isFavorite) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                Picasso.get().load(imageUrl).into(logoCoinImageView)

                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }

                root.setOnLongClickListener {
                   onLongCoinClickListener?.onLongCoinClick(this) == true
                }
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }

    interface OnLongCoinClickListener {
        fun onLongCoinClick(coinInfo: CoinInfo): Boolean
    }
}
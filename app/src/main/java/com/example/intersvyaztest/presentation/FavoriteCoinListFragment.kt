package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.presentation.adapter.CoinInfoAdapter
import com.example.itntersvyaztest.R
import com.example.itntersvyaztest.databinding.FragmentFavoriteCoinListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FavoriteCoinListFragment : Fragment() {
    private lateinit var viewModel: CoinViewModel

    private var _binding: FragmentFavoriteCoinListBinding? = null
    private val binding: FragmentFavoriteCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteCoinListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoinInfoAdapter(requireActivity().application)

        setupSwipeListener(binding.favoriteCoinListRecyclerView, adapter)

        bindRecyclerView(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun bindRecyclerView(adapter: CoinInfoAdapter) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                launchDetailFragment(coinPriceInfo.fromSymbol)
            }
        }

        binding.favoriteCoinListRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getFavoriteCoins().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailFragment(fromSymbol: String) {
        findNavController().navigate(
            FavoriteCoinListFragmentDirections.actionFavoriteCoinListFragmentToCoinDetailFragment(
                fromSymbol
            )
        )
    }

    private fun setupSwipeListener(
        favoriteCoinListRecyclerView: RecyclerView?,
        adapter: CoinInfoAdapter
    ) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.bindingAdapterPosition]

                lifecycleScope.launch {
                    viewModel.updateCoinInfo(
                        item.fromSymbol,
                        !item.isFavorite,
                        item.description
                    )
                }

                Snackbar.make(
                    requireView(),
                    getString(R.string.remove_from_favorites_snackbar_text),
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.undo_snackbar_text) {
                    adapter.submitList(arrayListOf<CoinInfo?>().apply {
                        addAll(adapter.currentList)
                        add(item)
                    })

                    lifecycleScope.launch {
                        viewModel.updateCoinInfo(
                            item.fromSymbol,
                            item.isFavorite,
                            item.description
                        )
                    }
                }
                    .show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(favoriteCoinListRecyclerView)
    }
}
package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.presentation.adapter.CoinInfoAdapter
import com.example.itntersvyaztest.R
import com.example.itntersvyaztest.databinding.FragmentCoinPriceListBinding
import com.google.android.material.snackbar.Snackbar

class CoinPriceListFragment : Fragment() {
    private lateinit var viewModel: CoinViewModel

    private var _binding: FragmentCoinPriceListBinding? = null
    private val binding: FragmentCoinPriceListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinPriceListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoinInfoAdapter(requireActivity().applicationContext)

        bindRecyclerView(adapter)
        bindSearch(adapter)
        setListenerOnNavigationBar()
    }

    private fun bindSearch(adapter: CoinInfoAdapter) {
        binding.searchCoinSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchCoinSearchView.clearFocus()
                try {
                    query?.let {
                        viewModel.getSearchResult(it.uppercase())
                            .observe(viewLifecycleOwner) { list ->
                                adapter.submitList(list)
                            }
                    }
                } catch (exception: Exception) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.nothing_found_text),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    if (newText.isNullOrBlank()) {
                        viewModel.coinInfoList.observe(viewLifecycleOwner) { list ->
                            adapter.submitList(list)
                        }
                    } else {
                        viewModel.getSearchResult(newText.uppercase())
                            .observe(viewLifecycleOwner) { list ->
                                adapter.submitList(list)
                            }
                    }
                } catch (exception: Exception) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.nothing_found_text),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setListenerOnNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favoriteButton -> {
                    launchFavoriteListFragment()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun bindRecyclerView(adapter: CoinInfoAdapter) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                launchDetailFragment(coinPriceInfo.fromSymbol)
            }
        }

        binding.coinPriceListRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailFragment(fromSymbol: String) {
        findNavController().navigate(
            CoinPriceListFragmentDirections.actionCoinPriceListFragmentToCoinDetailFragment(
                fromSymbol
            )
        )
    }

    private fun launchFavoriteListFragment() {
        findNavController().navigate(
            CoinPriceListFragmentDirections.actionCoinPriceListFragmentToFavoriteCoinListFragment()
        )
    }
}
package com.example.intersvyaztest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.intersvyaztest.domain.CoinInfo
import com.example.intersvyaztest.presentation.adapter.CoinInfoAdapter
import com.example.itntersvyaztest.databinding.FragmentCoinPriceListBinding

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

        val adapter = CoinInfoAdapter(requireContext())

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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
package com.hyshchak.findcharacter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hyshchak.findcharacter.databinding.FragmentFavoriteListBinding
import com.hyshchak.findcharacter.ui.adapters.FavoriteListAdapter
import com.hyshchak.findcharacter.ui.adapters.OnLikeClickListener
import com.hyshchak.findcharacter.ui.adapters.OnPersonClickListener
import com.hyshchak.findcharacter.ui.viewmodels.FavoriteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class FavoriteListFragment : Fragment() {

    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteListViewModel by viewModels()
    private lateinit var favoriteListAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRCV()
        initObservers()
    }

    private fun initObservers() {
        viewModel.favoriteList.observe(
            viewLifecycleOwner, {
                favoriteListAdapter.submitList(it)
            }
        )
    }

    private fun setupRCV() {
        favoriteListAdapter = FavoriteListAdapter(
            onPersonClickListener = OnPersonClickListener { personShort ->
                val action = FavoriteListFragmentDirections.actionFavoriteListFragmentToDetailsFragment(personShort)
                findNavController().navigate(action)
            },
            onLikeClickListener = OnLikeClickListener { personShort ->
                viewModel.updatePerson(personShort)
            }
        )
        binding.rcvFavoriteList.apply {
            adapter = favoriteListAdapter
        }
    }
}

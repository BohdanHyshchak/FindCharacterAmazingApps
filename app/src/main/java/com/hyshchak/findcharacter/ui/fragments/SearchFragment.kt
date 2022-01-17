package com.hyshchak.findcharacter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.hyshchak.findcharacter.databinding.FragmentSearchBinding
import com.hyshchak.findcharacter.ui.adapters.OnLikeClickListener
import com.hyshchak.findcharacter.ui.adapters.PeopleListAdapter
import com.hyshchak.findcharacter.ui.viewmodels.SearchViewModel
import com.hyshchak.findcharacter.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var peopleListAdapter: PeopleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRCV()
        initObservers()
        implementSearch()
    }

    private fun setupRCV() {
        peopleListAdapter = PeopleListAdapter(
            onPersonClickListener = PeopleListAdapter.OnPersonClickListener { personShort ->
                val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(personShort)
                findNavController().navigate(action)
            },
            onLikeClickListener = OnLikeClickListener { personShort ->
                viewModel.updatePerson(personShort)
            }
        )
        binding.rcvTodoList.apply {
            adapter = peopleListAdapter
        }
    }
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.peopleList.collectLatest {
                peopleListAdapter.submitData(it)
            }
        }
    }

    private fun implementSearch() {
        binding.etSearch.addTextChangedListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.setQuery(it.toString())
                delay(Constants.INPUT_DELAY)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

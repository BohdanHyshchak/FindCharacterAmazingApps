package com.hyshchak.findcharacter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hyshchak.findcharacter.R
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.databinding.FragmentDetailInfoBinding
import com.hyshchak.findcharacter.ui.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPersonFromFragment(args.personShort)
        initObservers()
        bindButton()
    }

    private fun initObservers() {
        viewModel.personFullInfo.observe(
            viewLifecycleOwner, {
                with(binding) {
                    tvNameDescription.text = it.name
                    tvBirthYearDescription.text = it.birthYear
                    tvGenderDescription.text = it.gender
                    tvHairColorDescription.text = it.hairColor
                    tvMassDescription.text = it.mass
                    tvHeightDescription.text = it.height
                    tvSkinColorDescription.text = it.skinColor
                }
            }
        )

        viewModel.isLiked.observe(
            viewLifecycleOwner, {
                if (it == true) {
                    binding.btnHeart.setBackgroundResource(R.drawable.ic_heart_feel)
                } else {
                    binding.btnHeart.setBackgroundResource(R.drawable.ic_heart_outline)
                }
            }
        )

        viewModel.filmsList.observe(
            viewLifecycleOwner, {
                binding.tvFilmsDescription.text = it.toString().removeSurrounding('['.toString(),
                    ']'.toString()
                )
            }
        )


    }

    private fun bindButton() {
        binding.btnHeart.setOnClickListener {
            viewModel.getPersonFromFragment(PersonShort(args.personShort.name, !viewModel.isLiked.value!!, args.personShort.id))
        }
    }
}

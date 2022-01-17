package com.hyshchak.findcharacter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyshchak.findcharacter.R
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.databinding.RcvPersonListBinding

class PeopleListAdapter(private val onPersonClickListener: OnPersonClickListener, private val onLikeClickListener: OnLikeClickListener,) : PagingDataAdapter<PersonShort, PeopleListAdapter.PeopleListViewHolder>(PeopleListDiffCallback()) {

    class PeopleListViewHolder(private val binding: RcvPersonListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: PersonShort, onLikeClickListener: OnLikeClickListener) {
            binding.tvName.text = person.name
            if (person.isFavorite) {
                binding.btnHeart.setBackgroundResource(R.drawable.ic_heart_feel)
            } else {
                binding.btnHeart.setBackgroundResource(R.drawable.ic_heart_outline)
            }
            binding.btnHeart.setOnClickListener {
                onLikeClickListener.onClickLike(person)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RcvPersonListBinding.inflate(inflater, parent, false)
        return PeopleListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onLikeClickListener)
        }
        holder.itemView.setOnClickListener {
            if (item != null) {
                onPersonClickListener.onClickPerson(item)
            }
        }
    }

    class OnPersonClickListener(val clickListener: (person: PersonShort) -> Unit) {
        fun onClickPerson(person: PersonShort) = clickListener(person)
    }
}

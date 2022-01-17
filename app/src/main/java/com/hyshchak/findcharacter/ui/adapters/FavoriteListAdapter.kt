package com.hyshchak.findcharacter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyshchak.findcharacter.R
import com.hyshchak.findcharacter.database.entities.PersonShort
import com.hyshchak.findcharacter.databinding.RcvPersonListBinding

class FavoriteListAdapter(private val onPersonClickListener: OnPersonClickListener, private val onLikeClickListener: OnLikeClickListener) :
    ListAdapter<PersonShort, FavoriteListAdapter.PersonViewHolder>(PeopleListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RcvPersonListBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = currentList[position]
        if (item != null) {
            holder.bind(item, onLikeClickListener)
        }
        holder.itemView.setOnClickListener {
            if (item != null) {
                onPersonClickListener.onClickPerson(item)
            }
        }
    }

    class PersonViewHolder(private val binding: RcvPersonListBinding) : RecyclerView.ViewHolder(binding.root) {
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
}

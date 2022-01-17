package com.hyshchak.findcharacter.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hyshchak.findcharacter.database.entities.PersonShort

class PeopleListDiffCallback : DiffUtil.ItemCallback<PersonShort>() {
    override fun areItemsTheSame(oldItem: PersonShort, newItem: PersonShort): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonShort, newItem: PersonShort): Boolean {
        return oldItem == newItem
    }
}

package com.hyshchak.findcharacter.ui.adapters

import com.hyshchak.findcharacter.database.entities.PersonShort

class OnLikeClickListener(val clickListener: (person: PersonShort) -> Unit) {
    fun onClickLike(person: PersonShort) = clickListener(person)
}
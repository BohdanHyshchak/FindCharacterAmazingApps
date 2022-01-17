package com.hyshchak.findcharacter.ui.adapters

import com.hyshchak.findcharacter.database.entities.PersonShort

class OnPersonClickListener(val clickListener: (person: PersonShort) -> Unit) {
    fun onClickPerson(person: PersonShort) = clickListener(person)
}
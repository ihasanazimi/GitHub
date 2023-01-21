package com.example.masir.ui.main.feachers.user.details.follow_lists

import androidx.recyclerview.widget.DiffUtil
import com.example.masir.model.User

class UserDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

}
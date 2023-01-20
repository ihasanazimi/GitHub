package com.example.masir.ui.main.feachers.user.details.follow_lists

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.masir.ApplicationLoader
import com.example.masir.model.User

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, ) : FragmentStateAdapter(fm, lifecycle) {

    private val items: ArrayList<User> = ArrayList()

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int) = position

    override fun createFragment(position: Int): Fragment {
        return FollowListFragment.newInstance(items)
    }

    fun addItems(items: ArrayList<User>) {
        this.items.addAll(items)
        val startPos = this.items.size - items.size
        notifyItemRangeInserted(startPos, items.size)
    }

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},150)
    }

//    override fun getItemId(position: Int): Long {
//        return this.items[position].hashCode().toLong()
//    }

    fun removeItem(i: Int){
        if (this.items.isNotEmpty()){
            this.items.removeAt(i)
            ApplicationLoader.applicationHandler.postDelayed({ notifyItemRemoved(i) },100)
        }
    }

    fun clearList(){
        this.items.clear()
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},250)
    }
}
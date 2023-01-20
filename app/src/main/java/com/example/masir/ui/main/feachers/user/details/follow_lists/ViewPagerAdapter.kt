package com.example.masir.ui.main.feachers.user.details.follow_lists

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import com.example.masir.model.User

class ViewPagerAdapter(fm: FragmentManager,behavior : Int) : FragmentPagerAdapter(fm,) {

    private val fragments : ArrayList<Fragment> = arrayListOf()
    private val titles : ArrayList<String> = arrayListOf()

    fun addFragment(fragment: Fragment , title :String){
        fragments.add(fragment)
        titles.add(title)
        notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }


}
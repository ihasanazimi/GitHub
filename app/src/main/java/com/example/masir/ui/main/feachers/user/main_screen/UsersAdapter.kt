package com.example.masir.ui.main.feachers.user.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.masir.ApplicationLoader
import com.example.masir.R
import com.example.masir.databinding.ItemUserBinding
import com.example.masir.model.User
import com.example.masir.ui.main.feachers.user.details.follow_lists.UserDiffCallback
import com.example.masir.utility.util.DiffUtilCallback

class UsersAdapter(var callBack: OnUserCallBacks) : RecyclerView.Adapter<UsersAdapter.VH>() {

    private val items = arrayListOf<User>()

    interface OnUserCallBacks{ fun onItemClick(model : User) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener{
            callBack.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(model: User){
        val index = items.indexOf(model)
        items.removeAt(index)
        notifyItemRemoved(index)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }

    fun addItem(model: User){
        items.add(model)
        notifyItemInserted(itemCount)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }

    fun setItemByDiffUtil(list: List<User>){
        val diffCallback = DiffUtilCallback(this.items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItems(items: ArrayList<User>) {
        this.items.addAll(items)
        val startPos = this.items.size - items.size
        notifyItemRangeInserted(startPos, items.size)
    }

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }


    fun clearList(){
        items.clear()
        ApplicationLoader.applicationHandler.postDelayed({notifyDataSetChanged()},200)
    }


    inner class VH(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: User){
            binding.data = model
            Glide.with(binding.root.context)
                .load(model.avatar_url)
                .timeout(30000)
                .error(R.drawable.ic_outline_info_24)
                .placeholder(R.drawable.ic_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivUserCover)
        }
    }
}
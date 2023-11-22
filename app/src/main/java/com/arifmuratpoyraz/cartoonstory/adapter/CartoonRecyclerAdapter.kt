package com.arifmuratpoyraz.cartoonstory.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arifmuratpoyraz.cartoonstory.R
import com.arifmuratpoyraz.cartoonstory.model.Cartoon
import com.arifmuratpoyraz.cartoonstory.util.Singleton
import com.arifmuratpoyraz.cartoonstory.util.createPlaceHolder
import com.arifmuratpoyraz.cartoonstory.util.downloadImage
import com.arifmuratpoyraz.cartoonstory.view.CartoonListFragmentDirections

class CartoonRecyclerAdapter(val cartoonList : ArrayList<Cartoon>): RecyclerView.Adapter<CartoonRecyclerAdapter.CartoonVH>() {

    class CartoonVH(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val cartoonListText : TextView = itemView.findViewById(R.id.cartoonListText)
        val cartoonListImage : ImageView = itemView.findViewById(R.id.cartoonListImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cartoon_recycler_row,parent,false)
        return CartoonVH(view)
    }

    override fun getItemCount(): Int {
        return cartoonList.size
    }

    override fun onBindViewHolder(holder: CartoonVH, position: Int) {
        holder.cartoonListText.text = cartoonList.get(position).safe_title
        holder.cartoonListImage.downloadImage(cartoonList.get(position).img, createPlaceHolder(holder.itemView.context))
        holder.itemView.setOnClickListener{
            val cartoonId = cartoonList[position].num
            if (cartoonId != null) {
                Singleton.id = cartoonId
            }
            val action = CartoonListFragmentDirections.actionCartoonListFragmentToCartoonDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshList (newList: Cartoon) {
        cartoonList.clear()
        cartoonList.add(newList)
        notifyDataSetChanged()
    }
}
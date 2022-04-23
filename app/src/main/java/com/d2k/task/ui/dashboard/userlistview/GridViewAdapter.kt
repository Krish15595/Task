package com.d2k.task.ui.dashboard.userlistview

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d2k.task.R
import com.d2k.task.databinding.RowHomeItemBinding
import com.squareup.picasso.Picasso

class GridViewAdapter(
    val context: Context,
    private val list: List<GridViewResponse>,
    val listener: OnListClick
) : RecyclerView.Adapter<GridViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = RowHomeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(
            v
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list, position, listener, context)
        holder.setIsRecyclable(false)
    }

    class Holder(var binding: RowHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            gridViewListResponse: List<GridViewResponse>,
            position: Int,
            listener: OnListClick,
            context: Context
        ) {
            val gridViewResponse = gridViewListResponse.get(position)
            binding.apply {
                tvName.text = gridViewResponse.gridTitle
                Picasso.get().load(gridViewResponse.count).into(ivUser)
                cvGridItem.setOnClickListener {
                    for (i in gridViewListResponse.indices) {
                        gridViewListResponse.get(i).selected = false
                    }
                    gridViewListResponse.get(position).selected = true
                    listener.onClicked(adapterPosition, gridViewResponse)
                }

                if (gridViewResponse.selected) {
                    ivBackground.visibility = View.VISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tvName.setTextColor(context.getColor(R.color.white))
                    }
                }
            }

        }

    }

    interface OnListClick {
        fun onClicked(pos: Int, customerListResponse: GridViewResponse)
    }
}
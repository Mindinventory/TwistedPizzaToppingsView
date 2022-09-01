package com.mindinventory.twistedpizzatoppingssample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mindinventory.twistedpizzatoppingssample.R
import com.mindinventory.twistedpizzatoppingssample.model.Topping
import kotlinx.android.synthetic.main.row_topping.view.*

/**
 * Uses for Topping list
 */
class ToppingAdapter(
    private val onSelectTopping: (topping: Topping, position: Int) -> Unit,
    private var items: ArrayList<Topping>
) : RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder>() {

    class ToppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        return ToppingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_topping, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        items[position].apply {
            holder.itemView.ivTopping.setImageResource(image)
            holder.itemView.tvTopping.text = name
            holder.itemView.tvTimes.isVisible = numberOfTimes > 0
            holder.itemView.tvTimes.text = numberOfTimes.toString()

            holder.itemView.setOnClickListener {
                numberOfTimes += 1
                notifyItemChanged(position)
                onSelectTopping.invoke(this, holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
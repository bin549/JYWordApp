package com.android.jywordapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.jywordapp.R
import com.android.jywordapp.databinding.ItemsRowBinding
import com.android.jywordapp.model.WordEntity

class ItemAdapter(
    private val items: ArrayList<WordEntity>,
    private val changeListener: (id: Int) -> Unit,
    private val updateListener: (id: Int) -> Unit,
    private val deleteListener: (id: Int) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvExplanation.text = item.explanation
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
        holder.ivChange.setOnClickListener {
            changeListener(item.id)
        }
        holder.ivEdit.setOnClickListener {
            updateListener(item.id)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener(item.id)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llMain = binding.llMain
        val tvName = binding.tvName
        val tvExplanation = binding.tvExplanation
        val ivChange = binding.ivChange
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }
}
package by.godevelopment.kroksample.domain.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.kroksample.R
import by.godevelopment.kroksample.common.TAG
import by.godevelopment.kroksample.databinding.ListItemBinding
import by.godevelopment.kroksample.domain.model.ListItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class KrokAdapter : RecyclerView.Adapter<KrokAdapter.KrokViewHolder>() {

    inner class KrokViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ListItemModel>() {
        override fun areItemsTheSame(oldItem: ListItemModel, newItem: ListItemModel): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: ListItemModel, newItem: ListItemModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listItemModels: List<ListItemModel>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = listItemModels.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KrokViewHolder {
        return KrokViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: KrokViewHolder, position: Int) {
        val listItemModel = listItemModels[position]
        holder.binding.apply {
            Glide.with(root)
                .load(listItemModel.pictures)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.image_not_loaded)
                .placeholder(R.drawable.image)
                .into(itemImage)

            itemText.text = listItemModel.text

            root.setOnClickListener {
                Log.i(TAG, "KrokAdapter onBindViewHolder: id = ${listItemModel.keyId}")
                listItemModel.onClickNav.invoke(listItemModel.keyId)
            }
        }
    }
}

package com.amaar.eilaji

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amaar.eilaji.databinding.ItemListBinding
import com.bumptech.glide.Glide


class ItemListAdapter(private val viewModel: MyViewModel) : ListAdapter<MedicationInfo,
        ItemListAdapter.ItemViewHolder>(DiffCallBack) {
    var datasourceObject = DataSource()


    var takePhoto = ""
    var describtion = ""
    var firstDay = ""
    var lastDay = ""
    var manyTime = ""


    class ItemViewHolder(
        private var binding:
        ItemListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicationInfo: MedicationInfo) {
            binding.result = medicationInfo
            binding.executePendingBindings()

        }

        var des: TextView = binding.describtionTextView
        val delete: ImageButton = binding.deleteIcon
        var edit: ImageButton = binding.editIcon
        var image: ImageView = binding.medicationImageView

    }


    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<MedicationInfo>() {
            override fun areItemsTheSame(
                oldItem: MedicationInfo,
                newItem: MedicationInfo
            ): Boolean {
                return oldItem.describtion == newItem.describtion
            }

            override fun areContentsTheSame(
                oldItem: MedicationInfo,
                newItem: MedicationInfo
            ): Boolean {
                return oldItem.takePhoto == newItem.takePhoto
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        val item = getItem(position)
        holder.bind(item)
        Log.e("TAG", "${item.takePhoto}")

        Glide.with(holder.image)

            .load(item.takePhoto)
            .into(holder.image)

        holder.des.text = item.describtion
        holder.delete.setOnClickListener {
            viewModel.deleteMed(item)


        }
        holder.edit.setOnClickListener { view: View ->
            Log.d("TAG", "onBindViewHolder: ${item.describtion}")
            Navigation.findNavController(view)
                .navigate(
                    HomePageFragmentDirections.actionHomePageFragmentToEditFragment(
                        item.idDataUser,
                        item.describtion,
                        item.firstDay,
                        item.lastDay,
                        item.manyTime,
                        item.takePhoto
                    )
                )


        }

    }

}
package com.amaar.eilaji

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amaar.eilaji.databinding.ItemListBinding
import com.bumptech.glide.Glide

//class ItemListAdapter :
//    ListAdapter<MedicationInfo, ItemListAdapter.ItemViewHolder>(DiffCallback) {
//
//
//    class ItemViewHolder(private var binding: ItemListBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: MedicationInfo) {
//           // binding.imageView. = item.takePhoto
//            binding.descriptionTextView.text = item.describtion
//
//
//        }
//    }
//
//    companion object {
//        private val DiffCallback = object : DiffUtil.ItemCallback<MedicationInfo>() {
//            override fun areItemsTheSame(oldItem: MedicationInfo, newItem: MedicationInfo): Boolean {
//                return oldItem.describtion === newItem.describtion
//            }
//
//            override fun areContentsTheSame(oldItem: MedicationInfo, newItem: MedicationInfo): Boolean {
//                return oldItem.describtion == newItem.describtion
//            }
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        return ItemViewHolder(
//            ItemListBinding.inflate(
//                LayoutInflater.from(
//                    parent.context
//                )
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val current = getItem(position)
//        holder.itemView.setOnClickListener {
//            val action = HomePageFragmentDirections.actionHomePageFragmentToAddFragment()
//            holder.itemView.findNavController().navigate(action)
//        }
//        holder.bind(current)
//    }
// }


class ItemListAdapter : ListAdapter<MedicationInfo,
        ItemListAdapter.ItemViewHolder>(DiffCallback) {
    var datasourceObject = DataSource()


    var index = 0
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
       // var medImage : ImageView = binding.medicationImageView
        var des : TextView = binding.describtionTextView
        val delete: ImageButton = binding.deleteIcon
        var edit: ImageButton = binding.editIcon
        var image : ImageView = binding.medicationImageView

      //  val desTextview: TextView = binding.descriptionTextView
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MedicationInfo>() {
        override fun areItemsTheSame(oldItem: MedicationInfo, newItem: MedicationInfo): Boolean {
            return newItem.describtion == oldItem.describtion
        }

        override fun areContentsTheSame(oldItem: MedicationInfo, newItem: MedicationInfo): Boolean {
            return oldItem.describtion == newItem.describtion
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {




        val item = getItem(position)
        holder.bind(item)

       // Glide.
       // holder.image. = item.takePhoto
        holder.des.text = item.describtion
        holder.delete.setOnClickListener {
            datasourceObject.deleteTask(position)
            notifyDataSetChanged()

//            Glide.with(context)
//                .load(item)
//                .into(holder.image)

        }
        holder.edit.setOnClickListener { view: View ->
            Log.d("TAG", "onBindViewHolder: ${item.describtion}")
            Navigation.findNavController(view)
                .navigate(HomePageFragmentDirections.actionHomePageFragmentToEditFragment(position,item.describtion,item.firstDay,item.lastDay,item.manyTime,item.takePhoto))
                //view.findNavController().navigate(position)
          //  datasourceObject.updateTask(index, MedicationInfo(takePhoto, describtion, firstDay, lastDay, manyTime ))


//        holder.desTextview.setOnClickListener {
//           view -> view.findNavController().navigate(R.id.action_moviesFragment_to_detailsFragment)
//            val action = HomePageFragmentDirections.actionHomePageFragmentToAddFragment()
//            holder.desTextview.findNavController().navigate(action)
//
//        }


        }

    }

}
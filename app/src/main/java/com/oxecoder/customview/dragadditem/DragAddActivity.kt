package com.oxecoder.customview.dragadditem

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oxecoder.customview.R
import com.oxecoder.customview.databinding.ActivityDragAddBinding
import com.oxecoder.customview.databinding.ItemItemBinding

class DragAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragAddBinding
    private val adapter by lazy { Adapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag_add)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter

//      add items to adapter
        val list = mutableListOf<String>()
        for (i in 0 until 20) {
            val suffix = when (i) {
                0 -> "START"
                19 -> "+"
                else -> i.toString()
            }
            list.add("TEXT $suffix")
        }
        adapter.list = list

    }


    companion object {
        fun newIntent(context: Context) = Intent(context, DragAddActivity::class.java)
    }
}

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var list = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)
    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    class ViewHolder(private val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

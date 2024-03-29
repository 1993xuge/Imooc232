package com.xuge.imooc232.view.common

import android.animation.ObjectAnimator
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.xuge.imooc232.R
import com.xuge.imooc232.utils.AdapterList
import kotlinx.android.synthetic.main.item_card.view.*
import org.jetbrains.anko.dip

abstract class CommonListAdapter<T>(@LayoutRes val itemResId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val CARD_TAP_DURATION = 100L

    }

    init {
        setHasStableIds(true)
    }

    private var oldPosition = -1
    val data = AdapterList<T>(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        LayoutInflater.from(parent.context).inflate(itemResId, itemView.contentContainer)
        return CommonViewHolder(itemView)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, position)
        holder.itemView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->
                    ViewCompat.animate(holder.itemView).scaleX(1.03f).scaleY(1.03f).translationZ(
                        holder.itemView.dip(10).toFloat()
                    ).duration = CARD_TAP_DURATION

                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    ViewCompat.animate(holder.itemView).scaleX(1f).scaleY(1f)
                        .translationZ(holder.itemView.dip(0).toFloat()).duration = CARD_TAP_DURATION
                }
            }
            false
        }

        holder.itemView.setOnClickListener {
            onItemClicked(holder.itemView, data[position])
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is CommonViewHolder && holder.layoutPosition > oldPosition) {
            addItemAnimation(holder.itemView)
            oldPosition = holder.layoutPosition
        }
    }

    private fun addItemAnimation(itemView: View) {
        ObjectAnimator.ofFloat(itemView, "translationY", 500f, 0f).setDuration(500).start()
    }

    abstract fun onBindData(viewHolder: RecyclerView.ViewHolder, position: Int)
    abstract fun onItemClicked(itemView: View, item: T)

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
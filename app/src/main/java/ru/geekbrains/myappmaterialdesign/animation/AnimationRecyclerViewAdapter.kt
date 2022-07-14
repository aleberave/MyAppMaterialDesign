package ru.geekbrains.myappmaterialdesign.animation

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import ru.geekbrains.myappmaterialdesign.R

class AnimationRecyclerViewAdapter
//    (private val activity: ActivityAnimationBinding? = null)
    :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_animation_recyclerview_item_second, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {

//            var rectExp = Rect(
//                it.x.toInt(),
//                it.y.toInt(),
//                it.x.toInt() + it.width.toInt(),
//                it.y.toInt() + it.height.toInt()
//            )
            val rectangle = Rect()
            it.getGlobalVisibleRect(rectangle)
            val explodeTransition = Explode()
            explodeTransition.duration = 2000L
            explodeTransition.epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition): Rect {
                    return rectangle
                }

            }
//            TransitionManager.beginDelayedTransition(
//                activity.recyclerViewTransition,
//                explodeTransition
//            )
//            activity.recyclerViewTransition.adapter = null
        }
    }

    override fun getItemCount(): Int {
        return 28
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
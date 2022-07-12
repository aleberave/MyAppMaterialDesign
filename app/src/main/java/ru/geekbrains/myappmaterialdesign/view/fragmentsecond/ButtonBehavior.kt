package ru.geekbrains.myappmaterialdesign.view.fragmentsecond

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs

class ButtonBehavior(context: Context, attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<FloatingActionButton>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            Log.i("@2", "${dependency.y}")
//            app:layout_anchor="@id/appBar"
//            app:layout_anchorGravity="bottom|end"
            child.y = dependency.y + dependency.height - child.height / 2
            child.x = (dependency.width - child.width).toFloat()
            child.alpha = 1 - (abs(dependency.y) / dependency.height * 1.5f)
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}
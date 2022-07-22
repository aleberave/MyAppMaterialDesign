package ru.geekbrains.myappmaterialdesign.view.fragmenttext

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import ru.geekbrains.myappmaterialdesign.R

class NestedBehavior(context: Context, attributeSet: AttributeSet? = null) :
    CoordinatorLayout.Behavior<NestedScrollView>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        if (dependency.id == R.id.appBar) {
            Log.i("@2", "${dependency.y}${dependency.height}")
            child.y = dependency.y + dependency.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}
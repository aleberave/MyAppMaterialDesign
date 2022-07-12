package ru.geekbrains.myappmaterialdesign.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.geekbrains.myappmaterialdesign.view.fragmentsecond.ButtonBehavior

class MyImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), CoordinatorLayout.AttachedBehavior {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return ButtonBehavior(context)
    }
}
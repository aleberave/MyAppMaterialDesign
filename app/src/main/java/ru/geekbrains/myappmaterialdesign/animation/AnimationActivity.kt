package ru.geekbrains.myappmaterialdesign.animation

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.ActivityAnimationEightStartBinding

class AnimationActivity : AppCompatActivity() {

    private var _binding: ActivityAnimationEightStartBinding? = null
    private val binding get() = _binding!!

    private var isFlag = false
    private val duration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationEightStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO FIRST
        // анимация движения кнопки и текста
//        binding.animationButton.setOnClickListener {
//            isFlag = !isFlag
//            val myAutoTransition = TransitionSet()
////            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
//            // заменяет настройки отдельно длякаждого состояния,
//            // и устанавливает для всего сета задержку
//            myAutoTransition.duration = 2000L
////            val fadeTransition = Fade()
//            val fadeTransition = Slide(Gravity.END)
//            fadeTransition.duration = 1000L
//            val changeBoundsTransition = ChangeBounds()
//            changeBoundsTransition.duration = 1000L
//            myAutoTransition.addTransition(fadeTransition)
//            myAutoTransition.addTransition(changeBoundsTransition)
//            TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)
//            binding.textAnimation.visibility = if (isFlag) View.VISIBLE else View.GONE
//        }
        //TODO SECOND
        // кнопки разлетаются в разные стороны
//        binding.recyclerViewTransition.adapter = AnimationRecyclerViewAdapter(binding)

        //TODO THREE
        // анимаци картинки
        // для плавного увеличения картинки важен порядок добавления действий в TransitionSet
//Before: android:layout_height="wrap_content"
//        android:scaleType="centerInside"
//After:  android:layout_height="match_parent"
//        android:scaleType="centerCrop"

//        binding.imageViewThreeAA.setOnClickListener {
//            isFlag = !isFlag
//
//            val transitionSet = TransitionSet()
//            val changeBounds = ChangeBounds()
//            changeBounds.duration = 1000L
//            val changeImageTransform = ChangeImageTransform()
//            changeImageTransform.duration = 1000L
//            transitionSet.addTransition(changeBounds)
//            transitionSet.addTransition(changeImageTransform)
//            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
//              // TODO LinearLayout в котором находиться картинка должен иметь высоту MATCH_PARENT
//            val params = it.layoutParams as LinearLayout.LayoutParams
//            if (isFlag) {
//                params.height = LinearLayout.LayoutParams.MATCH_PARENT
//                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
//            } else {
//                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
//                binding.imageViewThreeAA.scaleType = ImageView.ScaleType.CENTER_INSIDE
//            }
//            it.layoutParams = params
//        }

        //TODO FOUR
        // анимация движения кнопки по траектории
//        binding.buttonFourAA.setOnClickListener {
//            isFlag = !isFlag
//            val params = it.layoutParams as FrameLayout.LayoutParams
//
//            val changeBounds = ChangeBounds()
//            changeBounds.duration = 1000L
//            changeBounds.setPathMotion(ArcMotion())
//            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
//            if (!isFlag) {
//                params.gravity = Gravity.TOP or Gravity.START
//            } else {
//                params.gravity = Gravity.BOTTOM or Gravity.END
//            }
//            binding.buttonFourAA.layoutParams = params
//        }

        //TODO FIVE
        // анимация перемешивания TextView
//        val titles: MutableList<String> = ArrayList()
//        for (i in 0..4) {
//            titles.add("Item $i")
//        }
//
//        binding.buttonFiveAA.setOnClickListener {
//            isFlag = !isFlag
//            TransitionManager.beginDelayedTransition(binding.root)
//            binding.transitionContainer.removeAllViews()
//            titles.shuffle() // перемешиваем кнопки
//            titles.forEach {
//                binding.transitionContainer.addView(TextView(this).apply {
//                    text = it
//                    ViewCompat.setTransitionName(this, it)
//                })
//            }
//        }

        //TODO SIX
        // затенение картинки при выезде меню + обратно
//        binding.fab.setOnClickListener {
//            isFlag = !isFlag
//            if (isFlag) {
//                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0f, 675f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.plusImageView, View.ROTATION, 0f, 675f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -200f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -300f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.5f)
//                    .setDuration(duration).start()
//                binding.optionOneContainer.animate().alpha(1f).setDuration(duration)
//                    .setListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator?) {
//                            super.onAnimationEnd(animation)
//                            binding.optionOneContainer.isClickable = true
//                        }
//                    })
//                binding.optionTwoContainer.animate().alpha(1f).setDuration(duration)
//                    .setListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator?) {
//                            super.onAnimationEnd(animation)
//                            binding.optionTwoContainer.isClickable = true
//                        }
//                    })
//            } else {
//                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 675f, 0f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.plusImageView, View.ROTATION, 675f, 0f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f)
//                    .setDuration(duration).start()
//                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f)
//                    .setDuration(duration).start()
//                binding.optionOneContainer.animate().alpha(0f).setDuration(duration)
//                    .setListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator?) {
//                            super.onAnimationEnd(animation)
//                            binding.optionOneContainer.isClickable = false
//                        }
//                    })
//
//                binding.optionTwoContainer.animate().alpha(0f).setDuration(duration)
//                    .setListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator?) {
//                            super.onAnimationEnd(animation)
//                            binding.optionTwoContainer.isClickable = false
//                        }
//                    })
//            }
//        }

        //TODO SEVEN
        // показываем тень, если скролим и сверху что-то заехало под заголовок
//        binding.header.isSelected = true
//        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            // если скролиться, то появляется теньх
//            binding.header.isSelected = binding.scrollView.canScrollVertically(-1)
//        }

        //TODO EIGHT
        // два констрейта хватаются за края картинки и поэтому скрыты вначале, потом они выезжают.
        // Используется не MotionLayout, а TransitionManager

        //TODO 1 way
//        val constraintSetStart = ConstraintSet()
//        val constraintSetEnd = ConstraintSet()
//        constraintSetStart.clone(this, R.layout.activity_animation_eight_start)
//        constraintSetEnd.clone(this, R.layout.activity_animation_eight_end)
//        binding.tap.setOnClickListener {
//            isFlag = !isFlag
//            val changeBounds = ChangeBounds()
//            changeBounds.duration = duration
//            changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
//            TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
//            if (isFlag) {
//                constraintSetEnd.applyTo(binding.constraintContainer)
//            } else {
//                constraintSetStart.applyTo(binding.constraintContainer)
//            }

        //TODO 2 way
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animation_eight_start)
        binding.tap.setOnClickListener {
            isFlag = !isFlag
            val changeBounds = ChangeBounds()
            changeBounds.duration = duration
            changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
            if (isFlag) {
                constraintSet.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.RIGHT
                )
                constraintSet.clear(R.id.title, ConstraintSet.RIGHT)
            } else {
                constraintSet.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.LEFT
                )
            }
            constraintSet.applyTo(binding.constraintContainer)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
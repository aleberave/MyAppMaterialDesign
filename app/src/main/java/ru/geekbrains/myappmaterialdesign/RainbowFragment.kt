package ru.geekbrains.myappmaterialdesign

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.databinding.FragmentRainbowBinding

class RainbowFragment : Fragment() {

    private lateinit var spannableString: SpannableString
    private lateinit var countDownTimer: CountDownTimer

    private var _binding: FragmentRainbowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRainbowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spannableString = SpannableString(getString(R.string.lorem_ipsum))
        binding.tvRainbow.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableString = binding.tvRainbow.text as SpannableString
        getStrikeThroughSpan()
        rainbow(1)
    }

    private fun getStrikeThroughSpan() {
        /** стиль текста */
        spannableString.setSpan(
            StyleSpan(Typeface.NORMAL),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        /** размер текста по Х*/
        spannableString.setSpan(
            ScaleXSpan(2.0f),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        /** индекс-ссылка сверху */
        spannableString.setSpan(SuperscriptSpan(), 10, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        /** индекс-ссылка снизу */
        spannableString.setSpan(SubscriptSpan(), 50, 60, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        /** добавляем style  */
        spannableString.setSpan(
            TextAppearanceSpan(requireContext(), R.style.TextStyleRainbow),
            0,
            spannableString.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        /** подключаем шрифт */
        val font = ResourcesCompat.getFont(requireContext(), R.font.black_and_white_picture)
        val typeface = Typeface.create(font, Typeface.BOLD)
        spannableString.setSpan(
            TypefaceSpan(typeface),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        /** устонавливаем межстрочный интервал */
        spannableString.setSpan(
            LineHeightSpan.Standard(80),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    fun rainbow(i: Int = 1) {
        /** Запланируйте обратный отсчет до определенного времени в будущем с регулярными
         * уведомлениями через определенные промежутки времени в 0.2сек. Пример отображения
         * 20-секундного обратного отсчета в Log.i(). Вызовы onTick(long)синхронизируются
         * с этим объектом - object, поэтому ни один вызов onTick(long) никогда не произойдет
         * до завершения предыдущего обратного вызова. Это имеет значение только тогда, когда
         * реализация onTick(long)требует времени для выполнения, которое является значительным
         * по сравнению с интервалом обратного отсчета. cancel() - Отмените обратный отсчет.
         * start() - Запустите обратный отсчет.*/
        var currentCount = i
        var secondToFinished = 0

        countDownTimer = object : CountDownTimer(20000, 200) {
            /** Обратный вызов onTick() срабатывает через регулярные промежутки времени 0.2 сек. */
            override fun onTick(millisUntilFinished: Long) {
                getRainbowColors(currentCount)
                if (currentCount == 6) {
                    secondToFinished += 1
                    Log.i("@2", "onTick : $secondToFinished")
                }
                currentCount = if (++currentCount > 6) 1 else currentCount
            }

            /** Обратный вызов onFinish() срабатывает, когда время в 20 cек истекло. */
            override fun onFinish() {
                if (secondToFinished == 19) {
                    Log.i("@2", "onFinish : ${secondToFinished + 1}")
                    secondToFinished = 0
                }
                rainbow(currentCount)
                Log.i("@2", "$currentCount")
            }
        }
        countDownTimer.start()
    }

    private fun getRainbowColors(countColor: Int) {
        /** создание карты <индекс, значение> */
        val colorMap = mapOf(
            0 to ContextCompat.getColor(requireContext(), R.color.red),
            1 to ContextCompat.getColor(requireContext(), R.color.orange),
            2 to ContextCompat.getColor(requireContext(), R.color.yellow),
            3 to ContextCompat.getColor(requireContext(), R.color.green),
            4 to ContextCompat.getColor(requireContext(), R.color.blue),
            5 to ContextCompat.getColor(requireContext(), R.color.deepBlue),
            6 to ContextCompat.getColor(requireContext(), R.color.purple)
        )

        /** удаляем прежний span, чтобы не забивать память */
        val spans = spannableString.getSpans(
            0, spannableString.length,
            ForegroundColorSpan::class.java
        )
        for (span in spans) {
            spannableString.removeSpan(span)
        }

        var indexColor = countColor
        /** запись длины текста, чтобы не выходить за пределы массива (i + 1) */
        for (i in 0 until binding.tvRainbow.text.length) {
            if (indexColor == 6) indexColor = 0
            else indexColor += 1
            spannableString.setSpan(
                /** обращение к карте по индесу */
                ForegroundColorSpan(colorMap.getValue(indexColor)),
                i,
                i + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        countDownTimer.cancel()

    }

}
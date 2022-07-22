package ru.geekbrains.myappmaterialdesign.view.fragmenttext

import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.os.*
import android.text.*
import android.text.style.*
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentTextBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TextFragment : Fragment() {

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!
    private var isFabOpen = false
    private lateinit var spannableString: SpannableString
    private lateinit var countDownTimer: CountDownTimer

    companion object {
        @JvmStatic
        fun newInstance() = TextFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFabOpen) isFabOpen = false
        (binding.fabToolBarSF.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            ButtonBehavior(requireContext())

        if (binding.fabToolBarSF.alpha == 0f) {
            binding.fabToolBarSF.isClickable = false
            binding.fabToolBarSF.isFocusable = false
        }

        binding.fabSecondSF.setOnClickListener { myFabSecondTextFragment() }
        binding.fabEarthSF.setOnClickListener { navigateTo(EarthPictureFragment()) }
        binding.fabMarsSF.setOnClickListener { navigateTo(MarsPictureFragment()) }
        binding.fabSystemSF.setOnClickListener { navigateTo(SystemPictureFragment()) }

        with(binding) {
            textviewText.gravity = Gravity.CENTER_HORIZONTAL
            textviewText.typeface =
                Typeface.createFromAsset(requireActivity().assets, "myfont/az_Eret1.ttf")
            textviewText.textSize = 20f
        }

//        getSpannableStringHtml()
//        getSpannableString()
//        getSomeSpannableText()
//        getSpannableStringBuild()
        spannableString = SpannableString(getString(R.string.lorem_ipsum))
        binding.textviewText.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableString = binding.textviewText.text as SpannableString
        getStrikeThroughSpan()
        rainbow(1)
    }

    private fun myFabSecondTextFragment() {
        isFabOpen = !isFabOpen
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabSecondSF, View.ROTATION, 0f, 360f)
                .setDuration(500L).start()
            binding.fabSecondSF.setImageResource(R.drawable.ic_back_fab)
            binding.fabEarthSF.show()
            binding.fabMarsSF.show()
            binding.fabSystemSF.show()
        } else {
            ObjectAnimator.ofFloat(binding.fabSecondSF, View.ROTATION, 360f, 0f)
                .setDuration(500L).start()
            binding.fabSecondSF.setImageResource(R.drawable.ic_plus_fab)
            binding.fabEarthSF.hide()
            binding.fabMarsSF.hide()
            binding.fabSystemSF.hide()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).addToBackStack(getString(R.string.empty)).commit()
    }

    /**
     *  TODO Span- помогает связаться с textview и не пересоздавать textview каждый раз
    // 1) Spanned - хранит ссылку на результат работы SS, SSB.
    // 2) SpannableString - умеет применять к тексту все возможные span, но не умеет к строке
    //    новый текст прибавлять или удалять из строки, только стилизирует(декорирует) строку.
    // 3) SpannableStringBuilder - умеет хранить ссылку, работать со всеми span, умеет изменять
    //    текст, т.е. к строке новый текст прибавлять или удалять из строки.
     */

    private fun getSpannableStringHtml() {
        val spanned: Spanned
        val spannableString: SpannableString
        val spannableStringBuilder: SpannableStringBuilder

        /* html */
        val text = "My text <ul><li>bullet one</li><li>bullet two</li></ul>"
        with(binding) {
            textviewText.gravity = Gravity.CENTER_HORIZONTAL
            textviewText.text = Html.fromHtml(text)
        }
    }

    private fun getSpannableString() {
        /* создается один раз, один span - применяется максимум в одной позиции.
         Если нужно больше, то надо создавать ещё один BulletSpan.*/
        val spannableString: SpannableString
        val text = "My text \nbullet one\nbullet two"
        spannableString = SpannableString(text)

        /** рисуем точки-параграф */
        val bulletSpanOne = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple), 20)
        } else {
            BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple))
        }
        val bulletSpanTwo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple), 20)
        } else {
            BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple))
        }
        spannableString.setSpan(bulletSpanOne, 9, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(bulletSpanTwo, 20, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        /** все буквы t красного цвета */
        for (i in text.indices) {
            if (text[i] == 't') {
                spannableString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        /** векторная картинка */
        val verticalAlignment = DynamicDrawableSpan.ALIGN_CENTER
        val bitmap = ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)!!.toBitmap()
        for (i in text.indices) {
            if (text[i] == 'o') {
                spannableString.setSpan(
                    ImageSpan(requireContext(), bitmap, verticalAlignment),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        /** разноцветная картинка */
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_earth)!!
        val width = 90
        val height = 90
        drawable.setBounds(0, 0, width, height)
        for (i in text.indices) {
            if (text[i] == 'e') {
                spannableString.setSpan(
                    ImageSpan(drawable, verticalAlignment),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        /** зачеркивает текст */
        spannableString.setSpan(StrikethroughSpan(), 7, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        /** закрашивает фон */
        spannableString.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(requireContext(), R.color.yellow)),
            7,
            10,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textviewText.text = spannableString
    }

    private fun getSomeSpannableText() {
        val spannableString: SpannableString
        val text =
            "My text \nbullet one \nbulleterter two\nbullet wetwwefrtweteone \nbullet wetwettwo\nbullet wetwetwone \nbullet two"

        /** инициализируем  */
        spannableString = SpannableString(text)

        /** получаем все индексы переноса в нашей строке*/
        val result: List<Int> = text.indexesOf("\n")

        /** идем по списку и запоминаем текущий элемент. Изначально текущий элемент берем
         * как первый элемент. Если нужно начать с самого начала, пишем 0.
         * */
        var current = result.first()

        /** проходим циклом по всем индексам-переносов и на эти переносы накладываем
         * параграф(промежуток от переноса до переноса)
         * */
        result.forEach {
            if (current != it) { // TODO первый индекс-переноса пропускаем
                spannableString.setSpan(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple), 20)
                    } else {
                        BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple))
                    },
                    current + 1,
                    it,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            current = it
            /** Итог нашли все индесы */
        }

        /** Если строка начинается со \n ставим точку-параграф  */
        spannableString.setSpan(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple), 20)
            } else {
                BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.purple))
            },
            current + 1,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        with(binding) {
            textviewText.gravity = Gravity.START
            textviewText.text = spannableString
        }

    }

    /** extension-функция для строки-String. На вход подается большая строка, в которой ищем
     * субстроку - substr, находим все подстроки - findAll() и получаем массив из подстрок, далее
     * переводим этот массив в новый список - .map{}, это список из номеров позиций(вхождения)
     * нашей субстроки в большую строку.
     * */
    private fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this)
            .map { it.range.first }.toList()

    private fun getSpannableStringBuild() {
        val text = "My text \nbullet one\nbullet two"
        var spannableStringBuild = SpannableStringBuilder(text)

        /**
         * TextView.BufferType.SPANNABLE - spannableStringBuild
         * TextView.BufferType.EDITABLE - spannableString + spannableStringBuild
         * TextView.BufferType.NORMAL - spannableString
         * */
        // TODO ресурсоемкий вызов для применения изменений:
        //        binding.textviewText.text = spannableStringBuild
        //  Лучше и более быстрый вызов, который напишем только один раз:
        binding.textviewText.setText(spannableStringBuild, TextView.BufferType.EDITABLE)
        spannableStringBuild = binding.textviewText.text as SpannableStringBuilder

        spannableStringBuild.setSpan(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
                BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.blue), 20) else
                BulletSpan(10, ContextCompat.getColor(requireContext(), R.color.blue)),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        for (i in text.indices) {
            /** flag : если это место(индекс в массиве)  уже было как-то было отмечено,
             * то из-за флага при добавлении, замене, удалении текста это изменения применятся
             * к тексту, который займет этот индекс.
             * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE - исключить изменения и справа и слева
             * Spannable.SPAN_INCLUSIVE_INCLUSIVE - включить изменения и справа и слева
             * Spannable.SPAN_INCLUSIVE_EXCLUSIVE - включить изменениясправа и исключить изменения слева
             * Spannable.SPAN_EXCLUSIVE_INCLUSIVE - исключить изменения справа и включить изменения слева
             * */
            if (text[i] == 't') {
                spannableStringBuild.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue)),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        /**
         * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE - word - будет черным цветом
         * Spannable.SPAN_INCLUSIVE_INCLUSIVE - word - будет голубым цветом
         * Spannable.SPAN_INCLUSIVE_EXCLUSIVE - word - будет голубым цветом
         * Spannable.SPAN_EXCLUSIVE_INCLUSIVE - word - будет черным цветом
         * */
        spannableStringBuild.insert(3, "Brains")
        spannableStringBuild.replace(3, 4, "GeekB")

        getFontFamilySpan(spannableStringBuild)
    }

    private fun getFontFamilySpan(spannableStringBuilder: SpannableStringBuilder) {
        val request = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Black And White Picture",
            R.array.com_google_android_gms_fonts_certs
        )
        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                super.onTypefaceRetrieved(typeface)
                typeface?.let {
                    spannableStringBuilder.setSpan(
                        TypefaceSpan(it),
                        0,
                        spannableStringBuilder.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        FontsContractCompat.requestFont(
            requireContext(),
            request,
            callback,
            Handler(Looper.getMainLooper())
        )
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
        for (i in 0 until binding.textviewText.text.length) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        countDownTimer.cancel()
    }
}
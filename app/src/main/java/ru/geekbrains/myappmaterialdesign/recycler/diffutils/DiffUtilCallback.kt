package ru.geekbrains.myappmaterialdesign.recycler.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.geekbrains.myappmaterialdesign.recycler.Data

class DiffUtilCallback(
    private val oldItems: MutableList<Pair<Data, Boolean>>,
    private val newItems: MutableList<Pair<Data, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize(): Int {
        return newItems.size
    }

    //TODO ищет одинаковые между собой элементы
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    //TODO ищет различные между собой элементы
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name &&
                oldItems[oldItemPosition].first.type == newItems[newItemPosition].first.type &&
                oldItems[oldItemPosition].second == newItems[newItemPosition].second
    }

    //TODO если содержание элемента в старом и новом списках различается, то меняем старый элемент на новый
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]

//        super.getChangePayload(oldItemPosition, newItemPosition)
        //TODO есди есть различия, то записываем в Change(создаем пары)
        return Change(old, new)
    }
}

//TODO контейнер для хранения данных новых и старых
data class Change<T>(val oldData: T, val newData: T)

//TODO если пар много то задача выбрать самое сторое и самое новое
//TODO метод ищет самое новое и самое сторое
fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty()) //TODO если пришли пустые списки, то дальше не двигаться
    //TODO не берем промежуточчные состояния списка, только самый первый список(в котором нет изменений)
    // и самый последний(в котором уже есть все изменения)
    val firstChange = payloads.first() //TODO первый список
    val lastChange = payloads.last() //TODO послежний список
    //TODO возвращаем два этих состояния(два списка)
    return Change(firstChange.oldData, lastChange.newData)
}


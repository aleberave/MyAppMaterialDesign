package ru.geekbrains.myappmaterialdesign.recycler

const val TYPE_HEADER = 0
const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_SYSTEM = 3


data class Data(
    val id: Int = 0,
    val name: String = "text",
    val description: String? = "description",
    val type: Int = TYPE_HEADER
) {
}
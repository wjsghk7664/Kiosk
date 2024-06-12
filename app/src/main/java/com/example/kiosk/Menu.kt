package com.example.kiosk

abstract class Menu() {
    abstract var flag:Boolean
    abstract fun displayInfo()
    abstract fun orderList()
    abstract fun order():Int
    abstract fun changeOrder():Int
}
package com.samgithiaka.apps.foodforthehungry.data.model

class Action(private var mAction: Int = 0) {

    fun getValue(): Int {
        return mAction
    }

    companion object{
        val SHOW_CHILD = 0
        val SHOW_SPONSOR = 1
        val DELETE_CHILD = 2
        val DELETE_SPONSOR = 3
    }
}
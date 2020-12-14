package com.origogi.myapplication

enum class AppState {
    ERROR,
    LOADING,
    LOADED
}

enum class ViewType(val spanCount : Int) {
    LIST(1),
    GRID(4)
}
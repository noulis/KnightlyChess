package com.example.knighty_chess

class ChessTileInfo (val x: Int, val y: Int) {
    var isSource = false
    var isTarget = false

    fun clearSelection() {
        isTarget = false
        isSource = false
    }

}
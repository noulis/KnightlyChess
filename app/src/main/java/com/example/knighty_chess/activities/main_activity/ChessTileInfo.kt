package com.example.knighty_chess.activities.main_activity

class ChessTileInfo (val x: Int, val y: Int) {
    var isSource = false
    var isTarget = false

    fun clearSelection() {
        isTarget = false
        isSource = false
    }

}
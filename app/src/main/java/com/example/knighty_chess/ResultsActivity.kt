package com.example.knighty_chess

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "KnightlyChessResAct"

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val intent = intent
        val extras = intent.extras
        val results = extras!!.getString("results")
        val res = Results.deSerialize(results!!)
        Log.i(TAG,"Moves found (${res})")
    }
}

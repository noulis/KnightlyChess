package com.example.knighty_chess.activities.results_activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.knighty_chess.R
import com.example.knighty_chess.data.Results

private const val TAG = "KnightlyChessResAct"

class ResultsActivity : AppCompatActivity() {

    private lateinit var resultsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        resultsTextView = findViewById(R.id.resultsTextview)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val extras = intent.extras
        val resultsStr = extras?.getString("results", null)

        if (extras == null || resultsStr == null) {
            resultsTextView.setText(getString(R.string.no_results))
        } else {
            val results = Results.deSerialize(resultsStr)
            Log.i(TAG, "Moves found (${results})")
            displayResults(results)
        }
    }

    private fun displayResults(results: Results) {
        var resultsStr = ""
        for ((index, result) in results.results.withIndex()) {
            val num = index + 1
            resultsStr += "$num: ${result}\n"
        }
        resultsTextView.setText(resultsStr)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}

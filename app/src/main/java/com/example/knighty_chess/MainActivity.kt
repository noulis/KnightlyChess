package com.example.knighty_chess

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knighty_chess.ChessRecyclerViewAdapter.ChessTileSelectionListener
import com.example.knighty_chess.KnightMoveHelper.calculateMoves

class MainActivity : AppCompatActivity(), ChessTileSelectionListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var boardSizeSpinner: Spinner
    private lateinit var maxMovesSpinner: Spinner
    private lateinit var boardSizeOptionsAdapter: ArrayAdapter<Int>
    private lateinit var maxMovesAdapter: ArrayAdapter<Int>
    private var startPoint: Pair<Int, Int>? = null
    private var targetPoint: Pair<Int, Int>? = null
    private var boardSize = BOARD_SIZE_OPTIONS[DEFAULT_BOARD_SIZE_SPINNER_SELECTION]
    private var maxMoves = MAX_MOVES_OPTIONS[DEFAULT_MAX_MOVES_SPINNER_SELECTION]

    companion object {
        private val BOARD_SIZE_OPTIONS = arrayOf(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        private val MAX_MOVES_OPTIONS = arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
        private const val DEFAULT_BOARD_SIZE_SPINNER_SELECTION = 2
        private const val DEFAULT_MAX_MOVES_SPINNER_SELECTION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
    }

    private fun setUpUi() {
        setUpBoardSizeSpinner()
        setUpMaxMovesSpinner()
        setUpBoardView()
    }

    private fun setUpBoardSizeSpinner() {
        boardSizeSpinner = findViewById(R.id.boardSizeSpinner)
        boardSizeOptionsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, BOARD_SIZE_OPTIONS)
        boardSizeOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        boardSizeSpinner.adapter = boardSizeOptionsAdapter
        boardSizeSpinner.setSelection(DEFAULT_BOARD_SIZE_SPINNER_SELECTION)
        boardSize = BOARD_SIZE_OPTIONS[DEFAULT_BOARD_SIZE_SPINNER_SELECTION]
        boardSizeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val size = parent.getItemAtPosition(position) as Int
                setBoardSizeTo(size)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { // Do nothing
            }
        }
    }

    private fun setUpMaxMovesSpinner() {
        maxMovesSpinner = findViewById(R.id.maxMovesSpinner)
        maxMovesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, MAX_MOVES_OPTIONS)
        maxMovesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maxMovesSpinner.adapter = maxMovesAdapter
        maxMovesSpinner.setSelection(DEFAULT_MAX_MOVES_SPINNER_SELECTION)
        maxMoves = MAX_MOVES_OPTIONS[DEFAULT_MAX_MOVES_SPINNER_SELECTION]
        maxMovesSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val size = parent.getItemAtPosition(position) as Int
                setMaxMovesTo(size)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { // Do nothing
            }
        }
    }

    private fun setUpBoardView() {
        recyclerView = findViewById(R.id.recyclerView)
        setBoardSizeTo(boardSize)
    }

    private fun setBoardSizeTo(size: Int) {
        boardSize = size
        val adapter = ChessRecyclerViewAdapter(this, size, this)
        recyclerView.adapter = adapter
        val manager = GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager
    }

    private fun setMaxMovesTo(maxMoves: Int) {
        this.maxMoves = maxMoves
    }

    override fun onStartPointSelection(startPoint: Pair<Int, Int>) {
        this.startPoint = startPoint
    }

    override fun onTargetPointSelection(targetPoint: Pair<Int, Int>) {
        this.targetPoint = targetPoint
    }

    private fun startMoveCalculation() {
        calculateMoves(startPoint!!, targetPoint!!, boardSize, maxMoves)
    }
}
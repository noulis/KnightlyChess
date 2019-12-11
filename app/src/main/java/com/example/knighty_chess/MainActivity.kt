package com.example.knighty_chess

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knighty_chess.ChessRecyclerViewAdapter.ChessTileSelectionListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), ChessTileSelectionListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var boardSizeSpinner: Spinner
    private lateinit var maxMovesSpinner: Spinner
    private lateinit var boardSizeOptionsAdapter: ArrayAdapter<Int>
    private lateinit var maxMovesAdapter: ArrayAdapter<Int>
    private lateinit var chessAdapter:ChessRecyclerViewAdapter
    private var disposable:Disposable? = null
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

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
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

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun setUpBoardView() {
        recyclerView = findViewById(R.id.recyclerView)
        setBoardSizeTo(boardSize)
    }

    private fun setBoardSizeTo(size: Int) {
        boardSize = size
        chessAdapter= ChessRecyclerViewAdapter(this, size, this)
        recyclerView.adapter = chessAdapter
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
        onCalculateMovesButtonClicked()
    }

    private fun resetBoardInfo(){
        chessAdapter.clearSelections()
        startPoint = null
        targetPoint = null
    }

    private fun onCalculateMovesButtonClicked(){
        if (startPoint == null){
            Toast.makeText(this,"Please set a starting point",Toast.LENGTH_LONG).show()
            return
        }

        if (targetPoint == null){
            Toast.makeText(this,"Please set a target point",Toast.LENGTH_LONG).show()
            return
        }

        startMoveCalculation()
    }

    private fun startMoveCalculation() {
        Log.i("xaxa","Starting move calculation")
        disposable = KnightMoveHelper.getCalculateMovesObservable(startPoint!!, targetPoint!!, boardSize, maxMoves)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(::onMoveCalculationCancelled)
                .subscribe(::onMoveCalculationResult)
       // calculateMoves(startPoint!!, targetPoint!!, boardSize, maxMoves)
    }

    private fun onMoveCalculationResult(moves:Set<List<Pair<Int, Int>>>){
        Log.i("xaxa","Move calculation finished")
        if (moves.isEmpty()){
            // Handle no moves
            Log.w("xaxa","No moves found")
        }
        else{
            // Handle moves result
            Log.i("xaxa","Moves found (${moves.size})")
            Log.i("xaxa","Moves -> (${moves.toString()})")
        }
    }

    private fun onMoveCalculationCancelled(){

    }

}
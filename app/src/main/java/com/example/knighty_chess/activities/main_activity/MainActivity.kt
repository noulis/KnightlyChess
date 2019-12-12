package com.example.knighty_chess.activities.main_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knighty_chess.data.KnightMoveHelper
import com.example.knighty_chess.widgets.RoundSpinnerWithHint
import com.example.knighty_chess.R
import com.example.knighty_chess.activities.main_activity.ChessRecyclerViewAdapter.ChessTileSelectionListener
import com.example.knighty_chess.activities.results_activity.ResultsActivity
import com.example.knighty_chess.data.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private const val TAG = "KnightlyChessMainAct"

class MainActivity : AppCompatActivity(), ChessTileSelectionListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chessAdapter: ChessRecyclerViewAdapter
    private lateinit var boardSizePropertyTab: RoundSpinnerWithHint
    private lateinit var maxMovesPropertyTab: RoundSpinnerWithHint

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
        boardSizePropertyTab = findViewById(R.id.board_size_tab)
        boardSizePropertyTab.setAdapterAndSpinnerOptions(BOARD_SIZE_OPTIONS)
        boardSize = BOARD_SIZE_OPTIONS[DEFAULT_BOARD_SIZE_SPINNER_SELECTION]
        boardSizePropertyTab.selectedItemPosition = DEFAULT_BOARD_SIZE_SPINNER_SELECTION
        boardSizePropertyTab.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val size = parent.getItemAtPosition(position) as Int
                setBoardSizeTo(size)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        })
    }

    private fun setUpMaxMovesSpinner() {
        maxMovesPropertyTab =  findViewById(R.id.max_moves_tab)
        maxMovesPropertyTab.setAdapterAndSpinnerOptions(MAX_MOVES_OPTIONS)
        maxMovesPropertyTab.selectedItemPosition = DEFAULT_MAX_MOVES_SPINNER_SELECTION
        maxMovesPropertyTab.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val size = parent.getItemAtPosition(position) as Int
                setMaxMovesTo(size)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        })
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
        Log.i(TAG,"Starting move calculation")
        disposable = KnightMoveHelper.getCalculateMovesObservable(startPoint!!, targetPoint!!, boardSize, maxMoves)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(::onMoveCalculationCancelled)
                .subscribe(::onMoveCalculationResult)
       // calculateMoves(startPoint!!, targetPoint!!, boardSize, maxMoves)
    }

    private fun onMoveCalculationResult(moves:Set<List<Pair<Int, Int>>>){
        Log.i(TAG,"Move calculation finished")
        if (moves.isEmpty()){
            // Handle no moves
            Log.w(TAG,"No moves found")
            Toast.makeText(this,"No moves found.",Toast.LENGTH_LONG).show()
        }
        else{
            // Handle moves result
            val numberOfResults = moves.size

            Log.i(TAG,"Moves found (${numberOfResults})")
            Log.i(TAG,"Moves -> (${moves.toString()})")
            Log.i(TAG,"Moves sorted -> (${moves.sortedBy {it.size }.toString()})")

            val intent  = Intent(this, ResultsActivity::class.java)

            var res: Results

            res = if (numberOfResults > 500) {
                Log.i(TAG,"Too many results. Keeping only the first 500")
                Results(moves.sortedBy { it.size }.take(500).toSet())

            } else{
                Results(moves.sortedBy { it.size }.toSet())
            }

            intent.putExtra("results", Results.serialize(res))
            startActivity(intent)
        }
    }

    private fun onMoveCalculationCancelled(){
        Log.w(TAG,"Move calculation canceled")
    }

}
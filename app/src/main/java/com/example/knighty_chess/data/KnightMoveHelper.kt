package com.example.knighty_chess.data

import io.reactivex.Single

private const val TAG = "KnightlyChessMoveHelper"

object KnightMoveHelper {

    private var boardSize:Int = 7
    private var maximumNumberOfMoves = 8
    private var paths = mutableSetOf<List<Pair<Int,Int>>>()

    // All possible moves of a knight
    private val xAxisMoves = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
    private val yAxisMoves = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)
    private const val possibleMovesNum = 8


    /**
     * Get a Single (Rx Java Single Observable type) responsible for calculating the moves based on the provided data
     *
     * Note: This is CPU intensive work so the observable must be subscribed on a thread other than the UI thread
     */
    public fun getCalculateMovesObservable (startingPoint:Pair<Int,Int>, targetPoint:Pair<Int,Int>, boardSize:Int, maximumNumberOfMoves:Int): Single<Set<List<Pair<Int, Int>>>> {
        KnightMoveHelper.boardSize = boardSize
        KnightMoveHelper.maximumNumberOfMoves = maximumNumberOfMoves
        return Single.fromCallable {
            paths.clear()
            val emptyListOfMoves = listOf<Pair<Int,Int>>()
            visit(startingPoint, targetPoint, emptyListOfMoves, maximumNumberOfMoves)
            paths.toSet()
        }
    }

    /**
     * Recursive function that finds all the possible paths that a knight can follow
     * in order to reach the designated target.
     * The result is stored in the path variable that is global in the context of this file
     * as there is no need to carry it around as an argument of the function.
     *
     * Caution: This function is heavy on processing time. It must me ran from a thread in order
     * not to block the UI thread.
     */
    private fun visit(startingPoint:Pair<Int,Int>, targetPoint:Pair<Int,Int>, pastMovesList:List<Pair<Int,Int>>, remainingMoves:Int){

        if (!pointIsLegit(startingPoint)){
            return
        }

        if (pastMovesList.contains(startingPoint)){
            return
        }

        val pastMovesListNew:MutableList<Pair<Int,Int>> = mutableListOf()
        pastMovesListNew.addAll(pastMovesList)
        pastMovesListNew.add(startingPoint)

        if (reachedTarget(startingPoint, targetPoint)){
            paths.add(pastMovesListNew)
            return

        }

        if (remainingMoves == 0){
            return
        }

        // Call the function recursively for all possible moves
        for (i in (0 until possibleMovesNum)){
            val newS = Pair(startingPoint.first+ xAxisMoves[i], startingPoint.second+ yAxisMoves[i])
            visit(newS, targetPoint, pastMovesListNew.toList(), remainingMoves - 1)
        }

    }

    /**
     * Check if the point is legit.
     * @return true if point is inside the board, false otherwise
     */
    private fun pointIsLegit(point:Pair<Int,Int>) : Boolean{
        val x = point.first
        val y = point.second

        return !(x < 0 || y< 0 || x> boardSize -1 || y> boardSize -1)
    }

    /**
     * Check if the starting point matches the target point
     * @return true the points match, false otherwise
     */
    private fun reachedTarget(startingPoint:Pair<Int,Int>, targetPoint:Pair<Int,Int>):Boolean{
        return (startingPoint.first == targetPoint.first && startingPoint.second ==targetPoint.second)
    }

}
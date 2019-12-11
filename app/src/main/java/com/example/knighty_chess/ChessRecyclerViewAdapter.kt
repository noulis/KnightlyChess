package com.example.knighty_chess

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "KnightlyChessAdapter"

class ChessRecyclerViewAdapter(private val mContext: Context, boardDimension: Int, private val mListener: ChessTileSelectionListener) : RecyclerView.Adapter<ChessRecyclerViewAdapter.ViewHolder>() {
    private val data: ArrayList<ChessTileInfo> = ArrayList()
    private var source = -1
    private var target = -1

    init {
        for (y in 0 until boardDimension) {
            for (x in 0 until boardDimension) {
                data.add(ChessTileInfo(x, y))
            }
        }
    }

    fun clearSelections() {
        source = -1
        target = -1

        for (tileInfo in data) {
            tileInfo.clearSelection()
        }

        notifyDataSetChanged()
    }

    private fun handleClickInternally(tileInfo: ChessTileInfo?, position: Int) {
        if (source == position || target == position) { //Already selected
            return
        }
        if (source == -1) {
            tileInfo!!.isSource = true
            source = position
        } else if (target == -1) {
            tileInfo!!.isTarget = true
            target = position
        } else {
            //do nothing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(Vholder: ViewHolder, position: Int) {
        Vholder.setData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var textView: TextView
        private var imageView: ImageView
        private var relativeLayout: RelativeLayout
        private var chessTileInfoItem: ChessTileInfo? = null

        fun setData(chessTileInfoItem: ChessTileInfo) {
            this.chessTileInfoItem = chessTileInfoItem

            if (chessTileInfoItem.isSource) {
                relativeLayout.setBackgroundColor(Color.parseColor("#E9967A"))
            } else if (chessTileInfoItem.isTarget) {
                relativeLayout.setBackgroundColor(Color.parseColor("#8B0000"))
            } else {
                val isBlack = (chessTileInfoItem.x + chessTileInfoItem.y) % 2 == 0
                if (isBlack) {
                    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                } else {
                    relativeLayout.setBackgroundColor(Color.parseColor("#000000"))
                }
            }
        }

        override fun onClick(view: View) {
            Log.e(TAG, "Adapter position = $adapterPosition")
            handleClickInternally(chessTileInfoItem, adapterPosition)
            notifyItemChanged(adapterPosition)

            if (source == adapterPosition) {
                mListener.onStartPointSelection(Pair(chessTileInfoItem!!.x, chessTileInfoItem!!.y))
            } else if (target == adapterPosition) {
                mListener.onTargetPointSelection(Pair(chessTileInfoItem!!.x, chessTileInfoItem!!.y))
            } else {
                // Nothing for now
            }
        }

        init {
            view.setOnClickListener(this)
            textView = view.findViewById(R.id.textView)
            imageView = view.findViewById(R.id.imageView)
            relativeLayout = view.findViewById(R.id.relativeLayout)
        }
    }

    interface ChessTileSelectionListener {
        fun onStartPointSelection(startPoint: Pair<Int, Int>)
        fun onTargetPointSelection(targetPoint: Pair<Int, Int>)
    }

}
package com.example.knighty_chess;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ChessTileInfo> data;
    private Context mContext;
    private ItemListener mListener;
    private int boardDimension;
    private int source = -1;
    private int target = -1;

    public RecyclerViewAdapter(Context context, int boardDimension, ItemListener itemListener) {
        this.boardDimension = boardDimension;
        mContext = context;
        mListener = itemListener;
        data = new ArrayList<>();
        for (int y = 0; y < boardDimension; y++) {
            for (int x = 0; x < boardDimension; x++) {
                data.add(new ChessTileInfo(x, y));
            }
        }
    }

    public void clearSelections() {
        this.source = -1;
        this.target = -1;
        for (ChessTileInfo tileInfo : data) {
            tileInfo.clearSelection();
        }
        notifyDataSetChanged();
    }

    private void handleClickInternally(ChessTileInfo tileInfo, int position) {
        if (source == position || target == position) {
            //Already selected
            return;
        }

        if (source == -1) {
            tileInfo.setSource(true);
            source = position;
        } else if (target == -1) {
            tileInfo.setTarget(true);
            target = position;
        } else {
            //do nothing
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemListener {
        void onItemClick(Pair<Integer, Integer> pairClicked);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;
        RelativeLayout relativeLayout;
        ChessTileInfo item;

        ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }

        void setData(ChessTileInfo item) {
            this.item = item;

            if (item.isSource()) {
                relativeLayout.setBackgroundColor(Color.parseColor("#E9967A"));
            } else if (item.isTarget()) {
                relativeLayout.setBackgroundColor(Color.parseColor("#8B0000"));
            } else {

                boolean isBlack = (item.getX() + item.getY()) % 2 == 0;

                if (isBlack) {
                    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                Log.e("xaxa", "Adapter position = " + getAdapterPosition());
                // mListener.onItemClick(item);
                handleClickInternally(item, getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
                mListener.onItemClick(new Pair(item.getX(), item.getY()));
            }
        }

    }
}
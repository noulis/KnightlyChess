package com.example.knighty_chess;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {


    RecyclerView recyclerView;
    Spinner boardSizeSpinner;
    Spinner maxMovesSpinner;

    Integer[] boardSizeOptions = new Integer[]{6,7,8,9,10,11,12,13,14,15,16};
    ArrayAdapter<Integer> boardSizeOptionsAdapter;

    Integer[] maxMovesOptions = new Integer[]{2,3,4,5,6,7,8,9,10};
    ArrayAdapter<Integer> maxMovesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boardSizeSpinner = (Spinner)  findViewById(R.id.boardSizeSpinner);
        boardSizeOptionsAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, boardSizeOptions);
        boardSizeOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(boardSizeOptionsAdapter);
        boardSizeSpinner.setSelection(2);
        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int size = (Integer) parent.getItemAtPosition(position);
                setBoardSizeTo(size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        maxMovesSpinner = (Spinner)  findViewById(R.id.maxMovesSpinner);
        maxMovesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, maxMovesOptions);
        maxMovesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxMovesSpinner.setAdapter(maxMovesAdapter);
        maxMovesSpinner.setSelection(1);
        maxMovesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int size = (Integer) parent.getItemAtPosition(position);
                setMaxMovesTo(size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, 5, this);
        recyclerView.setAdapter(adapter);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(Pair<Integer,Integer> item) {

        Toast.makeText(getApplicationContext(), item.toString() + " is clicked", Toast.LENGTH_SHORT).show();

    }


    private void setBoardSizeTo(int size){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, size, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void setMaxMovesTo(int maxMoves){

    }


}

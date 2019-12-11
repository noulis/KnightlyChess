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
    private RecyclerView recyclerView;
    private Spinner boardSizeSpinner;
    private Spinner maxMovesSpinner;
    private int boardSize = BOARD_SIZE_OPTIONS[DEFAULT_BOARD_SIZE_SPINNER_SELECTION];
    private int maxMoves = MAX_MOVES_OPTIONS[DEFAULT_MAX_MOVES_SPINNER_SELECTION];

    private ArrayAdapter<Integer> boardSizeOptionsAdapter;
    private ArrayAdapter<Integer> maxMovesAdapter;

    private static final Integer[] BOARD_SIZE_OPTIONS = new Integer[]{6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static final Integer[] MAX_MOVES_OPTIONS = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int DEFAULT_BOARD_SIZE_SPINNER_SELECTION = 2;
    private static final int DEFAULT_MAX_MOVES_SPINNER_SELECTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUi();
    }

    @Override
    public void onItemClick(Pair<Integer, Integer> item) {

        Toast.makeText(getApplicationContext(), item.toString() + " is clicked", Toast.LENGTH_SHORT).show();
    }

    private void setUpUi() {
        setUpBoardSizeSpinner();
        setUpMaxMovesSpinner();
        setUpBoardView();
    }

    private void setUpBoardSizeSpinner() {
        boardSizeSpinner = findViewById(R.id.boardSizeSpinner);
        boardSizeOptionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BOARD_SIZE_OPTIONS);
        boardSizeOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(boardSizeOptionsAdapter);
        boardSizeSpinner.setSelection(DEFAULT_BOARD_SIZE_SPINNER_SELECTION);
        boardSize = BOARD_SIZE_OPTIONS[DEFAULT_BOARD_SIZE_SPINNER_SELECTION];
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
    }

    private void setUpMaxMovesSpinner() {
        maxMovesSpinner = findViewById(R.id.maxMovesSpinner);
        maxMovesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MAX_MOVES_OPTIONS);
        maxMovesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxMovesSpinner.setAdapter(maxMovesAdapter);
        maxMovesSpinner.setSelection(DEFAULT_MAX_MOVES_SPINNER_SELECTION);
        maxMoves = MAX_MOVES_OPTIONS[DEFAULT_MAX_MOVES_SPINNER_SELECTION];
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
    }

    private void setUpBoardView() {
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, boardSize, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, boardSize, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void setBoardSizeTo(int size) {
        boardSize = size;
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, size, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void setMaxMovesTo(int maxMoves) {
        this.maxMoves = maxMoves;
    }


}

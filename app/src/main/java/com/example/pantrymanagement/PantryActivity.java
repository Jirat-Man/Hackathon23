package com.example.pantrymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pantrymanagement.adaptor.PantryAdaptor;

public class PantryActivity extends AppCompatActivity {

    ViewModel mViewModel;
    PantryAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        this.setTitle("Pantry");//set title of activity

        //set up recycler onto the page
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptor = new PantryAdaptor();
        recyclerView.setAdapter(adaptor);

        adaptor = new PantryAdaptor();
        recyclerView.setAdapter(adaptor);

        //initialise runViewModel
        mViewModel = new ViewModelProvider(this).get(ViewModel.class);
        //set all the card views to the runEntities in the database
        mViewModel.getAllItem().observe(this, runEntities -> adaptor.setPantryEntities(runEntities));

        //Slide to delete item from the database
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            // Create an alert dialog to ask user for confirmation of deletion
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantryActivity.this);
                builder.setMessage("Are you sure you want to delete?")
                        .setTitle("Delete")
                        .setPositiveButton("Confirm", (dialog, id) -> {
                            mViewModel.Delete(adaptor.getPantryAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(PantryActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", (dialog, id) -> {
                            // Do nothing if user cancels;
                            mViewModel.Update(adaptor.getPantryAt((viewHolder.getAdapterPosition())));
                        });

                // Create the AlertDialog object and return it
                builder.create().show();
            }
        }).attachToRecyclerView(recyclerView);

        adaptor.setUpListener(runEntity -> {

        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UpdatePantry();
    }

    private void UpdatePantry() {
    }
}
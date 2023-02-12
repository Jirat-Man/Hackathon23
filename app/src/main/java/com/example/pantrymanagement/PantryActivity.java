package com.example.pantrymanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pantrymanagement.adaptor.PantryAdaptor;

public class PantryActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
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

        if(getIntent().hasExtra("WHATSHOULDIGET")){
            Toast.makeText(this, "You have items that are expiring soon!", Toast.LENGTH_SHORT).show();
            mViewModel.getItemByExpiry().observe(this, pantryEntities -> adaptor.setPantryEntities(pantryEntities));
        }
    }



    //inflate the menu with items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //handle menu click events
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_items:
                AlertDialog.Builder builder = new AlertDialog.Builder(PantryActivity.this);
                builder.setMessage(R.string.confirm_delete_all_run)
                        .setTitle(R.string.delete_all_run)
                        .setPositiveButton(R.string.confirm, (dialog, id) -> {
                            mViewModel.DeleteAll();
                            Toast.makeText(PantryActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.cancel, (dialog, id) -> {
                            //do nothing
                        });

                // Create the AlertDialog object and return it
                builder.create().show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UpdatePantry();
    }

    private void UpdatePantry() {
    }
}
package com.example.pantrymanagement.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PantryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PantryEntity item);

    @Update
    void update(PantryEntity item);

    @Delete
    void delete(PantryEntity item);

    @Query("DELETE FROM pantry_item")
    void deleteAll();

    @Query("Select * From pantry_item ORDER BY item_id DESC")
    LiveData<List<PantryEntity>> getItemsByIdDesc();

    //select food expiry date 2 or less days away
    @Query("Select * FROM pantry_item WHERE days_to_expiry <= 2")
    LiveData<List<PantryEntity>>  getAlmostExpiredItem();

    //order food ordered by calories descending
    @Query("Select * FROM pantry_item ORDER BY calories DESC")
    LiveData<List<PantryEntity>> getItemsByCaloriesDESC();

    //order food ordered by price descending
    @Query("Select * FROM pantry_item ORDER BY price DESC")
    LiveData<List<PantryEntity>> getItemsByPriceDESC();

    //order food ordered by calories ascending
    @Query("Select * FROM pantry_item ORDER BY calories ASC")
    LiveData<List<PantryEntity>> getItemsByCaloriesASC();

    //order food ordered by price ascending
    @Query("Select * FROM pantry_item ORDER BY price ASC")
    LiveData<List<PantryEntity>> getItemsByPriceASC();

    //order food ordered by carbon descending
    @Query("Select * FROM pantry_item ORDER BY carbon_footprint DESC")
    LiveData<List<PantryEntity>> getItemsByCarbonDESC();

    //order food ordered by carbon ascending
    @Query("Select * FROM pantry_item ORDER BY carbon_footprint ASC")
    LiveData<List<PantryEntity>> getItemsByCarbonASC();
}

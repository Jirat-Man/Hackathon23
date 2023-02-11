package com.example.pantrymanagement.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry_item")       //Name of table
public class PantryEntity {

    // Id of the item, also acts as primary key
    @PrimaryKey(autoGenerate = true)//auto generate ID
    @ColumnInfo(name = "item_id")
    private int id;

    @ColumnInfo(name = "item_name")
    private final String name;

    @ColumnInfo(name = "expiry_date")
    private final String expiryDate;

    @ColumnInfo(name = "comment")
    private final String comment;

    @ColumnInfo(name = "item_image")
    private final byte[] image;

    public int getId() {
        return id;
    }

    @ColumnInfo(name = "price")
    private final float price;

    @ColumnInfo(name = "calories")
    private final float calories;

    @ColumnInfo(name = "purchase_date")
    private final String purchaseDate;

    @ColumnInfo(name = "carbon_footprint")
    private final float carbonFootprint;

    @ColumnInfo(name = "days_to_expiry")
    private final float days_to_expiry;


    public void setId(int id) {
        this.id = id;
    }

    public PantryEntity(String name, String expiryDate, String comment, byte[] image, float price, float calories, String purchaseDate, float carbonFootprint, float days_to_expiry) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.comment = comment;
        this.image = image;
        this.price = price;
        this.calories = calories;
        this.purchaseDate = purchaseDate;
        this.carbonFootprint = carbonFootprint;
        this.days_to_expiry = days_to_expiry;
    }



    public String getName() {
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getComment() {
        return comment;
    }

    public float getDays_to_expiry() {
        return days_to_expiry;
    }

    public byte[] getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public float getCalories() {
        return calories;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public float getCarbonFootprint() {
        return carbonFootprint;
    }
}

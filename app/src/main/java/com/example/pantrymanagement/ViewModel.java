package com.example.pantrymanagement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pantrymanagement.repository.PantryRepository;
import com.example.pantrymanagement.room.PantryEntity;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private PantryRepository mRepository;

    private LiveData<List<PantryEntity>> mAllItem;

    public ViewModel(@NonNull Application application) {
        super(application);
        init(application);
    }

    public void init(Application application) {
        mRepository = new PantryRepository(application);
    }

    public LiveData<List<PantryEntity>> getAllItem(){
        this.mAllItem = mRepository.GetAllItems();
        return mAllItem;
    }

    //insert run entity into database
    public void Insert(PantryEntity pantryEntity) {
        mRepository.Insert(pantryEntity);
    }

    //Update run entity into database
    public void Update(PantryEntity pantryEntity) {
        mRepository.Update(pantryEntity);
    }

    //Delete run entity into database
    public void Delete(PantryEntity pantryEntity) {
        mRepository.Delete(pantryEntity);
    }

    public void DeleteAll(){mRepository.DeleteAll();}
}

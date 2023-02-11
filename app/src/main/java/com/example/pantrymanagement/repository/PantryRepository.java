package com.example.pantrymanagement.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pantrymanagement.room.PantryDao;
import com.example.pantrymanagement.room.PantryEntity;
import com.example.pantrymanagement.room.PantryRoomDatabase;

import java.util.List;

public class PantryRepository {

    private final PantryDao mPantryDao;
    private LiveData<List<PantryEntity>> mAllItems;

    public PantryRepository(Application application) {
        PantryRoomDatabase db = PantryRoomDatabase.getInstance(application);
        mPantryDao = db.pantryDao();
    }

    //notify Dao to Insert Entity into roomDatabase
    public void Insert(PantryEntity pantryEntity) {
        new insertAsyncTask(mPantryDao).execute(pantryEntity);
    }

    //notify Dao to Update Entity into roomDatabase
    public void Update(PantryEntity pantryEntity) {
        new UpdateAsyncTask(mPantryDao).execute(pantryEntity);
    }

    //notify Dao to Delete Entity into rommDatabase
    public void Delete(PantryEntity pantryEntity) {
        PantryRoomDatabase.databaseWriteExecutor.execute(() -> mPantryDao.delete(pantryEntity));
    }

    public LiveData<List<PantryEntity>> GetAllItems() {
        mAllItems = mPantryDao.getItemsByIdDesc();
        return mAllItems;
    }

    public LiveData<List<PantryEntity>> GetItemsByCaloriesDESC() {
        mAllItems = mPantryDao.getItemsByCaloriesDESC();
        return mAllItems;
    }

    //Insert Entity in Background
    private static class insertAsyncTask extends AsyncTask<PantryEntity, Void, Void> {
        private final PantryDao pantryDao;

        insertAsyncTask(PantryDao taskDao) {
            pantryDao = taskDao;
        }

        @Override
        protected Void doInBackground(PantryEntity... pantryEntities) {
            pantryDao.insert(pantryEntities[0]);
            return null;
        }
    }

    //Update Entity in Background
    private static class UpdateAsyncTask extends AsyncTask<PantryEntity, Void, Void> {
        private final PantryDao pantryDao;

        UpdateAsyncTask(PantryDao taskDao) {
            this.pantryDao = taskDao;
        }

        @Override
        protected Void doInBackground(PantryEntity... pantryEntities) {
            pantryDao.update(pantryEntities[0]);
            return null;
        }
    }
}

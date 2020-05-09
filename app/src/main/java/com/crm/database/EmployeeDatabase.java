package com.crm.database;


import android.content.Context;
import android.os.AsyncTask;

import com.crm.Utils.Constants;
import com.crm.Utils.DateRoomConverter;
import com.crm.database.dao.EmployeeDao;
import com.crm.database.entity.EmployeeEntity;
import com.crm.database.entity.JobEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = { EmployeeEntity.class, JobEntity.class }, version = 5)
@TypeConverters({DateRoomConverter.class})
public abstract class EmployeeDatabase extends RoomDatabase {


    public abstract EmployeeDao getEmployeeDao();


    private static EmployeeDatabase sEmployeeDBInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    // synchronized is use to avoid concurrent access in multithred environment
    public static  EmployeeDatabase getInstance(Context context) {
        if (null == sEmployeeDBInstance) {
            synchronized (EmployeeDao.class) {
                if (null == sEmployeeDBInstance) {
                    sEmployeeDBInstance = buildDatabaseInstance(context);
                }
             }
        }
        return sEmployeeDBInstance;
    }

    private static EmployeeDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                EmployeeDatabase.class,
                Constants.DATABASE_NAME).
                addCallback(getEmployeeDataCallback).
//                addCallback(getJobDataCallback).
                fallbackToDestructiveMigration().build();
//                allowMainThreadQueries().build();
    }

    private static RoomDatabase.Callback getEmployeeDataCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sEmployeeDBInstance).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final EmployeeDao mDao;
        PopulateDbAsync(EmployeeDatabase db) {
            mDao = db.getEmployeeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.getAllEmployees();
            return null;
        }
    }

    private static RoomDatabase.Callback getJobDataCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sEmployeeDBInstance).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateJobAsync extends AsyncTask<Void, Void, Void> {

        private final EmployeeDao mDao;
        PopulateJobAsync(EmployeeDatabase db) {
            mDao = db.getEmployeeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.getAllJobs();
            return null;
        }
    }



  /*  static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };*/

    public  void cleanUp(){
        sEmployeeDBInstance = null;
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
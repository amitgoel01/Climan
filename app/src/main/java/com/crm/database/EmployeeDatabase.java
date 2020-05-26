package com.crm.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.crm.Utils.Constants;
import com.crm.Utils.DateRoomConverter;
import com.crm.database.dao.ClientGroupDao;
import com.crm.database.dao.EmployeeDao;
import com.crm.database.dao.JobDao;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.database.entity.EmployeeEntity;
import com.crm.database.entity.JobEntity;
import com.crm.model.ClientGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = { EmployeeEntity.class, JobEntity.class, ClientGroupEntity.class}, version = 12)
@TypeConverters({DateRoomConverter.class})
public abstract class EmployeeDatabase extends RoomDatabase {


    public abstract EmployeeDao getEmployeeDao();
    public abstract JobDao getJobDao();
    public abstract ClientGroupDao getClientGroupDao();

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
                fallbackToDestructiveMigration().build();
//                allowMainThreadQueries().build();
    }

    private static RoomDatabase.Callback getEmployeeDataCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(sEmployeeDBInstance).execute();
                    new PopulateJobAsync(sEmployeeDBInstance).execute();
                    new PopulateClientGroupAsync(sEmployeeDBInstance).execute();
                }
            };

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final EmployeeDao mDao;
        PopulateDbAsync(EmployeeDatabase db) {
            Log.d("goel", "calling PopulateDbAsync");
            mDao = db.getEmployeeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.d("goel", "calling PopulateDbAsync doinbackground");
            mDao.getAllEmployees();
            return null;
        }
    }

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateJobAsync extends AsyncTask<Void, Void, Void> {

        private final JobDao mDao;
        PopulateJobAsync(EmployeeDatabase db) {
            Log.d("goel", "calling PopulateJobAsync");
            mDao = db.getJobDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.d("goel", "calling PopulateJobAsync doInBackground");
            mDao.listAllJobs();
            return null;
        }
    }

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateClientGroupAsync extends AsyncTask<Void, Void, Void> {

        private final ClientGroupDao mDao;
        PopulateClientGroupAsync(EmployeeDatabase db) {
            Log.d("goel", "calling PopulateClientGroupAsync");
            mDao = db.getClientGroupDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.d("goel", "calling PopulateClientGroupAsync doInBackground");
            mDao.listAllClientGroups();
            return null;
        }
    }


    private static RoomDatabase.Callback getJobDataCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateJobAsync(sEmployeeDBInstance).execute();
                }
            };


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
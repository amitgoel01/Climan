package com.crm.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.crm.Utils.Constants;
import com.crm.database.EmployeeDatabase;
import com.crm.database.dao.JobDao;
import com.crm.database.entity.JobEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public class JobDataRepository {

    private static JobDataRepository sInstance;

    //    private final JobDatabase mDatabase;
    private JobDao mJobDao;
    //    private MediatorLiveData<List<Job>> mObservableJobs;
    private LiveData<List<JobEntity>> mAllJobs;
    private MutableLiveData<List<JobEntity>> searchResults =
            new MutableLiveData<>();

    public JobDataRepository(Application application) {
        EmployeeDatabase db = EmployeeDatabase.getInstance(application);
        mJobDao = db.getJobDao();
        mAllJobs = mJobDao.listAllJobs();
    }

    private void asyncFinished(List<JobEntity> results) {
        searchResults.setValue(results);
    }


    public LiveData<List<JobEntity>> listAllJobs() {
        return mJobDao.listAllJobs();
    }

    public MutableLiveData<List<JobEntity>> getSearchResults() {
        return searchResults;
    }


    public void insertJob(JobEntity employee) {
        new InsertAsyncTask(mJobDao).execute(employee);
    }

    public void deleteJob(JobEntity employee) {
        DeleteAsyncTask task = new DeleteAsyncTask(mJobDao);
        task.execute(employee);
    }


    public void findJob(String type, String name) {
        QueryAsyncTask task = new QueryAsyncTask(type, mJobDao);
        task.delegate = this;
        task.execute(name);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<JobEntity>> {

        private JobDao asyncTaskDao;
        private String mType;
        private JobDataRepository delegate = null;

        QueryAsyncTask(String type, JobDao dao) {
            asyncTaskDao = dao;
            mType = type;
        }

        @Override
        protected List<JobEntity> doInBackground(final String... params) {
            if(mType.equals(Constants.JOB_DEPARTMENT)) {
                return asyncTaskDao.findJobWithDepartment(params[0]);
            }
            else if(mType.equals(Constants.JOB_CITY)) {
                return asyncTaskDao.findJobWithCity(params[0]);
            }
            else if(mType.equals(Constants.JOB_STATE)) {
                return asyncTaskDao.findJobWithState(params[0]);
            }
            else if(mType.equals(Constants.JOB_COUNTRY)) {
                return asyncTaskDao.findJobWithCountry(params[0]);
            }
            else if(mType.equals(Constants.JOB_ID)) {
                return asyncTaskDao.findJobWithId(params[0]);
            }
            else if(mType.equals(Constants.JOB_POSTED_DATE)) {
                return asyncTaskDao.findJobWithCountry(params[0]);
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<JobEntity> result) {
            delegate.asyncFinished(result);
        }
    }

    /**
     * Populate the database in the background.
     */
    private static class InsertAsyncTask extends AsyncTask<JobEntity, Void, Void> {

        private final JobDao mDao;

        InsertAsyncTask(JobDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(JobEntity... jobs) {
            mDao.insertJob(jobs[0]);
            return null;
        }

    }

    private static class DeleteAsyncTask extends AsyncTask<JobEntity, Void, Void> {

        private JobDao asyncTaskDao;

        DeleteAsyncTask(JobDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JobEntity... params) {
            asyncTaskDao.deleteJob(params[0]);
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

}

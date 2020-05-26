package com.crm.repository;

import android.app.Application;
import android.os.AsyncTask;


import com.crm.Utils.Constants;
import com.crm.database.EmployeeDatabase;
import com.crm.database.dao.EmployeeDao;
import com.crm.database.entity.EmployeeEntity;
import com.crm.database.entity.EmployeeEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public class EmployeeDataRepository {

    private static EmployeeDataRepository sInstance;

    //    private final EmployeeDatabase mDatabase;
    private EmployeeDao mEmpDao;
    //    private MediatorLiveData<List<Employee>> mObservableEmployees;
    private LiveData<List<EmployeeEntity>> mAllEmployees;
    private MutableLiveData<List<EmployeeEntity>> searchResults =
            new MutableLiveData<>();

    public EmployeeDataRepository(Application application) {
        EmployeeDatabase db = EmployeeDatabase.getInstance(application);
        mEmpDao = db.getEmployeeDao();
        mAllEmployees = mEmpDao.getAllEmployees();
    }


    private void asyncFinished(List<EmployeeEntity> results) {
        searchResults.setValue(results);
    }


    public LiveData<List<EmployeeEntity>> getEmployees() {
        return mEmpDao.getAllEmployees();
    }

    public MutableLiveData<List<EmployeeEntity>> getSearchResults() {
        return searchResults;
    }


    public void insertEmployee(EmployeeEntity employee) {
        new InsertAsyncTask(mEmpDao).execute(employee);
    }

    public void deleteEmployee(EmployeeEntity employee) {
        DeleteAsyncTask task = new DeleteAsyncTask(mEmpDao);
        task.execute(employee);
    }


    public void findEmployee(String type, String name) {
        QueryAsyncTask task = new QueryAsyncTask(type, mEmpDao);
        task.delegate = this;
        task.execute(name);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<EmployeeEntity>> {

        private EmployeeDao asyncTaskDao;
        private String mType;
        private EmployeeDataRepository delegate = null;

        QueryAsyncTask(String type, EmployeeDao dao) {
            asyncTaskDao = dao;
            mType = type;
        }

        @Override
        protected List<EmployeeEntity> doInBackground(final String... params) {
            if(mType.equals(Constants.EMPLOYEE_ID)) {
                return asyncTaskDao.findEmployeeWithEmpId(params[0]);
            }
            else if(mType.equals(Constants.EMAIL_ID)) {
                return asyncTaskDao.findEmployeeWithEmail(params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EmployeeEntity> result) {
            delegate.asyncFinished(result);
        }
    }

    /**
     * Populate the database in the background.
     */
    private static class InsertAsyncTask extends AsyncTask<EmployeeEntity, Void, Void> {

        private final EmployeeDao mDao;

        InsertAsyncTask(EmployeeDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(EmployeeEntity... employees) {
            mDao.insertEmployee(employees[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<EmployeeEntity, Void, Void> {

        private EmployeeDao asyncTaskDao;

        DeleteAsyncTask(EmployeeDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EmployeeEntity... params) {
            asyncTaskDao.deleteEmployee(params[0]);
            return null;
        }
    }

}

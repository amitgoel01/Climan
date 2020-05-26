package com.crm.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.crm.database.EmployeeDatabase;
import com.crm.database.dao.ClientGroupDao;
import com.crm.database.entity.ClientGroupEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public class ClientGroupDataRepository {

    private static ClientGroupDataRepository sInstance;

    private ClientGroupDao mClientGroupDao;
    private LiveData<List<ClientGroupEntity>> mAllClientGroup;
    private MutableLiveData<List<ClientGroupEntity>> searchResults =
            new MutableLiveData<>();
    private MutableLiveData<Long> insertResult =
            new MutableLiveData<>();

    public ClientGroupDataRepository(Application application) {
        EmployeeDatabase db = EmployeeDatabase.getInstance(application);
        mClientGroupDao = db.getClientGroupDao();
        mAllClientGroup = mClientGroupDao.listAllClientGroups();
    }

    private void asyncFinished(List<ClientGroupEntity> results) {
        searchResults.setValue(results);
    }

    private void insertPostExecute(Long result) {
        insertResult.setValue(result);
    }


    public LiveData<List<ClientGroupEntity>> listAllClientGroups() {
        return mClientGroupDao.listAllClientGroups();
    }

    public MutableLiveData<List<ClientGroupEntity>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
    }


    public void insertClientGroup(ClientGroupEntity clientGroup) {
        InsertAsyncTask task = new InsertAsyncTask(mClientGroupDao);
        task.delegate = this;
        task.execute(clientGroup);
    }

    public void deleteClientGroup(ClientGroupEntity clientGroup) {
        DeleteAsyncTask task = new DeleteAsyncTask(mClientGroupDao);
        task.execute(clientGroup);
    }


    public void findClientGroupCode(String clientGroupName) {
        QueryAsyncTask task = new QueryAsyncTask(mClientGroupDao);
        task.delegate = this;
        task.execute(clientGroupName);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<ClientGroupEntity>> {

        private ClientGroupDao asyncTaskDao;
        private ClientGroupDataRepository delegate = null;

        QueryAsyncTask(ClientGroupDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<ClientGroupEntity> doInBackground(final String... params) {
            return asyncTaskDao.findClientGroupCode(params[0]);
        }


        @Override
        protected void onPostExecute(List<ClientGroupEntity> result) {
            delegate.asyncFinished(result);
        }
    }

    /**
     * Populate the database in the background.
     */
    private static class InsertAsyncTask extends AsyncTask<ClientGroupEntity, Void, Long> {

        private final ClientGroupDao mDao;
        private ClientGroupDataRepository delegate = null;
        InsertAsyncTask(ClientGroupDao dao) {
            mDao = dao;
        }


        @Override
        protected Long doInBackground(ClientGroupEntity... clientGroupEntities) {
            return  mDao.insertClientGroup(clientGroupEntities[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            delegate.insertPostExecute(result);
        }

    }

    private static class DeleteAsyncTask extends AsyncTask<ClientGroupEntity, Void, Void> {

        private ClientGroupDao asyncTaskDao;

        DeleteAsyncTask(ClientGroupDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ClientGroupEntity... params) {
            asyncTaskDao.deleteClientGroup(params[0]);
            return null;
        }
    }

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateClientGroupAsync extends AsyncTask<Void, Void, Void> {

        private final ClientGroupDao mDao;
        PopulateClientGroupAsync(EmployeeDatabase db) {
            mDao = db.getClientGroupDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.listAllClientGroups();
            return null;
        }
    }

}

package com.crm.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.crm.database.EmployeeDatabase;
import com.crm.database.dao.ClientDao;
import com.crm.database.entity.ClientEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public class ClientDataRepository {

    private static ClientDataRepository sInstance;

    private ClientDao mClientDao;
    private LiveData<List<ClientEntity>> mAllClients;
    private MutableLiveData<List<ClientEntity>> searchResults =
            new MutableLiveData<>();

    public ClientDataRepository(Application application) {
        EmployeeDatabase db = EmployeeDatabase.getInstance(application);
        mClientDao = db.getClientDao();
        mAllClients = mClientDao.listAllClients();
    }

    private void asyncFinished(List<ClientEntity> results) {
        searchResults.setValue(results);
    }


    public LiveData<List<ClientEntity>> listAllClients() {
        return mClientDao.listAllClients();
    }

    public MutableLiveData<List<ClientEntity>> getSearchResults() {
        return searchResults;
    }

    public void insertClient(ClientEntity client) {
        InsertAsyncTask task = new InsertAsyncTask(mClientDao);
        task.delegate = this;
        task.execute(client);
    }

    public void deleteClient(ClientEntity client) {
        DeleteAsyncTask task = new DeleteAsyncTask(mClientDao);
        task.execute(client);
    }


    public void findClientWithId(String salesPersonId) {
        QueryAsyncTask task = new QueryAsyncTask(mClientDao);
        task.delegate = this;
        task.execute(salesPersonId);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<ClientEntity>> {

        private ClientDao asyncTaskDao;
        private ClientDataRepository delegate = null;

        QueryAsyncTask(ClientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<ClientEntity> doInBackground(final String... params) {
            return asyncTaskDao.findClientWithId(params[0]);
        }


        @Override
        protected void onPostExecute(List<ClientEntity> result) {
            delegate.asyncFinished(result);
        }
    }

    /**
     * Populate the database in the background.
     */
    private static class InsertAsyncTask extends AsyncTask<ClientEntity, Void, Void> {

        private final ClientDao mDao;
        private ClientDataRepository delegate = null;
        InsertAsyncTask(ClientDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(ClientEntity... clientEntities) {
            mDao.insertClient(clientEntities[0]);
            return null;
        }


    }

    private static class DeleteAsyncTask extends AsyncTask<ClientEntity, Void, Void> {

        private ClientDao asyncTaskDao;

        DeleteAsyncTask(ClientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ClientEntity... params) {
            asyncTaskDao.deleteClient(params[0]);
            return null;
        }
    }

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateClientAsync extends AsyncTask<Void, Void, Void> {

        private final ClientDao mDao;
        PopulateClientAsync(EmployeeDatabase db) {
            mDao = db.getClientDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.listAllClients();
            return null;
        }
    }

}

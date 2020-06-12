package com.crm.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.crm.database.EmployeeDatabase;
import com.crm.database.dao.ClientPersonDao;
import com.crm.database.entity.ClientPersonEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public class ClientPersonDataRepository {

    private static ClientPersonDataRepository sInstance;

    private ClientPersonDao mClientPersonDao;
    private LiveData<List<ClientPersonEntity>> mAllPersonClients;
    private MutableLiveData<List<ClientPersonEntity>> searchResults =
            new MutableLiveData<>();
    private MutableLiveData<List<ClientPersonEntity>> myClientPersonResults =
            new MutableLiveData<>();

    public ClientPersonDataRepository(Application application) {
        EmployeeDatabase db = EmployeeDatabase.getInstance(application);
        mClientPersonDao = db.getClientPersonDao();
        mAllPersonClients = mClientPersonDao.listAllClientPersons();
    }

    private void asyncFinished(List<ClientPersonEntity> results) {
        searchResults.setValue(results);
    }

    private void clientAsyncFinished(List<ClientPersonEntity> results) {
        myClientPersonResults.setValue(results);
    }

    public LiveData<List<ClientPersonEntity>> listAllClientPersons() {
        return mClientPersonDao.listAllClientPersons();
    }

    public MutableLiveData<List<ClientPersonEntity>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<ClientPersonEntity>> getMyClientResults() {
        return myClientPersonResults;
    }

    public void insertClientPerson(ClientPersonEntity client) {
        InsertAsyncTask task = new InsertAsyncTask(mClientPersonDao);
        task.delegate = this;
        task.execute(client);
    }

    public void updateClientPersonId(Long cpId, Long clientId) {
        UpdateAsyncTask task = new UpdateAsyncTask(mClientPersonDao);
        task.delegate = this;
        task.execute(cpId, clientId);
    }


    public void deleteClient(ClientPersonEntity client) {
        DeleteAsyncTask task = new DeleteAsyncTask(mClientPersonDao);
        task.execute(client);
    }


    public void findClientWithId(String salesPersonId) {
        QueryAsyncTask task = new QueryAsyncTask(mClientPersonDao);
        task.delegate = this;
        task.execute(salesPersonId);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<ClientPersonEntity>> {

        private ClientPersonDao asyncTaskDao;
        private ClientPersonDataRepository delegate = null;

        QueryAsyncTask(ClientPersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<ClientPersonEntity> doInBackground(final String... params) {
            return asyncTaskDao.findClientWithId(params[0]);
        }


        @Override
        protected void onPostExecute(List<ClientPersonEntity> result) {
            delegate.asyncFinished(result);
        }
    }

    public void findMyClientWithId(String salesPersonId) {
        QueryMyClientAsyncTask task = new QueryMyClientAsyncTask(mClientPersonDao);
        task.delegate = this;
        task.execute(salesPersonId);
    }

    private static class QueryMyClientAsyncTask extends
            AsyncTask<String, Void, List<ClientPersonEntity>> {

        private ClientPersonDao asyncTaskDao;
        private ClientPersonDataRepository delegate = null;

        QueryMyClientAsyncTask(ClientPersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<ClientPersonEntity> doInBackground(final String... params) {
            return asyncTaskDao.findClientWithId(params[0]);
        }


        @Override
        protected void onPostExecute(List<ClientPersonEntity> result) {
            delegate.clientAsyncFinished(result);
        }
    }


    /**
     * Populate the database in the background.
     */
    private static class InsertAsyncTask extends AsyncTask<ClientPersonEntity, Void, Void> {

        private final ClientPersonDao mDao;
        private ClientPersonDataRepository delegate = null;
        InsertAsyncTask(ClientPersonDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(ClientPersonEntity... clientPersonEntities) {
            mDao.insertClientPerson(clientPersonEntities[0]);
            return null;
        }


    }

    /**
     * Populate the database in the background.
     */
    private static class UpdateAsyncTask extends AsyncTask<Long, Void, Void> {

        private final ClientPersonDao mDao;
        private ClientPersonDataRepository delegate = null;
        UpdateAsyncTask(ClientPersonDao dao) {
            mDao = dao;
        }


        @Override
        protected Void doInBackground(Long... clientPersonEntities) {
            mDao.updateClientPersonId(clientPersonEntities[0], clientPersonEntities[1]);
            return null;
        }


    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<ClientPersonEntity, Void, Void> {

        private ClientPersonDao asyncTaskDao;

        DeleteAsyncTask(ClientPersonDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ClientPersonEntity... params) {
            asyncTaskDao.deleteClient(params[0]);
            return null;
        }
    }

    /**
     * Populate the Employee database in the background.
     */
    private static class PopulateClientAsync extends AsyncTask<Void, Void, Void> {

        private final ClientPersonDao mDao;
        PopulateClientAsync(EmployeeDatabase db) {
            mDao = db.getClientPersonDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.listAllClientPersons();
            return null;
        }
    }

}

package com.crm.viewmodel;

import android.app.Application;

import com.crm.database.entity.ClientGroupEntity;
import com.crm.repository.ClientGroupDataRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ClientGroupViewModel extends AndroidViewModel {

    private final ClientGroupDataRepository mRepository;

    private LiveData<List<ClientGroupEntity>> mAllClients;
    private MutableLiveData<List<ClientGroupEntity>> searchResults;
    private MutableLiveData<Long> insertResult;

    public ClientGroupViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ClientGroupDataRepository(application);
        mAllClients = mRepository.listAllClientGroups();
        searchResults = mRepository.getSearchResults();
        insertResult = mRepository.getInsertResult();
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ClientGroupEntity>> listAllClients() {
        return mAllClients;
    }

    public MutableLiveData<List<ClientGroupEntity>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
    }

    public void insertClientGroup(ClientGroupEntity clientGroupEntity) {
        mRepository.insertClientGroup(clientGroupEntity);
    }

    public void findClientGroupCode(String clientGroupName) {
        mRepository.findClientGroupCode(clientGroupName);
    }

    /*public void findClientGroup(String clientGroup, String pinCode) {
        mRepository.findClientGroup(clientGroup, pinCode);
    }*/

    public void deleteClientGroup(ClientGroupEntity clientGroupEntity) {
        mRepository.deleteClientGroup(clientGroupEntity);
    }
}

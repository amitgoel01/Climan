/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crm.viewmodel;

import android.app.Application;

import com.crm.database.entity.ClientPersonEntity;
import com.crm.repository.ClientPersonDataRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ClientPersonViewModel extends AndroidViewModel {

//    private final SavedStateHandle mSavedStateHandler;
    private final ClientPersonDataRepository mRepository;
    private LiveData<List<ClientPersonEntity>> mAllPersonClientInformation;
    private MutableLiveData<List<ClientPersonEntity>> mClientPersonResults;
    private MutableLiveData<List<ClientPersonEntity>> searchResults;

    public ClientPersonViewModel(Application application) {
        super(application);
        mRepository = new ClientPersonDataRepository(application);
        mAllPersonClientInformation = mRepository.listAllClientPersons();
        searchResults = mRepository.getSearchResults();
        mClientPersonResults = mRepository.getMyClientResults();
    }


    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ClientPersonEntity>> listAllPersonClients() {
        return mAllPersonClientInformation;
    }

    public MutableLiveData<List<ClientPersonEntity>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<ClientPersonEntity>> getClientResults() {
        return mClientPersonResults;
    }

    public void insertClientPerson(ClientPersonEntity client) {
        mRepository.insertClientPerson(client);
    }

    public void updateClientPersonId(String timeStamp, String clientId) {
        mRepository.updateClientPersonId(timeStamp, clientId);
    }

    public void findClientWithId(String salesPersonId) {
        mRepository.findClientWithId(salesPersonId);
    }

    public void findMyClientWithId(String salesPersonId) {
        mRepository.findMyClientWithId(salesPersonId);
    }

    public void deleteClient(ClientPersonEntity employee) {
        mRepository.deleteClient(employee);
    }

}

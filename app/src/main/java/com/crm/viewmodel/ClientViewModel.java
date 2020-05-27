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

import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientGroupEntity;
import com.crm.repository.ClientDataRepository;
import com.crm.repository.ClientDataRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ClientViewModel extends AndroidViewModel {

//    private final SavedStateHandle mSavedStateHandler;
    private final ClientDataRepository mRepository;
    private LiveData<List<ClientEntity>> mAllClients;
    private MutableLiveData<List<ClientEntity>> mClientResults;
    private MutableLiveData<List<ClientEntity>> searchResults;

    public ClientViewModel(Application application) {
        super(application);
        mRepository = new ClientDataRepository(application);
        mAllClients = mRepository.listAllClients();
        searchResults = mRepository.getSearchResults();
    }


    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ClientEntity>> listAllClients() {
        return mAllClients;
    }

    public MutableLiveData<List<ClientEntity>> getSearchResults() {
        return searchResults;
    }

    public void insertClient(ClientEntity client) {
        mRepository.insertClient(client);
    }

    public void findClientWithId(String salesPersonId) {
        mRepository.findClientWithId(salesPersonId);
    }


    public void deleteClient(ClientEntity employee) {
        mRepository.deleteClient(employee);
    }

}
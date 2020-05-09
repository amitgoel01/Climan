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


import com.crm.database.entity.EmployeeEntity;
import com.crm.repository.EmployeeDataRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EmployeeListViewModel extends AndroidViewModel {

//    private final SavedStateHandle mSavedStateHandler;
    private final EmployeeDataRepository mRepository;

    private LiveData<List<EmployeeEntity>> mAllEmployees;
    private MutableLiveData<List<EmployeeEntity>> searchResults;
//    private final LiveData<List<Employee>> mEmployees;

    public EmployeeListViewModel(Application application) {
        super(application);
        mRepository = new EmployeeDataRepository(application);
        mAllEmployees = mRepository.getEmployees();
        searchResults = mRepository.getSearchResults();
    }


    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<EmployeeEntity>> getEmployees() {
        return mAllEmployees;
    }

    public MutableLiveData<List<EmployeeEntity>> getSearchResults() {
        return searchResults;
    }

    public void insertEmployee(EmployeeEntity employee) {
        mRepository.insertEmployee(employee);
    }

    public void findEmployee(String field, String name) {
        mRepository.findEmployee(field, name);
    }

    public void deleteEmployee(EmployeeEntity employee) {
        mRepository.deleteEmployee(employee);
    }

}

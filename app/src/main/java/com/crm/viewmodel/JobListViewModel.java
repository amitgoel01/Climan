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

import com.crm.database.entity.JobEntity;
import com.crm.repository.JobDataRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class JobListViewModel extends AndroidViewModel {

//    private final SavedStateHandle mSavedStateHandler;
    private final JobDataRepository mRepository;

    private LiveData<List<JobEntity>> mAllJobs;
    private MutableLiveData<List<JobEntity>> searchResults;
//    private final LiveData<List<Job>> mJobs;

    public JobListViewModel(Application application) {
        super(application);
        mRepository = new JobDataRepository(application);
        mAllJobs = mRepository.listAllJobs();
        searchResults = mRepository.getSearchResults();
    }


    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<JobEntity>> listAllJobs() {
        return mAllJobs;
    }

    public MutableLiveData<List<JobEntity>> getSearchResults() {
        return searchResults;
    }

    public void insertJob(JobEntity employee) {
        mRepository.insertJob(employee);
    }

    public void findJob(String field, String name) {
        mRepository.findJob(field, name);
    }

    public void deleteJob(JobEntity employee) {
        mRepository.deleteJob(employee);
    }

}

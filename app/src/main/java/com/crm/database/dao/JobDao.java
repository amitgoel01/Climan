package com.crm.database.dao;



import com.crm.Utils.Constants;
import com.crm.database.entity.JobEntity;
import com.crm.database.entity.JobEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Pavneet_Singh on 12/31/17.
 */

@Dao
public interface JobDao {

    @Query("SELECT * FROM " + Constants.TABLE_JOB)
    LiveData<List<JobEntity>> listAllJobs();

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_DEPARTMENT + " = :department")
    List<JobEntity> findJobWithDepartment(String department);

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_CITY + " = :city")
    List<JobEntity> findJobWithCity(String city);

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_STATE + " = :state")
    List<JobEntity> findJobWithState(String state);

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_COUNTRY + " = :country")
    List<JobEntity> findJobWithCountry(String country);

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_ID + " = :jobId")
    List<JobEntity> findJobWithId(String jobId);

    @Query("SELECT * FROM " + Constants.TABLE_JOB + " WHERE " + Constants.JOB_POSTED_DATE + " = :job")
    List<JobEntity> findJobWithJobPostedDate(String job);

    /*
     * Insert the object in database
     * @param Job, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertJob(JobEntity Job);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<JobEntity> employeeList);

    /*
     * update the object in database
     * @param Job, object to be updated
     */
    @Update
    void updateJob(JobEntity repos);

    /*
     * delete the object from database
     * @param Job, object to be deleted
     */
    @Delete
    void deleteJob(JobEntity Job);

    // Job... is varargs, here Job is an array
    /*
     * delete list of objects from database
     * @param Job, array of oject to be deleted
     */
    @Delete
    void deleteJob(JobEntity... Job);

   /* @Query("SELECT * FROM user WHERE first_name LIKE :search " +
            "OR last_name LIKE :search")
    public List<User> findUserWithName(String search);*/

}

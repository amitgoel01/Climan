package com.crm.database.dao;



import com.crm.Utils.Constants;
import com.crm.database.entity.EmployeeEntity;
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
    LiveData<List<JobEntity>> getAllJobs();

    @Query("SELECT * FROM " + Constants.TABLE_EMPLOYEE + " WHERE " + Constants.EMAIL_ID + " = :emailId")
    List<EmployeeEntity> findEmployeeWithEmail(String emailId);

    @Query("SELECT * FROM " + Constants.TABLE_EMPLOYEE + " WHERE " + Constants.EMPLOYEE_ID + " = :empId")
    List<EmployeeEntity> findEmployeeWithEmpId(String empId);

    /*
     * Insert the object in database
     * @param Employee, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEmployee(EmployeeEntity Employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EmployeeEntity> employeeList);

    /*
     * update the object in database
     * @param Employee, object to be updated
     */
    @Update
    void updateEmployee(EmployeeEntity repos);

    /*
     * delete the object from database
     * @param Employee, object to be deleted
     */
    @Delete
    void deleteEmployee(EmployeeEntity Employee);

    // Employee... is varargs, here Employee is an array
    /*
     * delete list of objects from database
     * @param Employee, array of oject to be deleted
     */
    @Delete
    void deleteEmployee(EmployeeEntity... Employee);

   /* @Query("SELECT * FROM user WHERE first_name LIKE :search " +
            "OR last_name LIKE :search")
    public List<User> findUserWithName(String search);*/

}

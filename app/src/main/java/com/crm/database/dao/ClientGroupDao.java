package com.crm.database.dao;

import com.crm.Utils.Constants;
import com.crm.database.entity.ClientGroupEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClientGroupDao {

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT_GROUP)
    LiveData<List<ClientGroupEntity>> listAllClientGroups();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertClientGroupId(ClientGroupEntity clientGroupEntity);

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT_GROUP + " WHERE " + Constants.CLIENT_GROUP_NAME + " = :clientGroupName")
    List<ClientGroupEntity> findClientGroupCode(String clientGroupName);

    /*
     * Insert the object in database
     * @param ClientGroup, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertClientGroup(ClientGroupEntity clientGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClientGroupEntity> employeeList);

    /*
     * update the object in database
     * @param ClientGroup, object to be updated
     */
    @Update
    void updateClientGroup(ClientGroupEntity repos);

    /*
     * delete the object from database
     * @param ClientGroup, object to be deleted
     */
    @Delete
    void deleteClientGroup(ClientGroupEntity clientGroup);

    // ClientGroup... is varargs, here ClientGroup is an array
    /*
     * delete list of objects from database
     * @param ClientGroup, array of oject to be deleted
     */
    @Delete
    void deleteClientGroup(ClientGroupEntity... clientGroup);

}

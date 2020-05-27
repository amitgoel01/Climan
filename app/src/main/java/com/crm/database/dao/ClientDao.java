package com.crm.database.dao;

import com.crm.Utils.Constants;
import com.crm.database.entity.ClientEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT)
    LiveData<List<ClientEntity>> listAllClients();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertClient(ClientEntity ClientEntity);

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT + " WHERE " + Constants.SALES_PERSON_ID + " = :salesPersonId" +
            " ORDER BY " + Constants.CLIENT_ID + " DESC " + " LIMIT " + 1)
    List<ClientEntity> findClientWithId(String salesPersonId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClientEntity> employeeList);

    /*
     * update the object in database
     * @param Client, object to be updated
     */
    @Update
    void updateClient(ClientEntity repos);

    /*
     * delete the object from database
     * @param Client, object to be deleted
     */
    @Delete
    void deleteClient(ClientEntity Client);

    // Client... is varargs, here Client is an array
    /*
     * delete list of objects from database
     * @param Client, array of oject to be deleted
     */
    @Delete
    void deleteClient(ClientEntity... Client);

}

package com.crm.database.dao;

import com.crm.Utils.Constants;
import com.crm.database.entity.ClientPersonEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClientPersonDao {

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT_PERSON)
    LiveData<List<ClientPersonEntity>> listAllClientPersons();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertClientPerson(ClientPersonEntity clientPersonEntity);

    @Query("UPDATE " + Constants.TABLE_CLIENT_PERSON + " SET " + Constants.CLIENT_ID + " = :clientId" +  " WHERE " +
            Constants.TIME_STAMP + " = :timeStamp")
    void updateClientPersonId(String timeStamp, String clientId);

   /* @Query("SELECT * FROM " + Constants.TABLE_CLIENT_PERSON + " WHERE " + Constants.CLIENT_PERSON_ID + " = :clientPersonId" +
            " ORDER BY " + Constants.CLIENT_PERSON_ID + " DESC " + " LIMIT " + 1)
    List<ClientPersonEntity> findClientWithId(String clientPersonId);*/

    @Query("SELECT * FROM " + Constants.TABLE_CLIENT_PERSON + " WHERE " + Constants.CLIENT_ID+ " = :clientPersonId" +
            " ORDER BY " + Constants.TIME_STAMP + " ASC " )
    List<ClientPersonEntity> findClientWithId(String clientPersonId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClientPersonEntity> employeeList);

    /*
     * update the object in database
     * @param Client, object to be updated
     */
    @Update
    void updateClient(ClientPersonEntity repos);

    /*
     * delete the object from database
     * @param Client, object to be deleted
     */
    @Delete
    void deleteClient(ClientPersonEntity Client);

    // Client... is varargs, here Client is an array
    /*
     * delete list of objects from database
     * @param Client, array of oject to be deleted
     */
    @Delete
    void deleteClient(ClientPersonEntity... Client);

}

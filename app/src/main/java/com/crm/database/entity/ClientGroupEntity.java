package com.crm.database.entity;

import com.crm.Utils.Constants;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_CLIENT_GROUP)
public class ClientGroupEntity {
    @PrimaryKey(autoGenerate = true)
    private long clientGroupId;
    @NonNull
    private String clientGroupName;

    public ClientGroupEntity(@NonNull String clientGroupName) {
        this.clientGroupName = clientGroupName;
    }


    @NonNull
    public String getClientGroupName() {
        return clientGroupName;
    }

    public void setClientGroupName(@NonNull String clientGroupName) {
        this.clientGroupName = clientGroupName;
    }

    public long getClientGroupId() {
        return clientGroupId;
    }

    public void setClientGroupId(long clientGroupId) {
        this.clientGroupId = clientGroupId;
    }
}

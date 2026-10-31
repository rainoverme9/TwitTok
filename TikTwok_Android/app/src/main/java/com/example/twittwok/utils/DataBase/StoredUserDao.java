package com.example.twittwok.utils.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface StoredUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Void> insertAll(StoredUser... storedUsers);

    @Query("SELECT * FROM storeduser WHERE uid = :uid")
    public ListenableFuture<StoredUser> getUser(String uid);

}

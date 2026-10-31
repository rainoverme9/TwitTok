package com.example.twittwok.utils.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StoredUser.class}, version = 1)
public abstract class StoredUserRepository extends RoomDatabase {
    public abstract StoredUserDao storedUserDao();
}

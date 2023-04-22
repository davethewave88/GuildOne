package com.example.guild;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = User.class,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract GuildDAO getGuildDAO();

}

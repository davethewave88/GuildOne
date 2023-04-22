package com.example.guild;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {User.class,Storage.class,Wares.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    public static final String USER_STORAGE = "USER_STORAGE";

    public static final String GUILD_WARES = "GUILD_WARES";

    public abstract GuildDAO getGuildDAO();

}

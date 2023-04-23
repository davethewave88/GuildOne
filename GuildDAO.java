package com.example.guild;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import com.example.guild.User;

@Dao
public interface GuildDAO {
    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY mMoney DESC")
    LiveData<List<User>> getLiveUsers();

    @Insert
    void insert(Storage...storage);

    @Update
    void update(Storage... storage);

    @Delete
    void delete(Storage storage);

    @Query("SELECT * FROM " + AppDatabase.USER_STORAGE)
    List<Storage> getAllStorages();

    @Query("SELECT * FROM " + AppDatabase.USER_STORAGE + " WHERE userId = :userId")
    Storage getStorageByUserId(int userId);

    @Insert
    void insert(Wares...wares);

    @Update
    void update(Wares... wares);

    @Delete
    void delete(Wares ware);

    @Query("SELECT * FROM " + AppDatabase.GUILD_WARES)
    List<Wares> getAllWares();

    @Query("SELECT * FROM " + AppDatabase.GUILD_WARES + " WHERE name = :search")
    Wares getWareByName(String search);

}

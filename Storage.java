package com.example.guild;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.USER_STORAGE)
public class Storage {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private int potion_count;
    private int ration_count;
    private int weapon_count;
    private int armor_count;

    public Storage(int userId, int potion_count, int ration_count, int weapon_count, int armor_count) {
        this.userId = userId;
        this.potion_count = potion_count;
        this.ration_count = ration_count;
        this.weapon_count = weapon_count;
        this.armor_count = armor_count;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPotion_count() {
        return potion_count;
    }

    public void setPotion_count(int potion_count) {
        this.potion_count = potion_count;
    }

    public int getRation_count() {
        return ration_count;
    }

    public void setRation_count(int ration_count) {
        this.ration_count = ration_count;
    }

    public int getWeapon_count() {
        return weapon_count;
    }

    public void setWeapon_count(int weapon_count) {
        this.weapon_count = weapon_count;
    }

    public int getArmor_count() {
        return armor_count;
    }

    public void setArmor_count(int armor_count) {
        this.armor_count = armor_count;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "userId=" + userId +
                ", potion_count=" + potion_count +
                ", ration_count=" + ration_count +
                ", weapon_count=" + weapon_count +
                ", armor_count=" + armor_count +
                '}';
    }
}

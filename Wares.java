package com.example.guild;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.GUILD_WARES)
public class Wares {
    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private String name;
    private String desc;
    private int price;

    public Wares(String name, String desc, int price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

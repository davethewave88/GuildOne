package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guild.databinding.ActivityShopBinding;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private ActivityShopBinding binding;
    private User user;
    private GuildDAO guildDAO;
    private GuildDAO sguildDAO;
    private GuildDAO wguildDAO;
    private SharedPreferences preferences = null;

    private TextView username;
    private TextView money;
    private Button potion;
    private Button ration;
    private Button weapon;
    private Button armor;
    private TextView potion_label;
    private TextView ration_label;
    private TextView weapon_label;
    private TextView armor_label;
    private Button buy;
    private int potion_num = 0;
    private int ration_num = 0;
    private int weapon_num = 0;
    private int armor_num = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getDatabase();
        getStorageDb();
        getWaresDb();
        wireupDisplay();
    }

    private void getWaresDb() {
        wguildDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.GUILD_WARES)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getGuildDAO();
        List<Wares> wares = wguildDAO.getAllWares();
        if(wares.isEmpty()){
            Wares w1 = new Wares("potion"," a healing item",10);
            Wares w2 = new Wares("ration","a portion of food for a day", 15);
            Wares w3 = new Wares("weapon","a sturdy, reliable, armament", 100);
            Wares w4 = new Wares("armor","a fine set of protection made locally", 150);
            wguildDAO.insert(w1,w2,w3,w4);
    }
    }

    private void getStorageDb() {
        sguildDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.USER_STORAGE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getGuildDAO();
        int id = getIntent().getIntExtra("USER_ID_KEY",-1);
        Storage s = sguildDAO.getStorageByUserId(id);
        if(s == null){
            s = new Storage(id,1,1,1,1);
            sguildDAO.insert(s);
        }
    }

    private void getDatabase() {
        guildDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getGuildDAO();
    }

    private void wireupDisplay() {
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = guildDAO.getUserByUserId(getIntent().getIntExtra("USER_ID_KEY",-1));

        username = binding.usernameLabel;
        username.setText(user.getUserName());

        money = binding.moneyLabel;
        String s ="";
        s+=user.getMoney();
        s+= " coins";
        money.setText(s);

        potion = binding.potionButton;
        ration = binding.rationButton;
        weapon = binding.weaponButton;
        armor = binding.armorLabel;
        potion_label = binding.potionCount;
        ration_label = binding.rationCount;
        weapon_label = binding.weaponCount;
        armor_label = binding.armorCount;
        buy = binding.buyButton;

        potion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                potion_num++;
                String s = "";
                s+=potion_num;
                potion_label.setText(s);
            }
        });
        ration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ration_num++;
                String s = "";
                s+=ration_num;
                ration_label.setText(s);
            }
        });
        weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weapon_num++;
                String s = "";
                s+=weapon_num;
                weapon_label.setText(s);
            }
        });
        armor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                armor_num++;
                String s = "";
                s+=armor_num;
                armor_label.setText(s);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 0;
                cost += potion_num * wguildDAO.getWareByName("potion").getPrice();
                cost += ration_num * wguildDAO.getWareByName("ration").getPrice();
                cost += weapon_num * wguildDAO.getWareByName("weapon").getPrice();
                cost += armor_num * wguildDAO.getWareByName("armor").getPrice();
                if(cost == 0){
                    Toast.makeText(ShopActivity.this, "No items purchased", Toast.LENGTH_SHORT).show();
                }
                else if(user.getMoney() < cost){
                    Toast.makeText(ShopActivity.this, "Cannot afford purchase.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ShopActivity.this, "Items added", Toast.LENGTH_SHORT).show();
                    Storage storage = sguildDAO.getStorageByUserId(user.getUserId());sguildDAO.update(storage);
                    storage.setPotion_count(storage.getPotion_count() + potion_num);
                    storage.setRation_count(storage.getRation_count() + ration_num);
                    storage.setWeapon_count(storage.getWeapon_count() + weapon_num);
                    storage.setArmor_count(storage.getArmor_count() + armor_num);
                    sguildDAO.update(storage);
                    potion_label.setText("0");
                    ration_label.setText("0");
                    weapon_label.setText("0");
                    armor_label.setText("0");

                }
            }
        });

    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, ShopActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
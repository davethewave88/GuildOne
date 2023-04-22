package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guild.databinding.ActivityShopBinding;

public class ShopActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private ActivityShopBinding binding;
    private User user;
    private GuildDAO guildDAO;
    private SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

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
    private int potion_count = 0;
    private int ration_count = 0;
    private int weapon_count = 0;
    private int armor_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        wireupDisplay();
    }

    private void wireupDisplay() {
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = binding.usernameLabel;
        username.setText(user.getUserName());

        money = binding.moneyLabel;
        money.setText(user.getMoney());

        potion = binding.potionButton;
        ration = binding.rationButton;
        weapon = binding.weaponButton;
        armor = binding.armorLabel;
        potion_label = binding.potionCount;
        ration_label = binding.rationCount;
        weapon_label = binding.weaponCount;
        armor_label = binding.armorCount;

        potion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                potion_count++;
                potion_label.setText(potion_count);
            }
        });
        ration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ration_count++;
                ration_label.setText(ration_count);
            }
        });
        weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weapon_count++;
                weapon_label.setText(weapon_count);
            }
        });
        armor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                armor_count++;
                armor_label.setText(armor_count);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = (potion_count * 10 + ration_count * 15 + weapon_count * 100 + armor_count*150);
                if(potion_count == 0 && ration_count == 0 && weapon_count == 0 && armor_count == 0){
                    Toast.makeText(ShopActivity.this, "No items purchased", Toast.LENGTH_SHORT).show();
                }
                else if(user.getMoney() < cost){
                    Toast.makeText(ShopActivity.this, "Cannot afford purchase.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ShopActivity.this, "Items added", Toast.LENGTH_SHORT).show();
                    Storage storage = new Storage(user.getUserId(),potion_count,ration_count,weapon_count,armor_count);
                    guildDAO.update(storage);
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
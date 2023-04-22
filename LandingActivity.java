package com.example.guild;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guild.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private ActivityLandingBinding binding;
    private User user;
    private GuildDAO guildDAO;
    private SharedPreferences preferences = null;
    
    private TextView displayUser;
    private TextView displayRank;
    private Button wallet_button;
    private Button shop_button;
    private Button logout_button;
    private Button admin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getDatabase();

        int id = getIntent().getIntExtra("USER_ID_KEY",-1);
        preferences = this.getSharedPreferences(PREFERENCES_KEY,Context.MODE_PRIVATE);
        //int id = preferences.getInt(USER_ID_KEY,-1);
        Log.d(TAG, "onCreate: id from pref: " + id);
        user = guildDAO.getUserByUserId(id);
        Log.d(TAG, "onCreate: id from dao: " + user.getUserId());
        wireupDisplay();
    }

    private void getDatabase() {
        guildDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getGuildDAO();
    }

    private void wireupDisplay() {
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        displayUser = binding.usernameTextview;
        displayRank = binding.rankTextview;
        logout_button = binding.logoutLabelButton;

        displayUser.setText(user.getUserName());
        displayRank.setText(user.getRole());
        if(user.getIsAdmin()){
            admin_button.setVisibility(View.VISIBLE);
        }

        wallet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BankActivity.getIntent(getApplicationContext(),user.getUserId());

            }
        });

        shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopActivity.getIntent(getApplicationContext(),user.getUserId());

            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminActivity.getIntent(getApplicationContext(),user.getUserId());

            }
        });

    }

    private void logout() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getIntent().putExtra(USER_ID_KEY,-1);
                        SharedPreferences.Editor editor =preferences.edit();
                        editor.putInt(USER_ID_KEY,-1);
                        editor.apply();
                        int id = -1;
                        Intent intent = MainActivity.getIntent(getApplicationContext(),id);
                        startActivity(intent);
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guild.databinding.ActivityAdminBinding;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private ActivityAdminBinding binding;
    private User user;
    private GuildDAO guildDAO;
    private SharedPreferences preferences = null;

    private TextView display;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        wireupDisplay();
        refreshDisplay();
    }

    private void wireupDisplay() {
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        display = binding.mainDisplay;
        users = guildDAO.getAllUsers();
        for(User u : users){
            String text = u.toString() + "\n______________";
            display.setText(text);
        }

    }

    private void refreshDisplay() {}

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
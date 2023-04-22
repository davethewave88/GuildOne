package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.guild.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.daclink.drew.gymlogsp20.userIdKey";

    private ActivityLandingBinding binding;
    private User user;
    private GuildDAO guildDAO;
    
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

        int id = getIntent().getIntExtra(USER_ID_KEY, -1);
        user = guildDAO.getUserByUserId(id);

        wireupDisplay();
    }

    private void wireupDisplay() {
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        displayUser = binding.usernameTextview;
        displayUser.setText(user.getUserName());

        displayRank = binding.rankTextview;
        //displayRank.setText(user.getRank());
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(USER_ID_KEY,userId);

        return intent;
    }
}
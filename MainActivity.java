package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guild.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.daclink.drew.gymlogsp20.userIdKey";
    private static final String PREFENCES_KEY = "com.daclink.drew.gymlogsp20.PREFENCES_KEY";
    private ActivityMainBinding binding;
    private GuildDAO guildDAO;
    private User user;
    private int userId = -1;

    private Button apply_button;
    private Button login_button;
    private EditText username;
    private EditText password;
    private String newUser;
    private String newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForUser();
        getDatabase();
        wireupDisplay();
    }

    private void checkForUser() {
        userId = getIntent().getIntExtra(USER_ID_KEY,-1);
        if(userId != -1){
            return;
        }

        SharedPreferences preferences;
    }

    private void getDatabase() {
        guildDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGuildDAO();
    }

    private void wireupDisplay() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apply_button = binding.applyButton;
        login_button = binding.loginButton;
        username = binding.enterUsernameEdittext;
        password = binding.enterPasswordEdittext;

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser = username.getText().toString();
                newPass = password.getText().toString();
                user = guildDAO.getUserByUsername(newUser);
                if(user == null){
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                else if(newPass.equals(user.getPassword())){
                    Intent intent = LandingActivity.getIntent(getApplicationContext(),user.getUserId());
                    startActivity(intent);
                }
            }
        });
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}
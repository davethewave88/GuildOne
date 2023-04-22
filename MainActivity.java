package com.example.guild;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guild.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";
    private ActivityMainBinding binding;
    private GuildDAO guildDAO;
    private User user;
    private int userId = -1;
    private SharedPreferences preferences = null;

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
        getDatabase();
        checkForUser();
        wireupDisplay();
    }

    private void checkForUser() {
        userId = getIntent().getIntExtra(USER_ID_KEY,-1);
        if(userId != -1){
            return;
        }

        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCES_KEY,Context.MODE_PRIVATE);
        }

        userId = preferences.getInt(USER_ID_KEY,-1);
        if(userId != -1){
            return;
        }

        List<User> users = guildDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("testuser1","testuser1","beginner",0,false);
            User adminUser = new User("admin2","admin2","guildmaster",1000000,true);
            guildDAO.insert(defaultUser,adminUser);
        }

    }

    private void getDatabase() {
        guildDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getGuildDAO();
    }

    private void wireupDisplay() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        apply_button = binding.applyButton;
        login_button = binding.loginButton;
        username = binding.enterUsernameEdittext;
        password = binding.enterPasswordEdittext;
        setContentView(binding.getRoot());

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
                    addUserToPreferences();
                    Log.d(TAG, "onClick: user id = " + user.getUserId());
                    Intent intent = LandingActivity.getIntent(getApplicationContext(),user.getUserId());
                    startActivity(intent);
                }
            }
        });
    }

    private void addUserToPreferences() {
        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCES_KEY,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor =preferences.edit();
        editor.putInt(USER_ID_KEY,userId);
        editor.apply();
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}
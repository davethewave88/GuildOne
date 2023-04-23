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

import com.example.guild.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private EditText mNewUsername_editText;
    private EditText mNewPassword_editText;
    private EditText mNewRole_editText;
    private Button mNewApply_button;
    private ActivityLoginBinding binding;
    private GuildDAO guildDAO;
    private SharedPreferences preferences = null;

    private String username;
    private String password;
    private String role;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getDatabase();
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
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mNewUsername_editText = binding.newUsernameLabelEditext;
        mNewPassword_editText = binding.newPasswordLabelEditext;
        mNewRole_editText = binding.enterClassLabelEditText;
        mNewApply_button = binding.newApplyLabelButton;

        mNewApply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFields();
                if (checkForUserInDatabase()) {
                    Toast.makeText(LoginActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                }
                else{
                    User newUser = new User(username,password,role,0,false);
                    guildDAO.insert(newUser);
                    user = guildDAO.getUserByUsername(username);
                    Log.d(TAG, "onClick: new user: " + user.toString());
                    Log.d(TAG, "onClick: users: " + guildDAO.getAllUsers().toString());
                    addUserToPreference();
                    Log.d(TAG, "onClick: new user: " + user.toString());
                    Log.d(TAG, "onClick: users: " + guildDAO.getAllUsers().toString());
                    Intent intent = LandingActivity.getIntent(getApplicationContext(),user.getUserId());
                    startActivity(intent);
                }
            }

        });
    }

    private void addUserToPreference(){
        if(preferences == null){
            preferences = this.getSharedPreferences(PREFERENCES_KEY,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, user.getUserId());
        editor.apply();
    }
    private boolean checkForUserInDatabase() {
        user = guildDAO.getUserByUsername(username);
        if(user == null){
            //Toast.makeText(this, "no user " + username + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getTextFields() {
        username = mNewUsername_editText.getText().toString();
        password = mNewPassword_editText.getText().toString();
        role = mNewRole_editText.getText().toString();
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }
}
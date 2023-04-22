package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guild.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private EditText mNewUsername_editText;
    private EditText mNewPassword_editText;
    private EditText mNewRole_editText;
    private Button mNewApply_button;
    private ActivityLoginBinding binding;
    private GuildDAO guildDAO;

    private String username;
    private String password;
    private String role;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();
        getDatabase();

    }

    private void getDatabase() {
        guildDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.USER_TABLE)
                .allowMainThreadQueries()
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
                    Intent intent = MainActivity.getIntent(getApplicationContext(),user.getUserId());
                    startActivity(intent);
                }
            }

        });
    }

    private boolean checkForUserInDatabase() {
        user = guildDAO.getUserByUsername(username);
        if(user == null){
            Toast.makeText(this, "no user " + username + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getTextFields() {
        username = mNewUsername_editText.getText().toString();
        password = mNewPassword_editText.getText().toString();
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }
}
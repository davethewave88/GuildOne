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
    private EditText selection;
    private Button delete;
    private Button add;
    private EditText name_input;
    private EditText password_input;
    private EditText role_input;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selection = binding.userSelect;
        display = binding.mainDisplay;
        updateDisplay();
        users = guildDAO.getAllUsers();

        delete = binding.deleteButton;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select = Integer.parseInt(selection.getText().toString());
                user = guildDAO.getUserByUserId(3);
                guildDAO.delete(user);
                updateDisplay();
            }
        });

        name_input = binding.nameSelect;
        password_input = binding.passwordSelect;
        role_input = binding.roleSelect;
        add = binding.addButton;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString();
                String password = password_input.getText().toString();
                String role = role_input.getText().toString();
                User newUser = new User(name,password,role,200,false);
                guildDAO.insert(newUser);
                updateDisplay();
            }
        });
    }

    private void updateDisplay() {
        users = guildDAO.getAllUsers();
        String text = "";
        for(User u : users){
            text += (u.toString() + "\n" + "\n");
        }
        display.setText(text);
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
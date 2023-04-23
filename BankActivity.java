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
import android.widget.TextView;

import com.example.guild.databinding.ActivityBankBinding;

public class BankActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.guild.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.guild.PREFERENCES_KEY";

    private ActivityBankBinding binding;
    private User user;
    private Storage storage;
    private GuildDAO guildDAO;
    private GuildDAO sguildDAO;
    private SharedPreferences preferences = null;

    private TextView username;
    private TextView role;
    private TextView money;
    private TextView display;
    private EditText amount;
    private Button transfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        getDatabase();

        int id = getIntent().getIntExtra("USER_ID_KEY",-1);
        Log.d(TAG, "onCreate: id: " + id);
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        user = guildDAO.getUserByUserId(id);
        Log.d(TAG, "onCreate: user:" + user);
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
        binding = ActivityBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = binding.usernameLabel;
        username.setText(user.getUserName());
        role = binding.roleLabel;
        role.setText(user.getRole());
        money = binding.moneyLabel;
        String m = "";
        m+= user.getMoney();
        m+= " coins";
        money.setText(m);

        getStorageDb();
        display = binding.storageView;
        display.setText(sguildDAO.getStorageByUserId(user.getUserId()).toString());
        amount = binding.moneyEdittext;
        transfer = binding.transferButton;

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String funds = amount.getText().toString();
                Integer num = Integer.parseInt(funds);
                num += user.getMoney();
                user.setMoney(num);
                guildDAO.update(user);
                money.setText(user.getMoney() + " coins");
            }
        });


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

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, BankActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
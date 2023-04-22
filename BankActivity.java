package com.example.guild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private GuildDAO guildDAO;
    private SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

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
        wireupDisplay();
    }

    private void wireupDisplay() {
        binding = ActivityBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = binding.usernameLabel;
        username.setText(user.getUserName());
        role = binding.roleLabel;
        role.setText(user.getRole());
        money = binding.moneyLabel;
        money.setText(user.getMoney());

        display = binding.storageView;
        amount = binding.moneyEdittext;
        transfer = binding.transferButton;

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String funds = amount.getText().toString();
                Integer num = Integer.parseInt(funds);
                user = guildDAO.getUserByUserId(user.getUserId());
                user.setMoney(user.getMoney() + num);
                guildDAO.update(user);
                money.setText(user.getMoney());
            }
        });


    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, BankActivity.class);
        intent.putExtra("USER_ID_KEY",userId);

        return intent;
    }
}
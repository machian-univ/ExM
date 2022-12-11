package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText box_email, box_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.sign_in_button).setOnClickListener((View.OnClickListener) view -> {
            box_email = findViewById(R.id.editTextTextEmailAddress);
            box_password = findViewById(R.id.editTextTextPassword);

            String email_input = box_email.getText().toString();
            String pass_input = box_password.getText().toString();

            // если не пусто и соот. формату
            if(email_input.contains("@") && email_input.length() > 0 && pass_input.length() > 0) {
                // авторизация
                signRequest(new LoginModel(email_input, pass_input));
            }
            Toast.makeText(getApplicationContext(), "Проверьте формат введенных данных!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.profile_button).setOnClickListener((View.OnClickListener) view -> {
            //вход если авторизирован
        });
    }

    private void signRequest(LoginModel loginModel) {
        NetworkServices.getInstance().getJSONApi().login(loginModel).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Проверьте логин и пароль", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
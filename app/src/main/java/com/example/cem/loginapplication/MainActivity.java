package com.example.cem.loginapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register = (Button) findViewById(R.id.btnRegister);
        Button signIn = (Button) findViewById(R.id.btnSignIn);

        final TextView tvStatus = (TextView) findViewById(R.id.txtStatus);
        final TextView tvName = (TextView) findViewById(R.id.txtName);
        final TextView tvAge = (TextView) findViewById(R.id.txtAge);
        final TextView tvMail = (TextView) findViewById(R.id.txtMail);
        final EditText etEmail = (EditText) findViewById(R.id.emailText);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        SharedPreferences preference = getSharedPreferences("Login", Context.MODE_PRIVATE);

        final String name = preference.getString("name", "");
        final String email = preference.getString("email", "");
        String age = preference.getString("age", "");
        final String password = preference.getString("password", "");


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            tvStatus.setText(extras.getString("statu"));
            tvMail.setVisibility(View.VISIBLE);
            tvAge.setVisibility(View.VISIBLE);

        }
        tvName.setText(name);
        tvAge.setText("Age : " + age);
        tvMail.setText("Email : " + email);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Reg = new Intent(MainActivity.this, Register.class);

                startActivity(Reg);

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typedEmail = etEmail.getText().toString();
                typedEmail = typedEmail.toLowerCase();
                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please, register first!", Toast.LENGTH_LONG).show();
                } else {
                    if (password.isEmpty()) {
                        if (email.equals(typedEmail)) {
                            Toast.makeText(MainActivity.this, "Welcome email is " + email, Toast.LENGTH_LONG).show();
                            tvStatus.setText("Previously saved user signed in!");
                            tvName.setText("Name: " + name);
                            tvMail.setVisibility(View.INVISIBLE);
                            tvAge.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(MainActivity.this, "Wrong password or email", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String typedPassword = etPassword.getText().toString();
                        if ((email.equals(typedEmail)) && (password.equals(typedPassword))) {
                            Toast.makeText(MainActivity.this, "Welcome email is " + email + " password is " + password, Toast.LENGTH_LONG).show();
                            tvStatus.setText("Previously saved user signed in!");
                            tvName.setText("Name: " + name);
                            tvMail.setVisibility(View.INVISIBLE);
                            tvAge.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong password or email", Toast.LENGTH_LONG).show();
                        }
                    }
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText etEmail = (EditText) findViewById(R.id.emailText);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        SharedPreferences preference = getSharedPreferences("saveTextEdit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("email", etEmail.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences preference = getSharedPreferences("saveTextEdit", Context.MODE_PRIVATE);
        EditText etEmail = (EditText) findViewById(R.id.emailText);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail.setText(preference.getString("email", ""));
        etPassword.setText(preference.getString("password", ""));
    }
}

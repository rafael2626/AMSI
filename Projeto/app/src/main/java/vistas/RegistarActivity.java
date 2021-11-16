package vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.projeto.R;

public class RegistarActivity extends AppCompatActivity {

    private EditText etNome, etPassword, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        etEmail = findViewById(R.id.etEmail);
        etNome = findViewById(R.id.etNome);
        etPassword = findViewById(R.id.etPassword);
    }

    public void onClickregisto(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String nome = etNome.getText().toString();

        if (!isEmailValido(email)) {
            etEmail.setError("Email Inválido");
            return;
        }
        if(!isPasswordValida(password))
        {
            etPassword.setError("Password Inválida");
            return;
        }



    }

    private boolean isEmailValido(String email) {
        if (email == null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String password) {
        if (password == null)
            return false;
        return password.length() >= 6;
    }


}
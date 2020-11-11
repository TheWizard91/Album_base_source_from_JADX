package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thewizard91.thealbumproject.C2521R;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    /* access modifiers changed from: private */
    public EditText loginEmailText;
    /* access modifiers changed from: private */
    public EditText loginPasswordText;
    /* access modifiers changed from: private */
    public ProgressBar loginProgressbar;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    private Button registerButton;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_login);
        this.loginEmailText = (EditText) findViewById(C2521R.C2524id.login_email);
        this.loginPasswordText = (EditText) findViewById(C2521R.C2524id.login_password);
        this.loginButton = (Button) findViewById(C2521R.C2524id.login_button);
        this.registerButton = (Button) findViewById(C2521R.C2524id.register_button);
        this.loginProgressbar = (ProgressBar) findViewById(C2521R.C2524id.login_progressbar);
        this.mAuth = FirebaseAuth.getInstance();
        triggerLoginButton();
        triggerRegisterButton();
    }

    private void triggerLoginButton() {
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String loginEmail = LoginActivity.this.loginEmailText.getText().toString();
                String loginPassword = LoginActivity.this.loginPasswordText.getText().toString();
                if (!loginEmail.isEmpty() && !loginPassword.isEmpty()) {
                    LoginActivity.this.mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LoginActivity.this.loginProgressbar.setVisibility(0);
                                LoginActivity.this.sendToMain();
                                return;
                            }
                            Toast.makeText(LoginActivity.this, "Error: " + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                        }
                    });
                }
            }
        });
    }

    private void triggerRegisterButton() {
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

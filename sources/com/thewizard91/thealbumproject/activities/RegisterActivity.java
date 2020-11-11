package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {
    private Button alreadyHaveAnAccountButton;
    private Button createANewAccountButton;
    /* access modifiers changed from: private */
    public FirebaseAuth mAuth;
    /* access modifiers changed from: private */
    public EditText registerConfirmPasswordText;
    /* access modifiers changed from: private */
    public EditText registerEmailText;
    /* access modifiers changed from: private */
    public EditText registerPasswordText;
    /* access modifiers changed from: private */
    public ProgressBar registerProgressbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_register);
        this.registerEmailText = (EditText) findViewById(C2521R.C2524id.register_email);
        this.registerPasswordText = (EditText) findViewById(C2521R.C2524id.register_password);
        this.registerConfirmPasswordText = (EditText) findViewById(C2521R.C2524id.register_confirm_password);
        this.createANewAccountButton = (Button) findViewById(C2521R.C2524id.create_a_new_account_button);
        this.alreadyHaveAnAccountButton = (Button) findViewById(C2521R.C2524id.have_an_account_button);
        this.registerProgressbar = (ProgressBar) findViewById(C2521R.C2524id.register_progressbar);
        this.mAuth = FirebaseAuth.getInstance();
        finishTheLoginActivity();
        createANewAccount();
    }

    private void finishTheLoginActivity() {
        this.alreadyHaveAnAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    private void createANewAccount() {
        this.createANewAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = RegisterActivity.this.registerEmailText.getText().toString();
                String password = RegisterActivity.this.registerPasswordText.getText().toString();
                String passwordConfirmation = RegisterActivity.this.registerConfirmPasswordText.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirmation)) {
                    if (password.equals(passwordConfirmation)) {
                        RegisterActivity.this.registerProgressbar.setVisibility(0);
                        RegisterActivity.this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    RegisterActivity.this.sendToAccountSettingsActivity();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error : " + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                                }
                                RegisterActivity.this.registerProgressbar.setVisibility(4);
                            }
                        });
                        return;
                    }
                    Toast.makeText(RegisterActivity.this, "Confirm password does not match the password", 0).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToAccountSettingsActivity() {
        startActivity(new Intent(this, AccountSettingsActivity.class));
        finish();
    }
}

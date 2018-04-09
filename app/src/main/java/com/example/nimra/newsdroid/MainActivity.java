package com.example.nimra.newsdroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText email;
    private  EditText password;
    private  EditText city;
    private  EditText name;
    private  EditText phonenumber;
    private TextView signin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), userprofile.class));

        }

        progressDialog = new ProgressDialog(this);

        button = (Button) findViewById(R.id.register);

        email = (EditText) findViewById(R.id.email);

        password = (EditText) findViewById(R.id.password);

        phonenumber = (EditText) findViewById(R.id.phonenumber);

        city = (EditText) findViewById(R.id.city);

        name = (EditText) findViewById(R.id.name);

        signin = (TextView) findViewById(R.id.signin);

        button.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    private void registerUser(){
        String emailid = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        final String username = name.getText().toString().trim();
        final String usercity = city.getText().toString().trim();
        final String userphonenumber = phonenumber.getText().toString().trim();

        if(TextUtils.isEmpty(emailid)){
            //email is empty
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(pass)){
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(username)){
            //password is empty
            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(usercity)){
            //password is empty
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(userphonenumber)){
            //password is empty
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_LONG).show();
            //stopping the function execution further
            return;
        }



        progressDialog.setMessage("Registering User...");
        progressDialog.show();






        firebaseAuth.createUserWithEmailAndPassword(emailid,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), userprofile.class));
                            userInformation userInfo = new userInformation(username, usercity, userphonenumber);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(userInfo);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Registered not Successfull", Toast.LENGTH_LONG).show();
                        }
                    }


                });
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            registerUser();
        }

        if(view == signin){
            startActivity(new Intent(this, login.class));
        }

    }


}

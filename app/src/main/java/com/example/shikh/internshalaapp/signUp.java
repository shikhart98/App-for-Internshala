package com.example.shikh.internshalaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shikh.internshalaapp.database.DatabaseHelper;
import com.example.shikh.internshalaapp.database.table.UserTable;
import com.example.shikh.internshalaapp.models.user;

public class signUp extends Fragment implements View.OnClickListener {
    EditText et_signup_name,et_signup_email,et_signup_pass;
    Button btn_signup;
    TextView signup_login;
    DatabaseHelper helper;
    SQLiteDatabase write;
    SQLiteDatabase read;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getContext());
        write = helper.getWritableDatabase();
        read = helper.getReadableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_sign_up,container,false);
        et_signup_name = myView.findViewById(R.id.et_signup_name);
        et_signup_email = myView.findViewById(R.id.et_signup_email);
        et_signup_pass = myView.findViewById(R.id.et_signup_pass);
        btn_signup = myView.findViewById(R.id.btn_signup);
        signup_login = myView.findViewById(R.id.signup_login);

        btn_signup.setOnClickListener(this);
        signup_login.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup :
                checkforEmailandPassword();
                break;
            case R.id.signup_login :
                getFragmentManager().popBackStackImmediate();
                break;
        }

    }

    private void checkforEmailandPassword() {
        String name = et_signup_name.getText().toString();
        String email = et_signup_email.getText().toString();
        String password = et_signup_pass.getText().toString();

        if (email.isEmpty()) {
            et_signup_email.setError("Email is required");
            et_signup_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_signup_pass.setError("Password is required");
            et_signup_pass.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_signup_email.setError("Email address is invalid");
            et_signup_email.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_signup_pass.setError("Minimum length of password should be 6");
            et_signup_pass.requestFocus();
            return;
        }

        if(!UserTable.CheckForDuplicates(email,read)) {
            UserTable.insertUser(new user(name, email, password), write);
            Toast.makeText(getContext(),"Successfully registered!",Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStackImmediate();
        } else {
            Toast.makeText(getContext(),"Email already exist!",Toast.LENGTH_SHORT).show();
        }
    }
}

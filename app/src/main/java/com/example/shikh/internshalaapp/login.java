package com.example.shikh.internshalaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class login extends Fragment implements View.OnClickListener {

    Button btn_login;
    TextView skip_login, login_signup;
    SQLiteDatabase read;
    DatabaseHelper helper;
    EditText et_login_email, et_login_pass;
    SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DatabaseHelper(getContext());
        read = helper.getReadableDatabase();
        pref = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login = myView.findViewById(R.id.btn_login);
        skip_login = myView.findViewById(R.id.skip_login);
        login_signup = myView.findViewById(R.id.login_signup);
        et_login_email = myView.findViewById(R.id.et_login_email);
        et_login_pass = myView.findViewById(R.id.et_login_pass);
        btn_login.setOnClickListener(this);
        skip_login.setOnClickListener(this);
        login_signup.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkForEmailAndPassword();
                break;
            case R.id.skip_login:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.login_signup:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new signUp()).addToBackStack(null).commit();
                break;
        }
    }

    private void checkForEmailAndPassword() {
        String email = et_login_email.getText().toString();
        String password = et_login_pass.getText().toString();

        if (email.isEmpty()) {
            et_login_email.setError("Email is required");
            et_login_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_login_pass.setError("Password is required");
            et_login_pass.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_login_email.setError("Email address is invalid");
            et_login_email.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_login_pass.setError("Minimum length of password should be 6");
            et_login_pass.requestFocus();
            return;
        }

        int userId = UserTable.authenticate(email, password, read);

        if (userId == -1) {
            Toast.makeText(getContext(), "Invalid Email/Password!", Toast.LENGTH_SHORT).show();
        } else if (userId == -2) {
            Toast.makeText(getContext(), "Email not registered!", Toast.LENGTH_SHORT).show();
        } else {
            pref.edit().putInt("userId", userId).apply();
            pref.edit().putBoolean("loggedin", true).apply();
            Toast.makeText(getContext(),"Successfully logged in!",Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStackImmediate();
        }


    }
}

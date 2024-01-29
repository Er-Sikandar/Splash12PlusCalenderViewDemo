package com.example.simdetactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.example.simdetactapp.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {
    private String TAG="AuthActivity";
private ActivityAuthBinding binding;
private Auth0 account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        account =new  Auth0(
                getString(R.string.com_auth0_client_id),
                getString(R.string.com_auth0_domain)
        );

        binding.btnLogin.setOnClickListener(v ->{
            loginWithBrowser();
        });
     binding.btnLogout.setOnClickListener(v -> {
     logout();
     });
    }
    private void loginWithBrowser() {
        WebAuthProvider.login(account).withScheme("https").withScope("openid profile email")
                .start(this, new Callback<Credentials, AuthenticationException>() {
                    @Override
                    public void onSuccess(Credentials credentials) {
                        Log.e(TAG, "onSuccess: "+credentials.getAccessToken());
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException e) {
                        Log.e(TAG, "onFailure: "+e.getLocalizedMessage());
                    }
                });
    }
    private void logout() {
        WebAuthProvider.logout(account)
                .withScheme("demo")
                .start(this, new Callback<Void, AuthenticationException>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(TAG, "onSuccess: "+unused);
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException e) {
                        Log.e(TAG, "onFailure: "+e.getLocalizedMessage());
                    }
                });
    }
}
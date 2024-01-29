package com.example.simdetactapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.simdetactapp.databinding.ActivitySsoLoginnBinding;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;


import java.util.UUID;

public class SsoLoginnActivity extends AppCompatActivity {
    private String TAG="SsoLoginnActivity";
private ActivitySsoLoginnBinding binding;
    String[] scopes = {"User.Read"};
    IMultipleAccountPublicClientApplication mMultipleAccountApp = null;
    IAccount mFirstAccount = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySsoLoginnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            PublicClientApplication.createMultipleAccountPublicClientApplication(this,
                    R.raw.auth_config_single_account,
                    new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                        @Override
                        public void onCreated(IMultipleAccountPublicClientApplication application) {
                            mMultipleAccountApp = application;
                            Log.e(TAG, "onCreated: "+mMultipleAccountApp.getConfiguration().getClientId());
                            mMultipleAccountApp.acquireToken(SsoLoginnActivity.this, scopes, getAuthInteractiveCallback());
                        }

                        @Override
                        public void onError(MsalException exception) {
                            Log.e(TAG, "onError: "+exception.getMessage());
                        }
                    });
        });

    }

    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                String accessToken = authenticationResult.getAccessToken();
                Log.e(TAG, "onSuccess: "+accessToken);
                mFirstAccount = authenticationResult.getAccount();
            }
            @Override
            public void onError(MsalException exception) {
                Log.e(TAG, "onSuccess: "+exception.getErrorCode());
                if (exception instanceof MsalClientException) {
                    //And exception from the client (MSAL)
                } else if (exception instanceof MsalServiceException) {
                    //An exception from the server
                }

            }
            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel");
                /* User canceled the authentication */
            }
        };
    }

}
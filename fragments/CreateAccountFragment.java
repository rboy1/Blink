package com.immediasemi.blink.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.immediasemi.blink.BlinkApp;
import com.immediasemi.blink.activities.BaseActivity;
import com.immediasemi.blink.activities.OnboardingActivity;
import com.immediasemi.blink.activities.VisitBlinkActivity;
import com.immediasemi.blink.api.BlinkAPI;
import com.immediasemi.blink.api.BlinkAPI.BlinkAPICallback;
import com.immediasemi.blink.api.requests.Accounts.RegistrationRequest;
import com.immediasemi.blink.api.requests.Networks.AddNetworkRequest;
import com.immediasemi.blink.models.ANetwork;
import com.immediasemi.blink.models.AuthToken;
import com.immediasemi.blink.models.AuthToken.AuthTokenContents;
import com.immediasemi.blink.models.BlinkData;
import com.immediasemi.blink.models.BlinkError;
import com.immediasemi.blink.models.Network;
import com.immediasemi.blink.models.RegionManager;
import com.immediasemi.blink.utils.CustomSwitch;
import com.immediasemi.blink.utils.LiveViewWhiteList;
import com.immediasemi.blink.utils.OnClick;
import com.immediasemi.blink.utils.Validation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CreateAccountFragment
  extends BaseFragment
  implements AdapterView.OnItemSelectedListener
{
  private LinearLayout mConfirmContainer;
  private EditText mConfirmPassword;
  private Button mCreateAccountButton;
  private String mErrorString;
  private TextView mForgotPassword;
  private EditText mPassword;
  private int mSectionNumber;
  private View mShowPWSwitch;
  private TextView mTermsAndConditions;
  private EditText mUserName;
  private View mView;
  private RegionManager regionManager;
  
  private void addNetwork()
  {
    AddNetworkRequest localAddNetworkRequest = new AddNetworkRequest();
    localAddNetworkRequest.setName(new Date().toString() + " Network Added");
    BlinkAPI.BlinkAPIRequest(null, null, localAddNetworkRequest, new BlinkAPI.BlinkAPICallback()
    {
      public void onError(BlinkError paramAnonymousBlinkError)
      {
        if (CreateAccountFragment.this.getActivity() != null) {
          if (paramAnonymousBlinkError.response == null) {
            break label68;
          }
        }
        label68:
        for (paramAnonymousBlinkError = (String)paramAnonymousBlinkError.response.get("message");; paramAnonymousBlinkError = paramAnonymousBlinkError.getErrorMessage())
        {
          new AlertDialog.Builder(CreateAccountFragment.this.getActivity()).setMessage(paramAnonymousBlinkError).setPositiveButton(2131099891, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              CreateAccountFragment.this.getActivity().finish();
            }
          }).create().show();
          return;
        }
      }
      
      public void onResult(BlinkData paramAnonymousBlinkData)
      {
        paramAnonymousBlinkData = ((ANetwork)paramAnonymousBlinkData).getNetwork();
        BlinkApp.getApp().setLastNetworkId(String.valueOf(paramAnonymousBlinkData.getId()));
        paramAnonymousBlinkData = new Intent(CreateAccountFragment.this.getActivity(), OnboardingActivity.class);
        paramAnonymousBlinkData.putExtra("onboard_reason", 1);
        CreateAccountFragment.this.startActivity(paramAnonymousBlinkData);
      }
    }, false);
  }
  
  public static CreateAccountFragment newInstance(int paramInt)
  {
    CreateAccountFragment localCreateAccountFragment = new CreateAccountFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("arg_section_number", paramInt);
    localCreateAccountFragment.setArguments(localBundle);
    return localCreateAccountFragment;
  }
  
  private boolean validateInputs()
  {
    boolean bool = false;
    if (!Validation.validateEmail(this.mUserName.getText().toString())) {
      this.mErrorString = getString(2131099860);
    }
    for (;;)
    {
      return bool;
      if (this.mPassword.getText().length() < 6) {
        this.mErrorString = getString(2131099862);
      } else if (this.mPassword.getText().length() > 128) {
        this.mErrorString = getString(2131099862);
      } else {
        bool = true;
      }
    }
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    ((BaseActivity)paramActivity).setActionBarTitle(getString(2131099983));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (getArguments() != null) {
      this.mSectionNumber = getArguments().getInt("arg_section_number");
    }
    paramBundle = PreferenceManager.getDefaultSharedPreferences(BlinkApp.getApp().getApplicationContext());
    if (paramBundle.getInt("onboard_sequence", -1) > 0) {
      paramBundle.edit().remove("onboard_sequence").commit();
    }
    this.regionManager = ((RegionManager)getActivity().getIntent().getSerializableExtra("regionManager"));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, final ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mView = paramLayoutInflater.inflate(2130903107, paramViewGroup, false);
    this.mUserName = ((EditText)this.mView.findViewById(2131558651));
    this.mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (!paramAnonymousBoolean)
        {
          paramAnonymousView = (EditText)paramAnonymousView;
          paramAnonymousView.setText(paramAnonymousView.getText().toString().trim());
        }
      }
    });
    this.mPassword = ((EditText)this.mView.findViewById(2131558653));
    this.mForgotPassword = ((TextView)this.mView.findViewById(2131558654));
    this.mForgotPassword.setText("Password must be at least 6 characters");
    this.mForgotPassword.setTextColor(-16777216);
    this.mShowPWSwitch = ((CustomSwitch)this.mView.findViewById(2131558656));
    this.mShowPWSwitch.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!OnClick.ok()) {}
        for (;;)
        {
          return;
          if (((CustomSwitch)paramAnonymousView).isChecked()) {
            CreateAccountFragment.this.mPassword.setTransformationMethod(new PasswordTransformationMethod());
          } else {
            CreateAccountFragment.this.mPassword.setTransformationMethod(null);
          }
        }
      }
    });
    this.mTermsAndConditions = ((TextView)this.mView.findViewById(2131558658));
    this.mTermsAndConditions.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!OnClick.ok()) {}
        for (;;)
        {
          return;
          paramAnonymousView = new Intent(CreateAccountFragment.this.getActivity(), VisitBlinkActivity.class);
          paramAnonymousView.putExtra(VisitBlinkFragment.URL_STRING, CreateAccountFragment.this.getString(2131099966));
          paramAnonymousView.putExtra(VisitBlinkFragment.TITLE_STRING, CreateAccountFragment.this.getString(2131099718));
          CreateAccountFragment.this.startActivity(paramAnonymousView);
        }
      }
    });
    this.mCreateAccountButton = ((Button)this.mView.findViewById(2131558657));
    this.mCreateAccountButton.setText(2131099794);
    this.mCreateAccountButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!OnClick.ok()) {}
        for (;;)
        {
          return;
          RegistrationRequest localRegistrationRequest;
          int i;
          if (CreateAccountFragment.this.validateInputs())
          {
            localRegistrationRequest = new RegistrationRequest();
            localRegistrationRequest.setEmail(CreateAccountFragment.this.mUserName.getText().toString());
            localRegistrationRequest.setPassword(CreateAccountFragment.this.mPassword.getText().toString());
            localRegistrationRequest.setPassword_confirm(CreateAccountFragment.this.mPassword.getText().toString());
            str = "?";
            i = 0;
            paramAnonymousView = str;
          }
          try
          {
            PackageInfo localPackageInfo = CreateAccountFragment.this.getActivity().getPackageManager().getPackageInfo(CreateAccountFragment.this.getActivity().getPackageName(), 0);
            paramAnonymousView = str;
            str = localPackageInfo.versionName;
            paramAnonymousView = str;
            int j = localPackageInfo.versionCode;
            paramAnonymousView = str;
            i = j;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
          String str = " ||" + LiveViewWhiteList.getProcCPUInfoHardware() + "||";
          localRegistrationRequest.setClient_specifier(Build.MANUFACTURER + " " + Build.MODEL + " | " + String.valueOf(Build.VERSION.SDK_INT) + " | " + paramAnonymousView + " | " + i + str);
          localRegistrationRequest.setClient_version(paramAnonymousView);
          localRegistrationRequest.setClient_type("android");
          localRegistrationRequest.setClient_name(Build.MANUFACTURER + " " + Build.MODEL);
          if ((BlinkApp.getApp().getDeviceToken() != null) && (BlinkApp.getApp().getDeviceToken().length() > 0)) {
            localRegistrationRequest.setNotification_key(BlinkApp.getApp().getDeviceToken());
          }
          BlinkAPI.BlinkAPIRequest(null, null, localRegistrationRequest, new BlinkAPI.BlinkAPICallback()
          {
            public void onError(BlinkError paramAnonymous2BlinkError)
            {
              if (CreateAccountFragment.this.getActivity() != null) {
                if (paramAnonymous2BlinkError.response == null) {
                  break label74;
                }
              }
              label74:
              for (paramAnonymous2BlinkError = (String)paramAnonymous2BlinkError.response.get("message");; paramAnonymous2BlinkError = paramAnonymous2BlinkError.getErrorMessage())
              {
                new AlertDialog.Builder(CreateAccountFragment.this.getActivity()).setMessage(paramAnonymous2BlinkError).setPositiveButton(2131099891, new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface paramAnonymous3DialogInterface, int paramAnonymous3Int)
                  {
                    CreateAccountFragment.this.getActivity().finish();
                  }
                }).create().show();
                return;
              }
            }
            
            public void onResult(BlinkData paramAnonymous2BlinkData)
            {
              BlinkApp.getApp().setLoginAuthToken(((AuthToken)paramAnonymous2BlinkData).getAuthtoken().getAuthtoken());
              BlinkApp.getApp().setUserName(CreateAccountFragment.this.mUserName.getText().toString());
              BlinkApp.getApp().setPassword(CreateAccountFragment.this.mPassword.getText().toString());
              BlinkApp.getApp().setLoggedIn(true);
              BlinkApp.getApp().setLastNetworkId("");
              BlinkApp.getApp().setLastCameraId("");
              CreateAccountFragment.this.addNetwork();
            }
          }, false);
          continue;
          if (CreateAccountFragment.this.getActivity() != null)
          {
            if (CreateAccountFragment.this.mErrorString == null) {
              CreateAccountFragment.access$402(CreateAccountFragment.this, CreateAccountFragment.this.getString(2131099861));
            }
            new AlertDialog.Builder(CreateAccountFragment.this.getActivity()).setTitle(CreateAccountFragment.this.mErrorString).setMessage(CreateAccountFragment.this.getString(2131100009)).setPositiveButton(2131099891, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
            }).create().show();
          }
        }
      }
    });
    this.mUserName.clearFocus();
    if ((getActivity().getApplicationInfo().flags & 0x2) != 0) {}
    for (int i = 1;; i = 0)
    {
      if ((i == 0) && (this.regionManager.isGetRegionsSuceeded()))
      {
        paramLayoutInflater = (Spinner)this.mView.findViewById(2131558661);
        paramViewGroup = new ArrayAdapter(getContext(), 17367049, this.regionManager.getAllFriendlyNames());
        paramViewGroup.setDropDownViewResource(17367049);
        paramLayoutInflater.setAdapter(paramViewGroup);
        paramViewGroup = this.mView.findViewById(2131558659);
        ((Button)this.mView.findViewById(2131558662)).setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            paramViewGroup.setVisibility(4);
          }
        });
        paramLayoutInflater.setOnItemSelectedListener(this);
        paramLayoutInflater.setSelection(this.regionManager.getIndexOfPreferredRegion().intValue());
        paramViewGroup.setVisibility(0);
      }
      return this.mView;
    }
  }
  
  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Log.i("selected DNS", (String)this.regionManager.getAllFriendlyNames().get(paramInt));
    BlinkApp.getApp().setServerUrlForDNSSubdomain((String)this.regionManager.getAllDNSSubdomains().get(paramInt));
  }
  
  public void onNothingSelected(AdapterView<?> paramAdapterView) {}
}


/* Location:              /home/zips/Android/Apktool/Blink4Home/Blink-136-dex2jar.jar!/com/immediasemi/blink/fragments/CreateAccountFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
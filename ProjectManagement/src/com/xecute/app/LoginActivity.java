package com.xecute.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.CountCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class LoginActivity extends FragmentActivity implements LoginFragment.LoginFragmentListener,
        SignupFragment.SignupFragmentListener, ResetFragment.ResetFragmentListener {

    final private String LOGIN = "LOGIN ACTIVITY";
    Context mContext;

    LoginFragment loginFragment;

    ProgressBar progressBar;
    ImageView progressBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        Parse.initialize(mContext, "0168Opz62QUNZQq7KBoYpky76XHovSkbsic0CuaV", "geMyhc0Ni3HR5IX8uzpNt5dxklmOgVtfveIJDxNt" );

        createColorFiles();

        // Check for valid credentials to skip login
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            // Removes activity from the stack so we can not navigate back to the login screen
            finish();
        }

        progressBg = (ImageView) findViewById(R.id.progressBg);
        progressBar = (ProgressBar) findViewById(R.id.progress);


        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);

        loginFragment = new LoginFragment();
        fragTrans.add(R.id.login_container, loginFragment).commit();

    }


    @Override
    public void onButtonSelected(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                Log.i(LOGIN, "Login Btn pressed!");
                UserLogin();
                break;

            case R.id.goToSignupBtn:
                Log.i(LOGIN, "Go to signup!");

                SignupFragment signupFragment = new SignupFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit)
                        .replace(R.id.login_container, signupFragment)
                        .addToBackStack(null).commit();
                break;

            case R.id.goToResetBtn:
                Log.i(LOGIN, "Go to reset!");

                ResetFragment resetFragment = new ResetFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit)
                        .replace(R.id.login_container, resetFragment)
                        .addToBackStack(null).commit();
                break;

            case R.id.signupBtn:
                Log.i(LOGIN, "SignUp Btn pressed!");
                UserSignUp();
                break;

            case R.id.resetBtn:
                Log.i(LOGIN, "Reset Btn pressed!");
                passwordReset();
                break;
        }
    }

    public void UserLogin() {
        EditText userEmailInput = (EditText) findViewById(R.id.user_email);
        final EditText userPasswordInput = (EditText) findViewById(R.id.user_password);
        String email = userEmailInput.getText().toString();
        String password = userPasswordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("All Fields Must Be Filled In").setTitle("Alert");
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            progressBarActivation(true);
            ParseUser.logInInBackground(email, password, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        Log.i(LOGIN, "Log In Successful!");

                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        // Removes activity from the stack so we can not navigate back to the login screen
                        finish();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Log In Failed with Error: " + e.getMessage()).setTitle("Alert");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        userPasswordInput.setText("");
                        progressBarActivation(false);
                    }
                }
            });

        }
    }

    public void UserSignUp() {
        Log.i(LOGIN, "UserSignUp fired");
        EditText userNameInput = (EditText) findViewById(R.id.signupEmail);
        EditText passwordInput = (EditText) findViewById(R.id.signupPassword);
        EditText confirmPassInput = (EditText) findViewById(R.id.signupConfPassword);
        final String userName = userNameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        String confirmPassword = confirmPassInput.getText().toString();

        Log.i(LOGIN, "email= " + userName + " Pass= " + password + " ConfPass= " + confirmPassword);

        if (password.isEmpty() && confirmPassword.isEmpty() && userName.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("All Fields Must Be Filled In").setTitle("Alert");
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (!password.equals(confirmPassword)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Passwords Do Not Match").setTitle("Alert");
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            progressBarActivation(true);
            ParseUser user = new ParseUser();
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(userName);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i(LOGIN, "Sign up successful!");

                        ParseUser.logInInBackground(userName, password, new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    Log.i(LOGIN, "Log In Successful!");

                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    // Removes activity from the stack so we can not navigate back to the login screen
                                    finish();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setMessage("Log In Failed with Error: " + e.getMessage()).setTitle("Alert");
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    progressBarActivation(false);
                                }
                            }
                        });

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Sign Up Failed with Error: " + e.getMessage()).setTitle("Alert");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        progressBarActivation(false);
                    }
                }
            });
        }
    }

    public void passwordReset() {
        progressBarActivation(true);
        Log.i(LOGIN, "Reset password fired.");
        final EditText resetEmailinput = (EditText) findViewById(R.id.resetEmail);
        String email = resetEmailinput.getText().toString();

        if (email.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Please Input Your Account Email Address").setTitle("Alert");
            AlertDialog dialog = builder.create();
            dialog.show();
            progressBarActivation(false);
        } else {
            ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            Log.i(LOGIN, "Reset password successful.");
                            getSupportFragmentManager().beginTransaction().replace(R.id.login_container,loginFragment).commit();
                            progressBarActivation(false);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("Reset Failed with Error: " + e.getMessage()).setTitle("Alert");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            resetEmailinput.setText("");
                            progressBarActivation(false);
                        }
                    }
                });
        }

    }

    public void progressBarActivation(Boolean state) {
        if (state) {
            progressBg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBg.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    public void createColorFiles() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("color");
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null && count == 0) {
                    // The count request succeeded. Log the count
                    Log.i(LOGIN, "Color File Creation Started");
                    int[] colorFiles = {R.drawable.color_icons_01,R.drawable.color_icons_02,
                            R.drawable.color_icons_03,R.drawable.color_icons_04,
                            R.drawable.color_icons_05,R.drawable.color_icons_06,
                            R.drawable.color_icons_07,R.drawable.color_icons_08,
                            R.drawable.color_icons_09,R.drawable.color_icons_10,
                            R.drawable.color_icons_11,R.drawable.color_icons_12,
                            R.drawable.color_icons_13};

                    int arraySize = colorFiles.length;
                    for (int i = 0, j = arraySize; i < j; i++ ) {
                        Log.i("ColorCreation", "Color name= " + getResources().getResourceEntryName(colorFiles[i]));
                        Resources rsrc= mContext.getResources();
                        int imageId = colorFiles[i];
                        Bitmap image = BitmapFactory.decodeResource(rsrc ,imageId);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        image.recycle();
                        byte[] data = bos.toByteArray();
                        try {
                            bos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        final ParseFile colorImage = new ParseFile(rsrc.getResourceEntryName(colorFiles[i])+".png", data);
                        colorImage.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.i(LOGIN, "Error saving Color File: " + e.getMessage());
                                } else {
                                    addImageFileToColor(colorImage);
                                }
                            }
                        });

                    }
                } else {
                    // The request failed
                }
            }
        });
    }

    private void addImageFileToColor(ParseFile colorImage) {
        ParseObject color = new ParseObject("color");
        color.put("colorImage", colorImage);
        ParseACL groupACl = new ParseACL();

        groupACl.setPublicWriteAccess(true);
        groupACl.setPublicReadAccess(true);
        color.setACL(groupACl);

        color.put("useStatus", false);

        color.saveInBackground();

    }
}

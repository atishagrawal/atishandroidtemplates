package com.ec327.chatterbox.chatterbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EmailConfirm extends Activity {

    /* Declares the views(objects) on the xml layout of the corresponding name. It is later initialized
    * in the constructor. */
    EditText et;
    Context ConfirmContext;
    CharSequence ConfirmFAIL;

    /* This is the Constructor in context of Java for the Android app. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emailconfirm);
    }

    /* Checks the confirmation code the user must input to create account.*/
    public void createAccount(View view){

        /* Initializes and links the xml views with the objects on the java file.*/
        et = (EditText) findViewById(R.id.confirm_passcode_input);
        ConfirmContext = getApplicationContext();
        ConfirmFAIL = "Wrong passcode.";

        if(et.getText().toString().equals("12345"))
        {
            Intent toAddShows = new Intent(this, AddShows.class);
            startActivity(toAddShows);
        } else {
            Toast.makeText(ConfirmContext, ConfirmFAIL, Toast.LENGTH_LONG).show();
        }
    }
}


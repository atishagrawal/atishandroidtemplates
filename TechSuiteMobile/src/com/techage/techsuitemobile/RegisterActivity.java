package com.techage.techsuitemobile;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView e1=(TextView) findViewById(R.id.registeration);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/AlexBrush-Regular.ttf");
        e1.setTypeface(face);

        TextView e2=(TextView) findViewById(R.id.submit_button_text);
        Typeface face2= Typeface.createFromAsset(getAssets(),"fonts/AlexBrush-Regular.ttf");
        e2.setTypeface(face2);

    }

    public void onSubmit(View view) {
    }
}

package com.example.androidvirtuallearning;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	// Web Service URL
    //protected static URI SERVER_URL = null;
    
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtServerURL; // Web Service URL
    
    private Button loginButton, signUpButton;
    
    private JSONObject theResponse; // The Server Response
    private boolean isLoginSuccess;
    @SuppressWarnings("unused")
	private String responseMessage;
    
    private ProgressDialog progress;
    
    private DataManager sharedData;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sharedData = DataManager.getInstance();
        
        if(!sharedData.isOnline())
        {
        	initDummyData(); // for offline version
        }
        
        
     // TextFields
        txtUsername = (EditText)findViewById(R.id.txtUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtServerURL = (EditText)findViewById(R.id.txtServerURL);
        
        // Loading/ Progress Dialog
        progress = new ProgressDialog(this);
        progress.setTitle("Authenticating");
		progress.setMessage("Please wait...");
		
		
		// the Login button
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        
        final Intent studentHome = new Intent(this, StudentHomeActivity.class);
        final Intent signUpForm = new Intent(this, SignUpActivity.class);
        
        //On click listener for loginButton
        loginButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) {
				
				
				// starts the loading dialog
				progress.show();
				
				// basic validation
				if(txtUsername.getText().length() > 0 
						&& txtPassword.getText().length() > 0 
						&& txtServerURL.getText().length() > 0)
				{
					
					if(sharedData.isOnline())
					{
						loginRequest();
					}
					else
					{
						// jump into Home Page
						startActivity(studentHome);
					}
					
					
				}
				else
				{
					
					// shows a toast alert
					Toast.makeText(getApplicationContext(), "Invalid Inputs",
							   Toast.LENGTH_SHORT).show();
				}
				
				
				if(sharedData.isOnline())
				{
					if(isLoginSuccess)
					{
						// jump into Home Page
						startActivity(studentHome);
						
						// shows a toast alert
						Toast.makeText(getApplicationContext(), "Login Success",
								   Toast.LENGTH_SHORT).show();
						
					}
					else // incorrect credentials 
					{
						
						// shows a toast alert
						Toast.makeText(getApplicationContext(), "Login Fail",
								   Toast.LENGTH_SHORT).show();
					}
				}
				
				
				// ends the loading dialog
				progress.dismiss();
				
				
			}
        });
        
        signUpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(signUpForm);
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
		

        return true;
    }
    
    @Override
	public void onBackPressed() {
	    // do nothing.
		// This will disable the back button on the device so the user cannot return to the Login Page
	}
    
    private void loginRequest()
    {
    	
    	// running on separate Thread as a background process
    	//new Thread(new Runnable() {
		    //public void run() 
		    //{
				    
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("username",  txtUsername.getText().toString()));
				params.add(new BasicNameValuePair("password", txtPassword.getText().toString()));
		
				// Create an HTTP request
				// the Login script on the server
				HttpPost request = new HttpPost("http://"+ txtServerURL.getText().toString() + "/login.php"); 
				
				try {
					
					request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient().execute(request);
					
					// The Response
					theResponse = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
					
					responseMessage = theResponse.getString("message");
					
					System.out.println("THE RESPONSE: "+ theResponse);
					
					if (theResponse.getInt("success") == 1)
					{
						isLoginSuccess = true;
						return;
					}
					else
					{
						isLoginSuccess = false;
						return;
					}	
						
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
		    //}
		  //}).start();
    }

    
    private void initDummyData()
    {
    	sharedData = DataManager.getInstance();
    	
    	Subject csc21 = new Subject();
    	Subject csc22 = new Subject();
    	Subject practicum = new Subject();
    	
    	Student student = new Student();
    	
    	csc21.setCode("CSC 21");
    	csc21.setName("Artificial Intelligence");
    	csc21.setSchedule("Sat");
    	csc21.setInstructor("Jorge Resurrection");
    	
    	Calendar cal = GregorianCalendar.getInstance();
    	cal.set(2014, 2, 1, 8, 0);
    	csc21.setStartTime(cal.getTime());
    	cal.set(2014, 2, 1, 12, 0);
    	csc21.setEndTime(cal.getTime());
    	
    	csc22.setCode("CSC 22");
    	csc22.setName("Advance Programming");
    	csc22.setSchedule("Sat");
    	csc22.setInstructor("Rustan Capal");
    	
    	cal.set(2014, 2, 1, 13, 0);
    	csc22.setStartTime(cal.getTime());
    	cal.set(2014, 2, 1, 19, 0);
    	csc22.setEndTime(cal.getTime());
    	
    	practicum.setCode("Practicum");
    	practicum.setName("Practicum");
    	practicum.setSchedule("TTH");
    	practicum.setInstructor("Jason Guia");
    	
    	cal.set(2014, 2, 1, 17, 0);
    	practicum.setStartTime(cal.getTime());
    	cal.set(2014, 2, 1, 18, 30);
    	practicum.setEndTime(cal.getTime());
    	
    	//Uri video1 = Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.accountability_and_sustainability );
    	//Uri video2 = Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.a_short_lecture_on_education );
    	//Uri pdf = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.samplepdf);
    	//Uri textLecture = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.textlecture);
        
    	
    	csc21.AddVideoPath(R.raw.accountability_and_ustainability);
    	csc21.AddVideoPath(R.raw.a_short_lecture_on_education);
    	csc21.AddPdfPath(R.raw.samplepdf);
    	csc21.AddTextLecture(R.raw.textlecture);
    	
    	csc22.AddVideoPath(R.raw.accountability_and_ustainability);
    	csc22.AddVideoPath(R.raw.a_short_lecture_on_education);
    	csc22.AddPdfPath(R.raw.samplepdf);
    	csc22.AddTextLecture(R.raw.textlecture);
    	
    	practicum.AddVideoPath(R.raw.accountability_and_ustainability);
    	practicum.AddVideoPath(R.raw.a_short_lecture_on_education);
    	practicum.AddPdfPath(R.raw.samplepdf);
    	practicum.AddTextLecture(R.raw.textlecture);
    	
    	student.AddSubject(csc21);
    	student.AddSubject(csc22);
    	student.AddSubject(practicum);
    	
    	student.setStudentID("15483");
    	student.setLastname("Dela Cruz");
    	student.setFirstname("Juana");
    	student.setMiddleInitial("A");
    	student.setUsername("juana");
    	student.setPassword("pepsipaloma");
    	
    	student.setUserImage(BitmapFactory.decodeResource(getResources(),R.drawable.good_girl_gina));
    	
    	sharedData.setStudent(student);
    }
}

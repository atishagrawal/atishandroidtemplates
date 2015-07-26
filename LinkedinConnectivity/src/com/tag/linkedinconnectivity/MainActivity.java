package com.tag.linkedinconnectivity;

import java.util.EnumSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;

public class MainActivity extends Activity implements OnClickListener {

	private LinkedInOAuthService oAuthService;
	private LinkedInApiClientFactory factory;
	private LinkedInRequestToken liToken;
	private LinkedInApiClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		Button btnLinkedinMain = (Button) findViewById(R.id.btnLinkedin);

		btnLinkedinMain.setOnClickListener(this);
	}

	public void onClick(View v) {

		if (v.getId() == R.id.btnLinkedin) {

			oAuthService = LinkedInOAuthServiceFactory.getInstance()
					.createLinkedInOAuthService(Constants.CONSUMER_KEY,
							Constants.CONSUMER_SECRET);
			System.out.println("oAuthService : " + oAuthService);

			factory = LinkedInApiClientFactory.newInstance(
					Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);

			liToken = oAuthService
					.getOAuthRequestToken(Constants.OAUTH_CALLBACK_URL);
			
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
					.getAuthorizationUrl()));
			startActivity(i);

		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		try {
			linkedInImport(intent);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void linkedInImport(Intent intent) {
		String verifier = intent.getData().getQueryParameter("oauth_verifier");
		System.out.println("liToken " + liToken);
		System.out.println("verifier " + verifier);

		LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(
				liToken, verifier);
		client = factory.createLinkedInApiClient(accessToken);

		// client.postNetworkUpdate("LinkedIn Android app test");

		Person profile = client.getProfileForCurrentUser(EnumSet.of(
				ProfileField.ID, ProfileField.FIRST_NAME,
				ProfileField.LAST_NAME, ProfileField.HEADLINE));

		System.out.println("First Name :: " + profile.getFirstName());
		System.out.println("Last Name :: " + profile.getLastName());
		System.out.println("Head Line :: " + profile.getHeadline());

	}
}

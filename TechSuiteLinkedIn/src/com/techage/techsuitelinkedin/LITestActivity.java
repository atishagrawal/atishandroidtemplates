package com.techage.techsuitelinkedin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientException;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;

public class LITestActivity extends Activity {

	// /change keysssssssssssssssssssssssssssss!!!!!!!!!!

	static final String CONSUMER_KEY = "75k0bh1hxbz5s5";
	static final String CONSUMER_SECRET = "HL14hTIQCq2naHOo";

	static final String APP_NAME = "LITest";
	static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	static final String OAUTH_CALLBACK_HOST = "litestcalback";
	static final String OAUTH_CALLBACK_URL = String.format("%s://%s",
			OAUTH_CALLBACK_SCHEME, OAUTH_CALLBACK_HOST);
	static final String OAUTH_QUERY_TOKEN = "oauth_token";
	static final String OAUTH_QUERY_VERIFIER = "oauth_verifier";
	static final String OAUTH_QUERY_PROBLEM = "oauth_problem";

	final LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
			.getInstance().createLinkedInOAuthService(CONSUMER_KEY,
					CONSUMER_SECRET);
	final LinkedInApiClientFactory factory = LinkedInApiClientFactory
			.newInstance(CONSUMER_KEY, CONSUMER_SECRET);

	static final String OAUTH_PREF = "LIKEDIN_OAUTH";
	static final String PREF_TOKEN = "token";
	static final String PREF_TOKENSECRET = "tokenSecret";
	static final String PREF_REQTOKENSECRET = "requestTokenSecret";

	TextView tv = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		setContentView(tv);
		final SharedPreferences pref = getSharedPreferences(OAUTH_PREF,
				MODE_PRIVATE);
		final String token = pref.getString(PREF_TOKEN, null);
		final String tokenSecret = pref.getString(PREF_TOKENSECRET, null);
		if (token == null || tokenSecret == null) {
			startAutheniticate();
		} else {
			showCurrentUser(new LinkedInAccessToken(token, tokenSecret));
		}

	}

	void startAutheniticate() {
		new AsyncTask<Void, Void, LinkedInRequestToken>() {

			@Override
			protected LinkedInRequestToken doInBackground(Void... params) {
				return oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
			}

			@Override
			protected void onPostExecute(LinkedInRequestToken liToken) {
				final String uri = liToken.getAuthorizationUrl();
				getSharedPreferences(OAUTH_PREF, MODE_PRIVATE)
						.edit()
						.putString(PREF_REQTOKENSECRET,
								liToken.getTokenSecret()).commit();
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(i);
			}
		}.execute();
	}

	void finishAuthenticate(final Uri uri) {
		if (uri != null && uri.getScheme().equals(OAUTH_CALLBACK_SCHEME)) {
			final String problem = uri.getQueryParameter(OAUTH_QUERY_PROBLEM);
			if (problem == null) {

				new AsyncTask<Void, Void, LinkedInAccessToken>() {

					@Override
					protected LinkedInAccessToken doInBackground(Void... params) {
						final SharedPreferences pref = getSharedPreferences(
								OAUTH_PREF, MODE_PRIVATE);
						final LinkedInAccessToken accessToken = oAuthService
								.getOAuthAccessToken(
										new LinkedInRequestToken(
												uri.getQueryParameter(OAUTH_QUERY_TOKEN),
												pref.getString(
														PREF_REQTOKENSECRET,
														null)),
										uri.getQueryParameter(OAUTH_QUERY_VERIFIER));
						pref.edit()
								.putString(PREF_TOKEN, accessToken.getToken())
								.putString(PREF_TOKENSECRET,
										accessToken.getTokenSecret())
								.remove(PREF_REQTOKENSECRET).commit();
						return accessToken;
					}

					@Override
					protected void onPostExecute(LinkedInAccessToken accessToken) {
						showCurrentUser(accessToken);
					}
				}.execute();

			} else {
				Toast.makeText(this,
						"Appliaction down due OAuth problem: " + problem,
						Toast.LENGTH_LONG).show();
				finish();
			}

		}
	}

	void clearTokens() {
		getSharedPreferences(OAUTH_PREF, MODE_PRIVATE).edit()
				.remove(PREF_TOKEN).remove(PREF_TOKENSECRET)
				.remove(PREF_REQTOKENSECRET).commit();
	}

	void showCurrentUser(final LinkedInAccessToken accessToken) {
		final LinkedInApiClient client = factory
				.createLinkedInApiClient(accessToken);

		new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... params) {
				try {

					final Person p = client.getProfileForCurrentUser();

					// /////////////////////////////////////////////////////////
					// here you can do client API calls ...
					// client.postComment(arg0, arg1);
					// client.updateCurrentStatus(arg0);
					// or any other API call
					// (this sample only check for current user
					// and pass it to onPostExecute)
					// /////////////////////////////////////////////////////////
					return p;
				} catch (LinkedInApiClientException ex) {
					return ex;
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof Exception) {
					// result is an Exception :)
					final Exception ex = (Exception) result;
					clearTokens();
					Toast.makeText(
							LITestActivity.this,
							"Appliaction down due LinkedInApiClientException: "
									+ ex.getMessage()
									+ " Authokens cleared - try run application again.",
							Toast.LENGTH_LONG).show();
					finish();
				} else if (result instanceof Person) {
					final Person p = (Person) result;
					tv.setText(p.getLastName() + ", " + p.getFirstName());
				}
			}
		}.execute();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		finishAuthenticate(intent.getData());
	}

	public void postMethod(View view) {

	}

}
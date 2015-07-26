package com.example.androidvirtuallearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DLCViewingActivity extends Activity {
	private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;  
    
    private DataManager sharedData;
	private DownloadManager dm;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        sharedData = DataManager.getInstance();
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_dlcviewing);

        this.webview = (WebView)findViewById(R.id.wbDLCView);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setDefaultTextEncodingName("utf-8");
        
        //webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(DLCViewingActivity.this, "Please Wait", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            /*
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadData(url, "application/pdf", "utf-8");
                return true;
            }
            
            */

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(DLCViewingActivity.this, "Oops! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                //alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                //    public void onClick(DialogInterface dialog, int which) {
                //        return;
                //    }
                //});
                alertDialog.show();
            }
        });
        
       // ("file:///android_res/drawable/", html, "text/html", "UTF-8", null
        
        //if(){}
        
        System.out.println("FINAL STRING: " + sharedData.getCurrentDLCScheme().toString());
        
        webview.loadUrl("file:///android_res/raw/"+ sharedData.getCurrentDLCScheme().toString());
        
        
        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        
        //dm.addCompletedDownload(sharedData.getCurrentDLCScheme().toString(), "", true, "application/pdf", "file:///android_res/raw/"+sharedData.getCurrentDLCScheme().toString(), 300, true);
        
    }
}

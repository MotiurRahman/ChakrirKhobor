package chakrirkhobor.bd.com.chakrirkhobor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ChakrirKhobor extends AppCompatActivity {
    public static String FACEBOOK_URL = "https://www.facebook.com/chakrirkhobornets/";
    public static String FACEBOOK_PAGE_ID = "381462145568985";

    private ProgressBar proBar;
    private WebView chakrirkhobor;
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chakrir_khobor);


        //Check internet

        if (isNetworkConnected()) {

        } else {
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
        }

        // Webview
        chakrirkhobor = (WebView)findViewById(R.id.web1);
        WebSettings webSettings = chakrirkhobor.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Improve wevView performance

        chakrirkhobor.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        chakrirkhobor.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        chakrirkhobor.getSettings().setAppCacheEnabled(true);
        chakrirkhobor.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        chakrirkhobor.setInitialScale(1);
        chakrirkhobor.getSettings().setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);





        chakrirkhobor.loadUrl("https://chakrirkhobor.net/");
        chakrirkhobor.setWebViewClient(new mywebClient());

         proBar = (ProgressBar)findViewById(R.id.progressBar1);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-4951262838901192~5643339385");

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        //Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
    }

    //For webview progress bar loading

    public class mywebClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            proBar.setVisibility(View.GONE);
            setTitle(view.getTitle());

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
            proBar.setVisibility(View.VISIBLE);
            setTitle("Loading.....");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    //End webview progress bar


    //WebView back button

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (chakrirkhobor.canGoBack()) {
                        chakrirkhobor.goBack();
                    } 
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    //end WebView back button

    //For internet connection

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    //End internet connection


    //For menu view

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chakrirkhobor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_facebook) {

            if (isNetworkConnected()) {
                Intent facebookIntent = openFacebook(this);
                startActivity(facebookIntent);
            } else {
                Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
            }




        }

        if(id==R.id.action_ratings){

            if (isNetworkConnected()) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setData(Uri.parse("market://details?id=chakrirkhobor.bd.com.chakrirkhobor"));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
            }


        }


        if(id==R.id.action_apps){

            if (isNetworkConnected()) {
                Intent devAccount = new Intent(Intent.ACTION_VIEW);
                devAccount.setData(Uri.parse("http://play.google.com/store/apps/dev?id=6031616565948906744"));
                startActivity(devAccount);
            } else {
                Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
            }


        }

        if (id == R.id.home) {

            if (isNetworkConnected()) {
                chakrirkhobor.loadUrl("https://chakrirkhobor.net/");

            } else {
                Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    //End menu view


    //Open facebook page

    public static Intent openFacebook(Context context){


        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated =  packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if(activated){
                if ((versionCode >= 3002850)) {

                    return new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL));

                } else {
                    return new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://page/" + FACEBOOK_PAGE_ID));

                }
            }else{
                return new Intent(Intent.ACTION_VIEW,
                        Uri.parse(FACEBOOK_URL));
            }
        } catch (PackageManager.NameNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(FACEBOOK_URL));
        }
    }

    //End opne facebook page


}

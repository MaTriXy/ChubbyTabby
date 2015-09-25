package com.elkriefy.android.apps.chubbytabby.activity;


import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.elkriefy.android.apps.chubbytabby.CustomTabActivityHelper;
import com.elkriefy.android.apps.chubbytabby.R;
import com.elkriefy.android.apps.chubbytabby.WebviewFallback;


public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://en.wikipedia.org/wiki/Chubby_Bunny";

    private CustomTabActivityHelper mCustomTabActivityHelper;
    //Here in this callback we can make UI changes if we want to
    private CustomTabActivityHelper.ConnectionCallback mConnectionCallback = new CustomTabActivityHelper.ConnectionCallback() {
        @Override
        public void onServiceConnectedCallback() {
            Log.w("MainActivity","Service Connected ");
        }

        @Override
        public void onServiceDisconnectedCallback() {
            Log.w("MainActivity","Service Disconnected ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupCustomTabHelper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabActivityHelper.bindCustomTabsService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCustomTabActivityHelper.unbindCustomTabsService(this);
    }

    private void setupCustomTabHelper() {
        mCustomTabActivityHelper = new CustomTabActivityHelper();
        mCustomTabActivityHelper.setConnectionCallback(mConnectionCallback);
        mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(URL), null, null);
    }

    private void openCustomTab(boolean useCustom) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        //Setting Toolbar Color
        int color = getResources().getColor(R.color.primary);
        intentBuilder.setToolbarColor(color);

        //Enabling Title showing
        intentBuilder.setShowTitle(true);

        //This part is adding custom buttons to the over flow menu
        String menuItemTitle = getString(R.string.menu_title_share);
        PendingIntent menuItemPendingIntent = createPendingShareIntent();
        intentBuilder.addMenuItem(menuItemTitle, menuItemPendingIntent);
        String menuItemEmailTitle = getString(R.string.menu_title_email);
        PendingIntent menuItemPendingIntentTwo = createPendingEmailIntent();
        intentBuilder.addMenuItem(menuItemEmailTitle, menuItemPendingIntentTwo);

        //Setting custom Close Icon
        //intentBuilder.setCloseButtonIcon(mCloseButtonBitmap);
        //Adding custom icon with custom action for the share action
        //intentBuilder.setActionButton(mActionButtonBitmap, getString(R.string.menu_title_share), createPendingShareIntent());

        //Setting start and exit animation for the custom tab.
            intentBuilder.setStartAnimations(this,R.anim.slide_in_right, R.anim.slide_out_left);
            intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        CustomTabActivityHelper.openCustomTab(this, intentBuilder.build(), Uri.parse(URL), new WebviewFallback(), useCustom);
    }

    private PendingIntent createPendingEmailIntent() {
        Intent emailIntent = new Intent(
                Intent.ACTION_SENDTO, Uri.fromParts("mailto", "example@example.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        return PendingIntent.getActivity(getApplicationContext(), 0, emailIntent, 0);
    }

    private PendingIntent createPendingShareIntent() {
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/html");
        actionIntent.putExtra(Intent.EXTRA_TEXT, "This is the Chubby Bunny Challenge/n" + URL);
        return PendingIntent.getActivity(getApplicationContext(), 0, actionIntent, 0);
    }

    public void CustomTabClick(View view) {
        openCustomTab(true);
    }

    public void WebViewClick(View view) {
        openCustomTab(false);
    }
}

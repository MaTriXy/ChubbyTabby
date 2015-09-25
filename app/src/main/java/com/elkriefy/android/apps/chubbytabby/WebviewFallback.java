package com.elkriefy.android.apps.chubbytabby;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.elkriefy.android.apps.chubbytabby.activity.WebViewActivity;


/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        activity.startActivity(intent);
    }
}
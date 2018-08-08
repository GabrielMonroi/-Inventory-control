package com.example.biel.yyy;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebSuport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_suport);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WebView engine = (WebView) findViewById(R.id.web_engine);

        // Progress bar.
        // With full screen app, window progress bar (FEATURE_PROGRESS) doesn't seem to show,
        // so we add an explicit one.
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);

        engine.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                progressBar.setProgress(progress);
            }
        });

        engine.setWebViewClient(new WebSuport.FixedWebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url)
            {
                progressBar.setVisibility(View.GONE);
            }
        });
        engine.getSettings().setJavaScriptEnabled(true);

        engine.loadUrl("http://negteam.16mb.com/public_html/suport.html");
    }

    public void onBackPressed() {
        WebView engine = (WebView) findViewById(R.id.web_engine);
        String url = engine.getUrl();
        if (url.equals("http://negteam.16mb.com/public_html/suport.html") ||
                url.equals("http://negteam.16mb.com/public_html/suport.html")) {
            // exit
            super.onBackPressed();
        } else {
            // go back a page, like normal browser
            engine.goBack();
        }
    }

    private class FixedWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}

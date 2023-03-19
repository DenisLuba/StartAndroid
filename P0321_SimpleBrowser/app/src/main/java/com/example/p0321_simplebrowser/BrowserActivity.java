package com.example.p0321_simplebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        WebView webView = findViewById(R.id.webView);
        Uri data = getIntent().getData();
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
        webView.loadUrl(data.toString());
    }
}
package com.greentech.kpzg.xyez;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ren.yale.android.cachewebviewlib.CacheWebView;

public class MainActivity extends AppCompatActivity {
    CacheWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        webView = findViewById(R.id.web);
        webView.setWebContentsDebuggingEnabled(true);
        WebSettings webseting = webView.getSettings();
        webseting.setJavaScriptEnabled(true);
        webseting.setDomStorageEnabled(true);

        //webseting.setAppCacheMaxSize(1024*1024*8);//设置缓冲大小，我设的是8M
        //String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        String appCacheDir = getExternalCacheDir().getAbsolutePath();
        webseting.setDatabasePath(appCacheDir);

        webseting.setAppCacheEnabled(true);
        webseting.setAppCachePath(appCacheDir);
        webseting.setAllowFileAccess(true);

        webseting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //webView.loadUrl("http://kpzg.agri114.cn/kpzg_xyez/xyez_cache/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("zhou",url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("zhou",url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            /**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){

                }
            }
        });
       webView.loadUrl("http://kpzg.agri114.cn/kpzg_xyez/xyez_cache");
       // webView.loadUrl("http://192.168.71.134:8080/kpzg_xyez/xyez_cache");
    }
}

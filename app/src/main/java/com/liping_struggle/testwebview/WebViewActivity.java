package com.liping_struggle.testwebview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

/**
 * Created by struggle_liping on 2017/8/5.
 */

public class WebViewActivity extends Activity {
    private BridgeWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){

        mWebView = (BridgeWebView) findViewById(R.id.webView);

        mWebView.setDefaultHandler(new DefaultHandler());
        //需要调试必要加入这一段代码否则在google浏览器里面看不到设备（如果是用的我这个库可以不用加因为库里面已经加了）
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
        mWebView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            }


        });
        mWebView.loadUrl("file:///android_asset/test.html");
        findViewById(R.id.activity_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callHandler app调用h5

                mWebView.callHandler("getH5", "test", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(WebViewActivity.this,"apploadh5",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        //registerHandler h5调用app
        mWebView.registerHandler("getApp", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack("0");
                Toast.makeText(WebViewActivity.this,data,Toast.LENGTH_LONG).show();
            }
        });

    }
}

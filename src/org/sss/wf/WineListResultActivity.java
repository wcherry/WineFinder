package org.sss.wf;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: wcherry
 * Date: 3/19/12
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class WineListResultActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winelistresults);
        WebView web = (WebView)findViewById(R.id.winelist_result);
        Bundle extras = getIntent().getExtras();
        //try {
            String main = URLEncoder.encode(extras.getString("mainIng"));
            String sauce = URLEncoder.encode(extras.getString("sauceIng"));
            String side = URLEncoder.encode(extras.getString("sideIng"));
        
            String urlStr = "http://10.4.110.183/wineresults.html?main=" + main + "&sauce=" + sauce + "&side=" + side;
            web.loadUrl(urlStr);
        //} catch (UnsupportedEncodingException e) {
          //  Toast.makeText(this, "Unable to encode URL", Toast.LENGTH_SHORT);
        //}

        //web.
    }
}
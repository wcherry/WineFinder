package org.sss.wf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.integration.android.*;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: wcherry
 * Date: 3/19/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class IngredientsActivity extends Activity implements View.OnLongClickListener{
    public static final String TAG = IngredientsActivity.class.getName();
    private static final String ACCESS_TOKEN = "A812E541-BC1A-40F6-8301-19348E2BB153";

    EditText mainIng = null;
    EditText sauceIng = null;
    EditText sideIng = null;
    int viewId = 0;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
        mainIng = (EditText)findViewById(R.id.main_ingredient);
        sauceIng = (EditText)findViewById(R.id.sauce_ingredient);
        sideIng = (EditText)findViewById(R.id.side_ingredient);

        mainIng.setOnLongClickListener(this);
        sauceIng.setOnLongClickListener(this);
        sideIng.setOnLongClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState has "+((savedInstanceState!=null)?"state":"no state"));
        if(savedInstanceState!=null){
            savedInstanceState.putInt("viewId", viewId);
            Log.i(TAG, "Saved View Id " + viewId);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState has "+((savedInstanceState!=null)?"state":"no state"));
        if(savedInstanceState!=null){
            viewId = savedInstanceState.getInt("viewId",0);
            Log.i(TAG, "Loaded View Id "+viewId);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(TAG, "Barcode scanner returned");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Log.i(TAG, "Scanned: "+scanResult.getContents());
            if(viewId != 0){
                EditText e =  (EditText)findViewById(viewId);
                e.setText(getProductName(scanResult.getContents()));
            } else {
                Log.w(TAG, "No view was found after returning from barcode scanner");
            }
        }
    }

    public String getProductName(String upc){
        if(upc==null || upc.isEmpty()) return "";
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("request_type", "1"));
        qparams.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
        qparams.add(new BasicNameValuePair("upc", upc));
        try{
            URI uri = URIUtils.createURI("http", "www.searchupc.com", -1, "/handlers/upcsearch.ashx",
                    URLEncodedUtils.format(qparams, "UTF-8"), null);
            HttpGet get = new HttpGet(uri);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        String[] lines =EntityUtils.toString(entity).split("\n");
                        String[] values =lines[1].split(",");
                        return removeQuotes(values[0]);
                    } else {
                        // Stream content out
                    }
                }
            }   else {
                Log.e(TAG,"Invalid return code from searchupc.com: "+response.getStatusLine().getReasonPhrase());
            }


        } catch(Exception e){
            Log.e(TAG,"Unable to get product info", e);
        }

        return "";
    }

    private String removeQuotes(String value) {
        int start = 0;
        int end = value.length();
        if(value.charAt(0)=='"') start++;
        if(value.charAt(end-1)=='"') end--;
        return value.substring(start, end);
    }

    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.searchByIngredientsButton:
                Intent intent = new Intent(view.getContext(), WineListResultActivity.class);
                intent.putExtra("mainIng", mainIng.getText().toString());
                intent.putExtra("sauceIng", sauceIng.getText().toString());
                intent.putExtra("sideIng", sideIng.getText().toString());
                startActivity(intent);
                break;
        }
    }

    public boolean onLongClick(View view) {
        viewId = view.getId();
        Log.i(TAG, "Starting Barcode scanner from view "+viewId);
        IntentIntegrator integrator = new IntentIntegrator(IngredientsActivity.this);
        integrator.initiateScan();
        return false;
    }


}
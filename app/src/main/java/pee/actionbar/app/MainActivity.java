package pee.actionbar.app;


import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;

import org.apache.http.Header;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";


    boolean locName = false;
    boolean lastmodName = false;
    boolean changefreqName = false;

    String elementName = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreated(Bundle) called");


        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "inside onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_go) {
            handleSax();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "inside id == R.id.action_settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleGo(){
        Log.i(TAG, "inside handleGo()");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.google.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.i(TAG, "inside onStart()");
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "inside onSuccess()");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "inside onFailure()");

            }
        });
    }

    protected void handleSax(){
        Log.i(TAG, "inside handleSax()");

        AsyncHttpClient client = new AsyncHttpClient();
        SaxAsyncHttpResponseHandler saxAsyncHttpResponseHandler = null;
        saxAsyncHttpResponseHandler = new SaxAsyncHttpResponseHandler<SiteUrlSet>(new SiteUrlSet()) {

            @Override
            public void onStart() {
                Log.i(TAG, "inside onStart()");
            }

            @Override
            public void onSuccess(int i, Header[] headers, SiteUrlSet siteUrlSet) {
                Log.i(TAG, "inside onSuccess()");
                List<SiteUrl> siteUrlList = siteUrlSet.getSiteUrlList();
                Log.i(TAG, "siteUrlList.size(): "+siteUrlList.size());
                for(SiteUrl siteUrl : siteUrlList){
                    Log.i(TAG, "siteUrl: "+siteUrl);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, SiteUrlSet siteUrlSet) {
                Log.i(TAG, "inside onFailure()");
            }
        };

        client.get("http://bin-iin.com/sitemap.xml", saxAsyncHttpResponseHandler);


    }



    private class SAXTreeStructure extends DefaultHandler {



        public void startElement(String namespaceURI, String localName,
                                 String rawName, Attributes atts) {
            Log.i(TAG, "inside SAXTreeStructure.startElement(). rawName: "+rawName);

            elementName = rawName;
            locName = false;
            lastmodName = false;
            changefreqName = false;
            if (rawName.equalsIgnoreCase("loc")) {
                locName = true;
            }

            if (rawName.equalsIgnoreCase("lastmod")) {
                lastmodName = true;
            }

            if (rawName.equalsIgnoreCase("changefreq")) {
                changefreqName = true;
            }



        }

        public void endElement(String namespaceURI, String localName,
                               String rawName) {
            Log.i(TAG, "inside SAXTreeStructure.endElement(). rawName: "+rawName);
            locName = false;
            lastmodName = false;
            changefreqName = false;


        }

        public void characters(char[] data, int off, int length) {
            Log.i(TAG, "inside SAXTreeStructure.characters().");
            if (locName || lastmodName || changefreqName) {
                String content = new String(data, 0, length);
                Log.i(TAG, "content: "+content);
            }


        }
    }


}

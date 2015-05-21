package pee.actionbar.app;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by pvu_asus on 21/05/2015.
 */
public class UrlSetHandler extends DefaultHandler {

    private static final String TAG = "UrlSetHandler";

    boolean locName = false;
    boolean lastmodName = false;
    boolean changefreqName = false;

    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes atts) {
        Log.i(TAG, "inside SAXTreeStructure.startElement(). rawName: " + rawName);


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

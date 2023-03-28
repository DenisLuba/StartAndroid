package com.example.p0791_xmlpullparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs";
    private int data = 0;

    public static final String XML_TEXT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<data><phone><company>Samsung</company></phone></data>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        data = R.xml.data;
        XmlPullParser parser = getParser(data);
        logFromXml(parser);
    }

    private XmlPullParser getParser(int data) {

        XmlPullParser parser;

        if (data == 0) {
            try {
//            получаем фабрику
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                включаем поддержку namespace (по умолчанию выключена)
//                (в нашем случае это не требуется)
                factory.setNamespaceAware(true);
//                создаем парсер
                parser = factory.newPullParser();
//                даем парсеру на вход Reader
                Reader reader = new StringReader(XML_TEXT);
                parser.setInput(reader);
                return parser;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        parser = getResources().getXml(data);
        try {
            parser.next();
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        return parser;
    }

    private void logFromXml(XmlPullParser parser) {

        StringBuilder temp = new StringBuilder();

        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {

                switch (parser.getEventType()) {

//                    начало документа
                    case XmlPullParser.START_DOCUMENT -> Log.d(LOG_TAG, "START_DOCUMENT");
//                    начало тэга
                    case XmlPullParser.START_TAG -> {

                        Log.d(LOG_TAG, "START_TAG: name = <" + parser.getName() +
                                ">, depth = " + parser.getDepth() +
                                ", attributeCount = " + parser.getAttributeCount());

                        temp.delete(0, temp.length());

                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            temp.append("\t").append(parser.getAttributeName(i))
                                    .append("=")
                                    .append(parser.getAttributeValue(i))
                                    .append("\n");
                        }
                        if (!TextUtils.isEmpty(temp)) Log.d(LOG_TAG, "Attributes:\n" + temp);
                    }
//                    содержимое тэга
                    case XmlPullParser.TEXT -> Log.d(LOG_TAG, "TEXT: " + parser.getText());
//                    конец тэга
                    case XmlPullParser.END_TAG -> Log.d(LOG_TAG, "END_TAG: name = </" + parser.getName() + ">");
                }

                parser.next();
            }

            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
package com.e.com.videoandimageuploaddemo;

import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by SOL13 on 5/11/2016.
 */
public class SoapClient
    {

    private String SERVICE_URL;
    private InputStream response;
    public String ErrorText;
    private boolean isCancelled;
    private int connectionTimeOut;
    //  HttpURLConnection httpURLConnection;

    public String getServiceUrl() {
        return SERVICE_URL;
    }

    public void setServiceUrl(String serviceUrl) {
        this.SERVICE_URL = serviceUrl;
    }

    private String soapAction;

    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    private String soapBody;

    public String getSoapBody() {
        return soapBody;
    }

    public void setSoapBody(String soapBody) {
        this.soapBody = soapBody;
    }

    public void setConnectionTimeOut(int milliseconds) {
        this.connectionTimeOut = milliseconds;
    }

    public InputStream queryTheServer() {
        try {
            URL oURL = new URL(SERVICE_URL);
            URLConnection urlConnection = oURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
            httpURLConnection.setRequestProperty("SOAPAction", AppConstants.NameSpace + soapAction);
            String soapMessage = String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                    "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>%s</soap:Body></soap:Envelope>", soapBody);
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(soapMessage.length()));
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            if (connectionTimeOut > 0)
                httpURLConnection.setConnectTimeout(connectionTimeOut);

            httpURLConnection.setFixedLengthStreamingMode(soapMessage.getBytes().length);
            OutputStream out = httpURLConnection.getOutputStream();
            out.flush();
            out.write(soapMessage.getBytes(), 0, soapMessage.getBytes().length);

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.getInputStream();
            } else {
                InputStream errorStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                ErrorText = ErrorInoutStreamToString(errorStream, "faultstring");
            }
        } catch (IOException ioEx) {
            ErrorText = ioEx.getMessage();
        } catch (Exception e) {
            ErrorText = e.getMessage();
        }
        return response;
    }

        public  String ErrorInoutStreamToString(InputStream is, String name) {
            String result = null;
            InputStream errorStream = new BufferedInputStream(is);
            try {

                result = InputStreamToString(errorStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result == null || result.equals(""))
                result = InputStreamToString(errorStream);
            return result;
        }
    public byte[] queryTheServerForFile() {
        byte[] result = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(SERVICE_URL);
            /*if (!URLUtil.isValidUrl(SERVICE_URL)) {*/
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = new URL(uri.toASCIIString());
            /* }*/
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.getInputStream();
                result = readBytes(response);
            } else {
                InputStream errorStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                ErrorText = InputStreamToString(errorStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public InputStream queryTheServerForJsonRequest() {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(SERVICE_URL);
            if (!URLUtil.isValidUrl(SERVICE_URL)) {
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                url = uri.toURL();
            }
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setDoOutput(false);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.getInputStream();
            } else {
                InputStream errorStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                ErrorText = InputStreamToString(errorStream);//ParseEntities.ErrorInoutStreamToString(errorStream, "faultstring");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public InputStream queryTheServerForImage() {
        try {
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            //String Tag = "fSnd";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            URL oURL = new URL(SERVICE_URL);
            URLConnection urlConnection = oURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");//multipart/form-data;boundary=" + boundary);
            //httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("SOAPAction", AppConstants.NameSpace + soapAction);
            String soapMessage = String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                    "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>%s</soap:Body></soap:Envelope>", soapBody);
            //httpURLConnection.setRequestProperty("Content-Length", String.valueOf(soapMessage.length()));
            //httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("SendChunked", "True");
            httpURLConnection.setRequestProperty("UseCookieContainer", "True");
            HttpURLConnection.setFollowRedirects(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(true);
            httpURLConnection.setRequestProperty("Content-length", soapMessage.getBytes().length + "");
            httpURLConnection.setReadTimeout(100 * 1000);
            httpURLConnection.setFixedLengthStreamingMode(soapMessage.getBytes().length);
            OutputStream output = httpURLConnection.getOutputStream();
            output.write(soapMessage.getBytes());
            output.flush();

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.getInputStream();
            } else {
                InputStream errorStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                //ErrorText = InputStreamToString(errorStream, "faultstring");
            }
        } catch (MalformedURLException ex) {
            ErrorText = ex.getMessage();
        } catch (IOException ioEx) {
            ErrorText = ioEx.getMessage();
        } catch (Exception e) {
            ErrorText = e.getMessage();
        }
        return response;
    }

    public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    private static String InputStreamToString(InputStream is) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream errorStream = new BufferedInputStream(is);
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void cancelRequest() {
        isCancelled = true;

        /*if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    }
}

package com.unclechen.sp.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtil {

    private static HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        }
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);//
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(300000); //
        return httpClient;
    }

    private static HttpClient getNewHttpClient() {
        return new HttpClient();
    }

    public static String GetContentForNew(String url, String charset) {
        GetMethod get = new GetMethod(url);
        try {

            System.out.println("======>HttpClient4Util.GetContent(), url=" + url + ", start......");
            int res = getNewHttpClient().executeMethod(get);
            String resCharset = get.getResponseCharSet();
            String content = get.getResponseBodyAsString();
            if (!charset.equalsIgnoreCase(resCharset)) {
                byte[] bytearray = content.getBytes(resCharset);
                return new String(bytearray, charset);
            }
            System.out.println("======>HttpClient4Util.GetContent(), url=" + url + ", end......");

            get.abort();
            get.releaseConnection();
            return content;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }

        return null;
    }

    public static String GetContent(String url, String charset) {
        GetMethod get = new GetMethod(url);
        try {
         //   System.out.println("======>HttpClient4Util.GetContent(), url=" + url + ", start......");
            int res = getHttpClient().executeMethod(get);
            String resCharset = get.getResponseCharSet();
            String content = get.getResponseBodyAsString();
        //    System.out.println("======>HttpClient4Util.GetContent(), content=" + content );
            if (!charset.equalsIgnoreCase(resCharset)) {
                byte[] bytearray = content.getBytes(resCharset);
                return new String(bytearray, charset);
            }
        //    System.out.println("======>HttpClient4Util.GetContent(), url=" + url + ", end......");
            return content;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }

        return null;
    }
}

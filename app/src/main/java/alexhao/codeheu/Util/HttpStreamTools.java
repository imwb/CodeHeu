package alexhao.codeheu.Util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import alexhao.codeheu.Application.iApplication;

/**
 * Created by ALexHao on 15/7/16.
 * 封装http操作和流操作
 */
public class HttpStreamTools {

    iApplication iapp;
    String responseCookie = "cookie is null";
    String sessionId = "sessionid is null";

    public HttpStreamTools() {
        this.iapp = iApplication.getInstance();
    }

    /**
     * get请求
     * @param geturl
     * @param type 0 :请求验证图片 1：请求数据
     * @return
     * @throws IOException
     */
    public byte[] httpGet(String geturl,int type) throws IOException {
        URL url = new URL(geturl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("GET");
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Host", "jw.hrbeu.edu.cn");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
        if(type==1){
            conn.setRequestProperty("Cookie", iapp.getSessionId());
            conn.setRequestProperty("Referer", "http://jw.hrbeu.edu.cn/ACTIONLOGON.APPPROCESS?mode=1&applicant=ACTIONQUERYSTUDENTSCORE");
            if (conn.getResponseCode() == 200) {
                InputStream instream = conn.getInputStream();
                return streamToByte(instream,type);
            } else {
                byte[] falsestream = new byte[0];
                return falsestream;
            }
        }else{
            responseCookie = iapp.getResponseCookie();
            responseCookie = conn.getHeaderField("Set-Cookie");
            if(responseCookie==null)
            {
                byte[] falsestream = new byte[0];
                return falsestream;
            }
            iapp.setResponseCookie(responseCookie);
            sessionId = iapp.getSessionId();
            sessionId = responseCookie.substring(0, responseCookie.indexOf(";"));
            iapp.setSessionId(sessionId);

            if (conn.getResponseCode() == 200) {
                InputStream instream = conn.getInputStream();
                return streamToByte(instream,type);
            } else {
                byte[] falsestream = new byte[0];
                return falsestream;
            }
        }
    }

    /**
     * post请求
     * @param posturl
     * @param sb
     * @param type 0:登录 1: 学期请求
     * @return
     * @throws IOException
     */
    public byte[] httpPost(String posturl, StringBuilder sb,int type ) throws IOException {
        URL url=new URL(posturl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("Cache-Control","max-age=0");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Length", String.valueOf(sb.toString().getBytes().length));
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Host", "jw.hrbeu.edu.cn");
        conn.setRequestProperty("Origin", "http://jw.hrbeu.edu.cn");
        conn.setRequestProperty("Referer", "http://jw.hrbeu.edu.cn/ACTIONLOGON.APPPROCESS?mode=1&applicant=ACTIONQUERYSTUDENTSCORE");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
        conn.setRequestProperty("Cookie", iapp.getSessionId());
        conn.getOutputStream().write(sb.toString().getBytes("GBK"));
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        if (conn.getResponseCode() == 200) {
            InputStream instream = conn.getInputStream();
            return streamToByte(instream,type);
        } else {
            byte[] falsestream = new byte[0];
            return falsestream;
        }
    }

    /**
     * byte 转 String
     *
     * @param b
     * @return
     * @throws UnsupportedEncodingException
     */
    public String byteToString(byte[] b) throws UnsupportedEncodingException {
        String result = new String(b, "GBK");
        return result;
    }

    /**
     * stream 转 byte
     *
     * @param is
     * @return
     * @throws IOException
     */
    public byte[] streamToByte(InputStream is, int type) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2048];
        if (type == 0)   //下载验证图片
        {
            int count = 0;
            while (count == 0) {
                count = is.available();
            }
            if (count < 1000)
                return null;
            while (true) {
                    int length = is.read(b);
                    if (length == -1) {
                        break;
                    }
                    baos.write(b, 0, length);
                }
                return baos.toByteArray();
        } else if (type == 1) {  //其他数据
            while (true) {
                int length = is.read(b);
                if (length == -1) {
                    break;
                }
                baos.write(b, 0, length);
            }
            return baos.toByteArray();
        }
        return null;
    }
}

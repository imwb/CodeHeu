package alexhao.codeheu.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class HttpUtil {
	public static String get(String urlstr,String encoding){
		StringBuffer sb=new StringBuffer();
		try {
			
			URL url=new URL(urlstr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-type", "text/html");
			conn.setRequestProperty("Accept-Charset", encoding);
			
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (conn.getResponseCode() == 200)
			{
				
				InputStream is = conn.getInputStream();
				int len = 0;
				byte[] buf = new byte[1024];
				
				while ((len = is.read(buf)) != -1)
				{
					sb.append(new String(buf, 0, len, encoding));
				}
				
				is.close();
			} else
			{
				return "网络连接失败";
			}


		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}

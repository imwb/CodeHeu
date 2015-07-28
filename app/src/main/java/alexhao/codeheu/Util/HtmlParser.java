package alexhao.codeheu.Util;

import android.content.Context;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;

/**
 * Created by ALexHao on 15/7/17.
 */
public class HtmlParser{

    HttpStreamTools hst;
    public HtmlParser( ) {
        hst = new HttpStreamTools();
    }

    public HtmlParser(Context context) {
        hst = new HttpStreamTools();
    }

    public String doParser(String result,int type)
    {
        String a=null;
        String b=null;
        String c="";
        Document doc= Jsoup.parse(result);
        Elements eles=doc.select("[class=color-row]");

        for(int i=0;i<eles.size();i++)
        {
            Elements eles1=eles.get(i).select("td");
            a=eles1.get(2).text().toString();
            b=eles1.get(8).text().toString();
            c+=(a+"                   "+b)+"\n";

        }
        return  c;
    }
}


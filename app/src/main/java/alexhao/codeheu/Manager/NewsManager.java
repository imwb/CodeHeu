package alexhao.codeheu.Manager;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import alexhao.codeheu.Database.NewsDao;
import alexhao.codeheu.Entry.Content;
import alexhao.codeheu.Entry.News;
import alexhao.codeheu.Util.HttpStreamTools;
import alexhao.codeheu.Util.HttpUtil;


public class NewsManager {

	private Context context;
	private NewsDao dao;
	public NewsManager(Context context){
		super();
		this.context=context;
		this.dao=new NewsDao(context);
	};
	
	
	/**
	 * @author wb
	 * @param number 获取的新闻数目
	 * @return list<News>
	 */
	public  ArrayList<News> getNews(int number){
		String url="http://jw.hrbeu.edu.cn/ACTIONSHOWINFO.APPPROCESS?mode=5&size="+number+"&sizeCatalog=4";
		String encoding="gbk";
		String htmlstr= HttpUtil.get(url, encoding);
		Document document=Jsoup.parse(htmlstr);
		Elements trElements=document.getElementsByClass("t9");
		ArrayList<News> list=new ArrayList<News>();
		for(int i=0;i<trElements.size();i++){
			Element tdElement=trElements.get(i).getElementsByTag("td").get(0);
			String href=tdElement.getElementsByTag("a").attr("href");
			String id=href.substring(href.lastIndexOf("="), href.length());
			String title=tdElement.getElementsByTag("a").text();
			News news=new News();
			news.setHref(href);
			news.setId(id);
			news.setTitle(title);
			list.add(news);
		}
		return list;
	};
	public ArrayList<News> getNewsfromDB(int currentpager) {
		return (ArrayList<News>) dao.select(currentpager);
	}
	public void add(ArrayList<News> news){
		dao.insert(news);
	}
	public void delete(){
		dao.delete();
	}
	public ArrayList<Content>  getNewsContent(String url) throws IOException {
		ArrayList<Content> contents=new ArrayList<Content>();
		String encoding="gb2312";
		HttpStreamTools httpStreamTools=new HttpStreamTools();
		byte[] bytes=httpStreamTools.httpGet(url, 1);
		String htmlstr=httpStreamTools.byteToString(bytes);

	//	String htmlstr=HttpUtil.get(url,encoding);
		Document document=Jsoup.parse(htmlstr);
		
		String title=document.getElementsByClass("infoTitle").get(0).text();
		Content titleContent=new Content();
		titleContent.setTitle(title);
		titleContent.setType(1);
		contents.add(titleContent);
	
		Element element=document.select("td.infoBody").get(0);
		Elements pElements=element.getElementsByTag("p");
		for(Element pelement:pElements){
			Content content=new Content();
			content.setType(2);
			content.setP(pelement.text());
			contents.add(content);
		}

		Elements tableElements=element.getElementsByTag("table");
		if(!tableElements.isEmpty()){
			Elements treElements=tableElements.get(0).getElementsByTag("tr");
			for(Element trElement:treElements){
				Content content=new Content();
				content.setType(3);
				ArrayList<String> trStrings=new ArrayList<String>();
				Elements tdeElements=trElement.getElementsByTag("td");
				for(Element tdElement:tdeElements ){
					trStrings.add(tdElement.text());
				}
				content.setTrs(trStrings);
				contents.add(content);
			}
		}
		return contents;
	}
	
}

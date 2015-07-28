package alexhao.codeheu.Database;

import java.util.ArrayList;
import java.util.List;


import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import alexhao.codeheu.Entry.News;

public class NewsDao {

	private MyDbhelper dbhelper;
    public NewsDao(Context context) {
		// TODO Auto-generated constructor stub
    	this.dbhelper=new MyDbhelper(context);
    }	
	
	public void insert(News news){
		String sql="insert into tb_news(title,href,date,content)values(?,?,?,?)";
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		
		db.execSQL(sql,new Object[]{news.getTitle(),news.getHref(),news.getDate(),news.getContent()});
		db.close();
	}
	public void insert(ArrayList<News> news ){
			for(News mynews:news){
				insert(mynews);
			}
	}
	public List<News> select(int currentpage){
		int offset =20*(currentpage-1);
		
		ArrayList< News> news=new ArrayList<News>();
		String sql="select title,href,date,content from tb_news limit?,?";
		SQLiteDatabase db=dbhelper.getReadableDatabase();
		
		Cursor cursor=db.rawQuery(sql, new String[]{offset+"",""+(offset+20)});
		while(cursor.moveToNext()){
			News newsitem=new News();
			
			String title=cursor.getString(0);
			String href=cursor.getString(1);
			String date=cursor.getString(2);
			String content=cursor.getString(3);
			newsitem.setContent(content);
			newsitem.setHref(href);
			newsitem.setDate(date);
			newsitem.setTitle(title);
			news.add(newsitem);
		}
		cursor.close();
		return news;
	}
	public void delete(){
		String sql="delete from tb_news";
	}
}

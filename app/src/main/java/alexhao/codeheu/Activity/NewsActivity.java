package alexhao.codeheu.Activity;

import java.io.IOException;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import alexhao.codeheu.Adapter.NewsContentAdapter;
import alexhao.codeheu.Entry.Content;
import alexhao.codeheu.Manager.NewsManager;
import alexhao.codeheu.R;

public class NewsActivity extends Activity{

	private ListView listView;
	private NewsManager newsManager;
	private ArrayList<Content> contents;
	private Context context;
	private ProgressBar progressBar;
	NewsContentAdapter contentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		newsManager=new NewsManager(context);
		setContentView(R.layout.newscontent);
		String url="http://jw.hrbeu.edu.cn/"+(String) getIntent().getExtras().get("href");
		System.out.println("url"+url);
		//webView=(WebView) findViewById(R.id.webView1);
		listView=(ListView) findViewById(R.id.lv_content);
		
		progressBar=(ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.VISIBLE);
		
		View titleview=getLayoutInflater().inflate(R.layout.title, null);
		listView.addHeaderView(titleview);
		new LoadNewsContent().execute(url);
	}
	class LoadNewsContent extends AsyncTask<String, Void, Void>{

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			
		}
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				contents=newsManager.getNewsContent(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//webView.loadData(newsContent.getContent(), "text/html", "gb2312");
			contentAdapter=new NewsContentAdapter(contents, NewsActivity.this);
			listView.setAdapter(contentAdapter);
			progressBar.setVisibility(View.GONE);
		}
		
	}
}

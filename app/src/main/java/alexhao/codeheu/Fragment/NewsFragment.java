package alexhao.codeheu.Fragment;

import java.util.ArrayList;

import alexhao.codeheu.Activity.NewsActivity;
import alexhao.codeheu.Adapter.NewsItemAdapter_wb;
import alexhao.codeheu.Entry.News;
import alexhao.codeheu.Manager.NewsManager;
import alexhao.codeheu.R;
import alexhao.codeheu.Util.DateUtil;
import alexhao.codeheu.Util.NetUtil;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;





public class NewsFragment extends Fragment implements IXListViewRefreshListener,IXListViewLoadMore,OnItemClickListener{

	// TODO: Rename and change types of parameters
	private final  int LOAD_MORE=1;
	private static final int LOAD_REFLESH=0;
	private static final int NO_NETCONNECTION=0;
	private static final int NETCONNECTION=1;
	private XListView listView=null;
	private NewsItemAdapter_wb adapter;
	private ArrayList<News> news=new ArrayList<News>();
	//	private ArrayList<News> news;
	//是否是第一进入
	private boolean isFirst=true;
	//是否有网络
	private boolean isConnNet=false;
	//当前数据是否从网络获取
	private boolean isLoadFromNetWork;
	//当前页
	private int currentPage=1;
	//
	private NewsManager newsManager;
	private Context context;
	public static NewsFragment newInstance( ) {
		NewsFragment fragment = new NewsFragment();
		return fragment;
	}

	public NewsFragment() {

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view =inflater.inflate(R.layout.fragment_news, container, false);
		listView=(XListView) view.findViewById(R.id.list);
		context=getActivity();
		newsManager=new NewsManager(getActivity());
		
		initlistview();
		if(isFirst){
			listView.startRefresh();
			isFirst=false;
		}
		
		return view;
	}


	private void initlistview() {
		// TODO Auto-generated method stub
		adapter=new NewsItemAdapter_wb(news,listView,getActivity());
		listView.setAdapter(adapter);

		listView.setPullLoadEnable(this);
		listView.setPullRefreshEnable(this);
		listView.setRefreshTime(DateUtil.getCurDateStr());
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onDetach() {
		super.onDetach();

	}

	class LoadNews extends AsyncTask<Integer,Void , Integer>{
		/*
		 * 执行前
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Toast.makeText(getActivity(), "load", 1).show();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			switch (params[0]) {
			case LOAD_MORE:
				currentPage++;
				if(NetUtil.checkNet(context)){
					int sum=currentPage*20;
					news=newsManager.getNews(sum);
					newsManager.delete();
					newsManager.add(news);
					return NETCONNECTION;
				}
				else {
					currentPage++;
					news=newsManager.getNewsfromDB(currentPage);
					return NO_NETCONNECTION;
				}
			case LOAD_REFLESH:
				if(NetUtil.checkNet(context)){
					currentPage=1;
					news=newsManager.getNews(20);
					publishProgress();
					newsManager.delete();
					newsManager.add(news);
					return NETCONNECTION;
				}
				else {
					currentPage=1;
					news=newsManager.getNewsfromDB(1);
					return NO_NETCONNECTION;
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		/*
		 * 执行后(non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			switch (result) {
			case NO_NETCONNECTION:
				Toast.makeText(context, "亲，没有网啊", 1).show();
				break;

			default:
				break;
			}
			refreshNews(news);
			listView.setRefreshTime(DateUtil.getCurDateStr());
			listView.stopRefresh();
			listView.stopLoadMore();
		}


	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		System.out.println("-----loadmore..");
		new LoadNews().execute(LOAD_MORE);
	}

	@Override
	public void onRefresh() {
		new LoadNews().execute(LOAD_REFLESH);
	}
	private void refreshNews(ArrayList<News> news) {
		adapter.refresh(news);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		browserNews((String)view.findViewById(R.id.tv_title).getTag());

	}

	private void browserNews(String href) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(getActivity(),NewsActivity.class);
		intent.putExtra("href", href);
		startActivity(intent);
	}

}

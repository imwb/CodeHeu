package alexhao.codeheu.Adapter;
import java.util.ArrayList;

import alexhao.codeheu.Entry.News;
import alexhao.codeheu.R;
import me.maxwin.view.XListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by wb on 15/7/13.
 */
public class NewsItemAdapter_wb extends BaseAdapter {

    private Context context;
    private ArrayList<News> lists;
    private LayoutInflater inflater;
    private XListView listView;
    private final class ViewHolder{
        TextView tv_title;
        ImageView iv_image;

    }

    public NewsItemAdapter_wb(ArrayList<News> lists, XListView listView,Context context) {
        this.lists = lists;
        this.context = context;
        this.listView=listView;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view=inflater.inflate(R.layout.newsitem_wb,null);
            holder=new ViewHolder();
            holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            holder.iv_image= (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(holder);
        }
        else {
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(lists.get(i).getTitle());
        holder.tv_title.setTag(lists.get(i).getHref());
        return view;
    }

	public void refresh(ArrayList<News> news) {
		// TODO Auto-generated method stub
		this.lists=news;
		notifyDataSetChanged();
		listView.setSelection(news.size()-20);
	}
}

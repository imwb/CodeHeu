package alexhao.codeheu.Adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;



import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import alexhao.codeheu.Entry.Content;
import alexhao.codeheu.R;

public class NewsContentAdapter extends BaseAdapter{

	ArrayList<Content> contents;
	Context context;
	LayoutInflater inflater;
	public NewsContentAdapter(ArrayList<Content> contents, Context context) {
		super();
		this.contents = contents;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		Content content=contents.get(position);
		int type=content.getType();
		switch (type) {
		case 1:
			View titleView=inflater.inflate(R.layout.newstitle, null);
			TextView titletextView=(TextView) titleView.findViewById(R.id.tv_title);
			titletextView.setText(content.getTitle());
			view=titleView;
			break;
		case 2:
			TextView ptextView=new TextView(context);
			ptextView.setSingleLine(false);
			ptextView.setTextSize(16);
			ptextView.setText(content.getP());
			view=ptextView;
			break;
		case 3:
			View tableView=inflater.inflate(R.layout.tableitem, null);
			TableRow tableRow=(TableRow) tableView.findViewById(R.id.tableRow1);
			ArrayList<String> tr=content.getTrs();
			for(String td:tr){
				System.out.println(td);
				
				TextView trtextView=new TextView(context);
//				LayoutParams params=tableView.getLayoutParams();
//				trtextView.setLayoutParams(new LayoutParams(params.width/tr.size(), params.height));
				trtextView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
				trtextView.setText(td);
				trtextView.setSingleLine(false);
				tableRow.addView(trtextView);
				}
			view=tableView;
			
		default:
			break;
		}
				return view;
	}

}

package alexhao.codeheu.Entry;

import java.util.ArrayList;

public class Content {

	/*
	 * 内容类型 1：title
	 * 		   2：p
	 *         3：tr
	 */
	private int type;
	private String title;
	private String p;
	private ArrayList<String> trs;
	
	public String getP() {
		return p;
	}
	
	public String getTitle() {
		return title;
	}
	
	public ArrayList<String> getTrs() {
		return trs;
	}
	
	public int getType() {
		return type;
	}
	
	public void setP(String p) {
		this.p = p;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTrs(ArrayList<String> trs) {
		this.trs = trs;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
}

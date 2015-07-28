package alexhao.codeheu.Entry;

import java.util.ArrayList;

public class NewsContent {

	private String title;
	private String content;
	private ArrayList<String> pcontents;
	public ArrayList<String> getPcontents() {
		return pcontents;
	}
	public void setPcontents(ArrayList<String> pcontents) {
		this.pcontents = pcontents;
	}
	public String getContent() {
		return content;
	}
	public String getTitle() {
		return title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "NewsContent [title=" + title + ", content=" + content + "]";
	}
	
}

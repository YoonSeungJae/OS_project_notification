package notification_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlingSaupdan {

	String url;
	Document doc;
	ArrayList<Data> datalist;
	
	CrawlingSaupdan(){
		url = "https://sw7up.cbnu.ac.kr";
		datalist = new ArrayList<Data>();
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Data> getInformation() {
		Elements showNoticeInformations = doc.select("sw-article-list[title=공지사항] .list-group a");
		datalist.clear();
		for(Element showNoticeInformation : showNoticeInformations) {
			String noticetitles = showNoticeInformation.select(".article-title").text();
			String noticeURL = showNoticeInformation.select("a").attr("href");
			String noticeDate = showNoticeInformation.select(".created-at").text();
			noticeURL = this.url+noticeURL;
			Data data = new Data(noticetitles, noticeDate, noticeURL);
			datalist.add(data);
		}
		/*Iterator<Data> i= datalist.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		*/
		return datalist;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlingSaupdan hi = new CrawlingSaupdan();
		hi.getInformation();
	}

}
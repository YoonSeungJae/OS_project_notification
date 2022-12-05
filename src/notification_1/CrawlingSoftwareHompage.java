package notification_1;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class CrawlingSoftwareHompage {

	String url;
	Document doc;
	ArrayList<Data> datalist;
	
	CrawlingSoftwareHompage(){
		url = "https://software.cbnu.ac.kr";
		datalist = new ArrayList<Data>();
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Data> getInformation() {
		Elements showNoticeInformations = doc.select(".noticebox #contents2 .noticon li");
		datalist.clear();
		for(Element showNoticeInformation : showNoticeInformations) {
			String noticetitles = showNoticeInformation.select(".notitit").text();
			String noticeURL = showNoticeInformation.select("a").attr("href");
			String noticeDate = showNoticeInformation.select(".notidate").text();
			noticeURL = this.url+noticeURL;
			Data data = new Data(noticetitles, noticeDate, noticeURL);
			datalist.add(data);
		}
		return datalist;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlingSoftwareHompage hi = new CrawlingSoftwareHompage();
		hi.getInformation();
	}

}

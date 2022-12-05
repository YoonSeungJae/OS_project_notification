package notification_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingAcademiccalendar {
	
	String url;
	Document doc;
	ArrayList<Data2> datalist;
	
	CrawlingAcademiccalendar(){
		url = "https://www.chungbuk.ac.kr/site/www/sub.do?key=1853";
		datalist = new ArrayList<Data2>();
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<Data2> getInformation() {
		Elements showNoticeInformations = doc.select(".academic_calendar .con1 ul li");
		datalist.clear();
		String[] arr = new String[2];
		for(Element showNoticeInformation : showNoticeInformations) {
			int i= 0;
			String str = showNoticeInformation.select("li").text();
			StringTokenizer stk=new StringTokenizer(str,"[]",false);
			while(stk.hasMoreTokens()){
				arr[i++] = stk.nextToken();
			}
			String noticetitles = arr[1];
			String noticeDate = arr[0];
			datalist.add(new Data2(noticetitles,noticeDate));
			System.out.println(arr[1]+"  //   "+ arr[0]);
			
		}
		
		//System.out.println(showNoticeInformations);
		return datalist;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlingAcademiccalendar hi = new CrawlingAcademiccalendar();
		hi.getInformation();
	
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
	}

}

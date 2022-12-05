package notification_1;
import java.time.LocalDate;

public class Data {
	String noticeURL;
	String noticeTitle;
	String noticeDate; //"년-월-일"
	
	Data (String noticeTitle, String noticeDate, String noticeURL){
		this.noticeTitle = noticeTitle;
		this.noticeDate= noticeDate;
		this.noticeURL = noticeURL;
	}
	public String toString(){
		return "제목:"+this.noticeTitle + "  공지날짜:"+this.noticeDate + "  URL:"+this.noticeURL;
	}
}

class Data2 {
	String notice;
	String date; //"년-월-일"
	
	Data2(String notice, String date){
		this.notice = notice;
		this.date= date;
	}
	public String toString(){
		return "일정:"+this.notice + "  기간:"+this.date;
	}
}

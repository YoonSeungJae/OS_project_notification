package notification_1;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MySql {
	static private final String DB_URL = "jdbc:mysql://localhost:3306/notification_db?serverTimezone=UTC";
	static private final String USER_NAME ="choistar";
	static private final String PASSWORD = "1234";
	private Connection con = null;
	private Statement stmt = null;
	
	ArrayList<Data> datalist;
	ArrayList<Data2> datalist2;
	
	MySql(){
		datalist = new ArrayList<Data>();
		datalist2 = new ArrayList<Data2>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);  // USER_NAME과 PASSWORD
			stmt = con.createStatement();
            System.out.println("Success");
		}
		catch(SQLException ex) {
			   System.out.println("SQLException" + ex);
	            ex.printStackTrace();
	        }
	        catch(Exception ex){ 
	            System.out.println("Exception:" + ex);
	            ex.printStackTrace();
	        }
	}
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySql mysql = new MySql();
		CrawlingAcademiccalendar hi = new CrawlingAcademiccalendar();
		ArrayList<Data2> datalist = hi.getInformation();
		
		mysql.updateData(datalist);
	}
	*/
	
	public void InsertData(String tableName, Data data) {
		String noticeTitle;
		String noticeURL;
		String noticeDate; //"연-월-일"
		
		noticeTitle = data.noticeTitle;
		noticeURL = data.noticeURL;
		noticeDate = data.noticeDate;
		try {
			
			String sql = "INSERT INTO "+tableName+"(title,date,URL)  VALUES(?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, noticeTitle);
			pstmt.setString(2, noticeDate);	
			pstmt.setString(3, noticeURL);
			pstmt.executeUpdate();		
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public Data updateData(String tableName, ArrayList<Data> datalist) {
		Data newData = null;
		boolean flag =false;
		try {
			Iterator<Data> i =datalist.iterator(); 
			while(i.hasNext()) {		
				Data data = i.next();
				String sql_res= "SELECT * FROM "+tableName+
						" WHERE EXISTS (SELECT * FROM "
						+tableName+" where title =\""+data.noticeTitle+"\")";
				ResultSet rs=stmt.executeQuery(sql_res);
				if(!rs.next()) {
					InsertData(tableName, data);
					newData = data;
					flag=true;
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return newData;
	}
	
	 ArrayList<Data> getData(String tablesName) {
		try {
			String viewStr = "SELECT * FROM "+tablesName+ " order by date desc";
			ResultSet rs = stmt.executeQuery(viewStr);
			while(rs.next()) {
				String noticetitles = rs.getString(2);
				String noticeDate = rs.getString(3);
				String noticeURL = rs.getString(4);
				Data data = new Data(noticetitles, noticeDate, noticeURL);
				datalist.add(data);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return datalist;
	}
	 
	
	 public void InsertData(Data2 data) {
			String noticeTitle;
			String noticeDate;
			
			noticeTitle = data.notice;
			noticeDate  = data.date;
			try {
				
				String sql = "INSERT INTO calendar(content, date) VALUES(?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, noticeTitle);
				pstmt.setString(2, noticeDate);
				pstmt.executeUpdate();		
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	 public boolean updateData(ArrayList<Data2> datalist) { //학사일정에서 일정만을 넣는 메소드, 일정은 파싱을 하지못해 수작업으로 넣었따.
			boolean flag =false;
			try {
				Iterator<Data2> i =datalist.iterator(); 
				while(i.hasNext()) {		
					Data2 data = i.next();
					System.out.println(data);
					//String sql_res= "SELECT * FROM calendar WHERE EXISTS (SELECT * FROM academiccalendar where content =\""+data.notice+"\")";
					//ResultSet rs=stmt.executeQuery(sql_res);
					//if(!rs.next()) {
						InsertData(data);
						flag=true;
					//}
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			return flag;
		}
	
/*	 public void updateDate() {//학사일정 수작업을 위한 메소드
		 int id=5;
		 String startdate;
		 String enddate;
		 String sql_res;
		 ResultSet rs;
		 PreparedStatement pstmt;
		 Scanner scanner = new Scanner(System.in);
		 while(id<=68) {
			 try {
				 
				 sql_res = "SELECT * FROM academiccalendar WHERE id ="+id;
				 rs = stmt.executeQuery(sql_res);
				 while(rs.next()) 
						System.out.println(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)); 
				 System.out.print("시작일:");
				 startdate = scanner.next();
				 System.out.print("종료일:");
				 enddate = scanner.next();
				 String SQL = "update academiccalendar set startdate=?, enddate=? where id=?";
				 pstmt = con.prepareStatement(SQL);
				 
				 pstmt.setString(1,startdate);
				 pstmt.setString(2,enddate);
				 pstmt.setInt(3,id++);
				 int r =pstmt.executeUpdate();
			 }catch(Exception e) {
					System.out.println(e);
			 }
		 }
	
	 }
	 */
	 
	 ArrayList<Data2> getData2(String tablesName) {
			try {
				String viewStr = "SELECT * FROM "+tablesName;//+ " order by startdate asc";
				ResultSet rs = stmt.executeQuery(viewStr);
				while(rs.next()) {
					String noticetitles = rs.getString(2);
					String noticeDate = rs.getString(3);
					Data2 data = new Data2(noticetitles, noticeDate);
					datalist2.add(data);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			return datalist2;
		}
	
	//select * from 'table_name' where '날짜 컬럼' between date('start_date') and date('end_date')+1 기간
	

}

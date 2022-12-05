package notification_1;

import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.awt.*;

public class Notification extends JFrame{
	String data[][];
	
	
	Notification(ArrayList<Data> datalist){
		int dataNumber = datalist.size();
		data= new String[dataNumber][3];
		Iterator<Data> i =datalist.iterator(); 
		int row=0;
		while(i.hasNext()) {		
			Data dataStruct = i.next();
			data[row][0]= dataStruct.noticeTitle;
			data[row][1]= dataStruct.noticeDate;
			data[row][2]= dataStruct.noticeURL;
			row++;
		}
		String column[] = {"공지사항","게시일"};   //칼럼 (열)
		JTable jt = new JTable(data,column);
		jt.setCellSelectionEnabled(true);
		ListSelectionModel select= jt.getSelectionModel(); //리스트 모델
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 셀만
		
		select.addListSelectionListener(new ListSelectionListener() {   //리스너붙이기
			public void valueChanged(ListSelectionEvent e) {
				int row = jt.getSelectedRow();
				int columns = jt.getSelectedColumn();
				String url = data[row][2];
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setSize(500,300);
        Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        setVisible(true);
	}
		
		
	
    public static void main(String[] args) {
    	
    }
}
class Notification2 extends JFrame{
	String data[][];
	
	Notification2(ArrayList<Data2> datalist){
		int dataNumber = datalist.size();
		data= new String[dataNumber][3];
		Iterator<Data2> i =datalist.iterator(); 
		int row=0;
		while(i.hasNext()) {		
			Data2 dataStruct = i.next();
			data[row][0]= dataStruct.notice;
			data[row][1]= dataStruct.date;
			row++;
		}
		String column[] = {"일정","기간"};   //칼럼 (열)
		JTable jt = new JTable(data,column);
		jt.setCellSelectionEnabled(true);
		ListSelectionModel select= jt.getSelectionModel(); //리스트 모델
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 셀만
		
		select.addListSelectionListener(new ListSelectionListener() {   //리스너붙이기
			public void valueChanged(ListSelectionEvent e) {
				int row = jt.getSelectedRow();
				int columns = jt.getSelectedColumn();
				String url = data[row][2];
				try {
					Desktop.getDesktop().browse(new URI("https://www.chungbuk.ac.kr/site/www/sub.do?key=1853"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JScrollPane sp = new JScrollPane(jt);
		add(sp);
		setSize(500,300);
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
	}
	
	
		
		
}

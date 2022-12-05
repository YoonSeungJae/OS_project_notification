package notification_1;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;
//academiccalendar
//saupdandata
//softwarehompagedata

public class Main extends JFrame {
	MySql mysql;
	CrawlingSoftwareHompage swhompageSystem;
	CrawlingSaupdan saupdanSystem;
	ArrayList<Data> datalist;
	ArrayList<Data2>datalist2;
	TimerTask task; //일정시간에 한번씩 업데이트 확인을 위한 업무수행을 위한 변수
	


    public Main(){
    	mysql= new MySql();
    	swhompageSystem = new CrawlingSoftwareHompage();
    	saupdanSystem = new CrawlingSaupdan();
    	
    	datalist = new ArrayList<Data>();
    	datalist2 = new ArrayList<Data2>();
    	
    	task = new TimerTask() {
    	    public void run() {
    	    	datalist= swhompageSystem.getInformation();
    	    	Data newdata = null;
    	    	newdata = mysql.updateData("softwarehompagedata", datalist);
    	    	if(newdata != null) {
    	    		TrayIconHandler.displayMessage("소프트웨어홈페이지의 새로운 공지사항!", newdata.noticeTitle, MessageType.INFO);
    	    		newdata = null;
    	    	}
    	    	
    	    	datalist= saupdanSystem.getInformation(); 
    	    	newdata =mysql.updateData("saupdandata", datalist);
    	    	if( newdata!=null) {
    	    		TrayIconHandler.displayMessage("사업단의 새로운 공지사항!", newdata.noticeTitle, MessageType.INFO);
    	    		newdata =null;
    	    	}
    	    }
    	};
    	Timer timer = new Timer();
    	long period = 3600000L; //1시간에 한번 실행;
    	timer.scheduleAtFixedRate(task, 10000, period);
    	
    
    	
    	
    	
        setTitle("알리미");
        setSize(600,300);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        Container c = getContentPane();
        c.setLayout(null);

        JLabel l1 = new JLabel("학사일정 알리미");
        l1.setLocation(0, 20);
        l1.setSize(600, 50);
        l1.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("굴림 보통", Font.BOLD, 15);
        l1.setFont(font);
        c.add(l1);

        /*
        JLabel l2 = new JLabel("이번주 일정 : ?????");
        l2.setLocation(0,80);
        l2.setSize(600,50);
        l2.setHorizontalAlignment(JLabel.CENTER);
        c.add(l2);
         */

        JButton b1 = new JButton("학사일정");
        b1.setLocation(50,150);
        b1.setSize(100,50);
        c.add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(datalist2 != null) {
            		datalist2.clear();
            	}
            	datalist2= mysql.getData2("calendar");
                new Notification2(datalist2);
            }
        });

        JButton b2 = new JButton("소중단");
        b2.setLocation(250, 150);
        b2.setSize(100,50);
        c.add(b2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(datalist != null) {
            		datalist.clear();
            	}
            	datalist= mysql.getData("saupdandata");
                new Notification(datalist);
            }
        });

        JButton b3 = new JButton("학과공지");
        b3.setLocation(450,150);
        b3.setSize(100, 50);
        c.add(b3);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(datalist != null) {
            		datalist.clear();
            	}
            	datalist= mysql.getData("softwarehompagedata");
                new Notification(datalist);
            }
        });

        setVisible(true);
    }
    /*
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }
     */

    public static void main(String [] args){
    	Main app = new Main();
    	TrayIconHandler.registerTrayIcon( //트레이 아이콘 등록
    			Toolkit.getDefaultToolkit().getImage("src/images/background.png"),
    			"학사일정알리미",
    			new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					app.setVisible(true); //트레이 아이콘을 눌렀을때 jframe을 보이게 하기
    				}
    			}
    		);
    		
    		TrayIconHandler.addItem("Exit", new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) { //트레이 아이콘 우 버튼클릭후 Exit 버튼을 눌렀을때 프로그램이 종료
    				System.exit(0);
    			}
    		});
    		TrayIconHandler.displayMessage("공지알리미", "프로그램이 실행중입니다.", MessageType.INFO);
    		
    }
}
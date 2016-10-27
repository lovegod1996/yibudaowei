package userFunction;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import title_bar_Activity.TitlebarActivity;

public class GateInOut1 extends TitlebarActivity {
	TextView t_title;
	Button other;
	
	 WifiManager wifiManager;
	
	TextView wifiname1;
	TextView du1;
	Button in2;

	String wifiname;
	static SharedPreferences sp ;
	static String name_number;
	
	  Handler handler;
	    ClientThread clientThread;// 定义与服务器通信的子线程
	
	    
	
	   public void handlerMsg(){
	        handler=new Handler()
	             {
	             	@Override
	      			public void handleMessage(Message msg)
	      			{
	      				// 如果消息来自于子线程
	      				if (msg.what == 0x123)
	      				{// 读取的内容
	      					
	     					
	     					if(msg.obj.toString()!=null&&msg.obj.toString().contains("kong")){
	     						String[] Pinfo=msg.obj.toString().split(";");
	     						String pinfo=Pinfo[1];
	  
	     						Intent intent=new Intent(GateInOut1.this, GateInOut.class);
	       						intent.putExtra("pinfo",pinfo);
	       						Toast toast1=Toast.makeText(getApplicationContext(), pinfo+"", Toast.LENGTH_SHORT);			
	       					 toast1.show();
	       						
	       						 startActivity(intent);	
	     					}
	      					
	      				}							
	      			}
	      		};    
	            clientThread = new ClientThread(handler);
	           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
	       } 
	   
	  
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_inout2);
 
		init();
		this.handlerMsg();
		
		
in2.setOnClickListener(new OnClickListener() {
			
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sp = getSharedPreferences("userInfo", 0);
				  //取出手机号数据
				 name_number = sp.getString("USER_NAME",null); 
				
				 Message msg = new Message();
					msg.what = 0x345;  
					msg.obj = "kong;"+wifiname+";"+name_number;
					clientThread.revHandler.sendMessage(msg);
			}
		});
		
	}

	private void init() {
		// TODO Auto-generated method stub
		t_title = (TextView) findViewById(R.id.text_title);
		other = (Button) findViewById(R.id.button_other);
		t_title.setText("进<——>出");
		other.setVisibility(View.GONE);
		
		wifiname1 = (TextView) findViewById(R.id.welcome1);
		du1 = (TextView) findViewById(R.id.du1);
		in2=(Button)findViewById(R.id.in2);

		
		
		Intent intent=getIntent();
		 wifiname=intent.getStringExtra("wifiname");
		wifiname1.setText(wifiname);
		
String wifiQ=intent.getStringExtra("wifiq");
		
		
		du1.setText(wifiQ);
		
	
		
	
		
		
		
	}
}

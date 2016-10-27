package userFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import title_bar_Activity.TitlebarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import android.widget.Toast;

public class GateInOut extends TitlebarActivity {

	TextView t_title;
	Button other;

	//TextView wifi_name;
//	TextView wifi_du;
	TextView welcome;
	TextView pa_name;//停车场名
	TextView pa_left;//现有空车位
	
	
	
	TextView we_name;//用户名
	TextView pa_time2;//进场时间
	TextView pa_standard2;//收费标准		
	Button in;//按钮
	
	String pname = "";
	String pcri = "";
String intime="";

Handler handler;
ClientThread clientThread;// 定义与服务器通信的子线程


static SharedPreferences sp ;
static String name_number;

String save_time;

public void handlerMsg(){
    handler=new Handler()
         {
         	@Override
  			public void handleMessage(Message msg)
  			{
  				// 如果消息来自于子线程
  				if (msg.what == 0x123)
  				{// 读取的内容
  					
 					if(msg.obj.toString()!=null&&msg.obj.toString().contains("getin"))
 					{
 						
 					
 						AlertDialog.Builder builder =new AlertDialog.Builder(GateInOut.this);
 						builder.setTitle(pname);
 						//builder.setAdapter(new ArrayAdapter<String>(this, resource, textViewResourceId), null);
 						builder.setMessage(name_number+"\n"+"抬杆进场成功！"+"\n"+"时间:"+getNowtime());
 			
 	                 	builder.show();
 	                 
 	                 	Timer timer = new Timer();
 	                 	TimerTask task=new TimerTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								 Intent intent=new Intent(GateInOut.this, MainActivity.class);
		  						 startActivity(intent);	
		  						GateInOut.this.finish();
							}
						};
						timer.schedule(task, 1000 * 3);
 	                 	
 	             
 			}
 					
  					}
 					if(msg.obj.toString()!=null&&msg.obj.toString().contains("getout"))
 					{
 						//Toast toast1=Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT);			
      					// toast1.show();
  						
      					AlertDialog.Builder builder =new AlertDialog.Builder(GateInOut.this);
 						
      					builder.setTitle(pname);
 						builder.setMessage(name_number+"\n"+"抬杆出场成功！"+"\n"+"费用:"+msg.obj.toString().replace("getout;", "")+"  元"+"\n"+"出场时间："+getintime(save_time));
 	                 	builder.show();
 	                 	Timer timer = new Timer();
	                 	TimerTask task=new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							 Intent intent=new Intent(GateInOut.this, MainActivity.class);
	  						 startActivity(intent);	
	  						GateInOut.this.finish();
						}
					};
					timer.schedule(task, 1000 * 3);

  					}
  					
  				}							
  			
  		};    
        clientThread = new ClientThread(handler);
       	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
   } 



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_inout);

		init();

	}

	public void init() {
		t_title = (TextView) findViewById(R.id.text_title);
		other = (Button) findViewById(R.id.button_other);
		t_title.setText("进<——>出");
		other.setVisibility(View.GONE);

	//	wifi_name = (TextView) findViewById(R.id.wifi_name);
	//	wifi_du = (TextView) findViewById(R.id.wifi_du);
		we_name = (TextView) findViewById(R.id.we_name);
		pa_time2 = (TextView) findViewById(R.id.pa_time2);
		pa_standard2 = (TextView) findViewById(R.id.pa_standard2);///////////////////////
		
		
		
		
		welcome = (TextView) findViewById(R.id.welcome);
		pa_name = (TextView) findViewById(R.id.pa_name);
		pa_left = (TextView) findViewById(R.id.pa_left);
		in = (Button) findViewById(R.id.in);

		Intent intent = getIntent();
		String PI = intent.getStringExtra("pinfo");
	//	String WIFI = intent.getStringExtra("wifiname");
	//	String wifiQ = intent.getStringExtra("wifiq");
	//	wifi_name.setText(WIFI);
	//	wifi_du.setText(wifiQ);
		
		ArrayList<String> pinfo = new ArrayList<String>();
		pinfo = SplitString(PI);
		pa_name.setText(pinfo.get(0));
		pa_left.setText(pinfo.get(1)+"个");

		pname = pinfo.get(0);   //获取停车场名
		pcri = pinfo.get(2);    //获取停车场单价
		intime=pinfo.get(3);   //获取进场时间
		
		
		sp = getSharedPreferences("userInfo", 0);
		//取出手机号数据
		 name_number = sp.getString("USER_NAME",null); 
		 
		 
		we_name.setText(name_number);
		pa_time2.setText(getintime(getNowtime()));
		pa_standard2.setText(pcri+"元/小时");
		
		
		
		
	     this.handlerMsg();
		if (PI.contains("false")) {
			in.setText("进场");

			in.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					
					try {
					    Message msg = new Message();
						msg.what = 0x345;
						msg.obj = "getin;"+name_number+";"+pname+";"+getNowtime();
						clientThread.revHandler.sendMessage(msg);
				    } 
		    	   catch (Exception e)
		    	   {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }		
					
					
				}
			});
		} else {
			in.setText("出场");

			in.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub				
					///计算时间算法
					double pay=CalPfees(intime, getNowtime(), pcri);
					save_time=getNowtime();
					try {
					    Message msg = new Message();
						msg.what = 0x345;
					//	msg.obj = "getout;"+name_number+";"+pname+";"+getNowtime()+";"+pay;
						msg.obj = "getout;"+name_number+";"+pname+";"+save_time+";"+pay;//----------------------
						clientThread.revHandler.sendMessage(msg);
				    } 
		    	   catch (Exception e)
		    	   {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }			
				}
			});
		}

	}

	public static ArrayList<String> SplitString(String str) {
		String[] ss = str.split("\\+");
		ArrayList<String> list = new ArrayList<String>();
		for (String string : ss) {
			list.add(string);
		}
		return list;
	}
	public static String getNowtime(){
		//获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
	/*
	 * 计算停车费用
	 * 
	 */
	public static  double  CalPfees(String inTime,String ouTime,String pcri2){
		
		double fees=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				java.util.Date ot=df.parse(ouTime);
				java.util.Date it=df.parse(inTime);
				long l=ot.getTime()-it.getTime();
				
				  long day=l/(24*60*60*1000);
				   long hour=(l/(60*60*1000)-day*24);
				   long min=((l/(60*1000))-day*24*60-hour*60);
				   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
				   
				   double p=Double.parseDouble(pcri2);
				   
				   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
				   if(min<30&day==0&&hour==0){
					   fees=0;
				   }else{
					   fees=(day*24+hour+1)*p;
				   }
				  
				   
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return fees;
		
	}
	
	 public static String getintime(String in_time)  {
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat df2=new SimpleDateFormat("MM"+"月"+"dd"+"日"+"HH:mm");		
		Date d=new Date();
		String str = null;
		try {
			str = df2.format(df.parse(in_time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return str;
		 
	
	 }
}
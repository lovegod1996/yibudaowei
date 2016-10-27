package wifiAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import wifiAdapter.WifiAutoConnectManager.WifiCipherType;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import admin.Account_information;
import admin.Account_manage;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WifiMain extends Activity {

	Button btnConnect;
	WifiManager wifiManager;
	WifiAutoConnectManager wac;
	TextView textView1;
	EditText editPwd;
	EditText editSSID;
	
	SharedPreferences sp;//登录状态
	String   name_nu;
	
	
	
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
					if(msg.obj!=null)
					{
						 AlertDialog.Builder builder = new Builder(WifiMain.this); 
				      	 builder.setMessage("停车场当前空车位数量："+msg.obj.toString()); 
				      	// builder.setTitle("欢迎"+name_nu+"的登录"); 
				      	 builder.setTitle("欢迎"+name_nu+"的登录"); 
				      	 builder.setPositiveButton("确定", 
				      	 new android.content.DialogInterface.OnClickListener() { 
				      	  		   		 
						 @Override
						 public void onClick(DialogInterface dialog, int arg1) {
							// TODO Auto-generated method stub
							 
							 //数据库查询，是否有入场记录，若没有，弹出欢迎信息，记录进场时间插入数据库，记录用户当前信息
							 //若有，则询问用户是否出场，若出场，计算停车时间，计算应缴费用，扣费，离场

							 handler=new Handler()
				             {
				             	@Override
				      			public void handleMessage(Message msg)
				      			{
				      				// 如果消息来自于子线程
				      				if (msg.what == 0x123)
				      				{// 读取的内容
				      					
				     					if(msg.obj.toString().equals("mei"))
				      					{
				      						
				     						//弹出欢迎信息
				     						
				      						
				      					}
				     					else if(msg.obj.toString().equals("yuebuzu"))
				     					{
				     						//弹出余额不足信息
				     						
				     					}
				     					else
				     					{
				     						//出场，弹出费用消息
				     					}
				      					
				      				}							
				      			}
				      		};    
				            clientThread = new ClientThread(handler);
				           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据  
							 try 
							 {
								    Message msg = new Message();
									msg.what = 0x345;
									msg.obj = "cha"+name_nu;
									clientThread.revHandler.sendMessage(msg);
							    } 
					    	   catch (Exception e)
					    	   {
								// TODO Auto-generated catch block
								e.printStackTrace();
							   }	
							 
							 
							 /*Intent intent=new Intent(WifiMain.this,MainActivity.class);
				     		 startActivity(intent);
				     		 
				     		 WifiMain.this.finish();
							 dialog.dismiss();*/ 
					  	} 		   		 
				       }); 						      	 
				      	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
		                    @Override  
		                    public void onClick(DialogInterface dialog, int which) {  
		                        // TODO Auto-generated method stub  
		                    	 Intent intent=new Intent(WifiMain.this,MainActivity.class);
					     		 startActivity(intent);
					     		 WifiMain.this.finish();
					     		 
		                    	 dialog.dismiss(); 
		                    }  
		                }); 
				        builder.create().show();
								
							
					}
					else 
					{
						Toast toast=Toast.makeText(getApplicationContext(), "服务器繁忙，请稍后", Toast.LENGTH_SHORT);			
						toast.show();											
					}
				}
											
			}
		}; 
        clientThread = new ClientThread(handler);
      	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据  
	 }
	 	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_main);
		
		
	
		sp = getSharedPreferences("userInfo", 0);//获取Preferences
	    name_nu = sp.getString("USER_NAME",null);//取出数据

		btnConnect = (Button) findViewById(R.id.btnConnect);
		textView1 = (TextView) findViewById(R.id.txtMessage);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wac = new WifiAutoConnectManager(wifiManager);
		
		
		
		editPwd=(EditText) findViewById(R.id.editPwd);
		editSSID=(EditText) findViewById(R.id.editSSID);
		editSSID.setText("@Lovegod/\"");
		editPwd.setText("1234567890");	
		this.handlerMsg();
		 
	     
		 /*wac.mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) 
			{
				// 操作界面								
				textView1.setText(textView1.getText()+"\n"+msg.obj+"");
				super.handleMessage(msg);
			}
		};*/
		
		
		btnConnect.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				try
				{
					wac.connect(editSSID.getText().toString(), editPwd.getText().toString(),
							editPwd.getText().toString().equals("")?WifiCipherType.WIFICIPHER_NOPASS:WifiCipherType.WIFICIPHER_WPA);
				} 
				catch(Exception e)
				{
					textView1.setText(e.getMessage());
				}
				//获取进场时间
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy:MM:dd HH:mm:ss ");
				Date curDate = new Date(System.currentTimeMillis());//获取当前时间
				String str = formatter.format(curDate);
				
				System.out.println(str);
				//textView1.setText(str);
				
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();		
				textView1.setText(wifiInfo.getRssi()+"");
				if(wifiInfo.getRssi()>-50)
				{																
				 try 
				 {
					    Message msg = new Message();
						msg.what = 0x345;
						msg.obj = "kong";
						clientThread.revHandler.sendMessage(msg);
				    } 
		    	   catch (Exception e)
		    	   {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }	
			   }
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

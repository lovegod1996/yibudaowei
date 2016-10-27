package communication;

import login.LoginActivity;
import admin.Account_information;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class HandlerUnit extends Activity {

	Handler  handler;
	ClientThread clientThread;// 定义与服务器通信的子线程
	 public   Handler handlerMsg(){
	        handler=new Handler()
	             {
	       
	             	@Override
	      			public void handleMessage(Message msg)
	      			{
	      				// 如果消息来自于子线程
	      				if (msg.what == 0x123)
	      				{// 读取服务器返回的内容					
	      					//字符串解析 	        	
	      					if(msg.obj.equals("true"))
	      					{
	      						Toast toast=Toast.makeText(getApplicationContext(), "欢迎登录", Toast.LENGTH_SHORT);			
	      						toast.show();			
	      					}
	      					else if(msg.obj.toString().equals("false"))
	      					{
	      						Toast toast=Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT);			
	      						toast.show();											
	      					}	      	 				
	      				}							
	      			}
	      		};  
	      		return handler;
	            /*clientThread = new ClientThread(handler);
	           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
*/	       } 
}

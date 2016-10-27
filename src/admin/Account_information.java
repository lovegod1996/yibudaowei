package admin;

import login.LoginActivity;

import com.example.model.R;
import com.example.model.R.string;

import communication.ClientThread;
import title_bar_Activity.TitlebarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Account_information extends TitlebarActivity implements OnClickListener{
	TextView t_title;//标题栏
	Button imageButton;
	Button login_register;//登录注册按钮	
	TextView login_number;//登录账号
	TextView ticket_number;
	TextView money_number;
	View user_money;//用户钱包
	View user_record;//停车记录
	View user_parking_ticket;//停车券
	View user_find_illegal;//查违章
	View user_setting;//账户设置
	View about_us;//关于我们
	View user_feedback;//意见反馈
	
	 Button other;//头像按钮

	 Handler  handler;
	 ClientThread clientThread;
	 static SharedPreferences sp ;
	 static String name_nu;
	public void init()
	{
		  t_title=(TextView) findViewById(R.id.text_title);
	      login_register=(Button) findViewById(R.id.login_register);
	      imageButton=(Button)findViewById(R.id.to_information);
	      login_number=(TextView)findViewById(R.id.login_number);
	      
	      t_title.setText("我的");
	      other=(Button)findViewById(R.id.button_other);
	      other.setVisibility(View.GONE);
	      
	      money_number=(TextView) findViewById(R.id.money_number);
	      ticket_number=(TextView) findViewById(R.id.ticket_number);
	      //取出钱包数据
		/* Intent intent = getIntent();  
	     String moneyString = intent.getStringExtra("money_number");  
	     money_number.setText(moneyString);
	      */
	     
	      user_money=(View)findViewById(R.id.relativelayout_user_money);	     
	      user_record=(View)findViewById(R.id.relativelayout_user_record);
	      user_parking_ticket=(View)findViewById(R.id.relativelayout_user_parking_ticket);
	      user_find_illegal=(View)findViewById(R.id.relativelayout_user_find_illegal);
	      user_setting=(View)findViewById(R.id.relativelayout_user_setting);
	      about_us=(View)findViewById(R.id.relativelayout_user_about_as_our);
	      user_feedback=(View)findViewById(R.id.relativelayout_user_find_back);
	    	   
	      //获取Preferences
	       sp = getSharedPreferences("userInfo", 0);
	      //取出数据
	       name_nu = sp.getString("USER_NAME",null);
	      if(name_nu!=null)
	      {
	    	  login_register.setVisibility(View.GONE);//注册登录按钮隐藏
	            login_number.setText(name_nu);//显示账户
	      }
	      else
	      {
	    	  imageButton.setVisibility(View.GONE);
	      }
	}
	
	
	/*private void addListener()
	{
	 imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 Intent intent=new Intent(Account_information.this, Account_manage.class);
					 startActivity(intent);
				}
			});
            login_register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Account_information.this, LoginActivity.class);
		 			 startActivity(intent);
				}
			});
            user_money.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Account_information.this, Wallet.class);
		 			 startActivity(intent);
				}
			});
		      
        user_parking_ticket.setOnClickListener(new View.OnClickListener() {				
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});
       
        user_record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});
        
        user_find_illegal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});
       
        user_setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});
        
        user_feedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});
       
        about_us.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	Intent intent=new Intent(Main_user_information.this, User_Money.class);
    		//	startActivity(intent);
			}
		});        
	}*/
	
	
	
	public void handlerMsg(){
        handler=new Handler()
             {
             	@Override
      			public void handleMessage(Message msg)
      			{
      				// 如果消息来自于子线程
      				if (msg.what == 0x123)
      				{// 读取的内容
      					
     					if(msg.obj.toString()!=null&&msg.obj.toString().contains("name"))
      					{
      						
      						 Intent intent=new Intent(Account_information.this, Account_manage.class);
      						 String[] NA=msg.obj.toString().split(";");
      						 String name=NA[1];
      						 intent.putExtra("name_per",name);//传姓名到跳转页面
      						 startActivity(intent);	
      						
      					}
      					
      				}							
      			}
      		};    
            clientThread = new ClientThread(handler);
           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
       } 
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.account_infomation);
            init();
            this.handlerMsg();
            //addListener();
           imageButton.setOnClickListener(new OnClickListener() {				          	   
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub							
					try {
					    Message msg = new Message();
						msg.what = 0x345;
						msg.obj = "show;"+name_nu;
						clientThread.revHandler.sendMessage(msg);
				    } 
		    	   catch (Exception e)
		    	   {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }		
					 
				}
			});
            login_register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 Intent intent=new Intent(Account_information.this, LoginActivity.class);
		 			 startActivity(intent);
				}
			});
            user_money.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 Intent intent=new Intent(Account_information.this, Wallet.class);
		 			 startActivity(intent);
				}
			});
            user_find_illegal.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				sendToTwitter();    				
    			}

				private void sendToTwitter() {
					// TODO Auto-generated method stub
				   	    String url = "http://www.weizhang8.cn/";
			    	    Intent i = new Intent(Intent.ACTION_VIEW);
			    	    i.setData(Uri.parse(url)); 
			    	    startActivity(i);
				}
    		});           
            user_feedback.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				Intent intent=new Intent(Account_information.this, feed_back.class);
        			startActivity(intent);
    			}
    		});
	 }
	
}

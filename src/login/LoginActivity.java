   package login;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import pre_register.pre_registerActivity;
import title_bar_Activity.TitlebarActivity;
import admin.Account_information;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends  TitlebarActivity   {
	Handler  handler;
	EditText Account; //用户账号
	EditText Password;//用户密码
	Button   Login;   //登录按钮
	Button   Register;//注册按钮
	//TextView Show;    //测试文本框，显示注册的账号
	
	TextView t_title;//标题栏文字
	Button imageButton;//账户管理按钮

			
	ClientThread clientThread;// 定义与服务器通信的子线程
	
	SharedPreferences sp;//登录状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login);          
       initView();  //初始化界面控件           
       t_title.setText("登录界面");
       imageButton.setVisibility(View.GONE);
       this.handlerMsg();

       addListener(); //添加监听响应事件
    } 
      
      public void handlerMsg(){
        handler=new Handler()
             {	
             	@Override
      			public void handleMessage(Message msg)
      			{
      				// 如果消息来自于子线程
      				if (msg.what == 0x123)
      				{// 读取的内容					    				
      					if(msg.obj.equals("true"))
      					{
      						Toast toast=Toast.makeText(getApplicationContext(), "欢迎登录", Toast.LENGTH_SHORT);			
      						toast.show();
      						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
      						startActivity(intent);	
      						finish();
      					}
      					else if(msg.obj.toString().equals("false"))
      					{
      						Toast toast=Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT);			
      						toast.show();											
      					}
      	 				
      				}							
      			}
      		};    
            clientThread = new ClientThread(handler);
           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
       } 

	private void initView() {
		// TODO Auto-generated method stub
    
	    sp = this.getSharedPreferences("userInfo", 0); //获得实例对象 
		
		Account=(EditText) findViewById(R.id.account);
		Password=(EditText) findViewById(R.id.password);
		Login=(Button) findViewById(R.id.login);
		Register=(Button) findViewById(R.id.register);
	 //   Show=(TextView) findViewById(R.id.show);     
	    t_title=(TextView) findViewById(R.id.text_title);	    
	    imageButton = (Button) findViewById(R.id.button_other); 
	    if(sp.getBoolean("ISCHECK", false))  //保存密码
        {  
	    Account.setText(sp.getString("USER_NAME", ""));  //
        Password.setText(sp.getString("PASSWORD", ""));// 
        }
        if(sp.getBoolean("AUTO_ISCHECK", false))  //自动登录
        {  
           
              //跳转界面  
              Intent intent = new Intent(LoginActivity.this,MainActivity.class);  
              startActivity(intent);	
              finish();
        } 
	}
	
	 
	private void addListener() {
		// TODO Auto-generated method stub
		          
		 Login.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View v) {
					
				     String name = Account.getText().toString();//获取用户名
				     String password =Password.getText().toString();//获取用户密码
				     
				     Editor editor = sp.edit();  
                     editor.putString("USER_NAME", name);//用户名 用 SharedPreferences（sp）保存
                     editor.putString("PASSWORD",password);//密码 用 SharedPreferences（sp）保存
                     editor.commit();   
                     sp.edit().putBoolean("ISCHECK", true).commit();  //保存密码
                     sp.edit().putBoolean("AUTO_ISCHECK", true).commit(); //自动登陆
				    
				     if (name.equals("") || password.equals("")) 
				      {
				    	 Toast toast1=Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT);			
	 				     toast1.show();	
				      }//if				      
				
				     else
				     {//向服务器发送登录请求request,将用户名和密码封装成（某种类型）,发送给服务器，等待服务器处理并返回相应数据
				    	 try {
							    Message msg = new Message();
								msg.what = 0x345;
								msg.obj = "login;"+Account.getText().toString()+";"+Password.getText().toString();
								clientThread.revHandler.sendMessage(msg);
						    } 
				    	   catch (Exception e)
				    	   {
							// TODO Auto-generated catch block
							e.printStackTrace();
						   }				    					    	
				     }
				   }//onClick
				});//LOGIN_OnClickListener
		 
			Register.setOnClickListener(new OnClickListener()
			{		
				@Override
				public void onClick(View v) {
					
					Intent intent=new Intent(LoginActivity.this, pre_registerActivity.class);
					startActivity(intent);
				}
			});	//Register_OnClickListener		
		   }  
	
}

 



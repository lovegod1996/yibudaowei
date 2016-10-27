package admin;

import login.LoginActivity;
import modify.Pwd_modify;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import title_bar_Activity.TitlebarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Account_manage extends TitlebarActivity implements OnClickListener
{
	private String[] provinces = new String[]{ "男", "女" };
	private ButtonOnClick buttonOnClick = new ButtonOnClick(1);
	Button setting_user_sex;
	TextView t_title;
	Button other;
	Button back;
	
	TextView user_number;
	static TextView setting_user_name;
	View Layoutname;
	View change_pwd;
	View sex;
	Button logout;//退出登录按钮
	
	
	private void showSingleChoiceDialog()//选择男女对话框
	{
		new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(
				provinces, 1, buttonOnClick).setPositiveButton("确定",
				buttonOnClick).setNegativeButton("取消", buttonOnClick).show();
		
	}
	private class ButtonOnClick implements DialogInterface.OnClickListener
	{
		private int index;
		public ButtonOnClick(int index)
		{
			this.index = index;
		}
		@Override
		public void onClick(DialogInterface dialog, int whichButton)
		{
			if (whichButton >= 0)
			{
				index = whichButton;					
			}
			else
			{
				if (whichButton == DialogInterface.BUTTON_POSITIVE)
				{
					setting_user_sex=(Button)findViewById(R.id.setting_user_sex);
					setting_user_sex.setText(provinces[index]);
					setting_user_sex.setTextColor(0xFF000000);
					
				}			
			}

		}

	}
	

	public void init()
	{
		t_title=(TextView) findViewById(R.id.text_title);
		other=(Button)findViewById(R.id.button_other);
		back=(Button)findViewById(R.id.button_back);
		
		Layoutname=(View)findViewById(R.id.relativelayout_name);
		user_number=(TextView)findViewById(R.id.user_number);
		setting_user_name=(TextView)findViewById(R.id.setting_user_name);
		
		  SharedPreferences sp = getSharedPreferences("userInfo", 0);
	      //取出手机号数据
	      String name_number = sp.getString("USER_NAME",null); 
	      user_number.setText(name_number);
	      //取出姓名数据
		 Intent intent = getIntent();  
	     String nameString = intent.getStringExtra("name_per");  
	    
	     setting_user_name.setText(nameString);
	
		sex=(View)findViewById(R.id.relativelayout_sex);
		change_pwd=(View)findViewById(R.id.relativelayout_change_password);
		logout = (Button) findViewById(R.id.out_login);
		t_title.setText("账号管理");
		other.setVisibility(View.GONE);	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_manage);			
		init();
		/*back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});*/
		
        Layoutname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});		
		sex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showSingleChoiceDialog();
			}
		});				
		change_pwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Account_manage.this,Pwd_modify.class);
				startActivity(intent);
			}
		});
				
		logout.setOnClickListener(new OnClickListener() {//退出登录
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  //获取Preferences
			     SharedPreferences sp = getSharedPreferences("userInfo", 0);  
			      Editor editor = sp.edit();  
                  editor.putString("USER_NAME", null); //账号赋值为空 
                  editor.putString("PASSWORD",null);  
                  editor.commit();   
                 sp.edit().putBoolean("ISCHECK", false).commit();  
                 sp.edit().putBoolean("AUTO_ISCHECK", false).commit(); 
                 
				Intent intent=new Intent(Account_manage.this,LoginActivity.class);
				startActivity(intent);
			}
		});		
back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}	
}



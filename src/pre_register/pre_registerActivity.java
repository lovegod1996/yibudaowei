package pre_register;

import title_bar_Activity.TitlebarActivity;


import com.example.model.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class pre_registerActivity extends  TitlebarActivity implements OnClickListener{
	  ImageView iv_showCode;//图片显示生成的验证码
	  EditText  et_phoneNum;//手机号编辑框
	  EditText  et_phoneCode;//验证码编辑框
	  String realCode;	//产生的验证码		
	  Button button_next;//下一步的按钮，进入下一个界面	  
	  TextView t_title;//标题栏
	  Button imageButton;//ImageButton
	  Button back_back;
	  
	  
	  public void init()
	  {
		  et_phoneNum=(EditText) findViewById(R.id.phoneNumber);
		    et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes);//验证码编辑框
			iv_showCode = (ImageView) findViewById(R.id.iv_showCode);//将验证码用图片的形式显示出来
	        button_next=(Button)findViewById(R.id.button_next);//“下一步”按钮        
	        t_title = (TextView) findViewById(R.id.text_title); 
	        imageButton = (Button) findViewById(R.id.button_other); 
	        back_back=(Button)findViewById(R.id.button_back);
	        t_title.setText("注册界面");
	        imageButton.setVisibility(View.GONE);	
	        
	  }
	  @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			iv_showCode.setImageBitmap(GenerateCodeActivity.getInstance().createBitmap());
		} 	
	  public void addListener()
	  {
		  button_next.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub				
					 realCode = GenerateCodeActivity.getInstance().getCode();
							String phoneCode = et_phoneCode.getText().toString();
						
					
							if(phoneCode.equals(realCode))
							{						
								Intent intent=new Intent(pre_registerActivity.this, RegisterActivity.class);
								intent.putExtra("phone",et_phoneNum.getText().toString());//传到跳转页面
								startActivity(intent);
								
							}
							else
							{
								Toast.makeText(pre_registerActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
								
							}						
									
				}//onClick						
			});
		  
		  back_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	  }	 
	  
	  public void IdentifyCode()
	  {//将验证码用图片的形式显示出来
		    iv_showCode.setImageBitmap(GenerateCodeActivity.getInstance().createBitmap());
			iv_showCode.setOnClickListener(this);
			realCode = GenerateCodeActivity.getInstance().getCode();	
	  }
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_register);
        init();
        this.IdentifyCode();
        this.addListener();		
	}			
 }       

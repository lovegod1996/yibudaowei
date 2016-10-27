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
	  ImageView iv_showCode;//ͼƬ��ʾ���ɵ���֤��
	  EditText  et_phoneNum;//�ֻ��ű༭��
	  EditText  et_phoneCode;//��֤��༭��
	  String realCode;	//��������֤��		
	  Button button_next;//��һ���İ�ť��������һ������	  
	  TextView t_title;//������
	  Button imageButton;//ImageButton
	  Button back_back;
	  
	  
	  public void init()
	  {
		  et_phoneNum=(EditText) findViewById(R.id.phoneNumber);
		    et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes);//��֤��༭��
			iv_showCode = (ImageView) findViewById(R.id.iv_showCode);//����֤����ͼƬ����ʽ��ʾ����
	        button_next=(Button)findViewById(R.id.button_next);//����һ������ť        
	        t_title = (TextView) findViewById(R.id.text_title); 
	        imageButton = (Button) findViewById(R.id.button_other); 
	        back_back=(Button)findViewById(R.id.button_back);
	        t_title.setText("ע�����");
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
								intent.putExtra("phone",et_phoneNum.getText().toString());//������תҳ��
								startActivity(intent);
								
							}
							else
							{
								Toast.makeText(pre_registerActivity.this, "��֤�����", Toast.LENGTH_SHORT).show();
								
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
	  {//����֤����ͼƬ����ʽ��ʾ����
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
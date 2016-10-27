package pre_register;

import com.example.model.R;

import title_bar_Activity.TitlebarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bc_registerActivity extends TitlebarActivity implements OnClickListener {
	
	EditText bc_card_number;
	EditText bc_name;
	EditText bc_phone;
	Button down_next;
	
	TextView t_title;//标题栏
	Button other;
	 public void init()
	  {
		 bc_card_number=(EditText)findViewById(R.id.bc_card_number);
		 bc_name=(EditText)findViewById(R.id.bc_name);	
		 bc_phone=(EditText)findViewById(R.id.bc_phone);
		 down_next=(Button)findViewById(R.id.down_next);
		 t_title=(TextView) findViewById(R.id.text_title);
		 
		 t_title.setText("绑定银行卡");
	     other.setVisibility(View.GONE);
	  }
	 
	 private void addListener() {
			// TODO Auto-generated method stub
		 down_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Bc_registerActivity.this, RegisterActivity.class);		
				startActivity(intent);
			}
		});	
		}			
	 
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bc);
	        init();
	        this.addListener();	
		}
		
}

package modify;

import com.example.model.MainActivity;
import com.example.model.R;

import title_bar_Activity.TitlebarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Pwd_modify extends  TitlebarActivity {
	TextView t_title;
	Button other;
	Button   change_ok;
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pwd_modify);
	   
	        
	         change_ok=(Button)findViewById(R.id.change_ok);  
	         t_title=(TextView) findViewById(R.id.text_title);
	         other=(Button)findViewById(R.id.button_other);
	         t_title.setText("ÐÞ¸ÄÃÜÂë");
	         other.setVisibility(View.GONE);
	         
	         change_ok.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Pwd_modify.this, MainActivity.class);
					startActivity(intent);	
				}
			});
}
}
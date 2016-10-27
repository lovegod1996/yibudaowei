package admin;

import com.example.model.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import title_bar_Activity.TitlebarActivity;

public class feed_back extends TitlebarActivity{
	TextView t_title;//标题栏
	Button other;
	
	EditText help_feedback;
	Button submit;
	
	 public void init()
	  {
		 help_feedback=(EditText)findViewById(R.id.help_feedback);
		 submit=(Button)findViewById(R.id.submit);		 
		 other=(Button)findViewById(R.id.button_other);
		 t_title=(TextView) findViewById(R.id.text_title);		 
	     other.setVisibility(View.GONE);         
       }
	 
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.find_back);
	        init();
	        t_title.setText("意见反馈");
		}	  
	
}
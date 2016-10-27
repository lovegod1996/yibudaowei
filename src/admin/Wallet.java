package admin;

import com.example.model.R;

import title_bar_Activity.TitlebarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Wallet extends TitlebarActivity {
	TextView t_title;
	Button other;
	TextView balance;
	TextView ticket_w4; 
	View bill;
	View quan;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.wallet);
	        t_title=(TextView) findViewById(R.id.text_title);
	        other=(Button)findViewById(R.id.button_other);
	        
	        balance=(TextView) findViewById(R.id.balance);
	        bill=(View)findViewById(R.id.relativelayout_w3);
	        quan=(View)findViewById(R.id.relativelayout_w4);
	        ticket_w4=(TextView) findViewById(R.id.ticket_w4);
	        
	        t_title.setText("Ç®°ü");	 
	        other.setVisibility(View.GONE);
	        
	        bill.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        quan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
}
}
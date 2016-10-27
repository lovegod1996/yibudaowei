package userFunction;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.R;

import title_bar_Activity.TitlebarActivity;

public class ParkInformation_self extends TitlebarActivity {
	TextView t_title;//±ÍÃ‚¿∏
    Button other;
    Button back;
    TextView p_name;
    TextView p_lot;
    TextView p_address;
    TextView p_phone;
    TextView p_fee;

	 protected void onCreate(Bundle savedInstanceState){
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.park_info_one);
	        init(); 
	        
 }


	private void init() {
		// TODO Auto-generated method stub
		
		p_name=(TextView)findViewById(R.id.p_name);
		p_lot=(TextView)findViewById(R.id.p_lot);
		p_address=(TextView)findViewById(R.id.p_address);
		p_phone=(TextView)findViewById(R.id.p_phone);
		p_fee=(TextView)findViewById(R.id.p_fee);
		
		   t_title=(TextView)findViewById(R.id.text_title);
		   other=(Button)findViewById(R.id.button_other);
		   other.setVisibility(View.GONE);
		   back=(Button)findViewById(R.id.button_back);
		   
		   
		   
		  
		   Intent intent = getIntent();  
		   String nameString = intent.getStringExtra("ParkInfo");  //----------------------
		   ArrayList<String> Pinfo=new ArrayList<String>();
		   Pinfo=SplitString(nameString);
		   try{
		   t_title.setText(Pinfo.get(0));//--------------------------------------
		   p_name.setText(Pinfo.get(0));//-----------------------------
		   p_address.setText(Pinfo.get(1));
		   p_fee.setText(Pinfo.get(2));
		   p_phone.setText(Pinfo.get(3));
		   p_lot.setText(Pinfo.get(4));
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   
		   back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	 public static ArrayList<String> SplitString(String str){
		String[] ss= str.split("\\+");
		ArrayList<String>  list=new ArrayList<String>();
		 for (String string : ss) {
			list.add(string);
		}
		 return list;
	 }
	
}
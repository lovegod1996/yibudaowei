package userFunction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.model.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class PayMoney_self extends Activity{
	
	//ListView pay_records_list;
	RelativeLayout simple;
	TextView park_name;
	TextView data;
	TextView pay;
	TextView time;
	TextView time_start;
	TextView time_end;
	
	TextView t_title;
	Button other;
	Button back;
	ListView list;

     protected void onCreate(Bundle savedInstanceState){
    	  super.onCreate(savedInstanceState);
    	  setContentView(R.layout.payment_records);
    	  init();
    	  
    	  Intent intent = getIntent();  
		  String payString = intent.getStringExtra("payrecords").replace("payrecord;null;", ""); 
		  System.out.println(payString);
		  ArrayList<String> records=new ArrayList<String>();	
		  
		  records =SplitFenString(payString);
		  List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
		  
		  for (String string : records) {
			  String[] record=null;
			  Map<String, Object> listItem=new HashMap<String, Object>();
			  
			  record=string.split("\\+");
			
				 listItem.put("parknames", record[0]);
				  listItem.put("datas", record[1].replace(record[1].substring(record[1].indexOf(" ")), ""));
				  String longtime=getlongtime(record[1], record[2]);
				  listItem.put("pays", record[3]);
				 // String intime=getintime(record[1]);
				//  listItem.put("starttime", intime);
				
				 // String outtime=getintime(record[2]);
				//  listItem.put("endtime", outtime);
				  listItem.put("starttime", getintime(record[1]));
				  listItem.put("endtime", getintime(record[2]));
				  listItem.put("times", longtime);
				  System.out.println(listItem);
				  listItems.add(listItem);  
		}
		  SimpleAdapter simpleAdapter=new SimpleAdapter(this, listItems, R.layout.simple_item, 
				  new String[] {"parknames","datas","pays","starttime","endtime","times"}, 
                  new int[] {R.id.park_name,R.id.data,R.id.pay,R.id.time_start,R.id.time_end,R.id.time});
		  list.setAdapter(simpleAdapter);
	      
		  
		  back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
}

	private void init() {
		// TODO Auto-generated method stub
	      t_title=(TextView) findViewById(R.id.text_title);
	      other=(Button)findViewById(R.id.button_other);
	      back=(Button)findViewById(R.id.button_back);
	      t_title.setText("缴费记录");	 
	      other.setVisibility(View.GONE);
	      
	    //  pay_records_list=(ListView)findViewById(R.id.pay_records_list);
	      
	      simple=(RelativeLayout)findViewById(R.id.simple);
	      park_name=(TextView)findViewById(R.id.park_name);
	      data=(TextView)findViewById(R.id.data);
	      pay=(TextView)findViewById(R.id.pay);
	      time=(TextView)findViewById(R.id.time);
	      time_start=(TextView)findViewById(R.id.time_start);
	      time_end=(TextView)findViewById(R.id.time_end);
	      
		  list=(ListView)findViewById(R.id.pay_records_list);
		  list.setDivider(null); 
	   
	}
	
	 public static ArrayList<String> SplitString(String str){
			String[] ss= str.split("\\+");
			ArrayList<String>  list=new ArrayList<String>();
			 for (String string : ss)
			 {
				list.add(string);
			}
			 return list;
		 }
	 
	 public static ArrayList<String> SplitFenString(String StrConFen){
		 String[] str=StrConFen.split(";");
		 ArrayList<String> arraylist=new ArrayList<String>();
		 for (String string : str) {
			arraylist.add(string);
		}
		 return arraylist;
		 
	 }
	  /*
	   * 计算停车时间
	   * 
	   */
	 public static String getlongtime(String intime,String outtime){
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String longtime="";
		 try {
				java.util.Date ot=df.parse(outtime);
				java.util.Date it=df.parse(intime);
				long l=ot.getTime()-it.getTime();
				
				  long day=l/(24*60*60*1000);
				   long hour=(l/(60*60*1000)-day*24);
				   long min=((l/(60*1000))-day*24*60-hour*60);
				   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
				  
				   longtime=day+"天"+hour+"小时"+min+"分";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return longtime;
	 }
	 
	 public static String getintime(String in_time)  {
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		Date d=new Date();
		String str = null;
		try {
			str = df2.format(df.parse(in_time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return str;
		 
	
	 }
}

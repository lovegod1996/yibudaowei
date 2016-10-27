package userFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.model.MainActivity;
import com.example.model.R;

import communication.ClientThread;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import title_bar_Activity.TitlebarActivity;

public class ParkInformation extends TitlebarActivity {
	TextView t_title;//标题栏
    Button other;
    Button back;
    
    Button search;
    EditText ed_search;
    ImageView is_search;
    
    String editname;
    ListView ListView_park_name;
    ArrayList<String> arr1 = new ArrayList<String>();
    ArrayAdapter<String> adapter1;
    
   static String park;
    Handler  handler;
    ClientThread clientThread;
    public void handlerMsg(){
        handler=new Handler()
             {
             	@Override
      			public void handleMessage(Message msg)
      			{
      				// 如果消息来自于子线程
      				if (msg.what == 0x123)
      				{// 读取的内容
      					
     					if(msg.obj.toString()!=null&&msg.obj.toString().contains("parkinfo"))
      					{	
     						String[] PI=msg.obj.toString().split(";");
     						String pi=PI[1];
      						 Intent intent=new Intent(ParkInformation.this, ParkInformation_self.class);     												
							 intent.putExtra("ParkInfo",pi);
      						 startActivity(intent);					
      					}     					
      				}							
      			}
      		};    
            clientThread = new ClientThread(handler);
           	new Thread(clientThread).start();  	// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据          
       } 
    
    
   
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.park_info);
    	
    	init(); 
    	
    	/*
    	 * ListView_park_name.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String a=arr1.get(arg2).toString();//得到点击的列表项的值
				
				if(a!=null)
				{
					Intent intent=new Intent(ParkInformation.this, ParkInformation_self.class);
					intent.putExtra("park_name",a);//传停车场姓名到跳转页面
			    		startActivity(intent);					
				}
			}
		});
    	 * 		
    	 */
    	  this.handlerMsg(); 
    	  ListView_park_name.setOnItemClickListener(new OnItemClickListener() {

  			@Override
  			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
  					long arg3) {
  				// TODO Auto-generated method stub
  				
  				String pname=arr1.get(arg2).toString();//得到点击的列表项的值
  		
  				try {
  				    Message msg = new Message();
  					msg.what = 0x345;
  					msg.obj = "parkinfo;"+pname;
  				
  					clientThread.revHandler.sendMessage(msg);
  			    } 
  	    	   catch (Exception e)
  	    	   {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			   }		
  				 
  			}
  		});
        addListener(); 
    }
 

	private void init() {
		// TODO Auto-generated method stub
		
		t_title=(TextView)findViewById(R.id.text_title);
	    other=(Button)findViewById(R.id.button_other);
		t_title.setText("停车场信息查询");
		other.setVisibility(View.GONE);
		back=(Button)findViewById(R.id.button_back);
		
		search=(Button)findViewById(R.id.search);
		ed_search=(EditText)findViewById(R.id.ed_search);
		ed_search.clearFocus();
		
		
		is_search=(ImageView)findViewById(R.id.is_search);
		
		ListView_park_name=(ListView)findViewById(R.id.listView_park_name);
	   Intent intent=getIntent();
	    park=intent.getStringExtra("ParkList");
	
	  ArrayList<String> Pinfo=new ArrayList<String>();
	   Pinfo=SplitString(park);
    for (String string : Pinfo) {
	arr1.add(string);
           }	
       adapter1 =new ArrayAdapter<String>(this,R.layout.array_item,arr1);
        ListView_park_name.setAdapter(adapter1);		
	}
	private void addListener() {
		// TODO Auto-generated method stub
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		is_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ed_search.setText("");
			}
		});
		
		ed_search.addTextChangedListener(new TextWatcher() {
						
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()==0){
					is_search.setVisibility(View.GONE);
				}else {
					is_search.setVisibility(View.VISIBLE);
					editname=ed_search.getText().toString();	
				}		
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		search.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
		
				adapter1.clear();
				adapter1.notifyDataSetChanged();
				  ArrayList<String> Pinfo2=new ArrayList<String>();
				Pinfo2=SplitString(park);
				 
						for (String string : Pinfo2) {
							if(string.contains(editname)){
							System.out.println(string);
							arr1.add(string);
							}
						}	  
				   ListView_park_name.setAdapter(adapter1);				
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

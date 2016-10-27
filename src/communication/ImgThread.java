package communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ImgThread implements Runnable {

	private Socket s;
	private Handler handler;
	public Handler imhandler;
	DataInputStream din=null;
	OutputStream out=null;
	ClientThread client;
	BufferedReader br=null;
	BufferedInputStream bis=null;
	public ImgThread(Handler handler) {
		// TODO Auto-generated constructor stub
	
		this.handler=handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			s = new Socket("192.168.191.1", 8140);
			 din=new DataInputStream(s.getInputStream());
			 out=s.getOutputStream();
			// InputStreamReader inp=new InputStreamReader(s.getInputStream());
			 bis=new BufferedInputStream(s.getInputStream());
			 
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				 System.err.println("返回数据多少  "+br+"  inp  "+bis.available()+"");
			  new Thread(){
              
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 try {
						while(din.available()>0){
							 // 创建data数组并将流中数据读取到数组中  
						  byte[] data = new byte[din.readInt()];// 注此处同样没有处理图片大小超过int的范围的情况  
						  din.read(data);
						  Bundle bd=new Bundle();
						  bd.putByteArray("imgpp", data);
						  System.err.println("执行到了");
						 	Message msg = new Message();
							msg.what = 0x246;
							msg.setData(bd);
							handler.sendMessage(msg);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}  
			  }.start();
				Looper.prepare();
				
				imhandler=new Handler(){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						if(msg.what==0x135){
							try {
								out.write((msg.obj.toString() + "\r\n").getBytes("gbk"));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				
				
				
			 Looper.loop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

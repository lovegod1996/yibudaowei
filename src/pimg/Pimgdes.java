package pimg;



import android.app.Activity;
import android.os.Bundle;




public class Pimgdes extends Activity {






protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
}
}
/*package pimg;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import location.Getlocation;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.model.R;
import com.view.RoundImageDrawable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pimgdes extends Activity {

	private static final String URL = "http://avatar.csdn.net/3/2/4/2_ameyume.jpg";  
	
	ImageView img;
	TextView textview1;
	private String cityname;
	   private TextView weatherDespText;    //用于显示天气描述信息  
	    private TextView temp1Text;          //用于显示最低气温  
	    private TextView temp2Text;          //用于显示最高气温  
	    private TextView currentDateText;    //用于显示当前日期  
	private ImageView imgday;
	private ImageView imgnight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pimg);
		img=(ImageView) findViewById(R.id.image);
		Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.pd); 
		img.setImageDrawable(new RoundImageDrawable(bitmap1));
		textview1=(TextView) findViewById(R.id.weather);
		
		
		    weatherDespText = (TextView) findViewById(R.id.weather_desp);  
	        temp1Text = (TextView) findViewById(R.id.temp1);  
	        temp2Text = (TextView) findViewById(R.id.temp2);  
	        currentDateText = (TextView) findViewById(R.id.current_date);  
	        imgday=(ImageView) findViewById(R.id.imageday);
	        imgnight=(ImageView) findViewById(R.id.imagenight);
	        
	        if(NetIsConnect(Pimgdes.this)){
                if(this.GpsIsOpen()){
                	Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT)  
                    .show();  
                	
		  String  addJson=this.GetLocation();
		
		JSONObject adjson;
		try {
			adjson = new JSONObject(addJson);
			JSONObject resultJson= adjson.getJSONObject("result"); 
			String address=resultJson.getString("formatted_address");
			System.out.println(address);
			JSONObject detail=resultJson.getJSONObject("addressComponent");
			String city=detail.getString("city");
			String district=detail.getString("district");
			String province=detail.getString("province");
			String street=detail.getString("street");
			String streetnum=detail.getString("street_number");
			cityname=district;
			textview1.setText(address);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ccode=null;
		try{
	 ccode=java.net.URLEncoder.encode(cityname);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ccode!=null){
		String weatherUrl="http://api.map.baidu.com/telematics/v3/weather?location="+ccode+"&output=json&ak=MPDgj92wUYvRmyaUdQs1XwCf";
		
		String weatherJson=queryStringForGet(weatherUrl);
		System.out.println(weatherJson);
		 
		JSONObject jsonweaObject;
		JSONObject jobweather = null;
		try {
			jsonweaObject = new JSONObject(weatherJson);
			JSONArray jarrywea=jsonweaObject.getJSONArray("results") ;  
			JSONObject jsondatawea=jarrywea.getJSONObject(0);    
			JSONArray jarrywee=jsondatawea.getJSONArray("weather_data");  
			 jobweather=jarrywee.getJSONObject(0);  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 * 显示天气信息在界面上
		 
		try {
			currentDateText.setText(jobweather.getString("date"));
			weatherDespText.setText(jobweather.getString("weather"));
      //获取图片链接显示
	        
	        try {  
                byte[] data = getImage(jobweather.getString("dayPictureUrl"));  
                if(data!=null){  
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                    imgday.setImageBitmap(bitmap);// display image  
                }else{  
                    Toast.makeText(Pimgdes.this, "Image error!", 1).show();  
                }  
            } catch (Exception e) {  
                Toast.makeText(Pimgdes.this,"Newwork error!", 1).show();  
                e.printStackTrace();  
            }  
 //获取图片链接显示
	        
	        try {  
                byte[] data = getImage(jobweather.getString("nightPictureUrl"));  
                if(data!=null){  
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap  
                    imgnight.setImageBitmap(bitmap);// display image  
                }else{  
                    Toast.makeText(Pimgdes.this, "Image error!", 1).show();  
                }  
            } catch (Exception e) {  
                Toast.makeText(Pimgdes.this,"Newwork error!", 1).show();  
                e.printStackTrace();  
            }  
	        temp1Text.setText(jobweather.getString("temperature"));
	        temp2Text.setText(jobweather.getString("wind"));     
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			Intent intent=getIntent();  
        if(intent !=null)  
        {  
            byte [] bis=intent.getByteArrayExtra("bitmap");  
            Bitmap bitmap=BitmapFactory.decodeByteArray(bis, 0, bis.length);  
            img.setImageBitmap(bitmap);  
        }  
                }
	        }else{
            	Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();  
            }
	        }else{
	        	Toast.makeText(this, "请开启数据！", Toast.LENGTH_SHORT).show();  
	        }
	}
	
	public static String readData(InputStream inSream, String charsetName) throws Exception{  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len = -1;  
		while( (len = inSream.read(buffer)) != -1 ){  
		outStream.write(buffer, 0, len);  
		}  
		byte[] data = outStream.toByteArray();  
		outStream.close();  
		inSream.close();  
		return new String(data, charsetName);  
		}
	
	 *//** 
     * 网络查询 
     *//*  
    public static String queryStringForGet(String url) {  
        HttpGet request = new HttpGet(url);  
        String result = null;  
        try {  
            HttpResponse response = new DefaultHttpClient().execute(request);  
            if (response.getStatusLine().getStatusCode() == 200) {  
                result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);  
                return result;  
            }  
        } catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            result = "网络异常！";  
            return result;  
        } catch (ParseException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            result = "网络异常！";  
            return result;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return result;  
    }  

    *//** 
     * Get image from newwork 
     * @param path The path of image 
     * @return 
     * @throws Exception 
     *//*  
    public static byte[] getImage(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        InputStream inStream = conn.getInputStream();  
        if(conn.getResponseCode()==200){  
            return readStream(inStream);  
        }  
        return null;  
    }  
  
    *//** 
     * Get data from stream 
     * @param inStream 
     * @return 
     * @throws Exception 
     *//*  
    public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1){  
            outStream.write(buffer, 0, len);  
        }  
        outStream.close();  
        inStream.close();  
        return outStream.toByteArray();  
    }  
    
    
     * 判断网络是否打开
     
    
    public boolean NetIsConnect(Activity activity){
    	Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
     *判断gps是否打开
     
    
    public  Boolean GpsIsOpen(){
		 LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);  
		 if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {  
			 return true;
		 }else{
			 return false;
		 }
    }
    private  String GetLocation(){
    	String cityName="";
    	 // 获取位置管理服务  
        LocationManager locationManager;  
        String serviceName = Context.LOCATION_SERVICE;  
        locationManager = (LocationManager) this.getSystemService(serviceName);  
        // 查找到服务信息  
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度  
        criteria.setAltitudeRequired(false);  
        criteria.setBearingRequired(false);  
        criteria.setCostAllowed(true);  
        criteria.setPowerRequirement(Criteria.POWER_HIGH); // 低功耗  
 
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息  
      
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置  
       if(location==null){
    	   location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
       
       System.out.println("网络位置"+location);
       
       }
       if(location!=null){
    	   String latitude=location.getLatitude()+"";
           String longitude=location.getLongitude()+"";
           Getlocation gl=new Getlocation();
          cityName=gl.GetAddr(latitude, longitude);
       }
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米  
        locationManager.requestLocationUpdates(provider, 100 * 1000, 500,  
                locationListener); 
       return cityName;
    }
    *//**  
     * 方位改变时触发，进行调用  
     *//*    
    private final static LocationListener locationListener = new LocationListener() {    
      
        public void onLocationChanged(Location location) {    
        }    
    
        public void onProviderDisabled(String provider) {    
        }    
    
        public void onProviderEnabled(String provider) {    
        }    
    
        public void onStatusChanged(String provider, int status, Bundle extras) {    
        }    
    };    
    
    
    
    
  
}
*/
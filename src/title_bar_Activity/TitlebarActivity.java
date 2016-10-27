package title_bar_Activity;

import com.example.model.MainActivity;
import com.example.model.R;

import admin.Account_information;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitlebarActivity extends Activity implements OnClickListener {
	  RelativeLayout mContentLayout;
	  Button back;
	  Button other;
	  TextView t_title;
	  
	  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {  
        case R.id.button_back:  
            onBackward(v);  
            break;  

        case R.id.button_other:  
            onForward(v);  
            break;  

        default:  
            break;  
    }  
	}
	
	private void onForward(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(TitlebarActivity.this,Account_information.class);
		startActivity(intent);	
	}

	private void onBackward(View v) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setupViews();   //加载 title_bar 布局 ，并获取标题及两侧按钮  
    }

	private void setupViews() {
		// TODO Auto-generated method stub
		super.setContentView(R.layout.title_bar);  
		t_title = (TextView) findViewById(R.id.text_title);         
        back = (Button) findViewById(R.id.button_back);  
        other = (Button) findViewById(R.id.button_other); 
        mContentLayout = (RelativeLayout) findViewById(R.id.relativelayout_main); 
	}  
	
  
    //设置标题内容  
    @Override  
    public void setTitle(int titleId) {  
    	t_title.setText(titleId);  
    }  
  
    //设置标题内容  
    @Override  
    public void setTitle(CharSequence title) {  
    	t_title.setText(title);  
    }  
  
    //设置标题文字颜色  
    @Override  
    public void setTitleColor(int textColor) {  
    	t_title.setTextColor(textColor);  
    }  
    
    @Override  
    public void setContentView(View view) {  
        mContentLayout.removeAllViews();  
        mContentLayout.addView(view);  
        onContentChanged();  
    }  
     
}


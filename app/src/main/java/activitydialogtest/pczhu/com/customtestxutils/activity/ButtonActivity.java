package activitydialogtest.pczhu.com.customtestxutils.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import activitydialogtest.pczhu.com.customtestxutils.R;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:27
 * 版本：V1.0
 * 修改历史：
 */
public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);
        Button btn = (Button) findViewById(R.id.button);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        System.out.println("ButtonAcccccccccccccccc");
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                start(this,MainActivity.class);
                break;
            case R.id.button2:
                start(this,SecondActivity.class);
                break;
            case R.id.button3:
                start(this,ThirdActivity.class);
                break;
        }
    }
    public static void start(Context context,Class clz) {
        Intent starter = new Intent(context, clz);
        context.startActivity(starter);
    }
}

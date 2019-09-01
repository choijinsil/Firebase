package com.korea.myapp02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv_my_app;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_my_app=findViewById(R.id.tv_my_app);

        //다른앱에서 넘어오는 데이터를 받는 부분
        intent=getIntent();
        if(intent!=null){
            //1.action을 검사
            String action=intent.getAction();
            String type=intent.getType();
            if(Intent.ACTION_MAIN.equals(action)&&type!=null){
                //2.type이 일치할 때만
                if("text/plain".equals(type)){
                    String msg=intent.getExtras().getString("korea");
                    tv_my_app.setText(msg);

                    //수신받은 msg의 값이 hidden인 경우에만 tv_my_app에 클릭이벤트 부여
                    if("hidden".equals(msg)){
                        tv_my_app.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"적립금 30마넌", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        }
    }
}

package com.korea.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {
    private Intent intent;
    private Button btn_hidden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btn_hidden= findViewById(R.id.btn_hidden);


        intent= getIntent();
        String msg="";
        if(intent!=null){
            msg=intent.getExtras().getString("message");
            //히든모드 만들기
            if("hidden".equals(msg)){
                btn_hidden.setVisibility(View.VISIBLE);
                btn_hidden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(NotificationActivity.this,"적립금 30마넌", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
}

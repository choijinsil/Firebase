package com.korea.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "service_tag::";
    private Button btn_ano_app;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_ano_app=findViewById(R.id.btn_ano_app);

        //파이어베이스로부터 Token수신 받기 - 인증정보
        //기존에는 클래스로 구현을 했지만 이제 객체로 사용
        //인스턴스와 객체의 차이? 같은말. 특정한 클래스를 얘기할때 객체화 시키는 것을 인스터화 한다.
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                //인증정보의 확인
                if(!task.isSuccessful()){
                    Log.d(TAG, "인스턴스 ID 인증 실패", task.getException());
                    return;
                }
                //token 꺼내기
                String token= task.getResult().getToken();
                Toast.makeText(MainActivity.this,token+"",Toast.LENGTH_SHORT).show();
            }
        });

        //다른앱 이동하기
        intent=this.getPackageManager().getLaunchIntentForPackage("com.korea.myapp02"); //실행시킬 패키지 명을 작성하세요.
        intent.setAction(Intent.ACTION_MAIN);
        intent.setType("text/plain");
        btn_ano_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("korea","hidden");
                startActivity(intent);
            }
        });
    }
}

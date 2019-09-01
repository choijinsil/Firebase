package com.korea.my_chat_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

// firebase사이트 -realtime database 생성
public class MainActivity extends AppCompatActivity {
    private ListView list_chat;
    private EditText et_msg;
    private Button btn_send;
    private ArrayAdapter<String> adapter;
    private String userNm;

    //-리얼타임 데이터 베이스
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //데이터 베이스 자체
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    // 데이터베이스를 쉽게 사용하게 하기 위해서 제공되는 메서드들 ex) dml문

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_chat = findViewById(R.id.list_chat);
        et_msg = findViewById(R.id.et_msg);
        btn_send = findViewById(R.id.btn_send);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        list_chat.setAdapter(adapter);

        //유저명 만들기 ex)user1234, user1, user8888
        userNm=String.format("user%d",new Random().nextInt(10000));

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 베이스에 저장
                String msg=String.valueOf(et_msg.getText());
                ChatBean chatBean=new ChatBean(userNm,msg);
                databaseReference.child("message").push().setValue(chatBean);
                et_msg.setText("");
            }
        });

        //데이터 베이스로부터 채팅 수신하기
        //방별로 관리하려면 message를 방번호로
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //추가가 끝나면 스크린샷(dataSnapshot)으로 저장
                ChatBean chatBean= dataSnapshot.getValue(ChatBean.class);
                adapter.add(chatBean.getUserNm()+" : "+chatBean.getMsg());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

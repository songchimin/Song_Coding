package com.study.android.mytry.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.study.android.mytry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.study.android.mytry.chat.chat_room.nickname;

public class private_chat extends AppCompatActivity {
    private static final String TAG = "lecture";

    SimpleDateFormat format1 = new SimpleDateFormat ( "hh:mm:ss");
    String format_time1;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String user_email;

    FirebaseFirestore db;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String CHAT_NAME;
    private String USER_NAME;
    private String CHAT_ROOM_NAME;

    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;

    Button chat_content_back_btn;
    TextView chat_content_title;
    TextView chat_content;

    TextView chat_content_time;
    ImageView chat_content_image;

    ArrayList list = null;

//    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_private);

        chat_view = findViewById(R.id.chat_view);
        chat_view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        chat_edit = findViewById(R.id.chat_edit);
        chat_send = findViewById(R.id.chat_sent);

        chat_content_back_btn = findViewById(R.id.chat_content_back_btn);
        chat_content_title = findViewById(R.id.chat_content_title);

        chat_content_time = findViewById(R.id.chat_content_time);
        chat_content_image = findViewById(R.id.chat_content_image);

        Intent intent = getIntent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //채팅방이름 = 트라잇 번호.
        CHAT_NAME = intent.getStringExtra("chat_room_num");
        CHAT_ROOM_NAME = intent.getStringExtra("chat_room_title");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        user_email = user.getEmail();

        list = new ArrayList();

        chat_content_title.setText(CHAT_ROOM_NAME);

        openChat(CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                format_time1 = format1.format (System.currentTimeMillis());
                ChatDTO chat = new ChatDTO(nickname, chat_edit.getText().toString(), format_time1); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬

                chat_edit.setText(""); //입력창 초기화

            }
        });


        chat_content_back_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot, chat_content_singerAdapter adapter2) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);

        list.add(chatDTO);

        adapter2.notifyDataSetChanged();
    }

    private void removeMessage(DataSnapshot dataSnapshot, chat_content_singerAdapter adapter2) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);

//        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅  
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.chat_item_view, R.id.chat_content);
//        final chat_content_singerAdapter adapter2 = new chat_content_singerAdapter(this);

        final chat_content_singerAdapter adapter2 = new chat_content_singerAdapter(this, R.layout.chat_item_view, R.layout.chat_ritem_view, list);

        chat_view.setAdapter(adapter2);
        chat_view.setSelection(adapter2.getCount() - 1);
        //        chat_view.setAdapter(adapter);
//        chat_view.setSelection(adapter.getCount() - 1);


        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter2);

                Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter2);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
//        long tempTime = System.currentTimeMillis();
//        long intervalTime = tempTime - backPressedTime;

//        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
//        {
//            super.onBackPressed();
//        }
//        else
//        {
//            backPressedTime = tempTime;
//            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 꺼버린다.", Toast.LENGTH_SHORT).show();
//        }
        finish();
        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
    }

}

package com.study.android.wifi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;
import static com.study.android.wifi.Fragment1.weather;

public class Login extends AppCompatActivity {


    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";

    //define view objects
    private EditText editTextUserEmail;
    private Button buttonFind;
    private TextView textviewMessage;
    private ProgressDialog progressDialog;
    //define firebase object

    final Handler handler = new Handler();

    FirebaseFirestore db;

    //firebase auth object
    FirebaseUser user;
    String user_email;

    String input_do = null;
    String input_se = null;
    String input_dong = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);

        db=FirebaseFirestore.getInstance();

    }

    public void singUp(View view) {
        Intent intent = new Intent(Login.this, JoinActivity.class);
        startActivity(intent);

    }

    public void signIn(View view) {

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            loginUser(email, password);
        }
    }

    public void missingPw(View view ){
        Intent intent = new Intent(Login.this,Password.class);
        startActivity(intent);}

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }

        // 로그인
    private void loginUser(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){

                                // 로그인 성공
                                Toast.makeText(Login.this,"로그인 성공", Toast.LENGTH_SHORT).show();

                                // 여기서 가져와야함

                                firebaseAuth = FirebaseAuth.getInstance();
                                user = firebaseAuth.getCurrentUser();
                                user_email= user.getEmail();

                                db=FirebaseFirestore.getInstance();


                                db.collection("MyWeather")
                                        .document(user_email.toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    System.out.println(user_email);
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    input_do = (String) documentSnapshot.get("choice_do");
                                                    input_se = (String) documentSnapshot.get("choice_se");
                                                    input_dong = (String) documentSnapshot.get("choice_dong");

                                                    weather.setChoice_do(input_do);
                                                    weather.setChoice_se(input_se);
                                                    weather.setChoice_dong(input_dong);

                                                    if(input_do != null){
                                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                                        intent.putExtra("userEmail",editTextEmail.getText().toString());
                                                        finish();
                                                        startActivity(intent);
                                                    }
                                                    else{
                                                        Intent intent = new Intent(Login.this, region.class);
                                                        intent.putExtra("userEmail",editTextEmail.getText().toString());
                                                        finish();
                                                        startActivity(intent);
                                                    }
                                                } else {
                                                    Log.d("yejin", "Cached document failed: " + task.getException());
                                                    System.out.println("안됨");
                                                }
                                            }
                                        });


//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if( input_do != null && input_se != null  && input_dong != null){
//                                            Intent intent = new Intent(Login.this, MainActivity.class);
//                                            intent.putExtra("userEmail",editTextEmail.getText().toString());
//                                            finish();
//                                            startActivity(intent);
//                                        }
//                                        else
//                                        {
//                                            Intent intent = new Intent(Login.this, region.class);
//                                            intent.putExtra("userEmail",editTextEmail.getText().toString());
//                                            finish();
//                                            startActivity(intent);
//                                        }
//                                    }
//                                }, 1500 );


                            }else {
                                Toast.makeText(Login.this, "이메일 인증을 진행해주세요.",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            // 로그인 실패
                            Toast.makeText(Login.this,"로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


  }

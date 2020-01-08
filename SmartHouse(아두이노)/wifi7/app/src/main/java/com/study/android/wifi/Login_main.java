package com.study.android.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import static com.study.android.wifi.Fragment1.weather;


public class Login_main extends AppCompatActivity {
    FirebaseUser user;
    String user_email;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String input_do = null;
    String input_se = null;
    String input_dong = null;

    Intent mainIntent = null;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
    }

    public void join(View view) {
        Intent intent = new Intent(Login_main.this, JoinActivity.class);
        startActivity(intent);
    }


    public void slogin(View view) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);


                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    mainIntent = new Intent(Login_main.this, Login.class);
                    Login_main.this.finish();
                    Login_main.this.startActivity(mainIntent);
                } else {
                    Toast.makeText(Login_main.this, "자동로그인 되셨습니다.", Toast.LENGTH_LONG).show();


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
                                            mainIntent = new Intent(Login_main.this, MainActivity.class);
                                            Login_main.this.finish();
                                            Login_main.this.startActivity(mainIntent);
                                        }else{
                                            mainIntent = new Intent(Login_main.this, region.class);
                                            Login_main.this.finish();
                                            Login_main.this.startActivity(mainIntent);
                                        }

                                    } else {
                                        Log.d("yejin", "Cached document failed: " + task.getException());
                                        System.out.println("안됨");
                                    }
                                }
                            });

                }


    }


}

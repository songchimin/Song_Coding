package com.study.android.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.wifi.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class
Fragment2 extends Fragment implements RecognitionListener {
    private TextView mConnectionStatus;
    private EditText mInputEditText;
    private ArrayAdapter<String> mConversationArrayAdapter;

    private static final String TAG = "TcpClient";
    private boolean isConnected = false;

    private String mServerIP = null;
    private Socket mSocket = null;
    private PrintWriter mOut;
    private BufferedReader mIn;
    private Thread mReceiverThread = null;


    TextView stateTemp;
    TextView stateWater;
    TextView fstateTemp;
    TextView fstateWater;


    String arr[];
    ImageButton window;
    ImageButton homefan;
    ImageButton fan;
    ImageButton homeled;
    ImageButton h;
    ImageButton lcd;
    ImageButton farmled;
    ImageButton spring;
    ImageButton farmfan;
    ImageButton mic;

    Activity activity;

    private static final int REQUEST_CODE=1000;
    String str = "";
    TextView textView1;

    private SensorValueListener SensorValueListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            activity = (Activity) context;

        if(context instanceof SensorValueListener){
            SensorValueListener = (SensorValueListener) context;
        }else{
//            throw new RuntimeException(context.toString()
//                    + " must unoenebt SensorValueListener");
        }
    }

    public void onDetach(){
        super.onDetach();
        SensorValueListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        //음성인식
        mic = rootView.findViewById(R.id.mic);
        textView1 = rootView.findViewById(R.id.textView1);

        window = rootView.findViewById(R.id.window);
        homefan = rootView.findViewById(R.id.homefan);
        fan = rootView.findViewById(R.id.fan);
        homeled = rootView.findViewById(R.id.homeled);
        h = rootView.findViewById(R.id.h);
        lcd = rootView.findViewById(R.id.lcd);
        farmled = rootView.findViewById(R.id.formled);
        spring = rootView.findViewById(R.id.spring);
        farmfan = rootView.findViewById(R.id.formfan);


        stateTemp=(TextView)rootView.findViewById(R.id.stateTemp);
        stateWater=(TextView)rootView.findViewById(R.id.statewater);
        fstateTemp=(TextView)rootView.findViewById(R.id.fstateTemp);
        fstateWater=(TextView)rootView.findViewById(R.id.fstateWater);


        mConnectionStatus = (TextView)rootView.findViewById(R.id.connection_status_textview);


        //음성인식
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName());
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성검색");

                    startActivityForResult(intent, REQUEST_CODE);
                    Toast.makeText(getActivity(), "음성인식 시도", Toast.LENGTH_SHORT).show();

                } catch (ArithmeticException e) {
                    Log.d(TAG, "ActivityNotFoundException");
                }
            }

        });

        //이후에 tag switch로 바꾸기
        window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("a")).start();
                    Log.d(TAG,"창문");
                }
            }
        });
        homefan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("b")).start();
                    Log.d(TAG,"집 환풍기");
                }
            }
        });
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("c")).start();
                    Log.d(TAG,"집 선풍기");
                }
            }
        });
        homeled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("d")).start();
                    Log.d(TAG,"집 형광등");
                }
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("j")).start();
                    Log.d(TAG,"집 가습기");
                }
            }
        });
        lcd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("f")).start();
                    Log.d(TAG,"집 TV");
                }
            }
        });
        farmled.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("g")).start();
                    Log.d(TAG,"농장 조명");
                }
            }
        });
        spring.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("h")).start();
                    Log.d(TAG,"농장 펌프");
                }
            }
        });
        farmfan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                else {
                    new Thread(new SenderThread("i")).start();
                    Log.d(TAG,"농장 환풍기");
                }
            }
        });


        Button sendButton = (Button)rootView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String sendMessage = mInputEditText.getText().toString();
                if ( sendMessage.length() > 0 ) {
                    if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
                    else {
                        new Thread(new SenderThread(sendMessage)).start();
                        mInputEditText.setText(" ");
                    }
                }
            }
        });

//        mConversationArrayAdapter = new ArrayAdapter<>( this,  android.R.layout.simple_list_item_1);

        new Thread(new ConnectThread("192.168.219.109", 80)).start();

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        isConnected = false;
    }


//    private static long back_pressed;
//    @Override
//    public void onBackPressed(){
//
//        if (back_pressed + 2000 > System.currentTimeMillis()){
//            super.onBackPressed();
//
//            Log.d(TAG, "onBackPressed:");
//            isConnected = false;
//
//            finish();
//        }
//        else{
//            Toast.makeText(getBaseContext(), "한번 더 뒤로가기를 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
//            back_pressed = System.currentTimeMillis();
//        }
//
//    }


    private class ConnectThread implements Runnable {

        private String serverIP;
        private int serverPort;

        ConnectThread(String ip, int port) {
            serverIP = ip;
            serverPort = port;

            mConnectionStatus.setText("connecting to " + serverIP + ".......");
        }

        @Override
        public void run() {

            try {

                mSocket = new Socket(serverIP, serverPort);
                //ReceiverThread: java.net.SocketTimeoutException: Read timed out 때문에 주석처리
                //mSocket.setSoTimeout(3000);

                mServerIP = mSocket.getRemoteSocketAddress().toString();

            } catch( UnknownHostException e )
            {
                Log.d(TAG,  "ConnectThread: can't find host");
            }
            catch( SocketTimeoutException e )
            {
                Log.d(TAG, "ConnectThread: timeout");
            }
            catch (Exception e) {

                Log.e(TAG, ("ConnectThread:" + e.getMessage()));
            }


            if (mSocket != null) {

                try {

                    mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8")), true);
                    mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"));

                    isConnected = true;
                } catch (IOException e) {

                    Log.e(TAG, ("ConnectThread:" + e.getMessage()));
                }
            }


            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (isConnected) {

                        Log.d(TAG, "connected to " + serverIP);
                        mConnectionStatus.setText("connected to " + serverIP);

                        mReceiverThread = new Thread(new ReceiverThread());
                        mReceiverThread.start();
                    }else{

                        Log.d(TAG, "failed to connect to server " + serverIP);
                        mConnectionStatus.setText("failed to connect to server "  + serverIP);
                    }

                }
            });
        }
    }


    private class SenderThread implements Runnable {
        String msg;
        SenderThread(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {

            mOut.println(msg);
            mOut.flush();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "send message: " + msg);
//                    mConversationArrayAdapter.insert("Me - " + msg, 0);
                }
            });
        }
    }

    public void sendMeg(String sendMessage){
        System.out.println("메세지 받음 : "+sendMessage);

        if ( sendMessage.length() > 0 ) {

            if (!isConnected) showErrorDialog("서버로 접속된후 다시 해보세요.");
            else {
                new Thread(new SenderThread(sendMessage.substring(0,1))).start();

            }
        }
    }

    private class ReceiverThread implements Runnable{

        ReceiverThread(){

        }

        @Override
        public void run() {

            try {

                while (isConnected) {

                    if ( mIn ==  null ) {

                        Log.d(TAG, "ReceiverThread: mIn is null");
                        break;
                    }

                    final String recvMessage =  mIn.readLine();

                    if (recvMessage != null) {
                        System.out.println("들어옴");
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // 제어 : 창문, 환풍기, 선풍기, 형광등, 가습기, LCD, /  농장LED, 농장펌프, 농장환풍기, 농장문
                                // 상태 : 집온도 집습도 빗물12 가스13 / 농장온도 농장습도 토양습도16
                                arr = recvMessage.split(" ");

//                                //숫자로 시작하면 상태값
                                stateTemp.setText(arr[10]+"ºC"); // 현재 온도
                                stateWater.setText(arr[11]+"%"); // 현재 습도

                                fstateTemp.setText(arr[14]+"ºC"); // 집 온도
                                fstateWater.setText(arr[15]+"%"); //집 습도

                                change();

//                                //12빗물, 13가스, 14농장온도, 15농장습도, 16토양 Integer.parseInt(arr[16])
//                                if(Integer.parseInt(arr[12]) > 100 || Integer.parseInt(arr[13]) > 250 ) {
//                                    SensorValueListener.SensorValueset(Integer.parseInt(arr[12]), Integer.parseInt(arr[13]));
//                                }
//                                else{
//                                    SensorValueListener.SensorValueset(0, 0);
////                                    SensorValueListener.SensorValueset(Integer.parseInt(arr[12]), Integer.parseInt(arr[13]));
//                                }


                                Log.d(TAG, "recv message: "+recvMessage);
//                                mConversationArrayAdapter.insert(mServerIP + " - " + recvMessage, 0);
                            }
                        });
                    }
                }

                Log.d(TAG, "ReceiverThread: thread has exited");
                if (mOut != null) {
                    mOut.flush();
                    mOut.close();
                }

                mIn = null;
                mOut = null;

                if (mSocket != null) {
                    try {
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e) {
                System.out.println("안들어옴");
                Log.e(TAG, "ReceiverThread: "+ e);
            }
        }

    }


    public void change(){

        if(arr[0].equals("0")){
            window.setImageResource(R.drawable.window_off); //창문 닫침
        }else{
            window.setImageResource(R.drawable.window); //창문 열림
        }

        if(arr[1].equals("0")){
            homefan.setImageResource(R.drawable.fan_off); //환풍기 꺼짐
        }else{
            homefan.setImageResource(R.drawable.fan); //환풍기 켜짐
        }

        if(arr[2].equals("0")){
            fan.setImageResource(R.drawable.fan2_off); //선풍기 꺼짐
        }else{
            fan.setImageResource(R.drawable.fan2); //선풍기 켜짐
        }

        if(arr[3].equals("0")){
            homeled.setImageResource(R.drawable.idea_off); //집 LED 꺼짐
        }else{
            homeled.setImageResource(R.drawable.idea); //집 LED 켜짐
        }

        if(arr[4].equals("0")){
            h.setImageResource(R.drawable.humidifier_off); //가습기 꺼짐
        }else{
            h.setImageResource(R.drawable.humidifier); //가습기 켜짐
        }

        if(arr[5].equals("0")){
            lcd.setImageResource(R.drawable.lcd_off); //가습기 꺼짐
        }else{
            lcd.setImageResource(R.drawable.lcd); //가습기 켜짐
        }

        if(arr[6].equals("0")){
            farmled.setImageResource(R.drawable.power_off); //농장 LED 꺼짐
        }else{
            farmled.setImageResource(R.drawable.power_on); //농장 LED 켜짐
        }

        if(arr[7].equals("0")){
            spring.setImageResource(R.drawable.sprinkler_off); //농장 펌프 꺼짐
        }else{
            spring.setImageResource(R.drawable.sprinkler); //농장 펌프 켜짐
        }

        if(arr[8].equals("0")){
            farmfan.setImageResource(R.drawable.farmfan_off); //농장 환풍기 꺼짐
        }else{
            farmfan.setImageResource(R.drawable.farmfan); //농장 환풍기 켜짐
        }

//        if(arr[9].equals("0")){
//            spring.setImageResource(R.drawable.sprinkler); //농장 문 닫침
//        }else{
//            spring.setImageResource(R.drawable.sprinkler); //농장 문 열림
//        }

    }

    public void showErrorDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error");
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
        builder.create().show();
    }


    //음성인식 함수
    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        str = "";
        for (int i = 0; i < matches.size(); i++) {
            str = str + matches.get(i) + "\n";
            Log.d(TAG, "onReulstsText:" + matches.get(i));
        }
    }


    @Override
    public void onError(int errorCode){
        String message;
        switch (errorCode){
            case SpeechRecognizer.ERROR_AUDIO:
                message = "오디오 에러";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "클라이언트 에러";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "퍼미션 없음";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "네트워크  에러";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "네트워트 타임 아웃";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "찾을 수 없음";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "BUSY";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "서버이상";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "시간 초과";
                break;

            default:
                message="알수없음";
                break;
        }

        Log.d(TAG,"speech Error"+message);
    }

    @Override
    public void onRmsChanged(float v) {
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
    }

    @Override
    public void onPartialResults(Bundle bundle) {
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
    }


    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String>text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String[] rs = new String[text.size()];
                    text.toArray(rs);

//                    textView1.setText(rs[0]);

                    if(rs[0].equals("창문")) {
                        new Thread(new SenderThread("a")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("환풍기 켜")){
                        new Thread(new SenderThread("B")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("환풍기 꺼")){
                        new Thread(new SenderThread("2")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("선풍기 켜")){
                        new Thread(new SenderThread("C")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("선풍기 꺼")){
                        new Thread(new SenderThread("3")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("불 켜")){
                        new Thread(new SenderThread("D")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("불 꺼")){
                        new Thread(new SenderThread("4")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("가습기")){
                        new Thread(new SenderThread("e")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("TV")){
                        new Thread(new SenderThread("f")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("농장 형광등")){
                        new Thread(new SenderThread("g")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("펌프")){
                        new Thread(new SenderThread("h")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("농장 환풍기")){
                        new Thread(new SenderThread("i")).start();
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("자동 제어")){
                        new Thread(new SenderThread("u")).start();
                        window.setEnabled(false);
                        homefan.setEnabled(false);
                        fan.setEnabled(false);
                        homeled.setEnabled(false);
                        h.setEnabled(false);
                        lcd.setEnabled(false);
                        farmfan.setEnabled(false);
                        spring.setEnabled(false);
                        farmled.setEnabled(false);
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }else if(rs[0].equals("자동 제어 취소")){
                        new Thread(new SenderThread("n")).start();
                        window.setEnabled(true);
                        homefan.setEnabled(true);
                        fan.setEnabled(true);
                        homeled.setEnabled(true);
                        h.setEnabled(true);
                        lcd.setEnabled(true);
                        farmfan.setEnabled(true);
                        spring.setEnabled(true);
                        farmled.setEnabled(true);
                        Toast.makeText(getActivity(), rs[0], Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            }
        }
    }


}

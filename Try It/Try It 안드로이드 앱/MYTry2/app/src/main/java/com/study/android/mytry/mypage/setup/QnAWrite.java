package com.study.android.mytry.mypage.setup;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileUploadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.study.android.mytry.login.Intro.local_url;

public class QnAWrite extends AppCompatActivity {

    EditText title;
    EditText content;
    ImageView uploadImage;
    Button send;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 3;
    private static final int MULTIPLE_PERMISSIONS = 101;

    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private static final String TAG = "lecture";

    static String UploadImg;
    static String fileName;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_setup_qna_write);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        title =findViewById(R.id.qna_title);
        content=findViewById(R.id.qna_content);
        uploadImage=findViewById(R.id.qna_image_upload);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissions();
                selectFromAlbum();
            }
        });
        send=findViewById(R.id.qna_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = local_url + "/yejin/mypage_question_insert";
                url = url+"?id="+userid+
                            "&title="+title.getText()+
                            "&content="+content.getText()+
                            "&image="+fileName;
                Log.d("lecture", url);

                NetworkTask networkTask= new NetworkTask(url, null);
                networkTask.execute();

                Toast.makeText(getApplicationContext(), "1:1문의 신청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private boolean checkPermissions() {

        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }

    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FROM_ALBUM) {
                try {
                    // 사진 절대 경로
                    String imagePath = getRealPathFromURI(data.getData());
                    Log.d("name", "절대 경로" + imagePath);

                    // 파일 이름
                    fileName = getImageNameToUri(data.getData());
                    Log.d("name", "파일 이름" + fileName);

                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    // 리사이징 : imageView 사진 올릴때 필요함 !!!
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();

                    Bitmap src = BitmapFactory.decodeFile(imagePath);
                    Bitmap resized = Bitmap.createScaledBitmap(src, width / 4, height / 4, true);

                    // 비트맵을 jpeg로 바꿔서 따로 저장 후 통신해야함
                    saveBitmaptoJpeg(resized, "seatdot", fileName);

                    Log.d("name", "사진크기 : " + resized);
                    Log.d("name", "사진크기 : " + height / 4);
                    Log.d("name", "사진크기 : " + width / 4);

                    if (bitmap != null) {
                        // imageView에 뿌려주는건 bitmap
                        uploadImage.setImageBitmap(bitmap);
                        uploadImage.invalidate();

                        // 스레드
                        String urlString = local_url + "/yejin/private_upload";
                        Log.d("name", urlString);
                        FilUploadTask networkTask = new FilUploadTask(urlString, null);
                        networkTask.execute();

                    } else {
                        Log.d(TAG, "BBB");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == PICK_FROM_CAMERA) {
                //getPictureFromCamera();
            }
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String foler_name = "/" + folder + "/";
        String file_name = name + ".jpg";
        String string_path = ex_storage + foler_name;
        UploadImg = string_path + file_name;

        File file_path;
        try {
            file_path = new File(string_path);
            if (!file_path.isDirectory()) {
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path + file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        } catch (FileNotFoundException exception) {
            Log.e("FileNotFoundException", exception.getMessage());
        } catch (IOException exception) {
            Log.e("IOException", exception.getMessage());
        }
    }

    // 사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    // Uri 에서 파일명을 추출하는 로직
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    public void onBackPressed(View v){
        finish();
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    // 통신
    public class FilUploadTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public FilUploadTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            FileUploadConnection requestHttpURLConnection = new FileUploadConnection();
            result = requestHttpURLConnection.request(url, UploadImg, fileName); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

package com.study.android.mytry.certification;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationFifth;
import com.study.android.mytry.challenge_private.CreationFourth;
import com.study.android.mytry.challenge_public.FileUploadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.study.android.mytry.login.Intro.local_url;

public class Certificate_gallery extends AppCompatActivity {
    private static final String tag="selee";

    private static final int PICK_FROM_CMERA=1;
    private static final int PICK_FROM_ALBUM=2;

    TextView challenge_title;
    EditText comment;
    Button submit_btn;
    String userid;
    String challenge_num;
    String url;
    Button back_pressed;

    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;

    private Uri photoUri;
    private ImageView imageView;
    TextView sysdate;

    static String name_Str;
    static String UploadImgPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_gallery);

        challenge_title = findViewById(R.id.certification_gallery_title);
        imageView = findViewById(R.id.certification_gallery_imageView);
        sysdate = findViewById(R.id.certification_gallery_textView);

        comment = (EditText)findViewById(R.id.certification_gallery_editText);
        submit_btn = (Button)findViewById(R.id.certification_gallery_submit_btn);

        back_pressed = (Button) findViewById(R.id.certification_gallery_back_btn);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        Intent intent = getIntent();
        String tempTitle = intent.getExtras().getString("challenge_title");
        challenge_title.setText(tempTitle);
        challenge_num = intent.getExtras().getString("challenge_num");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Certificate_gallery.this, Certificate_chooseType_popup.class);
                startActivityForResult(intent3, 1235); // 다음화면으로 넘어가기
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClicked();
            }
        });

        checkPermissions();
    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for(String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if(result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if(!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if(grantResults.length > 0) {
                    for(int i = 0; i < permissions.length; i++) {
                        if(permissions[i].equals(this.permissions[0])) {
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    private void takeAPicture() {
        Log.d(tag,"여기오니?1");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Log.d(tag,"여기오니?2");
        if(photoFile != null) {
            photoUri = FileProvider.getUriForFile (
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            Log.d(tag,"여기오니?3");
            startActivityForResult(intent, PICK_FROM_CMERA);
        }
    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory()+"/images/");
        if(!dir.exists()) {
            dir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        name_Str = timeStamp + ".png";
        Log.d(tag,name_Str);

        File storageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsoluteFile()+"/images/"+name_Str);

        UploadImgPath = storageDir.getAbsolutePath();
        Log.d(tag,UploadImgPath);

        return storageDir;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(tag,"requestCode : "+requestCode);
        if(requestCode == 1235 && resultCode == RESULT_OK){
            String myData = data.getStringExtra("check");
            if(myData.equals("카메라")) {
                takeAPicture();

            } else if(myData.equals("갤러리")){
                selectFromAlbum();
            }

        }

        if(resultCode == RESULT_OK) {
            if (requestCode == PICK_FROM_CMERA) {

                // 카메라에서 가져오기

                Log.d(tag, "왜 안댕9");
                getPictureFromCamera();
            }
        }

        if(resultCode == RESULT_OK) {

            Log.d(tag, "왜 안댕8"+resultCode);

            if (requestCode == PICK_FROM_ALBUM) {

                Log.d(tag, "왜 안댕9"+requestCode);

                try {
                    // 사진 절대 경로
                    String imagePath = getRealPathFromURI(data.getData());
                    Log.d(tag, "절대 경로" + imagePath);

                    // 파일 이름
                    name_Str = getImageNameToUri(data.getData());
                    Log.d(tag, "파일 이름" + name_Str);

                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    // 리사이징 : imageView 사진 올릴때 필요함 !!!
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();

                    Bitmap src = BitmapFactory.decodeFile(imagePath);
                    Bitmap resized = Bitmap.createScaledBitmap(src, width / 4, height / 4, true);

                    // 비트맵을 jpeg로 바꿔서 따로 저장 후 통신해야함
                    saveBitmaptoJpeg(resized, "seatdot", name_Str);

                    Log.d("name", "사진크기 : " + resized);
                    Log.d("name", "사진크기 : " + height / 4);
                    Log.d("name", "사진크기 : " + width / 4);

                    if (bitmap != null) {
                        // imageView에 뿌려주는건 bitmap

                        imageView.setImageBitmap(bitmap);
                        imageView.invalidate();

                        // 스레드
                        String urlString = local_url + "/selee/feed_image_upload";
                        Log.d("name", urlString);
                        FilUploadTask networkTask = new FilUploadTask(urlString, null);
                        networkTask.execute();

                    } else {
                        Log.d(tag, "BBB");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void selectFromAlbum() {

        Log.d(tag, "왜 안댕1");

        Intent intent  = new Intent(Intent.ACTION_PICK);

        Log.d(tag, "왜 안댕2");
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Log.d(tag, "왜 안댕3");
        intent.setType("image/*");

        Log.d(tag, "왜 안댕4");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Log.d(tag, "왜 안댕5");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        Log.d(tag, "왜 안댕6");
        startActivityForResult(intent, PICK_FROM_ALBUM);

        Log.d(tag, "왜 안댕7");
    }

    private void getPictureFromCamera() {
        Log.d(tag, "왜 안댕11"+UploadImgPath);

        Bitmap bitmap = BitmapFactory.decodeFile(UploadImgPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(UploadImgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if(exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        // 이미지 뷰에 비트맵 넣기
        imageView.setImageBitmap(rotate(bitmap, exifDegree));
        imageView.invalidate();

        try {
            // 사진 절대 경로
            Log.d(tag, "절대 경로" + UploadImgPath);

            // 파일 이름
            Log.d(tag, "파일 이름" + name_Str);

            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

            // 리사이징 : imageView 사진 올릴때 필요함 !!!
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            Bitmap src = BitmapFactory.decodeFile(UploadImgPath);
            Bitmap resized = Bitmap.createScaledBitmap(src, width / 4, height / 4, true);

            // 비트맵을 jpeg로 바꿔서 따로 저장 후 통신해야함
            saveBitmaptoJpeg(resized, "seatdot", name_Str);

            Log.d("name", "사진크기 : " + resized);
            Log.d("name", "사진크기 : " + height / 4);
            Log.d("name", "사진크기 : " + width / 4);

            if (bitmap != null) {
                // imageView에 뿌려주는건 bitmap

                imageView.setImageBitmap(bitmap);
                imageView.invalidate();

                // 스레드
                String urlString = local_url + "/selee/feed_image_upload";
                Log.d("name", urlString);
                Certificate_gallery.FilUploadTask networkTask = new Certificate_gallery.FilUploadTask(urlString, null);
                networkTask.execute();

            } else {
                Log.d(tag, "BBB");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String formatDate = sdfNow.format(date);

        sysdate.setText(formatDate);
    }


    // 사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // 사진의 회전값 가져오기
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String foler_name = "/" +
                folder + "/";
        String file_name = name + ".jpg";
        String string_path = ex_storage + foler_name;
        UploadImgPath = string_path + file_name;

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
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
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
    ////////////////////////////////////////

    public void uploadClicked(){

        url = local_url + "/selee/feed_insert";

        String msg = "?member_id="+userid+
                "&challenge_num="+challenge_num+
                "&challenge_title="+challenge_title.getText().toString()+
                "&feed_info="+name_Str+
                "&feed_comment="+comment.getText().toString();

        url = url +msg;
        Log.d("certificate", url);

        Certificate_gallery.NetworkTask networkTask = new Certificate_gallery.NetworkTask(url, null);
        networkTask.execute();

        Toast.makeText(Certificate_gallery.this,"성공적으로 인증을 마쳤습니다.",Toast.LENGTH_LONG).show();

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
            result = requestHttpURLConnection.request(url, UploadImgPath, name_Str); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

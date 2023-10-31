package com.example.parkinson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TakephotoActivity extends AppCompatActivity {

    private EditText heightEditText;
    private EditText weightEditText;
    private Button submitButton;
    private TextView resultTextView;
    private RequestQueue queue;
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    public File sendpicture;
    private static final String API_URL = "http://121.36.17.26:8000/pks/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        queue = Volley.newRequestQueue(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();
                sendData(height, weight);
            }
        });
        Button takephoto = findViewById(R.id.take_photo);
        picture = findViewById(R.id.picture);
        takephoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(TakephotoActivity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TakephotoActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openCamera();
                }
            }
        });
    }
    private void openCamera() {
        File outputImage = new File(getExternalFilesDir(null), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(TakephotoActivity.this,
                    "com.example.parkinson.fileprovider", outputImage);

        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
    private void sendData(String height, String weight) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res=null;
                        try {
                            JSONObject jsonObject = new JSONObject(response); //将JSON字符串转换为JSONObject对象
                            int result = jsonObject.getInt("result"); //获取result字段的值
                            if (result == 0) {
                                res="您可能患有帕金森病（可信度80%）,请到专业医院抓紧时间诊疗";
                                //处理result为0的情况
                            } else if (result == 1) {
                                //处理result为1的情况
                                res="恭喜您没有帕金森病（可信度90%），请持续关注身体健康";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //处理JSON解析异常的情况
                        }
                        resultTextView.setText(res);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultTextView.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("height", height);
                params.put("weight", weight);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
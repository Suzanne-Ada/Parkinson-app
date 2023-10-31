package com.example.parkinson.audio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkinson.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AudioResultActivity extends AppCompatActivity {

    private EditText HeightEditText;
    private EditText WeightEditText;
    private Button submitButton;
    private RequestQueue queue;
    private TextView AudioResult;
    private static final String API_URL = "http://121.36.17.26:8000/pks/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_result);
        submitButton = findViewById(R.id.AudioSubmit);
        AudioResult = findViewById(R.id.AudioResult);
        HeightEditText = findViewById(R.id.HeightET);
        WeightEditText = findViewById(R.id.WeightET);
        queue = Volley.newRequestQueue(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = HeightEditText.getText().toString();
                String weight = WeightEditText.getText().toString();
                sendData(height,weight);
            }
        });

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
                                res="您可能患有帕金森病（可信度83.5%）,请到专业医院抓紧时间诊疗";
                                //处理result为0的情况
                            } else if (result == 1) {
                                //处理result为1的情况
                                res="恭喜您没有帕金森病（可信度95.8%），请持续关注身体健康";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //处理JSON解析异常的情况
                        }
                        AudioResult.setText(res);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AudioResult.setText(error.toString());
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
package com.example.parkinson.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkinson.HandActivity;
import com.example.parkinson.R;
import com.example.parkinson.TakephotoActivity;
import com.example.parkinson.audio.AudioListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForcastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForcastFragment extends Fragment {

    private RelativeLayout GoRecord;//点击进入录音界面按钮
    private RelativeLayout GoTakephoto;//点击进入拍照界面按钮

    private RelativeLayout GoHand;//点击进入手绘界面按钮
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForcastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForcastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForcastFragment newInstance(String param1, String param2) {
        ForcastFragment fragment = new ForcastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //        因为后面的监听中有view 先不return，先定义一个
        final View view = inflater.inflate(R.layout.fragment_forcast, container, false);
//        组件绑定 通过id 为了方便就直接用一样的id
        GoRecord = view.findViewById(R.id.audio_btn);
        //设置点击事件    点击录音界面按钮，进入录音界面
        GoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AudioListActivity.class);
                startActivity(intent);
            }
        });

        //        组件绑定 通过id 为了方便就直接用一样的id
        GoTakephoto = view.findViewById(R.id.takephoto_btn);
        //设置点击事件    点击录音界面按钮，进入录音界面
        GoTakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TakephotoActivity.class);
                startActivity(intent);
            }
        });
//      手绘线检测
        GoHand = view.findViewById(R.id.hand_btn);
        //设置点击事件    点击录音界面按钮，进入录音界面
        GoHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HandActivity.class);
                startActivity(intent);
            }
        });
        //这里要注意return view；不能缺少
        return view;
    }

}
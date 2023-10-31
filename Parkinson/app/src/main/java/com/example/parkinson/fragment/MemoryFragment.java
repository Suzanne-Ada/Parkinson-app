package com.example.parkinson.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkinson.R;
import com.example.parkinson.bean.AudioBean;
import com.example.parkinson.bean.MemoryBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView mSearchView;
    private ListView listView;
    private MemoryListAdapter mAdapter;
    private List<MemoryBean> datas;
    public MemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoryFragment newInstance(String param1, String param2) {
        MemoryFragment fragment = new MemoryFragment();
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
        return inflater.inflate(R.layout.fragment_memory, container, false);
//        View view =  inflater.inflate(R.layout.fragment_memory, container, false);
//        //2、绑定控件
//        listView=(ListView) view.findViewById(R.id.memory_lv);
//        //3、准备数据
//        //4、创建适配器 连接数据源和控件的桥梁
//        //参数 1：当前的上下文环境
//        //参数 2：当前列表项所加载的布局文件
//        //(android.R.layout.simple_list_item_1)这里的布局文件是Android内置的，里面只有一个textview控件用来显示简单的文本内容
//        //参数 3：数据源
//        mAdapter = new MemoryListAdapter(getActivity(),datas);
//        //5、将适配器加载到控件中
//        listView.setAdapter(mAdapter);
//        return view;
    }
}
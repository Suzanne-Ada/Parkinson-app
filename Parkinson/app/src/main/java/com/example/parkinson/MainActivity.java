package com.example.parkinson;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkinson.R;
import com.example.parkinson.audio.AudioListActivity;
import com.example.parkinson.fragment.ForcastFragment;
import com.example.parkinson.fragment.HomeFragment;
import com.example.parkinson.fragment.MainFragmentPagerAdapter;
import com.example.parkinson.fragment.MemoryFragment;
import com.example.parkinson.fragment.MineFragment;
import com.example.parkinson.utils.Contants;
import com.example.parkinson.utils.IFileInter;
import com.example.parkinson.utils.PermissionUtils;
import com.example.parkinson.utils.SDCardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    //   权限列表
    String []permissions = {android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    //   底部导航栏
    List<Fragment> fragmentList = new ArrayList<>();
    MainFragmentPagerAdapter adapter;
    ViewPager viewPager;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAppDir();
        PermissionUtils.getInstance().onRequestPermission(this,permissions,listener);
//      底部导航栏
        //初始化控件
        initView();
        //绑定RadioButton
        initViewPager();
    }
    private void createAppDir() {
        File recoderDir = SDCardUtils.getInstance().createAppFetchDir(IFileInter.FETCH_DIR_AUDIO);
        Contants.PATH_FETCH_DIR_RECORD=recoderDir.getAbsolutePath();
    }

    PermissionUtils.OnPermissionCallbackListener listener = new PermissionUtils.OnPermissionCallbackListener() {
        @Override
        public void onGranted() {
            //判断是否有应用文件夹，如果没有就创建应用文件夹
            createAppDir();
        }
        @Override
        public void onDenied(List<String> deniedPermissions) {
            PermissionUtils.getInstance()
                    .showDialogTipUserGotoAppSetting(MainActivity.this);
        }
    };

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        申请录音权限和存储权限
        PermissionUtils.getInstance().onRequestPermissionResult(this,requestCode,permissions,grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private void openCamera() {
        File outputImage = new File(this.getExternalFilesDir(null), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this,
                    "com.example.parkinson.fileprovider", outputImage);

        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        rg = findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(this);
        rg.getChildAt(0).performClick();
    }

    private void initViewPager() {
        //添加碎片
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ForcastFragment());
        fragmentList.add(new MemoryFragment());
        fragmentList.add(new MineFragment());

        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        rg.check(R.id.menu_home);
                        break;
                    case 1:
                        rg.check(R.id.menu_forcast);
                        break;
                    case 2:
                        rg.check(R.id.menu_memory);
                        break;
                    case 3:
                        rg.check(R.id.menu_mine);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.menu_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.menu_forcast:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menu_memory:
                viewPager.setCurrentItem(2);
                break;
            case R.id.menu_mine:
                viewPager.setCurrentItem(3);
                break;
        }
    }

}

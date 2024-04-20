package com.example.zbwx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbwx.fragments.HomeFragment;
import com.example.zbwx.fragments.MineFragment;
import com.example.zbwx.fragments.RepairFragment;
import com.example.zbwx.fragments.StudyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private MyApplication myApplication;
    private BottomNavigationView bnv;

    //声明Fragments的工具和四个fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HomeFragment homeFragment;
    StudyFragment studyFragment;
    RepairFragment repairFragment;
    MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置底部导航栏点击事件监听
        bnv = findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.menu_item_home): replace_fragment("home");break;
                    case (R.id.menu_item_study): replace_fragment("study");break;
                    case (R.id.menu_item_repair): replace_fragment("repair");break;
                    case (R.id.menu_item_mine): replace_fragment("mine");break;
                };
                return true;
            }
        });
        bnv.setSelectedItemId(R.id.menu_item_home);
        //判断登录状态
        sp = this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        if (sp.getBoolean("AUTO_ISCHECK", false)) {
            myApplication = (MyApplication) getApplication();
            myApplication.setUsername(sp.getString("USER_NAME", ""));
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }


    //根据参数，选择加载fragment
    private void replace_fragment(String string) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (string) {
            case "home":
                homeFragment = HomeFragment.newInstance("", "");
                fragmentTransaction.replace(R.id.fcv, homeFragment).commit();
                break;
            case "study":
                studyFragment = StudyFragment.newInstance("", "");
                fragmentTransaction.replace(R.id.fcv, studyFragment).commit();
                break;
            case "repair":
                repairFragment = RepairFragment.newInstance("", "");
                fragmentTransaction.replace(R.id.fcv, repairFragment).commit();
                break;
            case "mine":
                mineFragment = MineFragment.newInstance("", "");
                fragmentTransaction.replace(R.id.fcv, mineFragment).commit();
                break;
            default:
                break;
        }

    }
}
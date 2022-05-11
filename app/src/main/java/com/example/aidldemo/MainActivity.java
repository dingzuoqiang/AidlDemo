package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.aidldemo.aidl.MyAidlService;
import com.example.aidldemo.aidl.Person;
import com.example.aidldemo.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private IMyAidlInterface mAidl;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            mAidl = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.tvAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person("ding", new Random().nextInt(10));
                try {
                    mAidl.addPerson(person);
                    List<Person> personList = mAidl.getPersonList();
                    binding.tvContent.setText(personList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

//        如果是两个project，则客户端需用隐式调用，setPackage、setAction等
//        bindService(new Intent("com.example.aidldemo.IMyAidlInterface"), mServiceConnection, BIND_AUTO_CREATE);
        Intent intent1 = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent1, mServiceConnection, BIND_AUTO_CREATE);
    }


}
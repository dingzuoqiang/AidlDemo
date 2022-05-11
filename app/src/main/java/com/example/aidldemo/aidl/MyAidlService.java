package com.example.aidldemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aidldemo.IMyAidlInterface;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    public MyAidlService() {
    }

    /**
     * 如果是两个project，这个class应在服务端，aidl文件两端各一份
     * <p>
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        Log.d(TAG, "MyAidlService onBind");
        return mIBinder;
    }


    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<Person> mPersons;

    /**
     * 创建生成的本地 Binder 对象，实现 AIDL 制定的方法
     */
    private IBinder mIBinder = new IMyAidlInterface.Stub() {

        @Override
        public void addPerson(Person person) {
            mPersons.add(person);
        }

        @Override
        public List<Person> getPersonList() {
            return mPersons;
        }
    };
}
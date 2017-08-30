package com.base.resultset.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;
import com.base.resultset.ui.imageview.ImageActivity;
import com.base.resultset.ui.newapp.startmodule.broadcast.HeadsetReceiver;
import com.base.resultset.ui.textview.TextActivity;
import com.base.resultset.utils.Common;

public class MainActivity extends BaseActivity {
    private int[] butId = {R.id.button1, R.id.button2, R.id.button};
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        for (int i = 0; i < butId.length; i++) {
            Button butt = (Button) findViewById(butId[i]);
            butt.setOnClickListener(this);
        }
        setTitleBarVisible(false);
        //注册广播
        HeadsetReceiver noisyAudioStreamReceiver = new HeadsetReceiver();
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(noisyAudioStreamReceiver, filter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Common.ActivitySkip(this, TextActivity.class);
                break;
            case R.id.button2:
                Common.ActivitySkip(this, ImageActivity.class);
                break;
            case R.id.button:
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.liutao.help.personalbutler");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    // 没有安装要跳转的app应用，提醒一下
                    Toast.makeText(getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }



}

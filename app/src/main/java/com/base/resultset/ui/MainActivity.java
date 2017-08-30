package com.base.resultset.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;
import com.base.resultset.ui.baidu.MapHomePageActivity;
import com.base.resultset.ui.imageview.ImageActivity;
import com.base.resultset.ui.textview.TextActivity;
import com.base.resultset.utils.Common;

public class MainActivity extends BaseActivity {
    private int[] butId = {R.id.button1, R.id.button2, R.id.button};
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isStick = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        for (int i = 0; i < butId.length; i++) {
            Button butt = (Button) findViewById(butId[i]);
            butt.setOnClickListener(this);
        }
        setTitleBarVisible(false);

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
                Common.ActivitySkip(this, MapHomePageActivity.class);
                break;
        }

    }


}

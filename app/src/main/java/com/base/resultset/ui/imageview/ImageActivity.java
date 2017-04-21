package com.base.resultset.ui.imageview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;

/**
 * Created by Administrator on 2017/4/21.
 */

public class ImageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(ImageActivity.this, "ImageActivity", Toast.LENGTH_LONG).show();
    }
}

package platfotm.drawapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import platfotm.drawapp.R;
import platfotm.drawapp.View.impl.HenCoder1;

/**
 * @author Ly
 * @date 2018/1/4
 */

public class HenCode1 extends AppCompatActivity {
    private HenCoder1 henCoder1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        henCoder1 = (HenCoder1) findViewById(R.id.hencoder1);
        reDraw();
        henCoder1.draw();
        findViewById(R.id.bt_main_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reDraw();
                henCoder1.draw();
            }
        });
    }

    private void reDraw() {
        List<Float> mPointRateValue = new ArrayList<>();
        List<String> mPointName = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mPointRateValue.add((float) (Math.random() * 1));
            mPointName.add("角度" + i);
        }
        henCoder1.setPointName(mPointName);
        henCoder1.setPointRateValue(mPointRateValue);
    }

}

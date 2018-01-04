package platfotm.drawapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import platfotm.drawapp.View.impl.HenCoder1;

/**
 * @author Ly
 */
public class MainActivity extends AppCompatActivity {
    private HenCoder1 mHenCoder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_main_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHenCoder1.draw();
            }
        });
        mHenCoder1 = (HenCoder1) findViewById(R.id.he);


    }
}

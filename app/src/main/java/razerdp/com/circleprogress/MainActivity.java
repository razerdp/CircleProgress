package razerdp.com.circleprogress;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import razerdp.com.widget.CircleProgressView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private Button btn_finish;
    private Button btn_failed;
    private CircleProgressView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_failed = (Button) findViewById(R.id.btn_failed);
        progress = (CircleProgressView) findViewById(R.id.progress);

        btn_start.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_failed.setOnClickListener(this);

        progress.setOnFailedClickListener(new CircleProgressView.OnFailedClickListener() {
            @Override
            public void onFailedClick(View v) {
                progressStart();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                progressStart();
                break;
            case R.id.btn_finish:
                progressFinish();
                break;
            case R.id.btn_failed:
                progressFailed();
                break;
        }
    }

    private void progressStart() {
        if (!progress.isLoading()) {
            progress.setStart();
            ThreadPoolManager.execute(RefreshRunnable);
        }
    }

    private void progressFinish() {
        progress.setFinish(true);
    }

    private void progressFailed() {
        progress.setFailed();
    }

    private Runnable RefreshRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 101; i++) {
                SystemClock.sleep(100);
                progress.setCurrentPresent(i);
            }
        }
    };
}

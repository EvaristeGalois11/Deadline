package it.onetech.deadline;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private CustomImage buffer;
    private CountDownTimer timer;
    private final long millisInFuture = 60000;
    private long tempTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridView gridview = findViewById(R.id.gridView);

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            CustomImage imageView = (CustomImage) v;
            if (buffer == null) {
                imageView.setPadding(10, 10, 10, 10);
                buffer = imageView;
            } else {
                if (buffer.equals(imageView)) {
                    imageView.setPadding(0, 0, 0, 0);
                } else {
                    imageView.swap(buffer);
                    buffer.setPadding(0, 0, 0, 0);
                }
                buffer = null;
            }
        });

        gridview.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                gridview.setAdapter(new ImageAdapter(GameActivity.this, 100));
            }
        });

        initCountDown(millisInFuture);
    }

    private void initCountDown(long time) {
        timer = new CountDownTimer(time, 50) {

            @Override
            public void onTick(long millisUntilFinished) {
                tempTime = millisUntilFinished;
                ProgressBar progressBar = findViewById(R.id.progressBar);

                int max = progressBar.getMax();
                float m = millisInFuture;
                float r = millisUntilFinished;

                int newProgress = Math.round((1 - r / m) * max);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onFinish() {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getMax());
                Toast.makeText(GameActivity.this, "TIME'S UP", Toast.LENGTH_SHORT).show();
                onClickButton7(findViewById(R.id.button7));
            }
        }.start();
    }

    public void onClickButton6(View view) {
        timer.cancel();
        GridView gridView = findViewById(R.id.gridView);
        int elems = gridView.getChildCount();
        boolean equal = true;
        for (int i = 0; i < elems; i++) {
            CustomImage image = (CustomImage) gridView.getChildAt(i);
            equal &= image.getIndex() == i;
        }

        victoryCheck(equal);
    }

    private void victoryCheck(boolean equal) {
        Intent i = new Intent();
        if (equal) {
            ProgressBar p = findViewById(R.id.progressBar);
            int score = p.getMax() - p.getProgress();
            i.putExtra("score", score);
            Toast.makeText(this, "YOU WIN WITH A SCORE OF " + score, Toast.LENGTH_LONG).show();
            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, "NOT CORRECT", Toast.LENGTH_SHORT).show();
            initCountDown(tempTime);
        }
    }

    public void onClickButton7(View view) {
        timer.cancel();
        GridView gridView = findViewById(R.id.gridView);
        ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
        adapter.shuffleChunks();
        gridView.invalidateViews();
        buffer = null;
        initCountDown(millisInFuture);
    }

    public void exit(View view) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    @Override
    public void finish() {
        timer.cancel();
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent();
            setResult(RESULT_CANCELED, i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

package it.onetech.deadline;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private CustomImage buffer;
    private CountDownTimer timer;
    private final long millisInFuture = 60000;


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

        timer = new CountDownTimer(millisInFuture, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                ProgressBar progressBar = findViewById(R.id.progressBar);

                int max = progressBar.getMax();
                float m = millisInFuture;
                float r = millisUntilFinished;

                int newProgress =  Math.round((1 - r / m) * max);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onFinish() {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getMax());
                Toast.makeText(GameActivity.this, "I puzzle non fanno per te...", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }

    public void onClickButton6(View view) {
        GridView gridView = findViewById(R.id.gridView);

        int elems = gridView.getChildCount();
        boolean equal = true;
        for (int i = 0; i < elems; i++) {
            CustomImage image = (CustomImage) gridView.getChildAt(i);
            equal &= image.getIndex() == i;
        }

        Toast.makeText(this, "" + equal, Toast.LENGTH_SHORT).show();
    }

    public void onClickButton7(View view) {
        GridView gridView = findViewById(R.id.gridView);
        ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
        adapter.shuffleChunks();
        gridView.invalidateViews();
        buffer = null;
    }

    public void exit(View view) {
        finish();
    }

    @Override
    public void finish() {
        timer.cancel();
        super.finish();
    }

}

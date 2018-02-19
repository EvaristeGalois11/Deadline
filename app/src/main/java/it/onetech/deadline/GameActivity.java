package it.onetech.deadline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    CustomImage buffer;

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

}

package it.onetech.deadline;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridView gridview = findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, 3, 100, R.drawable.test));

        gridview.setOnItemClickListener((parent, v, position, id) -> Toast.makeText(GameActivity.this, "" + position,
                Toast.LENGTH_SHORT).show());

    }
}

package it.onetech.deadline;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    private int column;
    private int row;
    private int imageSizePx;
    private List<Bitmap> chunks;

    public ImageAdapter(Activity activity, int row, int imageSizeDp, int bitmapId) {
        this.activity = activity;
        this.row = row;
        imageSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSizeDp, activity.getResources().getDisplayMetrics());
        column = getScreenWidth() / imageSizePx;

        splitBitmap(bitmapId);
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void splitBitmap(int bitmapId) {
        chunks = new ArrayList<>();

        Bitmap img = BitmapFactory.decodeResource(activity.getResources(), bitmapId);

        int width = img.getWidth() / column;
        int height = img.getHeight() / row;

        for (int i = 0; i<row; i++) {
            for (int k = 0; k<column; k++) {
                Bitmap chunk = Bitmap.createBitmap(img, width * k, height * i, width, height);
                chunks.add(chunk);
            }
        }
    }

    @Override
    public int getCount() {
        return column * row;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(activity);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(imageSizePx, imageSizePx));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(chunks.get(position));
        return imageView;
    }
}

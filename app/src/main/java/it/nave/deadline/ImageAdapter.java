package it.nave.deadline;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ImageAdapter extends BaseAdapter {

    public static class BitMapWrapper {
        private Bitmap bitmap;
        private int index;

        public BitMapWrapper (Bitmap bitmap, int index) {
            this.bitmap = bitmap;
            this.index = index;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public int getIndex() {
            return index;
        }
    }

    private Activity activity;
    private int column;
    private int row;
    private int imageSizePx;
    private List<BitMapWrapper> chunks;

    public ImageAdapter(Activity activity, int imageSizeDp) {
        this.activity = activity;
        imageSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSizeDp, activity.getResources().getDisplayMetrics());

        GridView gridView = activity.findViewById(R.id.gridView);
        column = gridView.getNumColumns();
        row = 3; // TODO calcolarlo in base alle dimensioni dello schermo

        chunks = splitBitmap(R.drawable.test);
        shuffleChunks();
    }

    private List<BitMapWrapper> splitBitmap(int bitmapId) {
        List<BitMapWrapper> result = new ArrayList<>();

        Bitmap img = BitmapFactory.decodeResource(activity.getResources(), bitmapId);

        int width = img.getWidth() / column;
        int height = img.getHeight() / row;

        int id = 0;
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < column; k++) {
                Bitmap chunk = Bitmap.createBitmap(img, width * k, height * i, width, height);
                result.add(new BitMapWrapper(chunk, id++));
            }
        }

        return result;
    }

    public void shuffleChunks() {
        Collections.shuffle(chunks);
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
        CustomImage imageView;
        BitMapWrapper bitMapWrapper = chunks.get(position);

        if (convertView == null) {
            imageView = new CustomImage(activity);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(imageSizePx, imageSizePx));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Color.RED);
            imageView.setCropToPadding(true);
        } else {
            imageView = (CustomImage) convertView;
            imageView.setPadding(0,0,0,0);
        }

        imageView.setIndex(bitMapWrapper.getIndex());
        imageView.setImageBitmap(bitMapWrapper.getBitmap());
        return imageView;
    }
}

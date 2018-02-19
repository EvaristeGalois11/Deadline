package it.onetech.deadline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;

public class CustomImage extends AppCompatImageView {
    private int index;

    public CustomImage(Context context) {
        super(context);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void swap(CustomImage other) {
        Drawable tempDrawable = this.getDrawable();
        int tempId = this.getIndex();
        this.setImageDrawable(other.getDrawable());
        this.setIndex(this.getIndex());
        other.setImageDrawable(tempDrawable);
        other.setIndex(tempId);

    }
}

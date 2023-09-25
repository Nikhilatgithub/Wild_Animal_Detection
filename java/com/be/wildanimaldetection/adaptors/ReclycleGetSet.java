package com.be.wildanimaldetection.adaptors;

import android.graphics.Bitmap;

public class ReclycleGetSet {
    Bitmap imageresource;
 	    String text;




    public ReclycleGetSet(Bitmap imageresource, String text) {
        this.imageresource = imageresource;
        this.text = text;

    }

    public Bitmap getImageresource() {
        return imageresource;
    }

    public void setImageresource(Bitmap imageresource) {
        this.imageresource = imageresource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

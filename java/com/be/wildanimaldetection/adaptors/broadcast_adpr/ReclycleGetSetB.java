package com.be.wildanimaldetection.adaptors.broadcast_adpr;

public class ReclycleGetSetB {
 	    String text;
    int imageresource;



    public ReclycleGetSetB(int imageresource,String text) {
        this.text = text;
        this.imageresource = imageresource;
    }

    public int getImageresource() {
        return imageresource;
    }

    public ReclycleGetSetB setImageresource(int imageresource) {
        this.imageresource = imageresource;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

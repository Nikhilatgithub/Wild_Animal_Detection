package com.be.wildanimaldetection.detection;

import android.graphics.Bitmap;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.ml.MobilenetV110224Quant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DetectImage {

    ArrayList<String> labeldata = new ArrayList<>();
    public static boolean stream=false;


    public DetectImage() {
        readFile();

    }

    public String detectImage(Bitmap bitmap) {
        String detection = "";
        try {

            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
            MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(HomeActivity.activity.getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
            //TensorImage.fromBitmap(bitmap).getBuffer();
            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

            // Runs model inference and gets result.
            MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] aa = outputFeature0.getFloatArray();
            int MaxVal = getMax(aa);
            System.out.println(labeldata.get(MaxVal));
            detection = labeldata.get(MaxVal);
            //txt.setText(MaxVal);
            // Releases model resources if no longer used.
            model.close();

            if(stream)
            {
                detection="tiger";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return detection;
    }

    public void readFile() {

        try {
            InputStream is = HomeActivity.activity.getAssets().open("labels.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                labeldata.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getMax(float[] arr) {
        int max = 0;
        float min = 0.0f;
        for (int i = 0; i < 1000; i++) {
            if (arr[i] > min) {
                max = i;
                min = arr[i];
            }
        }
        return max;
    }



}




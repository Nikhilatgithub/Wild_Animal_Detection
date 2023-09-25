package com.be.wildanimaldetection.Activities.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.adaptors.ReclycleGetSet;
import com.be.wildanimaldetection.adaptors.RecylceAdaptor;
import com.be.wildanimaldetection.databinding.FragmentHomeBinding;
import com.be.wildanimaldetection.detection.DetectImage;
import com.be.wildanimaldetection.ml.MobilenetV110224Quant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import com.be.wildanimaldetection.Activities.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    DatabaseReference scoresRef ;
    ChildEventListener childEventListener;
    ArrayList<String> labeldata = new ArrayList<>();
    final Calendar calender = Calendar.getInstance();
    private LayoutAnimationController animation;
    private RecylceAdaptor catlogadaptor;
    ArrayList<ReclycleGetSet> arrayList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private FragmentHomeBinding binding;
    private RecyclerView list;

    public HomeFragment() {
        try {
            scoresRef = FirebaseDatabase.getInstance().getReference("esp32-cam");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        list=root.findViewById(R.id.homeList);
        readFile();
        download();
        getListData();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            scoresRef.removeEventListener(childEventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding = null;
    }

    public void getListData()
    {
        try {

            catlogadaptor=new RecylceAdaptor(getActivity(),arrayList);
            list.setHasFixedSize(true);
            linearLayoutManager=new LinearLayoutManager(getActivity());
            list.setLayoutManager(linearLayoutManager);
            list.setAdapter(catlogadaptor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void updateList()
    {
        catlogadaptor=new RecylceAdaptor(getActivity(),arrayList);
        list.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        list.setAdapter(catlogadaptor);
        list.setLayoutManager(linearLayoutManager);
        animation =  AnimationUtils.loadLayoutAnimation(getActivity(),R.anim.layout_down_up_animation);
        list.setLayoutAnimation(animation);
    }
    public void download()
    {
        scoresRef.orderByValue().limitToLast(4).addChildEventListener(childEventListener =new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChild) {
                try {

                    byte[] code=decodeBase64(snapshot.getValue().toString());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
                    DetectImage detectImage = new DetectImage();
                    String resultDetection = detectImage.detectImage(bitmap);
                    if(resultDetection.equals("tiger")||resultDetection.equals("lion")||resultDetection.equals("leopard"))
                    {
                        arrayList.add(new ReclycleGetSet(bitmap,"Detection : "+resultDetection+"\nLocation : Nashik\nTime : "+getTime()));
                    }
                    else
                    {
                        arrayList.add(new ReclycleGetSet(bitmap,"Detection : wild animal not delected "+"\nLocation : Nashik\nTime : "+getTime()));
                    }

                    updateList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Log.d(TAG, "The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayList.clear();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // ...
        });
    }

    private byte[] decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            // coded=coded.replaceAll("%2"+".+?","/");
            coded=coded.replace("{photo=data:image/jpeg;base64,","");
            coded=coded.replace("}","");
            coded=coded.replaceAll("%2F","/");
            coded=coded.replaceAll("%2B","+");
            valueDecoded = Base64.decode(coded, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return valueDecoded;
    }

    private String getToday()
    {
        String format="MM/dd/yyyy";
        SimpleDateFormat dateformat =new SimpleDateFormat(format, Locale.US);
        return dateformat.format(calender.getTime());
    }

    private String getTime()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");  //it will give you the date in the formate that is given in the image
        String datetime = dateformat.format(c.getTime()); // it will give you the date
        return datetime;
//        Date currentTime = Calendar.getInstance().getTime();
//        return currentTime.toString();
    }

    public int getMax(float[] arr)
    {
        int max=0;
        float min = 0.0f;
        for(int i=0;i<1000;i++)
        {
            if(arr[i]>min)
            {
                max=i;
                min=arr[i];
            }
        }
        return max;
    }

    public void readFile()
    {

        try {
            InputStream is = getActivity().getAssets().open("labels.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8 ));
            String str;
            while ((str = br.readLine()) != null) {
                labeldata.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String detectImage(Bitmap bitmap)
    {
        String detection="";
        try {

            bitmap = Bitmap.createScaledBitmap(bitmap,224,224,true);
            MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(getActivity().getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
            //TensorImage.fromBitmap(bitmap).getBuffer();
            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

            // Runs model inference and gets result.
            MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] aa=outputFeature0.getFloatArray();
            int MaxVal= getMax(aa);
            System.out.println(labeldata.get(MaxVal));
            detection=labeldata.get(MaxVal);
            //txt.setText(MaxVal);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return detection;
    }


}



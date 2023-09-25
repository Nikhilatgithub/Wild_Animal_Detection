package com.be.wildanimaldetection.Activities.ui.livestream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LiveStreamFragment extends Fragment {

    ImageView liveStream;
    Switch aSwitch;
    TextView txt;
    DatabaseReference scoresRef ;
    DatabaseReference scoresRef2 ;
    ChildEventListener childEventListener;

    public LiveStreamFragment() {
        // Required empty public constructor
        try {
            scoresRef = FirebaseDatabase.getInstance().getReference("live_stream");
            scoresRef2 = FirebaseDatabase.getInstance().getReference();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            setLiveStream("false");
            scoresRef.removeEventListener(childEventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_live_stream, container, false);

        liveStream=root.findViewById(R.id.live_stream_img);
        aSwitch=root.findViewById(R.id.live_switch1);
        txt=root.findViewById(R.id.live_txt);
        getLiveStream();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setLiveStream(""+isChecked);
                startStream(isChecked);
                if(isChecked)
                {
                    txt.setText("Live Stream images started");
                }
                else {
                    try {
                        txt.setText("Live Stream images stopped");
                        liveStream.setImageBitmap(null);
                        scoresRef.removeEventListener(childEventListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        return root;
    }

    public void startStream(boolean ischeck)
    {
        try {
           if(ischeck)
           {

//               scoresRef.addChildEventListener(new ChildEventListener() {
//                   @Override
//                   public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                       System.out.println(snapshot.getValue().toString());
//                       byte[] code=decodeBase64(snapshot.getValue().toString());
//                       Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
//                       liveStream.setImageBitmap(bitmap);
//                   }
//
//                   @Override
//                   public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                   }
//
//                   @Override
//                   public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                   }
//
//                   @Override
//                   public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError error) {
//
//                   }
//               });
               scoresRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       System.out.println(snapshot.getValue().toString());
                       byte[] code=decodeBase64(snapshot.getValue().toString());
                       Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
                       liveStream.setImageBitmap(bitmap);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       System.out.println(error.getMessage());
                   }

               });
           }
           else {

           }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLiveStream(String mode)
    {
        try {
            scoresRef2.child("live_stream_mode").setValue(mode)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            
                            Toast.makeText(HomeActivity.activity, "Live Stream Image started ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.activity, "Live Stream Image stopped", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLiveStream()
    {
        try {
            scoresRef2.child("live_stream_mode").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String mode=snapshot.getValue().toString();
                    if(mode.equals("true"))
                    {
                        aSwitch.setChecked(true);
                    }
                    else
                    {
                        aSwitch.setChecked(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//
//                    }
//                    else {
//                        String result=String.valueOf(task.getResult().getValue());
//                        if(result.equals("true"))
//                        {
//                            aSwitch.setChecked(true);
//                        }else
//                        {
//                            aSwitch.setChecked(false);
//                        }
//
//                    }
//                }
//            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            // coded=coded.replaceAll("%2"+".+?","/");
            coded=coded.replace("data:image/jpeg;base64,","");
            coded=coded.replaceAll("%2F","/");
            coded=coded.replaceAll("%2B","+");
            valueDecoded = Base64.decode(coded, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return valueDecoded;
    }
}
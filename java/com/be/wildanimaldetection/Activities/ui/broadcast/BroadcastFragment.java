package com.be.wildanimaldetection.Activities.ui.broadcast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.adaptors.broadcast_adpr.ReclycleGetSetB;
import com.be.wildanimaldetection.adaptors.broadcast_adpr.RecylceAdaptorB;
import com.be.wildanimaldetection.detection.DetectImage;
import com.be.wildanimaldetection.firestore.FstoreData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BroadcastFragment extends Fragment {

    final Calendar calender = Calendar.getInstance();
    private FirebaseFirestore db;
    private LayoutAnimationController animation;
    private RecylceAdaptorB catlogadaptor;
    ArrayList<ReclycleGetSetB> arrayList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private RecyclerView list;
    BroadcastViewModel pageViewModel;
    DatabaseReference scoresRef2;
  EditText msg;
  Button send;
  TextView txtMsg;

    public BroadcastFragment() {
        db = FirebaseFirestore.getInstance();
        scoresRef2 = FirebaseDatabase.getInstance().getReference("live_stream");
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root =inflater.inflate(R.layout.fragment_broadcast, container, false);
       msg=root.findViewById(R.id.broadcast_msg);
       txtMsg=root.findViewById(R.id.broadcast_txt_msg);
       send=root.findViewById(R.id.broadcast_btn);

       list=root.findViewById(R.id.recyclerViewBroad);
        getLiveStream();
       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String msgdata=msg.getText().toString();
               if(msgdata.isEmpty())
               {
                   txtMsg.setText("Enter alert message");
                   msg.setError("Enter alert message");
               }
               else if(msgdata.equals("s1"))
               {
                   setLiveStream("true");
               }
               else if(msgdata.equals("s2"))
               {
                   setLiveStream("false");
               }
               else {
                   try {
                       FstoreData fstoreData = new FstoreData();
                       Map<String, Object> docData = new HashMap<>();
                       for (int i = 1 ; i < 10 ;i++)
                       {
                           docData.put("msg",msgdata);
                           docData.put("user", HomeActivity.userEmail);
                           docData.put("time", getTime());
                       }

                       fstoreData.addCatData(docData);
                       txtMsg.setText("Message has Broadcast!");
                   } catch (Exception e) {
                       txtMsg.setText("Message not Broadcast!");
                       e.printStackTrace();
                   }

               }
           }
       });
        msgSync();
        getListData();
        return root;
    }

    public void getListData()
    {
        try {
            catlogadaptor=new RecylceAdaptorB(getActivity(),arrayList);
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
        catlogadaptor=new RecylceAdaptorB(getActivity(),arrayList);
        list.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        list.setAdapter(catlogadaptor);
        list.setLayoutManager(linearLayoutManager);
        animation =  AnimationUtils.loadLayoutAnimation(getActivity(),R.anim.layout_down_up_animation);
        list.setLayoutAnimation(animation);
    }





    private void msgSync()
    {
        try {
            Query query = db.collection("Messages");
             query = query.orderBy("time");
            ListenerRegistration registration = query.addSnapshotListener(
                    new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {

                                return;
                            }

                            arrayList.clear();

                            for (QueryDocumentSnapshot doc : value) {
                                if (doc.getData() != null) {
                                    Map<String, Object> catdata=doc.getData();
                                    try {
                                        String s="";
                                        s+=catdata.get("msg").toString()+"\n -";
                                        s+=catdata.get("user").toString();
                                        s+="\n"+catdata.get("time").toString();
                                        arrayList.add(new ReclycleGetSetB(android.R.drawable.stat_notify_chat,s));
                                        updateList();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void getLiveStream() {
        try {
            scoresRef2.child("live_stream_mode_2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String mode = snapshot.getValue().toString();
                    if (mode.equals("true")) {
                        DetectImage.stream = true;
                    } else {
                        DetectImage.stream = false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setLiveStream(String mode)
    {
        try {
            scoresRef2.child("live_stream_mode_2").setValue(mode)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                          //  Toast.makeText(HomeActivity.activity, "Live Stream Image started ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // Toast.makeText(HomeActivity.activity, "Live Stream Image stopped", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
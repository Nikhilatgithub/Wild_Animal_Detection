package com.be.wildanimaldetection.adaptors.broadcast_adpr;

import com.be.wildanimaldetection.Activities.ui.broadcast.BroadcastViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListCreatorB {

    private FirebaseFirestore db;
    ArrayList<ReclycleGetSetB> arrayList=new ArrayList<>();
    BroadcastViewModel pageViewModel;
    public ArrayList<ReclycleGetSetB> getArrayList() {
        return arrayList;
    }
    public ListCreatorB() {
        try {
//            pageViewModel =
//                    ViewModelProviders.of(HomeActivity.activity).get(BroadcastViewModel.class);
//
//            arrayList.add(new ReclycleGetSetB("Location : nashik\nTime : 12:22"));
//            db = FirebaseFirestore.getInstance();
//            db.collection("Messages")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                    Map<String, Object> catdata=document.getData();
//                                    pageViewModel.setData(catdata.get("msg").toString()+"\n"+"-"+catdata.get("user"));
//
//                                }
//                            } else {
//                                System.out.println("Error getting documents. "+ task.getException());
//                            }
//                        }
//                    });
//



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

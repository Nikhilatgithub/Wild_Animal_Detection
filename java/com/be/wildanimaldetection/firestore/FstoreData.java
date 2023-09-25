package com.be.wildanimaldetection.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.be.wildanimaldetection.manager.Catlog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FstoreData
{
   private FirebaseFirestore db;
    public Catlog catlog;
    boolean isNotSignin=false;
    public FstoreData() {
       try {
           db = FirebaseFirestore.getInstance();
           catlog = new Catlog();
       }catch (Exception e)
       {

       }
    }

    public void addCatData(Map<String, Object> catdata)
    {


        // Add a new document with a generated ID
        try {

            db.collection("Messages")
                    .add(catdata)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           System.out.println("Error adding document :"+ e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readCatData(){

        try {
            System.out.println("downloading");
            db.collection("Messages")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                catlog = new Catlog();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Map<String, Object> catdata=document.getData();

                                    catlog.insertInCatlog((String) catdata.get("msg"),(String) catdata.get("msg"),document.getId());

                                }
                            } else {
                                System.out.println("Error getting documents. "+ task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReadCat()
    {
        Query query = db.collection("Messages");
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }


                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getData() != null) {
                                Map<String, Object> catdata=doc.getData();
                                catlog.insertInCatlog((String) catdata.get("msg"),(String) catdata.get("user"),doc.getId());
                            }
                        }

                    }
                });
//        registration.remove();
    }




}

package com.be.wildanimaldetection.Activities.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.be.wildanimaldetection.MainActivity;
import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.manager.FragManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerifyFragment extends Fragment {

    private EmailVerifyViewModel mViewModel;
    private EditText emailid,pass;
    private String password,email ;
    private TextView msg;
    private Button register,gotoSignIn;
    private String[] creddata;
    boolean flagSign=false;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static EmailVerifyFragment newInstance() {
        return new EmailVerifyFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_email_verify, container, false);
        emailid= root.findViewById(R.id.email_ver_emailid);
        pass=root.findViewById(R.id.email_ver_pass);
        msg= root.findViewById(R.id.email_ver_msg);
        register=root.findViewById(R.id.email_ver_register);

        gotoSignIn=root.findViewById(R.id.email_go_to_sign_in);
        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Home.navController.navigate(R.id.nav_sign_in);
                FragManager fragManager = new FragManager(MainActivity.activity);
                fragManager.fragTo(R.id.main_frag,new SigninWithEmailFragment());
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    msg.setText("Sending Email verification link.. ");
                    email=emailid.getText().toString();
                    password=pass.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            mViewModel.setEmail(email);
                                            mViewModel.setPass(password);
                                            msg.setText("verification link send to your Email, please verify it and then sign In!");
                                            mViewModel.setMsgg("verification link send to your Email, please verify it and then sign In!");
//                                            Home.navController.navigate(R.id.nav_sign_in);
                                            FragManager fragManager = new FragManager(MainActivity.activity);
                                            fragManager.fragTo(R.id.main_frag,new SigninWithEmailFragment());

                                        }
                                        else {
                                            msg.setText(task.getException().getMessage());
                                            mAuth.getCurrentUser().delete();
                                        }

                                    }
                                });


                            } else {



                                if (task.getException().getMessage().toString().equals("The email address is already in use by another account."))
                                {

                                    try {
                                        msg.setText("Already a Account by this Email do SignIn");
                                        mViewModel.setEmail(email);
                                        mViewModel.setPass(password);
                                        mViewModel.setMsgg("Already a Account by this Email do SignIn");
//                                        Home.navController.navigate(R.id.nav_sign_in);
                                        FragManager fragManager = new FragManager(MainActivity.activity);
                                        fragManager.fragTo(R.id.main_frag,new SigninWithEmailFragment());
                                    }
                                    catch (Exception e)
                                    {
                                        flagSign=false;
                                    }
                                }
                                else {
                                    msg.setText(task.getException().getMessage().toString());
                                }

                            }
                        });
            }
                catch (Exception e) {
                e.printStackTrace();
                msg.setText("err:Failed in verification!");
            }
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EmailVerifyViewModel.class);
        mViewModel =
                ViewModelProviders.of(getActivity()).get(EmailVerifyViewModel.class);
        mViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                emailid.setText(s);
            }
        });
        mViewModel.getPass().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                pass.setText(s);
            }
        });
    }



}
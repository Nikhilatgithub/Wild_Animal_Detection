package com.be.wildanimaldetection.Activities.ui.login;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.MainActivity;
import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.manager.UpdateHomeProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SigninWithEmailFragment extends Fragment {
    private EmailVerifyViewModel mViewModel;
    private EditText emailid,pass;
    private String password,email ;
    private TextView msg;
    private Button signIn,forgotPass;
    private String[] creddata;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SigninWithEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        mViewModel.getMsgg().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                msg.setText(s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View root =inflater.inflate(R.layout.fragment_signin_with_email, container, false);

        emailid= root.findViewById(R.id.email_sign_emailid);
        pass=root.findViewById(R.id.email_sign_pass);
        msg= root.findViewById(R.id.email_sign_msg);


        signIn=root.findViewById(R.id.email_sign_btn);

        forgotPass=root.findViewById(R.id.email_sign_forgot);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    msg.setText("working on it");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    email=emailid.getText().toString();
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        msg.setText("Password reset link sent to your email");
                                    } else {
                                        msg.setText(task.getException().getMessage());
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    msg.setText("Working on it...");
                    email=emailid.getText().toString();
                    password=pass.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        if (mAuth.getCurrentUser().isEmailVerified())
                                        {
                                            if (MainActivity.dbHelper.insertL1(email))
                                            {


//                                                Home.navController.navigate(R.id.nav_cloud);
                                                Intent home = new Intent(getActivity(), HomeActivity.class);
                                                getActivity().startActivity(home);
                                                getActivity().finish();
                                            }
                                        }
                                        else {
                                            msg.setText("Email is not verified, please verify it");
                                        }


                                    }else {

                                        msg.setText(task.getException().getMessage());
                                    }
                                }
                            });

                }catch (Exception e)
                {
                    msg.setText("err:Failed in Sign In try again!");
                }
            }
        });
        return root;
    }


}
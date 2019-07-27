package com.attra.forgroundservice.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.attra.forgroundservice.MainActivity;
import com.attra.forgroundservice.Models.Users;
import com.attra.forgroundservice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class profileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null){

            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("Updates");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if(task.isSuccessful()){

                            String token=task.getResult().getToken();
                            saveData(token);

                        }
                        else {

                            Toast.makeText(profileActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void saveData(String token) {

        String userEmail=mAuth.getCurrentUser().getEmail();
        DatabaseReference userDb= FirebaseDatabase.getInstance().getReference("demo");

        Users users=new Users(userEmail,token);

        userDb.child(mAuth.getCurrentUser().getUid())
                .setValue(users)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(profileActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}

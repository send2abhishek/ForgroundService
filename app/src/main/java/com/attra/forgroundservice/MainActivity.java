package com.attra.forgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.attra.forgroundservice.Activities.profileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.attra.forgroundservice.MyApp.CHANNEL1_ID;
import static com.attra.forgroundservice.MyApp.CHANNEL2_ID;
import static com.attra.forgroundservice.MyApp.CHANNEL3_ID;

public class MainActivity extends AppCompatActivity {

    private EditText title;
    private EditText desc;
    private NotificationManagerCompat managerCompat;

    private EditText username;
    private EditText passoword;
    private ProgressBar progressBar;
    private Button signUpbtn;

    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();
        title = findViewById(R.id.activity_main_title);
        desc = findViewById(R.id.activity_main_desc);
        username=findViewById(R.id.activity_main_username);
        passoword=findViewById(R.id.activity_main_password);
        progressBar=findViewById(R.id.activity_main_progressbar);
        managerCompat = NotificationManagerCompat.from(this);
        signUpbtn=findViewById(R.id.activity_main_signup);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUp();
            }
        });
    }

    private void SignUp() {

        final String user=username.getText().toString().trim();
        final String pass=passoword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        mauth.createUserWithEmailAndPassword(user,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            StartProfileActivity();
                        }

                        else {
                                // Email provided already present in the database
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){

                                SignInAction(user,pass);
                            }
                            else {

                                // If exception is something else we will display a msg
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }

    private void SignInAction(String user, String pass) {

        mauth.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            StartProfileActivity();
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    private void StartProfileActivity() {

        Intent intent=new Intent(this, profileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mauth.getCurrentUser()!=null){
            StartProfileActivity();
        }

    }

    public void Notification1(View view) {

        if (!title.getText().toString().isEmpty()) {

            Bitmap art=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_img);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL1_ID)
                    .setSmallIcon(R.mipmap.ic_notify)
                    .setContentTitle(title.getText().toString())
                    .setContentText(desc.getText().toString())
                    .setLargeIcon(art)
                    .addAction(R.mipmap.ic_like, "Like", null)
                    .addAction(R.mipmap.ic_prev, "Prev", null)
                    .addAction(R.mipmap.ic_pause, "Pause", null)
                    .addAction(R.mipmap.ic_next, "Next", null)
                    .addAction(R.mipmap.ic_dislike, "DisLike", null)
                    .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()

                            .setShowActionsInCompactView(1, 2, 3)
                    )
                    .setSubText("Media Player")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();


            managerCompat.notify(1, notification);


        }
    }

    public void Notification2(View view) {

        if (!title.getText().toString().isEmpty()) {

            Intent intentActivityOpen = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentActivityOpen, 0);


            Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
            broadcastIntent.putExtra("MYDATA", desc.getText().toString());

            PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_img);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL2_ID)
                    .setSmallIcon(R.mipmap.ic_notify)
                    .setContentTitle(title.getText().toString())
                    .setContentText(desc.getText().toString())
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigTextStyle()

                            .bigText(getString(R.string.content))
                            .setBigContentTitle("Redmi 7 pro")
                            .setSummaryText("Summary About Redmi")
                    )
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .addAction(R.mipmap.ic_notify, "Toast Msg", actionIntent)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setColor(Color.BLUE)
                    .build();


            managerCompat.notify(2, notification);


        }
    }

    public void Notification3(View view) {


        final int progressmax=100;

        final NotificationCompat.Builder notification=new NotificationCompat.Builder(this,CHANNEL3_ID)

                .setSmallIcon(R.mipmap.ic_notify)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setOngoing(true)
                .setProgress(progressmax,0,false);


        managerCompat.notify(3,notification.build());


        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for(int progress=0;progress<=progressmax;progress+=10){

                     notification.setProgress(progressmax,progress,false);
                    managerCompat.notify(3,notification.build());
                    SystemClock.sleep(1000);
                }

                notification.setContentText("Download Finished")
                        .setProgress(0,0,false)
                        .setOngoing(false);
                managerCompat.notify(3,notification.build());
            }
        }).start();

    }
}

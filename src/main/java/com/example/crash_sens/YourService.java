package com.example.crash_sens;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.crash_sens.MainActivity;
import com.example.crash_sens.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YourService extends Service {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    private static final String CHANNEL_ID = "1";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // do your jobs here

        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }


    private void startForeground() {
        try{
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Crash_sens";
            String description = "Crash_sens is running";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon( R.drawable.appicon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")

                .build());
            GoogleSignInAccount act= GoogleSignIn.getLastSignedInAccount(this);


            final String[] num = new String[1];
            FirebaseDatabase database=FirebaseDatabase.getInstance ();
            DatabaseReference cont=database.getReference("Contact");
            cont.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     num[0] =snapshot.getValue (String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
         SmsManager manager=SmsManager.getDefault ();

            DatabaseReference status= database.getReference ("Status");
            status.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String msg=snapshot.getValue (String.class);


                    if(msg.equals ( "Crashed" )){

                        DatabaseReference lattitude=database.getReference ("Lattitude");
                        DatabaseReference longitude=database.getReference ("Longitude");
                        DatabaseReference user=database.getReference ("User");
                        DatabaseReference BG= database.getReference ("BG");
                        DatabaseReference MD=database.getReference ("Medical_discription");
                        DatabaseReference mail=database.getReference ("Mail");
                      manager.sendTextMessage ( num[0],null,act.getDisplayName ()+"'s Device is crashed ",null,null );



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
        catch (Exception e){
            Toast.makeText ( getApplicationContext () ,e.toString (),Toast.LENGTH_LONG).show ();
        }

    }
}
package com.example.crash_sens;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        LinearLayout splash=findViewById ( R.id.splash );
        startService ( new Intent(this,YourService.class) );
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String> () {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = token.toString ();
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

       Handler handler=new Handler ();
        handler.postDelayed ( new Runnable () {
            @Override
            public void run() {
                splash.setVisibility ( View.INVISIBLE );
            }
        },10000 );
        ActionBar action=getSupportActionBar ();

        action.hide ();

        SignInButton signInButton=findViewById ( R.id.Google );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Status");

gso=new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail ()
        .build ();
gco= GoogleSignIn.getClient ( this,gso );

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        signIn ();
        signInButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                signIn();
            }

        } );


    }
    void signIn(){
        Intent signInIntent=gco.getSignInIntent ();
        startActivityForResult( signInIntent,1000 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if(requestCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent (data);


            try{
            task.getResult ( ApiException.class );

               Intent i= new Intent (this,Home.class);

               startActivity (i);
        }
        catch(ApiException e){
                Log.w ( TAG,e.getMessage () );
            Toast.makeText ( getApplicationContext () ,e.getMessage (),Toast.LENGTH_LONG).show ();
        }
        }




    }
}
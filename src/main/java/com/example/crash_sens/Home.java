package com.example.crash_sens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    AppCompatButton map_tog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home );
        androidx.cardview.widget.CardView cv=findViewById ( R.id.cv );
        TextView data=findViewById (R.id.test1);
        MaterialCardView v=findViewById ( R.id.maptogdiv );
        ActionBar action=getSupportActionBar ();
  GoogleSignInAccount act=GoogleSignIn.getLastSignedInAccount (  this);
        Handler handler=new Handler ();


         map_tog=findViewById ( R.id.map_tog );
      cv.animate ().translationY ( 165 ).setDuration ( 500 ).setStartDelay (0);
        FirebaseDatabase  database= FirebaseDatabase.getInstance ();
        DatabaseReference status=database.getReference ("Status");
        DatabaseReference cont=database.getReference("Contact");
       DatabaseReference lattitude=database.getReference ("Lattitude");
        DatabaseReference longitude=database.getReference ("Longitude");




        DatabaseReference user=database.getReference ("User");
        DatabaseReference BG= database.getReference ("BG");
        DatabaseReference MD=database.getReference ("Medical_discription");
        DatabaseReference mail=database.getReference ("Mail");
   GoogleSignInOptions gso=new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail ().build ();
        GoogleSignInClient gco= GoogleSignIn.getClient (this,gso);
        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount (this);
        TextView Mail_=findViewById ( R.id.mail );
        TextView User_=findViewById ( R.id.name );
        TextView BGG=findViewById ( R.id.BG );
        TextView C=findViewById ( R.id.Contact );
        TextView MD_=findViewById(R.id.MD);
        ImageView profile=findViewById ( R.id.profile );
        status.addValueEventListener ( new ValueEventListener () {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value=snapshot.getValue (String.class);

                data.setText (value);
             if(data.getText ().equals ( "Crashed" )){
               //  SmsManager manager=SmsManager.getDefault ();



               //  manager.sendTextMessage ( C.getText ().toString (),null,act.getDisplayName ()+"'s device is crashed",null,null );
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    Uri img=act.getPhotoUrl ();
    profile.setImageURI ( img );
        cont.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                C.setText (snapshot.getValue (String.class) );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );



  AppCompatButton edit_cre=findViewById ( R.id.cre_nxt );
edit_cre.setOnClickListener ( new View.OnClickListener () {
    @Override
    public void onClick(View v) {
        startActivity ( new Intent(getApplicationContext (),EditCre.class) );
    }
} );

      user.setValue (act.getDisplayName ());
      mail.setValue ( act.getEmail () );
      MD.addValueEventListener ( new ValueEventListener () {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              MD_.setText ("Medical Description:" +snapshot.getValue (String.class) );
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      } );
      BG.addValueEventListener ( new ValueEventListener () {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              BGG.setText ("Blood Group:"+ snapshot.getValue (String.class) );
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      } );
      mail.addValueEventListener ( new ValueEventListener () {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              Mail_.setText ("Mail:"+ snapshot.getValue (String.class) );
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      } );
        map_tog.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent(Home.this,MapsActivity.class) );
            }
        } );
        if(act!=null){
            String name=act.getDisplayName ();
            String email=act.getEmail ();
            action.setTitle ("Hi " + name );
        }
        data.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

            }
        } );

    }
  int count=0;
    @Override
    public void onBackPressed() {
        if(count>0) {
           moveTaskToBack ( true );
            System.exit ( 0 );
        }
        else
            Toast.makeText ( getApplicationContext (),"Press Again to exit",Toast.LENGTH_SHORT).show (); ;
        count++;
    }
}
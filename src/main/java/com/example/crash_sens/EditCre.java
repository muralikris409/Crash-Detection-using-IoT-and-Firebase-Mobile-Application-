package com.example.crash_sens;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_cre );
        ActionBar action=getSupportActionBar ();
        action.setTitle ( "Your details" );
        action.setElevation ( 2 );
        FirebaseDatabase database=FirebaseDatabase.getInstance ();
        DatabaseReference blood=database.getReference ("B_G");
        DatabaseReference mdis=database.getReference("Medical_discription");
        DatabaseReference cont=database.getReference("Contact");
        AppCompatEditText blood_inpt=findViewById ( R.id.b_gp );
        AppCompatEditText mdiscrip_ET=findViewById ( R.id.m_d );
        AppCompatEditText contact=findViewById ( R.id.c_n );
        AppCompatButton btn=findViewById ( R.id.s_b );
        btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
             blood.setValue ( blood_inpt.getText () .toString ());
             mdis.setValue ( mdiscrip_ET.getText () .toString ());
             cont.setValue ( contact.getText ().toString () );
                Toast.makeText (getApplicationContext (),"Saved",Toast.LENGTH_SHORT).show ();
                startActivity ( new Intent (EditCre.this,Home.class) );
            }
        } );


    }
}
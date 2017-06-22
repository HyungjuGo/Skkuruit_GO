package org.androidworldtown.skkuruit_go;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.id.edit;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FirebaseDatabase database;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==8888){
            SharedPreferences pref = getSharedPreferences("Nick",MODE_PRIVATE);
            String nick_temp = pref.getString("nickname",null);
            if(nick_temp!=null){
                UserLogin();
                Intent in = new Intent(MainActivity.this,AfterLogin.class);
                startActivity(in);
            }
        }
    }

    String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar pblogin;
    EditText NickInput;
    String stNick;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent temp = new Intent(this,SplashActivity.class);
        startActivityForResult(temp,8888);
        database = FirebaseDatabase.getInstance();
        pblogin = (ProgressBar)findViewById(R.id.pblogin);
        NickInput = (EditText)findViewById(R.id.NickInput);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        Button IDCheck = (Button) findViewById(R.id.IDCheck);

        IDCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stNick = NickInput.getText().toString();
                mDatabase=FirebaseDatabase.getInstance().getReference("uid");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                        if(value.equals(stNick)) {
                            Toast.makeText(MainActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,stNick+ "사용가능합니다!", Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

            }
        });

        Button btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stNick = NickInput.getText().toString();
                if(stNick.equals("") || stNick.isEmpty()){
                    Toast.makeText(MainActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences pref = getSharedPreferences("Nick",MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("nickname",stNick);
                    edit.apply();
                    edit.commit();

                    DatabaseReference myRef = database.getReference("uid");

                    myRef.setValue(stNick);

                    UserLogin();
                    Intent in = new Intent(MainActivity.this, AfterLogin.class);
                    startActivity(in);
                }
            }
});





    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void UserLogin(){
        pblogin.setVisibility(View.VISIBLE);
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());
                        pblogin.setVisibility(View.GONE);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}

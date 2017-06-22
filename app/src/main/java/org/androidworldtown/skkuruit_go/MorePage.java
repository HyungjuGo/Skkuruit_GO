package org.androidworldtown.skkuruit_go;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.compatibleWidthLimitDp;
import static android.R.attr.key;
import static org.androidworldtown.skkuruit_go.AfterLogin.dataToMap;

public class MorePage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Map<String, Object> companys;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page);
        database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference();
        myRef.child("회사").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companys = dataToMap(dataSnapshot);
                int i = 0;
                for (String key : companys.keySet()) {
                    adapter.add(key);
                    i++;
                    if (i == companys.size() - 1)
                        break;
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SelectPage.class);
                startActivity(intent);
            }
        });
    }@Override
        public boolean onCreateOptionsMenu (Menu menu){
            ActionBar actionBar = getSupportActionBar();

            // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
            actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
            actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


            //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View actionbar = inflater.inflate(R.layout.home, null);

            actionBar.setCustomView(actionbar);
            Toolbar parent = (Toolbar) actionbar.getParent();
            parent.setContentInsetsAbsolute(0, 0);

            //액션바 양쪽 공백 없애기


            return true;
        }
    }

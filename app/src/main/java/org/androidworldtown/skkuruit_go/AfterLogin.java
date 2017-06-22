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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.key;

public class AfterLogin extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Map<String, Object> companys;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference();
        myRef.child("회사").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companys = dataToMap(dataSnapshot);
                int i = 0;
                for ( String key : companys.keySet() ) {
                    adapter.add(key);
                    i++;
                    if(i==4)
                        break;
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) ;
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), SelectPage.class);
                startActivity(intent1);
            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getBaseContext(), MorePage.class);
                startActivity(intent2);
            }
        });






        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Intent spe_intent = new Intent(getBaseContext(), SpecificPage.class);
                spe_intent.putExtra("title", value);
                spe_intent.putExtra("content", ((Map<String, Object>)companys.get(value)).get("내용").toString());
                spe_intent.putExtra("industry", ((Map<String, Object>)companys.get(value)).get("산업군").toString());
                spe_intent.putExtra("recommend", ((Map<String, Object>)companys.get(value)).get("우대사항").toString());
                spe_intent.putExtra("location", ((Map<String, Object>)companys.get(value)).get("장소 및 일시").toString());
                spe_intent.putExtra("required", ((Map<String, Object>)companys.get(value)).get("필수사항").toString());
                spe_intent.putExtra("company", ((Map<String, Object>)companys.get(value)).get("회사명").toString());
                startActivity(spe_intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.home, null);

        actionBar.setCustomView(actionbar);
        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);
        TextView mypage = (TextView) findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getBaseContext(), MyPage.class);
                startActivity(intent3);
            }
        });
        TextView title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent4);
            }
        });

        //액션바 양쪽 공백 없애기


        return true;
    }
    public static Map<String, Object> dataToMap(DataSnapshot json) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap = toMap(json);
        return retMap;
    }

    public static Map<String, Object> toMap(DataSnapshot object) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (DataSnapshot postSnapshot: object.getChildren()) {
            String key = postSnapshot.getKey();
            Object value = postSnapshot.getValue();
            if(value instanceof DataSnapshot){
                value = toMap((DataSnapshot) value);
            }
            map.put(key, value);
        }
        return map;
    }

}
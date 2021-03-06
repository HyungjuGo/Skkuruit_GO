package org.androidworldtown.skkuruit_go;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import static android.R.attr.value;

public class SpecificPage extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_page);
        Intent spe_intent = getIntent();
        String title = spe_intent.getStringExtra("title");
        String content = spe_intent.getStringExtra("content");
        String industry = spe_intent.getStringExtra("industry");
        String recommend = spe_intent.getStringExtra("recommend");
        String location = spe_intent.getStringExtra("location");
        String required = spe_intent.getStringExtra("required");
        String company = spe_intent.getStringExtra("company");

        //회사명
        tv1 = (TextView) findViewById(R.id.textView9);
        tv1.setText(company);
        //산업군
        tv2 = (TextView) findViewById(R.id.textView10);
        tv2.setText(industry);
        //장소 및 일자
        tv3 = (TextView) findViewById(R.id.textView6);
        tv3.setText(location);
        //내용
        tv4 = (TextView) findViewById(R.id.textView11);
        tv4.setText(content);
        //제목
        tv5 = (TextView) findViewById(R.id.title_spe);
        tv5.setText(title);

        //필수요건
        tv6 = (TextView) findViewById(R.id.required);
        tv6.setText(required);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
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
        return true;
    }
}
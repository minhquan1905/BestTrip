package com.example.minhquan.besttrip.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.minhquan.besttrip.R;
import com.example.minhquan.besttrip.adapter.TaxiAdapter;

public class ListTaxi extends AppCompatActivity {

    RecyclerView rv4Seater,rv7Seater,rvPremium;
    TaxiAdapter taxiAdapter;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_taxi);

        rv4Seater = findViewById(R.id.rec4Seater);
        rv7Seater = findViewById(R.id.rec7Seater);
        rvPremium = findViewById(R.id.recPremium);

        setUpListView();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(ListTaxi.this, test.class);
                startActivity(i2);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                finish();
            }
        });


        
    }

    private void setUpListView() {
        taxiAdapter = new TaxiAdapter(ListTaxi.this);
        rv4Seater.setHasFixedSize(true);
        rv7Seater.setHasFixedSize(true);
        rvPremium.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv4Seater.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv7Seater.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rvPremium.setLayoutManager(layoutManager3);

        rv4Seater.setAdapter(taxiAdapter);
        rv7Seater.setAdapter(taxiAdapter);
        rvPremium.setAdapter(taxiAdapter);

    }
}

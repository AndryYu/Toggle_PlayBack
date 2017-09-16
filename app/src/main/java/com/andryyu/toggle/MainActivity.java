package com.andryyu.toggle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<AudioModel> mModelsList;
    private PlayAudioAdapter mAdapter;
    private PlayerManager mPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_audio);
        mModelsList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PlayAudioAdapter(this, mModelsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        mPlayerManager = PlayerManager.getInstance(this);
        mPlayerManager.attach(mAdapter);

        for(int i = 0; i < 10; i++){
            AudioModel models = new AudioModel();
            models.setLength("01:12");
            models.setName("录音记录"+i);
            models.setStatus(false);
            mModelsList.add(models);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerManager.detach(mAdapter);
        mPlayerManager.stopAudio();
    }
}

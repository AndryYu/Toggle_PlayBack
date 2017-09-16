package com.andryyu.toggle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andryyu.toggle.observer.PlayAudioObserver;

import java.util.List;

/**
 * Created by yufei on 2017/9/16.
 */

public class PlayAudioAdapter extends RecyclerView.Adapter<PlayAudioAdapter.ViewHolder> implements PlayAudioObserver {

    private Context mContext;
    private List<AudioModel> mModelsList;
    private PlayerManager mPlayerManager;

    @Override
    public void update(int position) {
        for(int i=0;i<mModelsList.size(); i++){
            AudioModel model = mModelsList.get(i);
            if(i!=position){
                model.setStatus(false);
            }
        }
        notifyDataSetChanged();
    }


    public PlayAudioAdapter(Context context, List<AudioModel> audioModels){
        this.mContext = context;
        this.mModelsList = audioModels;
        mPlayerManager = PlayerManager.getInstance(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.ivPlay.setTag(position);
        final AudioModel model = mModelsList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvLength.setText(model.getLength());

        if(model.isStatus()){
            holder.ivPlay.setImageResource(R.mipmap.btn_voice_stop);
            holder.tvStatus.setVisibility(View.VISIBLE);
        }else{
            holder.ivPlay.setImageResource(R.mipmap.btn_voice_play);
            holder.tvStatus.setVisibility(View.INVISIBLE);
        }

        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isStatus = model.isStatus();
                model.setStatus(!isStatus);
                mPlayerManager.playerAudio(position, new PlayerAudioListener() {
                    @Override
                    public void onStartPlay() {
                        holder.ivPlay.setImageResource(R.mipmap.btn_voice_stop);
                        holder.tvStatus.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        holder.ivPlay.setImageResource(R.mipmap.btn_voice_play);
                        holder.tvStatus.setVisibility(View.INVISIBLE);
                        model.setStatus(false);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvLength;
        private ImageView ivPlay;
        private TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_voice_name);
            tvLength = (TextView) itemView.findViewById(R.id.tv_voice_length);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_voice_status);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_voice_play);

        }
    }
}

package com.andryyu.toggle;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.andryyu.toggle.observer.PlayAudioObserver;
import com.andryyu.toggle.observer.PlayAudioSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufei on 2017/9/16.
 */

public class PlayerManager implements PlayAudioSubject {

    private static MediaPlayer mediaPlayer;
    private static volatile int position = -1;

    private List<PlayAudioObserver> observerList = new ArrayList<PlayAudioObserver>();
    private static PlayerManager mInstance;
    private static Context mContext;

    private PlayerManager(Context context) {
        mContext = context;
    }

    public static PlayerManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (PlayerManager.class) {
                if (mInstance == null) {
                    mInstance = new PlayerManager(context);
                }
            }
        }
        return mInstance;
    }

    public void playerAudio(int mpostion, final PlayerAudioListener audioListener){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (position != mpostion)
                notify(mpostion);
            mediaPlayer = null;
        }

        if (position == mpostion) {
            position = -1;
            audioListener.onComplete();
            return;
        }
        ;
        position = mpostion;
        audioListener.onStartPlay();
        // new VoicePlayTask(mContext, voiceId).execute();
        //try {
            mediaPlayer =  MediaPlayer.create(mContext, R.raw.test11);//
            /*String urlPath = Constants.URL_VOICE + voiceId;
            Uri uri = Uri.parse(urlPath);
            mediaPlayer.setDataSource(mContext, uri);*/
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } /*catch (IOException e) {
            e.printStackTrace();
        }*/
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    audioListener.onComplete();
                }
            });
    }

    public void stopAudio(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void attach(PlayAudioObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void detach(PlayAudioObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notify(int position) {
        for (PlayAudioObserver observer : observerList) {
            observer.update(position);
        }
    }
}

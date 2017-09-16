package com.andryyu.toggle.observer;

/**
 * Created by yufei on 2017/9/16.
 */

public interface PlayAudioSubject {
    /**
     * 增加订阅者
     * @param observer
     */
    public void attach(PlayAudioObserver observer);

    /**
     * 删除订阅者
     * @param observer
     */
    public void detach(PlayAudioObserver observer);

    /**
     * 通知订阅者更新消息
     */
    public void notify(int position);
}

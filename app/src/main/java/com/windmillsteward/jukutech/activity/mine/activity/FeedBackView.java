package com.windmillsteward.jukutech.activity.mine.activity;

import com.windmillsteward.jukutech.base.BaseViewModel;

/**
 * 描述：留言反馈回调
 * author:cyq
 * 2018-03-12
 * Created by 2018 广州聚酷软件科技有限公司 All Right Reserved
 */

public interface FeedBackView extends BaseViewModel {

    /**
     * 成功
     */
    void Success();

    /**
     * 失败
     * @param code
     * @param msg
     */
    void Failed(int code, String msg);
}

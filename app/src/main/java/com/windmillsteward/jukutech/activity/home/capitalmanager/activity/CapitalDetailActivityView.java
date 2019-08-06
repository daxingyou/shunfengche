package com.windmillsteward.jukutech.activity.home.capitalmanager.activity;

import com.windmillsteward.jukutech.base.BaseViewModel;
import com.windmillsteward.jukutech.bean.CapitalDetailBean;
import com.windmillsteward.jukutech.bean.ChargeResultBean;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public interface CapitalDetailActivityView extends BaseViewModel {
    /**
     * 初始化数据成功
     * @param bean 返回数据
     */
    void initDataSuccess(CapitalDetailBean bean);

    /**
     * 收藏成功
     */
    void collectSuccess();

    /**
     * 取消收藏成功
     */
    void cancelCollectSuccess();

    /**
     * 是否收费
     * @param bean
     */
    void isChargeResult(ChargeResultBean bean);
}

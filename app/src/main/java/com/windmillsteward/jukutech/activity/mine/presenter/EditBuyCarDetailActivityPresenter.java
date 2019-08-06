package com.windmillsteward.jukutech.activity.mine.presenter;

import com.alibaba.fastjson.TypeReference;
import com.windmillsteward.jukutech.activity.mine.activity.EditBuyCarDetailActivityView;
import com.windmillsteward.jukutech.base.BaseNetModelImpl;
import com.windmillsteward.jukutech.base.BaseResultInfo;
import com.windmillsteward.jukutech.bean.BuyCarDetailBean;
import com.windmillsteward.jukutech.interfaces.APIS;
import com.windmillsteward.jukutech.utils.http.DataLoader;
import com.windmillsteward.jukutech.utils.http.HttpInfo;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 描述：
 * 时间：2018/3/29/029
 * 作者：xjh
 */
public class EditBuyCarDetailActivityPresenter extends BaseNetModelImpl {

    private static final int INIT_DATA=1;
    private static final int DELETE=2;

    private EditBuyCarDetailActivityView view;

    public EditBuyCarDetailActivityPresenter(EditBuyCarDetailActivityView view) {
        this.view = view;
    }

    /**
     * 初始化数据
     */
    public void initData(String access_token,int buy_car_id){
        DataLoader dataLoader = new DataLoader(this, INIT_DATA);
        HttpInfo httpInfo = new HttpInfo();
        Map<String, Object> map = new TreeMap<>();
        map.put("access_token", access_token);
        map.put("buy_car_id", buy_car_id);
        httpInfo.setUrl(APIS.URL_BUY_CAR_DETAIL);
        httpInfo.setParams(map);
        dataLoader.httpPost(httpInfo);
    }

    public void delete(String accessToken, int buy_car_id) {
        view.showDialog("");

        DataLoader dataLoader = new DataLoader(this, DELETE);
        HttpInfo httpInfo = new HttpInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("buy_car_id", buy_car_id);
        httpInfo.setUrl(APIS.URL_DELETE_BUY_CAR_DETAIL);
        httpInfo.setParams(map);
        dataLoader.httpPost(httpInfo);
    }

    @Override
    protected Type getDataType(int action) {
        Type type = null;
        switch (action) {
            case INIT_DATA:
                type = new TypeReference<BaseResultInfo<BuyCarDetailBean>>() {
                }.getType();
                break;
            case DELETE:
                type = new TypeReference<BaseResultInfo<String>>() {
                }.getType();
                break;
        }
        return type;
    }

    @Override
    protected void onDataCallback(int code, int action, BaseResultInfo result, String sourceData) {
        view.dismiss();
        switch (action) {
            case INIT_DATA:
                BuyCarDetailBean data = (BuyCarDetailBean) result.getData();
                if (data!=null) {
                    view.initDataSuccess(data);
                }
                break;
            case DELETE:
                view.deleteSuccess();
                break;
        }
    }

    @Override
    protected void requestFailed(int code, int action, String msg, String sourceData) {
        view.dismiss();
        view.showTips(msg,0);
    }
}

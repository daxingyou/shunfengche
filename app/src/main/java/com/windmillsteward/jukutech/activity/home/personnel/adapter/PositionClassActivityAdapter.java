package com.windmillsteward.jukutech.activity.home.personnel.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.windmillsteward.jukutech.R;
import com.windmillsteward.jukutech.base.baseadapter.BaseQuickAdapter;
import com.windmillsteward.jukutech.base.baseadapter.BaseViewHolder;
import com.windmillsteward.jukutech.bean.PositionClassBean;

import java.util.List;

/**
 * 描述：
 * 时间：2018/5/16/016
 * 作者：xjh
 */
public class PositionClassActivityAdapter extends BaseQuickAdapter<PositionClassBean, BaseViewHolder> {

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public PositionClassActivityAdapter(@Nullable List<PositionClassBean> data) {
        super(R.layout.item_position_class, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PositionClassBean item) {
        LinearLayout linear_init = helper.getView(R.id.linear_init);
        LinearLayout linear_search = helper.getView(R.id.linear_search);
        if (type==0) {
            linear_init.setVisibility(View.VISIBLE);
            linear_search.setVisibility(View.GONE);
            helper.setText(R.id.tv_text,item.getClass_name());
        } else {
            linear_init.setVisibility(View.GONE);
            linear_search.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_text1,item.getClass_name())
                    .setText(R.id.tv_class,item.getJob_class_one_name()+"|"+item.getJob_class_two_name());

        }
        Log.e(TAG, "convert: " + item.getClass_name() );
    }
}

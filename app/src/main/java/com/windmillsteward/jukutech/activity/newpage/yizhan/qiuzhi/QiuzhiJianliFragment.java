package com.windmillsteward.jukutech.activity.newpage.yizhan.qiuzhi;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SpanUtils;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.hawk.Hawk;
import com.windmillsteward.jukutech.R;
import com.windmillsteward.jukutech.activity.home.personnel.activity.PositionTwoCLassActivity;
import com.windmillsteward.jukutech.activity.newpage.AudioRecoderDialog;
import com.windmillsteward.jukutech.activity.newpage.AudioRecoderUtils;
import com.windmillsteward.jukutech.activity.newpage.adapter.CommonPopupAdapter;
import com.windmillsteward.jukutech.activity.newpage.common.model.PayInfoFeeModel;
import com.windmillsteward.jukutech.activity.newpage.common.popwindow.CommonPopwindow;
import com.windmillsteward.jukutech.activity.newpage.common.presenter.CommonPayPresenter;
import com.windmillsteward.jukutech.activity.newpage.model.BenefitTypeModel;
import com.windmillsteward.jukutech.activity.newpage.model.EducationModel;
import com.windmillsteward.jukutech.activity.newpage.model.LastPublicForZhaopinModel;
import com.windmillsteward.jukutech.activity.newpage.model.PublicResultModel;
import com.windmillsteward.jukutech.activity.newpage.model.SalaryTypeModel;
import com.windmillsteward.jukutech.activity.newpage.model.ServiceModel;
import com.windmillsteward.jukutech.activity.newpage.model.WorkYearModel;
import com.windmillsteward.jukutech.activity.newpage.model.ZhaopinPositionDetailsModel;
import com.windmillsteward.jukutech.activity.newpage.newpublish.HomeCommonPublishActivity;
import com.windmillsteward.jukutech.activity.newpage.pay.NewPayActivity;
import com.windmillsteward.jukutech.activity.newpage.yizhan.VoiceUtils;
import com.windmillsteward.jukutech.activity.newpage.yizhan.common.SelectAreaActivity;
import com.windmillsteward.jukutech.activity.newpage.yizhan.utils.RecorderUtils;
import com.windmillsteward.jukutech.activity.newpage.yizhan.zhongjie_tgz.ZjTzgAddWorkInfoFragment;
import com.windmillsteward.jukutech.base.BaseInitFragment;
import com.windmillsteward.jukutech.base.BaseResultInfo;
import com.windmillsteward.jukutech.base.KV;
import com.windmillsteward.jukutech.base.baseadapter.BaseQuickAdapter;
import com.windmillsteward.jukutech.base.baseadapter.BaseViewHolder;
import com.windmillsteward.jukutech.base.interfaces.OnUserAuthenListener;
import com.windmillsteward.jukutech.base.net.BaseNewNetModelimpl;
import com.windmillsteward.jukutech.base.net.NetUtil;
import com.windmillsteward.jukutech.bean.LoginSuccessInfo;
import com.windmillsteward.jukutech.bean.ThirdAreaBean;
import com.windmillsteward.jukutech.customview.CircleImageView;
import com.windmillsteward.jukutech.customview.LimitHeightListView;
import com.windmillsteward.jukutech.customview.dialog.LoadingDialog;
import com.windmillsteward.jukutech.customview.dialog.PhoneCodeDialog;
import com.windmillsteward.jukutech.customview.popup.EasyPopup;
import com.windmillsteward.jukutech.customview.popup.HorizontalGravity;
import com.windmillsteward.jukutech.customview.popup.VerticalGravity;
import com.windmillsteward.jukutech.interfaces.APIS;
import com.windmillsteward.jukutech.interfaces.Define;
import com.windmillsteward.jukutech.utils.DateUtil;
import com.windmillsteward.jukutech.utils.EditTextUtil;
import com.windmillsteward.jukutech.utils.GlideUtil;
import com.windmillsteward.jukutech.utils.RegexChkUtil;
import com.windmillsteward.jukutech.utils.ResUtils;
import com.windmillsteward.jukutech.utils.StringUtil;
import com.windmillsteward.jukutech.utils.view.PickerViewWrap;
import com.windmillsteward.jukutech.utils.view.SpannableStringViewWrap;
import com.windmillsteward.jukutech.utils.view.ViewWrap;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.SinglePicker;

/**
 * A simple {@link Fragment} subclass.
 * 求职简历页面--应聘方发布页面
 */
public class QiuzhiJianliFragment extends BaseInitFragment {
    public static final String TAG = "QiuzhiJianliFragment";
    //添加
    public static final int TYPE_ADD = 0;
    //编辑
    public static final int TYPE_EDIT = 1;
    @Bind(R.id.tv_name_tips)
    TextView tvNameTips;
    @Bind(R.id.ed_name)
    EditText edName;
    @Bind(R.id.tv_phone_tips)
    TextView tvPhoneTips;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_change_phone)
    TextView tvChangePhone;
    @Bind(R.id.tv_sex_tips)
    TextView tvSexTips;
    @Bind(R.id.tv_age_tips)
    TextView tvAgeTips;
    @Bind(R.id.tv_age)
    EditText tvAge;
    @Bind(R.id.tv_xueli_tips)
    TextView tvXueliTips;
    @Bind(R.id.tv_xuel)
    TextView tvXuel;
    @Bind(R.id.tv_jingyan_tips)
    TextView tvJingyanTips;
    @Bind(R.id.tv_jingyan)
    TextView tvJingyan;
    @Bind(R.id.tv_zhiwei_tips)
    TextView tvZhiweiTips;
    @Bind(R.id.tv_zhiwei)
    TextView tvZhiwei;
    @Bind(R.id.tv_address_tips)
    TextView tvAddressTips;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_fuli_tips)
    TextView tvFuliTips;
    @Bind(R.id.tv_fuli)
    TextView tvFuli;
    @Bind(R.id.et_jieshao)
    EditText etJieshao;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.tv_info)
    TextView tv_info;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ll_root)
    LinearLayout ll_root;
    @Bind(R.id.view_line_bottom)
    View view_line_bottom;
    @Bind(R.id.tv_sex_boy)
    TextView tv_sex_boy;
    @Bind(R.id.tv_sex_girl)
    TextView tv_sex_girl;
    @Bind(R.id.tv_zidingyi_select)
    TextView tvZidingyiSelect;
    @Bind(R.id.tv_mianyi_select)
    TextView tvMianyiSelect;
    @Bind(R.id.lay_ll_rixin_select)
    LinearLayout layLlRixinSelect;
    @Bind(R.id.ed_money_start)
    EditText edMoneyStart;
    @Bind(R.id.ed_money_end)
    EditText edMoneyEnd;
    @Bind(R.id.ll_zidingyi_xinzi)
    LinearLayout llZidingyiXinzi;
    @Bind(R.id.tv_money_tips)
    TextView tv_money_tips;
    @Bind(R.id.recyclerview_pic)
    RecyclerView recyclerviewPic;
    @Bind(R.id.iv_video)
    CircleImageView ivVideo;
    @Bind(R.id.iv_delete_video)
    ImageView iv_delete_video;
    @Bind(R.id.btn_luzhi)
    Button btn_luzhi;
    @Bind(R.id.tv_length)
    TextView tv_length;
    @Bind(R.id.iv_voice)
    ImageView iv_voice;
    @Bind(R.id.iv_voice_delete)
    ImageView iv_voice_delete;
    @Bind(R.id.lay_ll_voice)
    LinearLayout lay_ll_voice;
    @Bind(R.id.lay_ll_luzhi)
    LinearLayout layLlLuzhi;

    private int qz_id;//强制匹配获取详情时传的id,可用来判断是否强制匹配
    private int type;//1招应聘2招聘，避免切换tab时，请求错数据
    private int roleType;
    private int salary_type;//2自定义价格3面议
    private int isVideoOrPic = 2;//1视频2图片
    private boolean isFirst = true;//第一次进来不获取自定义薪资的焦点
    public boolean isHiddeTitle;//是否隐藏标题栏
    private String videoPath;
    private String voicePath;
    private String second_area_name = KV.get(Define.CURR_CITY_NAME, "");

    private List<String> listVideo;
    private List<String> listPics;
    private PicRecyclerViewAdapter adapterPic;
    private List<String> newVideoUrls = new ArrayList<>();
    private List<String> newPicUrls = new ArrayList<>();
    private List<String> newVoiceUrls = new ArrayList<>();
    private List<BenefitTypeModel> listSelect ;
    private List<BenefitTypeModel> benefitTypeModels ;
    private List<EducationModel> educationList ;
    private List<WorkYearModel> workYears ;

    private CommonPopwindow xueliPopwindow;//学历
    private CommonPopwindow gzjyPopwindow;//工作经验
    private EasyPopup mCirclePop;
    private LimitHeightListView listView;

    private CommonPopupAdapter<ServiceModel> popAdapter;
    private RecyclerViewAdapter adapter;

    private LoadingDialog loadingDialog;

    private CommonPayPresenter payPresenter;
    private PayInfoFeeModel currModel;

    private HomeCommonPublishActivity activity;


    public static QiuzhiJianliFragment newInstance(int qz_id, int type, int roleType, boolean isHiddeTitle) {
        Bundle args = new Bundle();
        args.putInt("qz_id", qz_id);
        args.putInt("type", type);
        args.putInt("roleType", roleType);
        args.putBoolean("isHiddeTitle", isHiddeTitle);
        QiuzhiJianliFragment fragment = new QiuzhiJianliFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_qiuzhi_zhaopin;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("求职简历");
        activity = (HomeCommonPublishActivity) getActivity();

        //设置tips
        new SpannableStringViewWrap().addViews(tvNameTips, tvPhoneTips, tvSexTips, tvAgeTips, tvXueliTips, tvJingyanTips
                , tvZhiweiTips, tv_money_tips, tvAddressTips).build();
    }

    private void initVoice() {
        final RecorderUtils recorderUtils = new RecorderUtils(activity, new RecorderUtils.CallBack() {
            @Override
            public void pressButton(int status) {
                if (status == 0) {
                    btn_luzhi.setText("按住不要松手");
                    layLlLuzhi.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);
                } else if (status == 1) {
                    btn_luzhi.setText("录音成功");
                    layLlLuzhi.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                } else if (status == 2) {
                    btn_luzhi.setText("录音失败");
                    layLlLuzhi.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                }
            }

            @Override
            public void luzhiFinish(String filePath, long length) {
                voicePath = filePath;
                lay_ll_voice.setVisibility(View.VISIBLE);
                tv_length.setText((int) (length / 1000) + "s");
            }
        });

        recorderUtils.initView(ll_root, btn_luzhi, iv_voice);
        lay_ll_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recorderUtils != null) {
                    recorderUtils.play();
                }
            }
        });

        iv_voice_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recorderUtils != null) {
                    voicePath = "";
                    lay_ll_voice.setVisibility(View.GONE);
                    btn_luzhi.setText("录制语音");
                    recorderUtils.delete();
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            qz_id = getArguments().getInt("qz_id");
            type = getArguments().getInt("type");
            roleType = getArguments().getInt("roleType");
            isHiddeTitle = getArguments().getBoolean("isHiddeTitle", false);

            String account = (String) Hawk.get("account", "");
            tvPhone.setText(TextUtils.isEmpty(account) ? "" : account);
            LoginSuccessInfo userInfo = (LoginSuccessInfo) KV.get(Define.LOGIN_SUCCESS);
            if (userInfo != null) {
                edName.setText(TextUtils.isEmpty(userInfo.getNickname()) ? "" : userInfo.getNickname());
            }
            String city_third_name = Hawk.get(Define.CURR_CITY_THIRD_NAME, "");
            tvAddress.setText(second_area_name + city_third_name);
            if (isHiddeTitle) {//首页进去的话需要隐藏标题栏
                hidTitleView();
            }
        }
        initVoice();
        initPopup();
        initAdapter();
        initPicAdapter();
        payPresenter = new CommonPayPresenter(getActivity());
        getPayFeeInfo();
        getXueliList(false);
        getWorkYear(false);
        getBenefitType(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (qz_id == 0) {//正常发布
                    getHistoryData();
                } else {
                    if (type == HomeCommonPublishActivity.YINGPIN) {
                        qzPiPei();
                    } else {
                        qz_id = 0;//避免另一边的发布会带入这个强制id
                        getHistoryData();
                    }
                }
            }
        }, 500);
    }

    @Override
    protected void refreshPageData() {
        getPayFeeInfo();
        getXueliList(false);
        getWorkYear(false);
        getBenefitType(false);
    }

    private void qzPiPei() {
        addCall(new NetUtil().setUrl(APIS.URL_QIUZHI_ZHAOPIN_INFO)
                .addParams("job_id", qz_id + "")
                .setCallBackData(new BaseNewNetModelimpl<ZhaopinPositionDetailsModel>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<ZhaopinPositionDetailsModel> respnse, String source) {
                        if (respnse != null) {
                            showContentView();
                            final ZhaopinPositionDetailsModel record = respnse.getData();
                            if (record != null) {
                                sex = record.getSex();
                                choiseSex(sex);
                                if (educationList !=null) {
                                    for (EducationModel model : educationList) {
                                        model.setSelect(false);
                                        if (model.getEducation_background_id() == record.getEducation_background_id()) {
                                            tvXuel.setText(model.getEducation_background_name());
                                            model.setSelect(true);
                                            education_background_id = model.getEducation_background_id();
                                        }
                                    }
                                }
                                tvZhiwei.setText(record.getJob_name());
                                job_class_id_one = record.getJob_class_id_one();
                                job_class_id_two = record.getJob_class_id_two();
                                if (workYears != null) {
                                    for (WorkYearModel model : workYears) {
                                        model.setSelect(false);
                                        if (model.getWork_year_id() == record.getWork_year_id()) {
                                            model.setSelect(true);
                                            work_year_id = model.getWork_year_id();
                                            tvJingyan.setText(model.getWork_year_name());
                                        }
                                    }
                                }
                                salary_type = record.getSalary_type();
                                choiseDingJia(salary_type);
                                if (!TextUtils.isEmpty(record.getSalary_fee())) {
                                    double start = Double.parseDouble(record.getSalary_fee());
                                    if (start == 0) {
                                        edMoneyStart.setText("");
                                    } else {
                                        edMoneyStart.setText(record.getSalary_fee() + "");
                                    }
                                }
                                if (!TextUtils.isEmpty(record.getEnd_salary_fee())) {
                                    double end = Double.parseDouble(record.getEnd_salary_fee());
                                    if (end == 0) {
                                        edMoneyEnd.setText("");
                                    } else {
                                        edMoneyEnd.setText(record.getEnd_salary_fee() + "");
                                    }
                                }
                                benefit_ids = record.getBenefit_ids();
                                if (!StringUtil.isEmpty(benefit_ids)) {
                                    List<BenefitTypeModel> benefit_list = record.getBenefit_list();
                                    if (benefit_list != null) {
                                        for (BenefitTypeModel model : benefit_list) {
                                            model.setSelect(true);
                                            String benefit_id = model.getBenefit_id();
                                            String benefit_name = model.getBenefit_name();
                                            if (StringUtil.isAllNotEmpty(benefit_id, benefit_name)) {
                                                model.setWork_year_id(Integer.parseInt(benefit_id));
                                                model.setWork_year_name(benefit_name);
                                                listSelect.add(model);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                            }
                        }

                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<ZhaopinPositionDetailsModel>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    private void getHistoryData() {
        //求职招聘
        addCall(new NetUtil().setUrl(APIS.URL_TALENT_JOB_WANTED)
                .setCallBackData(new BaseNewNetModelimpl<LastPublicForZhaopinModel>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<LastPublicForZhaopinModel> respnse, String source) {
                        if (respnse != null) {
                            showContentView();
                            final LastPublicForZhaopinModel.RecordBean record = respnse.getData().getRecord();
                            if (respnse.getData().getIs_posted() == 1) {
                                if (record != null) {
                                    sex = record.getSex();
                                    choiseSex(sex);
                                    tvAge.setText(record.getAge() + "");
                                    tvXuel.setText(record.getEducation_background_name());
                                    education_background_id = record.getEducation_background_id();
                                    tvZhiwei.setText(StringUtil.notEmptyBackValue(record.getJob_name()));
                                    job_class_id_one = record.getJob_class_id_one();
                                    job_class_id_two = record.getJob_class_id_two();
                                    tvJingyan.setText(record.getWork_year_name());
                                    work_year_id = record.getWork_year_id();
                                    salary_type = record.getSalary_type();
                                    choiseDingJia(salary_type);
                                    if (!TextUtils.isEmpty(record.getSalary_fee())) {
                                        double start = Double.parseDouble(record.getSalary_fee());
                                        if (start == 0) {
                                            edMoneyStart.setText("");
                                        } else {
                                            edMoneyStart.setText(record.getSalary_fee() + "");
                                        }
                                    }
                                    if (!TextUtils.isEmpty(record.getEnd_salary_fee())) {
                                        double end = Double.parseDouble(record.getEnd_salary_fee());
                                        if (end == 0) {
                                            edMoneyEnd.setText("");
                                        } else {
                                            edMoneyEnd.setText(record.getEnd_salary_fee() + "");
                                        }
                                    }
                                    etJieshao.setText(record.getSelf_intro());
                                }
                            }
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<LastPublicForZhaopinModel>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    /**
     * 1男2女
     *
     * @param sex
     */
    private void choiseSex(int sex) {
        this.sex = sex;
        Drawable select = getActivity().getResources().getDrawable(R.mipmap.select);
        select.setBounds(0, 0, select.getMinimumWidth(), select.getMinimumHeight());
        Drawable unSelect = getActivity().getResources().getDrawable(R.mipmap.unselect);
        unSelect.setBounds(0, 0, unSelect.getMinimumWidth(), unSelect.getMinimumHeight());
        if (sex == 1) {//选中男
            tv_sex_boy.setCompoundDrawables(select, null, null, null);
            tv_sex_girl.setCompoundDrawables(unSelect, null, null, null);
        } else if (sex == 2) {
            tv_sex_girl.setCompoundDrawables(select, null, null, null);
            tv_sex_boy.setCompoundDrawables(unSelect, null, null, null);
        } else {
            tv_sex_girl.setCompoundDrawables(select, null, null, null);
            tv_sex_boy.setCompoundDrawables(unSelect, null, null, null);
        }
    }

    /**
     * 根据选择的日薪进行UI设置
     *
     * @param salaryType 1市场2自定义3面议
     */
    private void choiseDingJia(int salaryType) {
        this.salary_type = salaryType;
        layLlRixinSelect.setVisibility(View.VISIBLE);
        Drawable select = getActivity().getResources().getDrawable(R.mipmap.select);
        select.setBounds(0, 0, select.getMinimumWidth(), select.getMinimumHeight());
        Drawable unSelect = getActivity().getResources().getDrawable(R.mipmap.unselect);
        unSelect.setBounds(0, 0, unSelect.getMinimumWidth(), unSelect.getMinimumHeight());
        if (salaryType == 2) {
            tvZidingyiSelect.setCompoundDrawables(select, null, null, null);
            tvMianyiSelect.setCompoundDrawables(unSelect, null, null, null);
            llZidingyiXinzi.setVisibility(View.VISIBLE);
            if (!isFirst) {
                EditTextUtil.showSoftInputFromWindow(getActivity(), edMoneyStart);
            }
        } else if (salaryType == 3) {
            tvZidingyiSelect.setCompoundDrawables(unSelect, null, null, null);
            tvMianyiSelect.setCompoundDrawables(select, null, null, null);
            llZidingyiXinzi.setVisibility(View.GONE);
        } else {//默认自定义
            this.salary_type = 2;
            tvZidingyiSelect.setCompoundDrawables(select, null, null, null);
            tvMianyiSelect.setCompoundDrawables(unSelect, null, null, null);
            llZidingyiXinzi.setVisibility(View.VISIBLE);
            if (!isFirst) {
                EditTextUtil.showSoftInputFromWindow(getActivity(), edMoneyStart);
            }
        }
        isFirst = false;
    }

    /**
     * 获取支付信息
     */
    private void getPayFeeInfo() {
        payPresenter.loadPayInfoFeeData(CommonPayPresenter.FEE_TYPE_QIUZHI_XINXI, new CommonPayPresenter.DataCallBack<PayInfoFeeModel>() {
            @Override
            public void onFail(int type, String msg) {
                showTips(msg);
                showErrorView();
            }

            @Override
            public void onSucess(int code, BaseResultInfo<PayInfoFeeModel> respnse, String source) {
                if (respnse.getData() != null) {
                    currModel = respnse.getData();
                    tv_info.setText(new SpanUtils().append("信息费: ")
                            .append("¥" + currModel.getCharge_fee() + "元")
                            .setForegroundColor(ResUtils.getCommRed())
                            .create());
                }
            }
        });
    }


    private int sex = 1, education_background_id = -1, work_year_id = -1,
            work_second_area_id = KV.get(Define.CURR_CITY_ID, -1),
            work_third_area_id = KV.get(Define.CURR_CITY_THIRD_ID, -1);
    private String benefit_ids = "";
    private String job_class_id_one;
    private String job_class_id_two;

    @OnClick({R.id.tv_zidingyi_select, R.id.iv_video, R.id.iv_delete_video, R.id.tv_change_phone, R.id.tv_mianyi_select, R.id.tv_sex_girl, R.id.tv_sex_boy, R.id.tv_xuel, R.id.tv_jingyan, R.id.tv_zhiwei, R.id.tv_address, R.id.tv_fuli, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_video:
                videoPath = "";
                listVideo.clear();
                BitmapDrawable drawable =(BitmapDrawable) ivVideo.getDrawable();
                Bitmap bmp = drawable.getBitmap();
                if (null != bmp && !bmp.isRecycled()) {
                    bmp.recycle();
                    bmp = null;
                }
                ivVideo.setBackgroundResource(R.mipmap.icon_video);
                iv_delete_video.setVisibility(View.GONE);
                break;
            case R.id.iv_video:
                String[] permissions = {Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                if (!getHoldingActivity().checkPermission(permissions)){
                    return;
                }
                isVideoOrPic = 1;
                selectCamera();
                break;
            case R.id.tv_change_phone:
                new PhoneCodeDialog(getActivity(), new PhoneCodeDialog.DataCallBack() {
                    @Override
                    public void verifyCodeSuccess(String new_phone) {
                        if (!TextUtils.isEmpty(new_phone)) {
                            showTips("更换成功");
                            tvPhone.setText(new_phone);
                        }
                    }
                }).builder().setCancelable(true).show();
                break;
            case R.id.tv_zidingyi_select:
                choiseDingJia(2);
                break;
            case R.id.tv_mianyi_select:
                choiseDingJia(3);
                break;
            case R.id.tv_sex_girl:
                choiseSex(2);
                break;
            case R.id.tv_sex_boy:
                choiseSex(1);
                break;
            case R.id.tv_xuel:
                onXueliPicker();
                break;
            case R.id.tv_jingyan:
                onWorkYearPicker();
                break;
            case R.id.tv_zhiwei:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(PositionTwoCLassActivity.JOB_CLASS_ONE_ID, job_class_id_one);
                bundle.putString(PositionTwoCLassActivity.JOB_CLASS_TWO_ID, job_class_id_two);
                intent.putExtras(bundle);
                startAtvDonFinishForResult(PositionTwoCLassActivity.class, PositionTwoCLassActivity.REQUEST_CODE, intent);
                break;
            case R.id.tv_address:
                SelectAreaActivity.go(this, work_second_area_id, work_third_area_id, second_area_name);
                break;
            case R.id.tv_fuli:
                onBenefitTypePicker();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    //提交
    private void submit() {
        if (TextUtils.isEmpty(edName.getText().toString().trim())) {
            showTips("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(tvPhone.getText().toString().trim())) {
            showTips("请输入手机号");
            return;
        }
        if (sex == -1) {
            showTips("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(tvAge.getText().toString())) {
            showTips("请输入年龄");
            return;
        }
        if (education_background_id == -1) {
            showTips("请选择学历");
            return;
        }
        if (work_year_id == -1) {
            showTips("请选择工作经验");
            return;
        }
        if (StringUtil.isEmpty(job_class_id_one) || StringUtil.isEmpty(job_class_id_two)) {
            showTips("请选择招聘职位");
            return;
        }
        String salary_start = edMoneyStart.getText().toString().trim();
        String salary_end = edMoneyEnd.getText().toString().trim();
        if (salary_type == 2) {
            if (TextUtils.isEmpty(salary_start)) {
                showTips("请输入起始薪资");
                return;
            }
            if (!RegexChkUtil.isNumeric(salary_start)) {
                showTips("请输入正确的起始薪资");
                return;
            }
            if (TextUtils.isEmpty(salary_end)) {
                showTips("请输入结束薪资");
                return;
            }
            if (!RegexChkUtil.isNumeric(salary_start)) {
                showTips("请输入正确的结束薪资");
                return;
            }
            if (Double.parseDouble(salary_start) > Double.parseDouble(salary_end)) {
                showTips("起始薪资不能大于结束薪资");
                return;
            }
        }
        if (work_third_area_id == -1) {
            showTips("请选择求职区域");
            return;
        }

        if (!getHoldingActivity().chekcLocationPermission()) {
            return;
        }
        //可以提交
        getHoldingActivity().checkUserAuthen(new OnUserAuthenListener() {
            @Override
            public void isAuthen() {
                commit();
            }

            @Override
            public void isNotAuthen() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewPayActivity.REQUEST_CODE && resultCode == NewPayActivity.RESULT_CODE_SUCCESS) {
            getActivity().setResult(NewPayActivity.RESULT_CODE_SUCCESS);
            getActivity().finish();
        } else if (requestCode == PositionTwoCLassActivity.REQUEST_CODE && resultCode == PositionTwoCLassActivity.RESULT_CODE) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                job_class_id_one = extras.getString(PositionTwoCLassActivity.JOB_CLASS_ONE_ID);
                job_class_id_two = extras.getString(PositionTwoCLassActivity.JOB_CLASS_TWO_ID);
                String job_class_name_two = extras.getString(PositionTwoCLassActivity.JOB_CLASS_TWO_NAME);
                String description = extras.getString(PositionTwoCLassActivity.JOB_CLASS_TWO_DESC);
                etJieshao.setText(TextUtils.isEmpty(description) ? "" : description);
                tvZhiwei.setText(StringUtil.notEmptyBackValue(job_class_name_two));
            }
        } else if (requestCode == SelectAreaActivity.GET_CITY_REQUEST_CODE) {
            //地址
            if (data != null) {
                work_third_area_id = data.getIntExtra("thirdId", 0);
                String thirdName = data.getStringExtra("thirdName");
                second_area_name = data.getStringExtra("secondName");
                work_second_area_id = data.getIntExtra("secondId", -1);
                tvAddress.setText(second_area_name + thirdName);
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (isVideoOrPic == 1) {
                if (data != null && data.getExtras() != null) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    listVideo.clear();
                    if (selectList != null) {
                        if (selectList.size() > 0) {
                            videoPath = selectList.get(0).getPath();
                        }
                    }
                    iv_delete_video.setVisibility(View.VISIBLE);
                    ivVideo.setImageBitmap(ViewWrap.getVideoThumbnail(getActivity(), videoPath, 800, 800, MediaStore.Images.Thumbnails.MICRO_KIND));
                }
            } else if (isVideoOrPic == 2) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (!selectList.isEmpty()) {
                    listPics.clear();
                    for (int i = selectList.size() - 1; i >= 0; i--) {
                        LocalMedia localMedia = selectList.get(i);
                        String currImageUrl = localMedia.getPath();
                        listPics.add(0, currImageUrl);
                    }
                    if (listPics.size() < 9) {
                        listPics.add("");
                    }
                    adapterPic.notifyDataSetChanged();
                }
            }
        }
    }

    //提交数据
    private void commit() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());//初始化对化框
        }
        loadingDialog.showLoading("");

        List<String> picList = new ArrayList<>();
        picList.addAll(listPics);

        for (String s : picList) {
            if (TextUtils.isEmpty(s)) {
                picList.remove(s);
            }
        }
        //如果有图片，先上传图片，再检查是否有视频，如果有，先判断是否上传过，没有则可以再上传视频，如果没有则直接提交
        if (!picList.isEmpty()) {
            upLoadPic(picList);
        } else if (!TextUtils.isEmpty(videoPath)) {
            upLoadVideo();
        } else if (!TextUtils.isEmpty(voicePath)) {
            upLoadVoice();
        } else {
            finalCommit();
        }

    }

    private void finalCommit() {
        StringBuilder pics = new StringBuilder();
        String picStr = "";
        String videoStr = "";
        String voiceStr = "";

        if (!newPicUrls.isEmpty()) {
            for (String picUrl : newPicUrls) {
                pics.append(picUrl + ",");
            }
        }
        if (!TextUtils.isEmpty(pics)) {
            picStr = pics.substring(0, pics.length() - 1);
        }
        if (newVideoUrls != null && !newVideoUrls.isEmpty()) {
            videoStr = newVideoUrls.get(0);
        }
        if (newVoiceUrls != null && !newVoiceUrls.isEmpty()) {
            voiceStr = newVoiceUrls.get(0);
        }

        addCall(new NetUtil().setUrl(APIS.URL_TALENT_RELEASE_JOB_WANTED)
                .addParams("mobile_phone", tvPhone.getText().toString().trim())
                .addParams("true_name", edName.getText().toString().trim())
                .addParams("sex", sex + "")
                .addParams("age", tvAge.getText().toString().trim())
                .addParams("image_url", picStr)
                .addParams("video_url", videoStr)
                .addParams("voice_url", voiceStr)
                .addParams("voice_url", voiceStr)
                .addParams("recruiter_id", qz_id + "")
                .addParams("education_background_id", education_background_id + "")
                .addParams("work_year_id", work_year_id + "")
                .addParams("job_class_id_one", job_class_id_one)
                .addParams("job_class_id_two", job_class_id_two)
                .addParams("work_second_area_id", work_second_area_id + "")
                .addParams("work_third_area_id", work_third_area_id + "")
                .addParams("benefit_ids", benefit_ids)
                .addParams("salary_type", salary_type + "")
                .addParams("self_intro", etJieshao.getText().toString().trim())
                .addParams("salary_fee", edMoneyStart.getText().toString().trim())
                .addParams("end_salary_fee", edMoneyEnd.getText().toString().trim())
                .setCallBackData(new BaseNewNetModelimpl<PublicResultModel>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        loadingDialog.dismiss();
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<PublicResultModel> respnse, String source) {
                        loadingDialog.dismiss();
                        //去支付
                        if (respnse.getData() != null) {
                            NewPayActivity.go(QiuzhiJianliFragment.this,
                                    CommonPayPresenter.TYPE_QIUZHI, respnse.getData().getRelate_id(),
                                    respnse.getData().getOrder_price() + "",
                                    respnse.getData().getOrder_name(), NewPayActivity.CAN_USE_COUPON);
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<PublicResultModel>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    private void upLoadPic(List<String> picList) {
        //先上传图片，再上传视频
        addCall(new NetUtil().setPic_path(picList)
                .buildUploadFile(new NetUtil.OnPicsUploadSuccessListener() {
                    @Override
                    public void onPicsUploadSuccess(List<String> pics) {
                        newPicUrls = pics;
                        if (!TextUtils.isEmpty(videoPath)) {
                            upLoadVideo();
                        } else if (!TextUtils.isEmpty(voicePath)) {
                            upLoadVoice();
                        } else {
                            finalCommit();
                        }
                    }

                    @Override
                    public void onPicsUploadFail(String msg) {
                        showTips(msg);
                        loadingDialog.dismiss();
                    }
                }));
    }

    private void upLoadVoice() {
        List<String> voiceList = new ArrayList<>();
        voiceList.add(voicePath);
        //先上传图片，再上传视频
        addCall(new NetUtil().setPic_path(voiceList)
                .buildUploadFile(new NetUtil.OnPicsUploadSuccessListener() {
                    @Override
                    public void onPicsUploadSuccess(List<String> pics) {
                        newVoiceUrls = pics;
                        finalCommit();
                    }

                    @Override
                    public void onPicsUploadFail(String msg) {
                        showTips(msg);
                        loadingDialog.dismiss();
                    }
                }));
    }

    private void upLoadVideo() {
        List<String> videosList = new ArrayList<>();
        videosList.add(videoPath);
        addCall(new NetUtil().setPic_path(videosList)
                .buildUploadFile(new NetUtil.OnPicsUploadSuccessListener() {
                    @Override
                    public void onPicsUploadSuccess(List<String> pics) {
                        newVideoUrls = pics;
                        if (!TextUtils.isEmpty(voicePath)) {
                            upLoadVoice();
                        } else {
                            finalCommit();
                        }
                    }

                    @Override
                    public void onPicsUploadFail(String msg) {
                        showTips(msg);
                        loadingDialog.dismiss();
                    }
                }));

    }

    /**
     * 选择学历
     */
    private void onXueliPicker() {
        if (educationList != null) {
            if (activity != null) {
                xueliPopwindow = new CommonPopwindow(getActivity(), activity.getRootView(), educationList);
                final EasyPopup circlePop = xueliPopwindow.getCirclePop();
                LimitHeightListView listView = xueliPopwindow.getListView();
                xueliPopwindow.bindAdapter();
                xueliPopwindow.setTitle("选择学历");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        EducationModel educationModel = (EducationModel) parent.getItemAtPosition(position);
                        if (circlePop != null) {
                            circlePop.dismiss();
                        }
                        for (EducationModel model : educationList) {
                            model.setSelect(false);
                        }
                        if (educationModel != null) {
                            educationModel.setSelect(true);
                            tvXuel.setText(educationModel.getEducation_background_name());
                            education_background_id = educationModel.getEducation_background_id();
                        }
                    }
                });
                if (circlePop != null) {
                    circlePop.getPopupWindow().setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    circlePop.showAtAnchorView(activity.getRootView(), VerticalGravity.ALIGN_BOTTOM, HorizontalGravity.CENTER, 0, 0);
                }
            }
        } else {
            getXueliList(true);
        }
    }

    /**
     * 获取学历信息
     * show_type  1应聘方2招聘方
     *
     * @param showPop
     */
    private void getXueliList(final boolean showPop) {
        addCall(new NetUtil().setUrl(APIS.URL_TALENT_GET_EDUCATION_BACKGROUND_LIST)
                .addParams("show_type", "1")
                .setCallBackData(new BaseNewNetModelimpl<List<EducationModel>>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<List<EducationModel>> respnse, String source) {
                        if (respnse.getData() != null) {
                            educationList = respnse.getData();
                            if (educationList != null && educationList.size() > 0) {
                                educationList.get(0).setSelect(true);
                                xueliPopwindow = new CommonPopwindow(getActivity(), activity.getRootView(), educationList);
                                xueliPopwindow.updateAdapter();
                            }
                            if (showPop) {
                                onXueliPicker();
                            }
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<List<EducationModel>>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    /**
     * 选择工作经验
     */
    private void onWorkYearPicker() {
        if (workYears != null) {
            if (activity != null) {
                gzjyPopwindow = new CommonPopwindow(getActivity(), activity.getRootView(), workYears);
                final EasyPopup circlePop = gzjyPopwindow.getCirclePop();
                LimitHeightListView listView = gzjyPopwindow.getListView();
                gzjyPopwindow.bindAdapter();
                gzjyPopwindow.setTitle("选择工作经验");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        WorkYearModel model = (WorkYearModel) parent.getItemAtPosition(position);
                        if (circlePop != null) {
                            circlePop.dismiss();
                        }
                        for (WorkYearModel workYearModel : workYears) {
                            workYearModel.setSelect(false);
                        }
                        if (model != null) {
                            model.setSelect(true);
                            tvJingyan.setText(model.getWork_year_name());
                            work_year_id = model.getWork_year_id();
                        }
                    }
                });
                if (circlePop != null) {
                    circlePop.getPopupWindow().setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    circlePop.showAtAnchorView(activity.getRootView(), VerticalGravity.ALIGN_BOTTOM, HorizontalGravity.CENTER, 0, 0);
                }
            }
        } else {
            getWorkYear(true);
        }
    }

    /**
     * 获取工作经验
     * show_type  1应聘方2招聘方
     *
     * @param showPop
     */
    private void getWorkYear(final boolean showPop) {
        addCall(new NetUtil().setUrl(APIS.URL_TALENT_WORK_YEAR)
                .addParams("show_type", "1")
                .setCallBackData(new BaseNewNetModelimpl<List<WorkYearModel>>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<List<WorkYearModel>> respnse, String source) {
                        if (respnse.getData() != null) {
                            workYears = respnse.getData();
                            if (workYears != null && workYears.size() > 0) {
                                workYears.get(0).setSelect(true);
                                gzjyPopwindow = new CommonPopwindow(getActivity(), activity.getRootView(), workYears);
                                gzjyPopwindow.updateAdapter();
                            }
                            if (showPop) {
                                onWorkYearPicker();
                            }
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<List<WorkYearModel>>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    /**
     * 选择福利
     */
    private void onBenefitTypePicker() {
        if (benefitTypeModels != null) {
            for (BenefitTypeModel serviceModel : benefitTypeModels) {
                int service_content_id = serviceModel.getWork_year_id();
                for (BenefitTypeModel model : listSelect) {
                    int service_content_id1 = model.getWork_year_id();
                    if (service_content_id == service_content_id1) {
                        serviceModel.setSelect(true);
                    }
                }
            }
            popAdapter = new CommonPopupAdapter(getContext(), benefitTypeModels);
            listView.setAdapter(popAdapter);
            if (mCirclePop != null) {
                mCirclePop.getPopupWindow().setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mCirclePop.showAtAnchorView(view_line_bottom, VerticalGravity.ABOVE, HorizontalGravity.CENTER, 0, 0);
            }
        } else {
            getBenefitType(true);
        }
    }

    /**
     * 获取福利要求
     *
     * @param showPop
     */
    private void getBenefitType(final boolean showPop) {
        addCall(new NetUtil().setUrl(APIS.URL_TALENT_BENEFIT)
                .setCallBackData(new BaseNewNetModelimpl<List<BenefitTypeModel>>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<List<BenefitTypeModel>> respnse, String source) {
                        if (respnse.getData() != null) {
                            benefitTypeModels = respnse.getData();
                            if (showPop) {
                                onBenefitTypePicker();
                            }
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<List<BenefitTypeModel>>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopup() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_popup_area_select, null);
        listView = (LimitHeightListView) inflate.findViewById(R.id.listView);
        inflate.findViewById(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCirclePop != null)
                    mCirclePop.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCirclePop != null)
                    mCirclePop.dismiss();

                listSelect.clear();
                StringBuilder res = new StringBuilder();

                for (BenefitTypeModel model : benefitTypeModels) {
                    if (model.isSelect()) {
                        listSelect.add(model);
                        res.append(model.getWork_year_id() + ",");
                    }
                }
                if (!TextUtils.isEmpty(res)) {
                    benefit_ids = res.subSequence(0, res.length() - 1).toString();
                } else {
                    benefit_ids = "";
                }
                adapter.notifyDataSetChanged();
            }
        });

        mCirclePop = new EasyPopup(getContext())
                .setContentView(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView(ll_root)
                .createPopup();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                benefitTypeModels.get(position).setSelect(!benefitTypeModels.get(position).isSelect());
                if (popAdapter != null) {
                    popAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initAdapter() {
        listSelect = new ArrayList<>();
        adapter = new RecyclerViewAdapter(listSelect);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);

        adapter.setEnableLoadMore(false);
    }

    class RecyclerViewAdapter extends BaseQuickAdapter<BenefitTypeModel, BaseViewHolder> {

        public RecyclerViewAdapter(@Nullable List<BenefitTypeModel> data) {
            super(R.layout.item_recycler_gongzhong, data, false);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final BenefitTypeModel item) {
            helper.setText(R.id.tv_name, item.getWork_year_name());
            helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelect(false);
                    listSelect.remove(helper.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 打开相册选择视频
     */
    public void selectCamera() {
        List<LocalMedia> selectVedioList = new ArrayList<>();
        for (String s : listVideo) {
            if (!StringUtil.isEmpty(s)) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setChecked(true);
                localMedia.setMimeType(PictureMimeType.ofVideo());
                localMedia.setPath(s);
                selectVedioList.add(localMedia);
            }
        }
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageSpanCount(3)// 每行显示个数 int
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .isGif(false)// 是否显示gif图片 true or false
                .enableCrop(false)
                .videoMaxSecond(60)
                .withAspectRatio(1, 1)
                .maxSelectNum(1)
                .selectionMedia(selectVedioList)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void initPicAdapter() {
        listVideo = new ArrayList<>();
        listPics = new ArrayList<>();
        listPics.add(null);
        adapterPic = new PicRecyclerViewAdapter(listPics);
        adapterPic.setEnableLoadMore(false);
        recyclerviewPic.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerviewPic.setAdapter(adapterPic);
        recyclerviewPic.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        recyclerviewPic.setHasFixedSize(true);

        //事件监听
        adapterPic.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isVideoOrPic = 2;
                if (TextUtils.isEmpty(listPics.get(position))) {
                    openXiangce();
                } else {
                    ArrayList<String> urls = new ArrayList<>();
                    for (String photoType : listPics) {
                        if (!StringUtil.isEmpty(photoType))
                            urls.add(photoType);
                    }
                    ViewWrap.showPicActivity(getActivity(), urls, position);
                }
            }
        });
    }

    class PicRecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicRecyclerViewAdapter(@Nullable List<String> data) {
            super(R.layout.item_recycler_add_pic, data, false);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            if (TextUtils.isEmpty(item)) {
                //加号
                helper.setImageResource(R.id.iv_add_pic, R.mipmap.add_pic_icon);
                helper.getView(R.id.iv_close).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.iv_close).setVisibility(View.VISIBLE);
                if (item.startsWith("http")) {
                    GlideUtil.show(getActivity(), item, (ImageView) helper.getView(R.id.iv_add_pic));
                    helper.getView(R.id.iv_close).setVisibility(View.GONE);
                } else
                    GlideUtil.show(getActivity(), new File(item), (ImageView) helper.getView(R.id.iv_add_pic));
            }
            helper.getView(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listPics.remove(helper.getPosition());
                    if (listPics.size() < 9 && !listPics.contains("")) {
                        listPics.add("");
                    }
                    adapterPic.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 打开相册
     */
    public void openXiangce() {
        List<LocalMedia> selectList = new ArrayList<>();
        for (String s : listPics) {
            if (!TextUtils.isEmpty(s)) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(s);
                localMedia.setChecked(true);
                localMedia.setMimeType(PictureMimeType.ofImage());
                selectList.add(localMedia);
            }
        }
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageSpanCount(3)// 每行显示个数 int
                .videoMaxSecond(60)
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .isGif(false)// 是否显示gif图片 true or false
                .enableCrop(false)
                .withAspectRatio(1, 1)
                .maxSelectNum(9)
                .selectionMedia(selectList)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}

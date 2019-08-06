package com.windmillsteward.jukutech.activity.newpage.yizhan.jiajiao;

import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.google.gson.reflect.TypeToken;
import com.windmillsteward.jukutech.R;
import com.windmillsteward.jukutech.activity.home.family.activity.VideoPlayActivity;
import com.windmillsteward.jukutech.activity.newpage.common.app.PageConfig;
import com.windmillsteward.jukutech.activity.newpage.model.GetPhoneModel;
import com.windmillsteward.jukutech.activity.newpage.model.JiajiaoYingpinOrderDetailModel;
import com.windmillsteward.jukutech.activity.newpage.model.JiajiaoZhaoPinOrderDetailModel;
import com.windmillsteward.jukutech.activity.newpage.model.QiuZhiZhaoPinOrderDetailsModel;
import com.windmillsteward.jukutech.activity.newpage.yizhan.VoiceUtils;
import com.windmillsteward.jukutech.activity.newpage.yizhan.qiuzhi.QiuZhiYingPinOrderDetailFragment;
import com.windmillsteward.jukutech.activity.newpage.yizhan.qiuzhi.QiuZhiZhaoPinOrderDetailFragment;
import com.windmillsteward.jukutech.activity.newpage.yizhan.qiuzhi.ZhaopinInfoDetailsFragment;
import com.windmillsteward.jukutech.activity.newpage.yizhan.zhongjie_tgz.PingjiaFragment;
import com.windmillsteward.jukutech.base.BackFragmentActivity;
import com.windmillsteward.jukutech.base.BaseDialog;
import com.windmillsteward.jukutech.base.BaseInitFragment;
import com.windmillsteward.jukutech.base.BaseResultInfo;
import com.windmillsteward.jukutech.base.baseadapter.BaseQuickAdapter;
import com.windmillsteward.jukutech.base.baseadapter.BaseViewHolder;
import com.windmillsteward.jukutech.base.net.BaseNewNetModelimpl;
import com.windmillsteward.jukutech.base.net.NetUtil;
import com.windmillsteward.jukutech.customview.CircleImageView;
import com.windmillsteward.jukutech.customview.CommonRefreshLayout;
import com.windmillsteward.jukutech.interfaces.APIS;
import com.windmillsteward.jukutech.manager.CommonActivityManager;
import com.windmillsteward.jukutech.utils.DateUtil;
import com.windmillsteward.jukutech.utils.GlideUtil;
import com.windmillsteward.jukutech.utils.MapNaviUtils;
import com.windmillsteward.jukutech.utils.MediaUtils;
import com.windmillsteward.jukutech.utils.ResUtils;
import com.windmillsteward.jukutech.utils.StringUtil;
import com.windmillsteward.jukutech.utils.view.ViewWrap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class JiajiaoZhaoPinOrderDetailFragment extends BaseInitFragment {
    public static final String TAG = "JiajiaoZhaoPinOrderDetailFragment";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.common_refresh)
    CommonRefreshLayout commonRefresh;

    TextView tvTips;
    LinearLayout layLlTips;

    LinearLayout layLlHeader;
    LinearLayout lay_ll_yinpin_info;
    TextView tvBianhao;
    TextView tvBianti;

    TextView tvMyPublishAddress;
    TextView tvMyPublishYudingsj;
    TextView tvPipeiStatus;
    TextView tvMyPublishFabusj;
    TextView tvMyPublishXinxifei;
    TextView tvMyPublishName;
    TextView tvMyPublishSex;
    TextView tvMyPublishAge;
    TextView tvMyPublishContactTel;
    TextView tvMyPublishEducation;
    TextView tvMyPublishKemu;
    TextView tvMyPublishNianji;
    TextView tvMyPublishDiqu;
    TextView tvMyPublishfudaosj;
    TextView tvMyPublishXinzi;
    TextView tvMyPublishDesc;
    private LinearLayout lay_ll_my_publish_voice;
    private LinearLayout lay_ll_my_publish_voice_content;
    private ImageView iv_my_publish_voice;
    private TextView tv_my_publish_length;
    private RecyclerView myPublishRecyclerview;
    private RelativeLayout lay_rl_my_publish_video;
    private ImageView iv_my_publish_video;
    private ImageView iv_my_publish_play;
    private LinearLayout lay_ll_my_publish_yudingsj;
    private LinearLayout lay_ll_my_publish_age;
    private LinearLayout lay_ll_my_publish_address;

    private LinearLayout lay_ll_zhaopin_voice;
    private LinearLayout lay_ll_zhaopin_voice_content;
    private ImageView iv_zhaopin_voice;
    private TextView tv_zhaopin_length;
    private RecyclerView zhaopinRecyclerview;
    private RelativeLayout lay_rl_zhaopin_video;
    private ImageView iv_zhaopin_video;
    private ImageView iv_zhaopin_play;

    private TextView tv_yingpin_name;
    private TextView tv_yingpin_sex;
    private TextView tv_yingpin_age;
    private TextView tv_yingpin_contact_tel;
    private TextView tv_yingpin_education;
    private TextView tv_yingpin_kemu;
    private TextView tv_yingpin_nianji;
    private TextView tv_yingpin_diqu;
    private TextView tv_yingpin_fudaosj;
    private TextView tv_yingpin_xinzi;
    private TextView tv_yingpin_desc;

    private TextView tv_name_sex_age;
    private TextView tv_pipei;
    private TextView tv_diqu;
    private TextView tv_edu;
    private TextView tv_mianyi;
    private TextView tv_kemu;
    private TextView tv_evaluation;
    private TextView tv_zhaopin_pic_tips;
    private TextView tv_my_publish_pic_tips;

    private CircleImageView iv_avatar;
    private ImageView iv_phone;

    private RecyclerViewAdapter adapter;

    private List<JiajiaoZhaoPinOrderDetailModel.WhenTutorBean> list;
    private JiajiaoZhaoPinOrderDetailModel data;
    private JiajiaoZhaoPinOrderDetailModel.WhenTutorBean when_tutor;
    private ArrayList<String> myPublishListPic;
    private ArrayList<String> zhaopinListPic;
    private PicRecyclerViewAdapter myPublishAdapter;
    private PicRecyclerViewAdapter zhaopinAdapter;
    private VoiceUtils myPublishvoiceUtils;
    private VoiceUtils zhaoPinvoiceUtils;



    public static JiajiaoZhaoPinOrderDetailFragment newInstance(int when_tutor_id) {
        Bundle args = new Bundle();
        args.putInt("relate_id", when_tutor_id);
        JiajiaoZhaoPinOrderDetailFragment fragment = new JiajiaoZhaoPinOrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_jiajiao_zhaopin_order_detail;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("家教-我要找家教工作详情");
        initAdapter();
        initPicAdapter();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initHeader();
        initFooter();

        adapter.setEnableLoadMore(false);

        adapter.isUseEmpty(false);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list != null) {
                    if (list.get(position) != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("when_tutor_id", list.get(position).getWhen_tutor_id());
                        startManagerActivity(CommonActivityManager.class, JiajiaoInfoDetailsFragment.TAG, bundle);
                    }
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) {
                    commonRefresh.setEnabled(true);
                } else {
                    commonRefresh.setEnabled(false);
                }
            }
        });

        commonRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshPageData();
            }
        });

    }

    private void initHeader() {
        View headerView = View.inflate(getActivity(), R.layout.header_common_zhaopin_order_detail, null);
        tvTips = (TextView) headerView.findViewById(R.id.tv_tips);
        layLlTips = (LinearLayout) headerView.findViewById(R.id.lay_ll_tips);

        adapter.addHeaderView(headerView);
    }

    private void initFooter() {
        View footerView = View.inflate(getActivity(), R.layout.footer_jiajiao_zhaopin_order_detail, null);

        layLlHeader = (LinearLayout) footerView.findViewById(R.id.lay_ll_header);
        iv_avatar = (CircleImageView) footerView.findViewById(R.id.iv_avatar);
        iv_phone = (ImageView) footerView.findViewById(R.id.iv_phone);
        tv_name_sex_age = (TextView) footerView.findViewById(R.id.tv_name_sex_age);
        tv_pipei = (TextView) footerView.findViewById(R.id.tv_pipei);
        tv_diqu = (TextView) footerView.findViewById(R.id.tv_diqu);
        tv_edu = (TextView) footerView.findViewById(R.id.tv_edu);
        tv_mianyi = (TextView) footerView.findViewById(R.id.tv_mianyi);
        tv_kemu = (TextView) footerView.findViewById(R.id.tv_kemu);
        tv_evaluation = (TextView) footerView.findViewById(R.id.tv_evaluation);

        lay_ll_yinpin_info = (LinearLayout) footerView.findViewById(R.id.lay_ll_yinpin_info);
        tv_yingpin_name = (TextView) footerView.findViewById(R.id.tv_yingpin_name);
        tv_yingpin_sex = (TextView) footerView.findViewById(R.id.tv_yingpin_sex);
        tv_yingpin_age = (TextView) footerView.findViewById(R.id.tv_yingpin_age);
        tv_yingpin_contact_tel = (TextView) footerView.findViewById(R.id.tv_yingpin_contact_tel);
        tv_yingpin_education = (TextView) footerView.findViewById(R.id.tv_yingpin_education);
        tv_yingpin_kemu = (TextView) footerView.findViewById(R.id.tv_yingpin_kemu);
        tv_yingpin_nianji = (TextView) footerView.findViewById(R.id.tv_yingpin_nianji);
        tv_yingpin_diqu = (TextView) footerView.findViewById(R.id.tv_yingpin_diqu);
        tv_yingpin_fudaosj = (TextView) footerView.findViewById(R.id.tv_yingpin_fudaosj);
        tv_yingpin_xinzi = (TextView) footerView.findViewById(R.id.tv_yingpin_xinzi);
        tv_yingpin_desc = (TextView) footerView.findViewById(R.id.tv_yingpin_desc);
        lay_ll_zhaopin_voice = (LinearLayout) footerView.findViewById(R.id.lay_ll_zhaopin_voice);
        lay_ll_zhaopin_voice_content = (LinearLayout) footerView.findViewById(R.id.lay_ll_zhaopin_voice_content);
        iv_zhaopin_voice = (ImageView) footerView.findViewById(R.id.iv_zhaopin_voice);
        tv_zhaopin_length = (TextView) footerView.findViewById(R.id.tv_zhaopin_length);
        zhaopinRecyclerview = (RecyclerView) footerView.findViewById(R.id.zhaopin_recyclerview);
        lay_rl_zhaopin_video = (RelativeLayout) footerView.findViewById(R.id.lay_rl_zhaopin_video);
        iv_zhaopin_video = (ImageView) footerView.findViewById(R.id.iv_zhaopin_video);
        iv_zhaopin_play = (ImageView) footerView.findViewById(R.id.iv_zhaopin_play);
        tv_zhaopin_pic_tips = (TextView) footerView.findViewById(R.id.tv_zhaopin_pic_tips);


        tvMyPublishFabusj = (TextView) footerView.findViewById(R.id.tv_my_publish_fabusj);
        tvBianhao = (TextView) footerView.findViewById(R.id.tv_bianhao);
        tvBianti = (TextView) footerView.findViewById(R.id.tv_bianti);
        tvMyPublishXinxifei = (TextView) footerView.findViewById(R.id.tv_my_publish_xinxifei);
        tvMyPublishName = (TextView) footerView.findViewById(R.id.tv_my_publish_name);
        tvMyPublishContactTel = (TextView) footerView.findViewById(R.id.tv_my_publish_contact_tel);
        tvMyPublishKemu = (TextView) footerView.findViewById(R.id.tv_my_publish_kemu);
        tvMyPublishNianji = (TextView) footerView.findViewById(R.id.tv_my_publish_nianji);
        tvMyPublishSex = (TextView) footerView.findViewById(R.id.tv_my_publish_sex);
        tvMyPublishAge = (TextView) footerView.findViewById(R.id.tv_my_publish_age);
        tvMyPublishEducation = (TextView) footerView.findViewById(R.id.tv_my_publish_education);
        tvMyPublishDiqu = (TextView) footerView.findViewById(R.id.tv_my_publish_diqu);
        tvMyPublishAddress = (TextView) footerView.findViewById(R.id.tv_my_publish_address);
        tvMyPublishYudingsj = (TextView) footerView.findViewById(R.id.tv_my_publish_yudingsj);
        tvMyPublishfudaosj = (TextView) footerView.findViewById(R.id.tv_my_publish_fudaosj);
        tvMyPublishXinzi = (TextView) footerView.findViewById(R.id.tv_my_publish_xinzi);
        tvMyPublishDesc = (TextView) footerView.findViewById(R.id.tv_my_publish_desc);
        tvPipeiStatus = (TextView) footerView.findViewById(R.id.tv_pipei_status);
        lay_ll_my_publish_voice = (LinearLayout) footerView.findViewById(R.id.lay_ll_my_publish_voice);
        lay_ll_my_publish_voice_content = (LinearLayout) footerView.findViewById(R.id.lay_ll_my_publish_voice_content);
        iv_my_publish_voice = (ImageView) footerView.findViewById(R.id.iv_my_publish_voice);
        tv_my_publish_length = (TextView) footerView.findViewById(R.id.tv_my_publish_length);
        myPublishRecyclerview = (RecyclerView) footerView.findViewById(R.id.my_publish_recyclerview);
        lay_rl_my_publish_video = (RelativeLayout) footerView.findViewById(R.id.lay_rl_my_publish_video);
        iv_my_publish_video = (ImageView) footerView.findViewById(R.id.iv_my_publish_video);
        iv_my_publish_play = (ImageView) footerView.findViewById(R.id.iv_my_publish_play);
        tv_my_publish_pic_tips = (TextView) footerView.findViewById(R.id.tv_my_publish_pic_tips);
        lay_ll_my_publish_yudingsj = (LinearLayout) footerView.findViewById(R.id.lay_ll_my_publish_yudingsj);
        lay_ll_my_publish_age = (LinearLayout) footerView.findViewById(R.id.lay_ll_my_publish_age);
        lay_ll_my_publish_address = (LinearLayout) footerView.findViewById(R.id.lay_ll_my_publish_address);

        adapter.addFooterView(footerView);
    }

    @Override
    protected void initData() {
        showDialog();
        addCall(new NetUtil().setUrl(APIS.URL_JIAJIAO_ZHAOPIN_ORDER_DETAIL)
                .addParams("look_for_tutor_id", getArguments().getInt("relate_id") + "")
                .setCallBackData(new BaseNewNetModelimpl<JiajiaoZhaoPinOrderDetailModel>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        commonRefresh.refreshComplete();
                        showErrorView();
                        dismiss();
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<JiajiaoZhaoPinOrderDetailModel> respnse, String source) {
                        commonRefresh.refreshComplete();
                        showContentView();
                        dismiss();
                        if (respnse.getData() != null) {
                           data =  respnse.getData();
                            setData(respnse.getData());
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<JiajiaoZhaoPinOrderDetailModel>>() {
                        }.getType();
                    }
                })
                .buildPost()
        );
    }

    @Override
    public boolean autoRefresh() {
        return true;
    }

    private void setData(final JiajiaoZhaoPinOrderDetailModel data) {
        layLlHeader.setVisibility(View.GONE);
        layLlTips.setVisibility(View.GONE);
        lay_ll_yinpin_info.setVisibility(View.GONE);
        int is_match = data.getIs_match();
        if (is_match == 2) {//已匹配
            tvPipeiStatus.setTextColor(Color.parseColor("#3172f4"));
            tvPipeiStatus.setText("已匹配");
        } else if (is_match == 3) {
            tvPipeiStatus.setTextColor(Color.parseColor("#fdbe44"));
            tvPipeiStatus.setText("匹配失败");
        } else if (is_match == 4) {
            layLlTips.setVisibility(View.VISIBLE);
            layLlHeader.setVisibility(View.VISIBLE);
            lay_ll_yinpin_info.setVisibility(View.VISIBLE);
            tv_evaluation.setVisibility(View.VISIBLE);
            tvTips.setText("尊敬的管家用户，恭喜您已与雇主匹配成功，请保持电话畅通，或及时与雇主联系。");
            tvPipeiStatus.setTextColor(Color.parseColor("#ff0000"));
            tvPipeiStatus.setText("匹配成功");
            when_tutor = data.getWhen_tutor();
            //-------------------------------确认用工应聘人信息-------------------------------\\
            String sex = "";
            if (when_tutor.getSex() == 1) {
                sex = "男";
            } else if (when_tutor.getSex() == 1) {
                sex = "女";
            } else {
                sex = "不限";
            }
            GlideUtil.show(getActivity(), when_tutor.getUser_avatar_url(), iv_avatar);
            tv_pipei.setText(new SpanUtils().append("匹配度:")
                    .append(when_tutor.getMatch_value() + "%")
                    .setForegroundColor(ResUtils.getCommRed())
                    .create());
            tv_name_sex_age.setText(StringUtil.notEmptyBackValue(when_tutor.getUser_name()) + " "
                    + sex + " " + when_tutor.getAge() + "岁");
            tv_diqu.setText(StringUtil.notEmptyBackValue(when_tutor.getWork_second_area_name() + StringUtil.notEmptyBackValue(when_tutor.getWork_third_area_name())));
            tv_edu.setText(StringUtil.notEmptyBackValue(when_tutor.getEducation_background_name()));
            tv_kemu.setText(StringUtil.notEmptyBackValue(when_tutor.getCoach_subject_name()));
            final String user_name = when_tutor.getUser_name();
            final String mobile_phone = when_tutor.getMobile_phone();
            if (when_tutor.getIs_evaluation() == 0){
                tv_evaluation.setText("评价");
            }else{
                tv_evaluation.setText("已评价");
            }
            tv_evaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (when_tutor != null) {
                        if (when_tutor.getIs_evaluation() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("relate_id", when_tutor.getWhen_tutor_id());
                            bundle.putInt("roleType", PageConfig.TYPE_JIAJIAO);
                            startManagerActivity(CommonActivityManager.class, PingjiaFragment.TAG, bundle);
                        }
                    }
                }
            });
            iv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BaseDialog baseDialog = new BaseDialog(getActivity());
                    baseDialog.showTwoButton("提示", "是否拨打电话?\n" + mobile_phone + "\n" + user_name
                            , "拨打", "取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (baseDialog != null) {
                                        baseDialog.dismiss();
                                    }
                                    String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                                    if (checkPermission(permissions)) {
                                        PhoneUtils.dial(mobile_phone);
                                    }
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (baseDialog != null) {
                                        baseDialog.dismiss();
                                    }
                                }
                            });
                }
            });
            //-------------------------------确认用工那个人的应聘信息-------------------------------\\

            if (when_tutor != null) {
                tv_yingpin_name.setText(StringUtil.notEmptyBackValue(when_tutor.getUser_name()));
                tv_yingpin_age.setText(StringUtil.notEmptyBackValue(when_tutor.getAge() + "岁"));
                String yingpinSex = "";
                if (when_tutor.getSex() == 1) {
                    yingpinSex = "男";
                } else if (when_tutor.getSex() == 2) {
                    yingpinSex = "女";
                } else {
                    yingpinSex = "不限";
                }
                tv_yingpin_sex.setText(yingpinSex);
                tv_yingpin_education.setText(StringUtil.notEmptyBackValue(when_tutor.getEducation_background_name()));
                tv_yingpin_kemu.setText(StringUtil.notEmptyBackValue(when_tutor.getCoach_subject_name()));
                tv_yingpin_nianji.setText(StringUtil.notEmptyBackValue(when_tutor.getCoach_grade_name()));
                tv_yingpin_fudaosj.setText(StringUtil.notEmptyBackValue(when_tutor.getCoach_time_name()));
                tv_yingpin_contact_tel.setText(StringUtil.notEmptyBackValue(when_tutor.getMobile_phone()));
                tv_yingpin_diqu.setText(StringUtil.notEmptyBackValue(when_tutor.getWork_second_area_name() + StringUtil.notEmptyBackValue(when_tutor.getWork_third_area_name())));
                int salary_type = when_tutor.getSalary_type();
                if (salary_type == 1) {
                    tv_yingpin_xinzi.setText("市场定价");
                    tv_mianyi.setText("市场定价");
                } else if (salary_type == 2) {
                    tv_yingpin_xinzi.setText(StringUtil.notEmptyBackValue(when_tutor.getSalary()) + "-" + StringUtil.notEmptyBackValue(when_tutor.getEnd_salary_fee() + "元"));
                    tv_mianyi.setText(StringUtil.notEmptyBackValue(when_tutor.getSalary()) + "-" + StringUtil.notEmptyBackValue(when_tutor.getEnd_salary_fee() + "元"));
                } else {
                    tv_yingpin_xinzi.setText("面议");
                    tv_mianyi.setText("面议");
                }
                tv_yingpin_desc.setText(StringUtil.notEmptyBackValue(when_tutor.getSelf_intro()));
                String zhaopin_pic_urls = when_tutor.getImage_url();
                if (!TextUtils.isEmpty(zhaopin_pic_urls)) {
                    List<String> zhaopinPicList = Arrays.asList(zhaopin_pic_urls.split(","));
                    zhaopinListPic.clear();
                    if (zhaopinPicList != null){
                        if (zhaopinPicList.size() == 0){
                            tv_zhaopin_pic_tips.setVisibility(View.GONE);
                        }else{
                            tv_zhaopin_pic_tips.setVisibility(View.VISIBLE);
                        }
                        zhaopinListPic.addAll(zhaopinPicList);
                        zhaopinAdapter.notifyDataSetChanged();
                    }
                }else{
                    tv_zhaopin_pic_tips.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(when_tutor.getVideo_url())) {
                    lay_rl_zhaopin_video.setVisibility(View.VISIBLE);
                    iv_zhaopin_video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(when_tutor.getVideo_url())) {
                                Bundle bundle = new Bundle();
                                bundle.putString(VideoPlayActivity.VIDEO_URL, when_tutor.getVideo_url());
                                startAtvDonFinish(VideoPlayActivity.class, bundle);
                            }
                        }
                    });
                    MediaUtils.getImageForVideo(when_tutor.getVideo_url(), new MediaUtils.OnLoadVideoImageListener() {
                        @Override
                        public void onLoadImage(File file) {
                            if (file == null) {
                                return;
                            }
                            final Uri uri = Uri.fromFile(file);
                            if (getActivity() != null) {
                                final ContentResolver contentResolver = getActivity().getContentResolver();
                                if (contentResolver != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                                                iv_zhaopin_video.setImageBitmap(bitmap);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }else{
                    lay_rl_zhaopin_video.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(when_tutor.getVoice_url())){
                    lay_ll_zhaopin_voice_content.setVisibility(View.VISIBLE);
                    zhaoPinvoiceUtils = new VoiceUtils(iv_zhaopin_voice);
                    zhaoPinvoiceUtils.setFilePath(when_tutor.getVoice_url());
                    zhaoPinvoiceUtils.getTime(tv_zhaopin_length);
                    lay_ll_zhaopin_voice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean playing = zhaoPinvoiceUtils.isPlaying();
                            if (playing) {
                                zhaoPinvoiceUtils.stop();
                            } else {
                                zhaoPinvoiceUtils.play();
                            }
                        }
                    });
                }else{
                    lay_ll_zhaopin_voice_content.setVisibility(View.GONE);
                }
            }
        } else {
            tvPipeiStatus.setTextColor(Color.parseColor("#239491"));
            tvPipeiStatus.setText("正在匹配中");
        }

        //--------------------------匹配到的人-------------|\\\\\\\\\\\\\\\\\\\\\\\\
        List<JiajiaoZhaoPinOrderDetailModel.WhenTutorBean> when_tutor_list = data.getWhen_tutor_list();
        if (when_tutor_list != null) {
            list.clear();
            if (when_tutor_list.size() > 0) {
                layLlTips.setVisibility(View.VISIBLE);
                tvTips.setText("1.如果您不满意请点击不感兴趣，管家会为您重新匹配。"+"\n"+"2.如果您满意请点击获取联系方式，即可获得详细联系方式");
            }
            list.addAll(when_tutor_list);
            adapter.notifyDataSetChanged();
        }

        //--------------------------------我发布的招聘信息-------------------------------\\
        //发布时间
        tvMyPublishFabusj.setText(DateUtil.StampTimeToDate(data.getAdd_time() + "", "yyyy-MM-dd HH:mm:ss"));
        tvBianhao.setText(StringUtil.notEmptyBackValue(data.getOrder_sn()));
        tvBianti.setText(StringUtil.notEmptyBackValue(data.getTitle()));
        //信息费
        tvMyPublishXinxifei.setText(data.getInfo_fee() + "元");
        tvMyPublishName.setText(TextUtils.isEmpty(data.getUser_name()) ? "" : data.getUser_name());
        String sex = "";
        if (data.getSex() == 1) {
            sex = "男";
        } else if (data.getSex() == 2) {
            sex = "女";
        } else {
            sex = "不限";
        }
        tvMyPublishSex.setText(sex);
        if (!TextUtils.isEmpty(data.getAge_name())){
            lay_ll_my_publish_age.setVisibility(View.VISIBLE);
            tvMyPublishAge.setText(StringUtil.notEmptyBackValue(data.getAge_name()));
        }else{
            lay_ll_my_publish_age.setVisibility(View.GONE);
        }
        tvMyPublishContactTel.setText(TextUtils.isEmpty(data.getMobile_phone()) ? "" : data.getMobile_phone());
        tvMyPublishEducation.setText(TextUtils.isEmpty(data.getEducation_background_name()) ? "" : data.getEducation_background_name() );
        tvMyPublishKemu.setText(TextUtils.isEmpty(data.getCoach_subject_name()) ? "" : data.getCoach_subject_name());
        tvMyPublishNianji.setText(TextUtils.isEmpty(data.getCoach_grade_name()) ? "" : data.getCoach_grade_name());
        if (StringUtil.isAllNotEmpty(data.getWork_second_area_name(), data.getWork_third_area_name())) {
            tvMyPublishDiqu.setText(data.getWork_second_area_name() + data.getWork_third_area_name());
        }
        if (!TextUtils.isEmpty(data.getAddress())){
            lay_ll_my_publish_address.setVisibility(View.VISIBLE);
            tvMyPublishAddress.setText(StringUtil.notEmptyBackValue(data.getAddress()));
        }else{
            lay_ll_my_publish_address.setVisibility(View.GONE);
        }
        if (data.getStart_booking_time() == 0 || data.getEnd_booking_time() == 0){
            lay_ll_my_publish_yudingsj.setVisibility(View.GONE);
        }else{
            lay_ll_my_publish_yudingsj.setVisibility(View.VISIBLE);
            tvMyPublishYudingsj.setText(DateUtil.StampTimeToDate(data.getStart_booking_time() + "", "yyyy-MM-dd")
                    + "至" +
                    DateUtil.StampTimeToDate(data.getEnd_booking_time() + "", "yyyy-MM-dd"));
        }
        tvMyPublishfudaosj.setText(TextUtils.isEmpty(data.getCoach_time_name()) ? "" : data.getCoach_time_name());
        if (data.getSalary_type() == 2) {
            if (StringUtil.isAllNotEmpty(data.getStart_salary(), data.getEnd_salary())) {
                tvMyPublishXinzi.setText(data.getStart_salary() + "-" + data.getEnd_salary() + "元");
            }
        } else if (data.getSalary_type() == 3) {
            tvMyPublishXinzi.setText("面议");
        }else {
            tvMyPublishXinzi.setText("市场定价");
        }
        //自我介绍
        tvMyPublishDesc.setText(TextUtils.isEmpty(data.getWork_info()) ? "" : data.getWork_info());
        String pic_urls = data.getImage_url();
        if (!TextUtils.isEmpty(pic_urls)) {
            List<String> mypublishPicList = Arrays.asList(pic_urls.split(","));
            myPublishListPic.clear();
            if (mypublishPicList != null) {
                if (mypublishPicList.size() == 0){
                    tv_my_publish_pic_tips.setVisibility(View.GONE);
                }else{
                    tv_my_publish_pic_tips.setVisibility(View.VISIBLE);
                }
                myPublishListPic.addAll(mypublishPicList);
                myPublishAdapter.notifyDataSetChanged();
            }
        }else{
            tv_my_publish_pic_tips.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getVideo_url())) {
            lay_rl_my_publish_video.setVisibility(View.VISIBLE);
            iv_my_publish_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(data.getVideo_url())) {
                        Bundle bundle = new Bundle();
                        bundle.putString(VideoPlayActivity.VIDEO_URL, data.getVideo_url());
                        startAtvDonFinish(VideoPlayActivity.class, bundle);
                    }
                }
            });
            MediaUtils.getImageForVideo(data.getVideo_url(), new MediaUtils.OnLoadVideoImageListener() {
                @Override
                public void onLoadImage(File file) {
                    if (file == null) {
                        return;
                    }
                    final Uri uri = Uri.fromFile(file);
                    if (getActivity() != null) {
                        final ContentResolver contentResolver = getActivity().getContentResolver();
                        if (contentResolver != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                                        iv_my_publish_video.setImageBitmap(bitmap);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }else{
            lay_rl_my_publish_video.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getVoice_url())){
            myPublishvoiceUtils = new VoiceUtils(iv_my_publish_voice);
            lay_ll_my_publish_voice_content.setVisibility(View.VISIBLE);
            myPublishvoiceUtils.setFilePath(data.getVoice_url());
            myPublishvoiceUtils.getTime(tv_my_publish_length);
            lay_ll_my_publish_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean playing = myPublishvoiceUtils.isPlaying();
                    if (playing) {
                        myPublishvoiceUtils.stop();
                    } else {
                        myPublishvoiceUtils.play();
                    }
                }
            });
        }else{
            lay_ll_my_publish_voice_content.setVisibility(View.GONE);
        }
    }

    @Override
    protected void refreshPageData() {
        initData();
    }

    private void addOnClick(final int type, final int when_tutor_id) {
        if (type == 1) {//不感兴趣
            unInterested(when_tutor_id);
        } else if (type == 2) {//确认用工
            final BaseDialog baseDialog = new BaseDialog(getActivity());
            baseDialog.showTwoButton("提示", "是否确认用工？", "确定", "取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseDialog.dismiss();
                    confirmUse(when_tutor_id);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseDialog.dismiss();
                }
            });
        }
    }

    //不感兴趣
    private void unInterested(final int when_tutor_id) {
        if (data != null) {
            int num = data.getUninterested_num();
            final BaseDialog baseDialog = new BaseDialog(getActivity());
            baseDialog.showTwoButton("提示", "管家还可以为您提供" + num + "次匹配机会", "重新匹配", "取消",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            baseDialog.dismiss();
                            //完成之后 tvLeft 隐藏
                            showDialog();
                            addCall(new NetUtil().setUrl(APIS.URL_TALENT_UNINTERESTED_REQUIRE_JIAJIAO)
                                    .addParams("when_tutor_id", when_tutor_id + "")
                                    .setCallBackData(new BaseNewNetModelimpl() {
                                        @Override
                                        protected void onFail(int type, String msg) {
                                            dismiss();
                                            showTips(msg);
                                        }

                                        @Override
                                        protected void onSuccess(int code, BaseResultInfo respnse, String source) {
                                            dismiss();
                                            if (respnse.getCode() == 0) {
                                                showTips("操作成功");
                                                refreshPageData();
                                            }
                                        }

                                        @Override
                                        protected Type getType() {
                                            return new TypeToken<BaseResultInfo>() {
                                            }.getType();
                                        }
                                    }).buildPost()
                            );
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            baseDialog.dismiss();
                        }
                    });
        }
    }


    //确认用工
    private void confirmUse(int when_tutor_id) {
        showDialog();
        addCall(new NetUtil().setUrl(APIS.URL_JIAJIAO_COMFIRM_USER)
                .addParams("when_tutor_id", when_tutor_id + "")
                .setCallBackData(new BaseNewNetModelimpl<String>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        dismiss();
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, final BaseResultInfo<String> respnse, String source) {
                        dismiss();
                        if (respnse.getData() != null) {
                            refreshPageData();
                           showTips("确认用工成功");
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<String>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }


    class RecyclerViewAdapter extends BaseQuickAdapter<JiajiaoZhaoPinOrderDetailModel.WhenTutorBean, BaseViewHolder> {

        public RecyclerViewAdapter(@Nullable List<JiajiaoZhaoPinOrderDetailModel.WhenTutorBean> data) {
            super(R.layout.item_recycler_has_match_jiajiao_zhaopin, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final JiajiaoZhaoPinOrderDetailModel.WhenTutorBean item) {

            helper.setText(R.id.tv_name_sex_age, item.getUser_name() + " " + (item.getSex() == 1 ? "男" : "女") + " " + item.getAge() + "岁")
                    .setText(R.id.tv_diqu, item.getWork_second_area_name() + item.getWork_third_area_name())
                    .setText(R.id.tv_edu, item.getEducation_background_name())
                    .setText(R.id.tv_fudaokemu, item.getCoach_subject_name());

            GlideUtil.show(getActivity(), item.getUser_avatar_url(), (ImageView) helper.getView(R.id.iv_avatar));
            TextView tvPipei = (TextView) helper.getView(R.id.tv_pipei);
            tvPipei.setText(new SpanUtils().append("匹配度:")
                    .append(item.getMatch_value() + "%")
                    .setForegroundColor(ResUtils.getCommRed())
                    .create());
            String salary = "";
            int salary_type = item.getSalary_type();
            if (salary_type == 2) {
                salary = item.getSalary() + "-" + item.getEnd_salary_fee();
            } else if (salary_type == 3) {
                salary = "面议";
            }
            TextView tv_salary = (TextView) helper.getView(R.id.tv_salary);
            tv_salary.setText(salary);
            final TextView tv_interestring = (TextView) helper.getView(R.id.tv_interestring);
            final TextView tv_call_phone = (TextView) helper.getView(R.id.tv_call_phone);
            int is_uninterested = item.getIs_uninterested();
            if (is_uninterested == 1) {
                tv_interestring.setVisibility(View.GONE);
            }
            if (data != null) {
                if (data.getUninterested_num() == 0) {
                    tv_interestring.setVisibility(View.GONE);
                }
                if (data.getIs_match() == 4) {
                    tv_interestring.setVisibility(View.GONE);
                }
            }

            tv_interestring.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOnClick(1, item.getWhen_tutor_id());
                }
            });

            tv_call_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOnClick(2, item.getWhen_tutor_id());
                }
            });
        }
    }
    public void initPicAdapter() {
        myPublishListPic = new ArrayList<>();
        myPublishAdapter = new PicRecyclerViewAdapter(myPublishListPic);
        myPublishRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        myPublishRecyclerview.setNestedScrollingEnabled(false);
        myPublishRecyclerview.setAdapter(myPublishAdapter);

        myPublishAdapter.setEnableLoadMore(false);

        myPublishAdapter.isUseEmpty(false);

        //事件监听
        myPublishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ViewWrap.showPicActivity(getActivity(), myPublishListPic, position);
            }
        });

        zhaopinListPic = new ArrayList<>();
        zhaopinAdapter = new PicRecyclerViewAdapter(zhaopinListPic);
        zhaopinRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        zhaopinRecyclerview.setNestedScrollingEnabled(false);
        zhaopinRecyclerview.setAdapter(zhaopinAdapter);

        zhaopinAdapter.setEnableLoadMore(false);

        zhaopinAdapter.isUseEmpty(false);

        //事件监听
        zhaopinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ViewWrap.showPicActivity(getActivity(), zhaopinListPic, position);
            }
        });
    }


    class PicRecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicRecyclerViewAdapter(@Nullable List<String> data) {
            super(R.layout.item_recycler_add_pic, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.iv_close).setVisibility(View.GONE);
            GlideUtil.show(getActivity(), item, (ImageView) helper.getView(R.id.iv_add_pic));
        }
    }

}

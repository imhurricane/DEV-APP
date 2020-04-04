package com.dev.eda.frame.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.dev.eda.app.utils.AppTool;
import com.dev.eda.app.utils.HttpRequestUrl;
import com.dev.eda.app.utils.JsonCallback;
import com.dev.eda.frame.login.model.LoginUser;
import com.dev.eda.frame.root.activity.MainActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import constacne.UiType;
import listener.OnInitUiListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class LoginActivity1 extends BaseActivity {

    @BindView(R.id.edit_login_name)
    EditText editLoginName;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_app_version_code)
    TextView tvAppVersionName;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .statusBarColor(R.color.soft_2)
                .init();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        tvAppVersionName.setText("版  本  号: " + AppTool.getAppVersionName(LoginActivity1.this));
        tvAppVersion.setText("版权所有: Create By Hurricane");
    }

    @Override
    protected void setListener() {
        super.setListener();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<LoginUser>post(HttpRequestUrl.LoginAppUrl)
                        .tag(this) //请求的TAG，用于取消对应的请求
                        .isMultipart(true) //强制使用Multipart/from-data表单上传
//                        .isSpliceUrl(true) //强制将params的参数拼接到URL后面
                        .retryCount(0) //超时重连次数
                        .cacheKey("key") //设置当前请求的缓存key
                        .cacheTime(5000) //缓存过期时间
//                        .cacheMode(CacheMode.DEFAULT) //缓存模式
//                        .headers("", "") //请求头  支持多请求头添加
//                        .headers("", "")
                        .params("userName", editLoginName.getText().toString()) //添加请求参数，支持多次添加
                        .params("password", AppTool.encodeByMd5(editPassword.getText().toString())) //添加请求参数，支持多次添加
//                        .params("", new File("filePath")) //支持添加文件
                        .execute(new JsonCallback<LoginUser>(LoginUser.class) {
                            @Override
                            public void onSuccess(Response<LoginUser> response) {
                                LoginUser user = new LoginUser();
                                user.updateAll("currentUserXtm = ''");
                                response.body().save();
                                Intent intent = new Intent(LoginActivity1.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivity1.this.finish();
                            }
                        });
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateConfig updateConfig = new UpdateConfig();
                updateConfig.setForce(false);//强制更新
                updateConfig.setCheckWifi(true);
                updateConfig.setNeedCheckMd5(true);
                updateConfig.setShowNotification(false);
                updateConfig.setNotifyImgRes(R.drawable.logo);
                updateConfig.setAlwaysShowDownLoadDialog(true);
                updateConfig.setDebug(false);
                updateConfig.setShowDownloadingToast(false);
                UiConfig uiConfig = new UiConfig();

//                uiConfig.setUiType(UiType.PLENTIFUL);
                uiConfig.setUiType(UiType.CUSTOM);//自定义UI
                uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);

                UpdateAppUtils
                        .getInstance()
                        .apkUrl(HttpRequestUrl.UpdateAppUrl)
                        .updateTitle("我要更新")
                        .updateContent("1、快来升级最新版本\n2、这次更漂亮了\n3、快点来吧")
                        .uiConfig(uiConfig)
                        .updateConfig(updateConfig)
                        .setOnInitUiListener(new OnInitUiListener() {
                            @Override
                            public void onInitUpdateUi(@org.jetbrains.annotations.Nullable View view, @NotNull UpdateConfig updateConfig, @NotNull UiConfig uiConfig) {
                                TextView tvUpdate = view.findViewById(R.id.tv_update_title);
                                tvUpdate.setText("版本更新啦");
                                TextView tvVersion = view.findViewById(R.id.tv_version_name);
                                tvVersion.setText("V_1.00.00");
                                TextView tvSure = view.findViewById(R.id.btn_update_sure);
                                tvSure.setText("立即更新");
                            }
                        }).update();
//                UpdateAppUtils.getInstance().deleteInstalledApk();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

package com.dev.eda.frame.login.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.eda.R;
import com.dev.eda.app.base.BaseActivity;
import com.dev.eda.app.utils.AppTool;
import com.dev.eda.app.utils.Logger;
import com.dev.eda.frame.login.data.LoginDataSever;
import com.dev.eda.frame.login.model.LoginUser;
import com.dev.eda.frame.root.activity.MainActivity;
import com.dev.eda.frame.view.utils.DateUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kongzue.dialog.v3.MessageDialog;
import com.lzy.okgo.OkGo;

import java.lang.reflect.Method;
import java.util.Date;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_login_name)
    EditText editLoginName;
    @BindView(R.id.edit_password)
    EditText editPassword;
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
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        tvAppVersionName.setText("版  本  号: " + AppTool.getAppVersionName(LoginActivity.this));
        tvAppVersion.setText("版权所有: Create By Hurricane");
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    LoginUser loginUser = (LoginUser) msg.obj;
                    if (loginUser.getCode() == 0) {
                        MessageDialog.show(LoginActivity.this, "提示", "用户名或密码错误");
                    } else {
                        LoginUser user = new LoginUser();
                        int i = user.updateAll("currentUserXtm = ''");
                        loginUser.setLastLoginDate(DateUtils.getTime(new Date(), DateUtils.formatStr_yyyyMMddHHmmss));
                        loginUser.setCurrentUserXtm(loginUser.getYhxtm());
                        Logger.e("loginUser", loginUser.toString());
                        loginUser.saveOrUpdate("yhxtm = '" + loginUser.getYhxtm() + "'");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
            }
            return false;
        }
    });

    @Override
    protected void setListener() {
        super.setListener();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editLoginName.getText().toString();
                String password = editPassword.getText().toString();
                if (userName.equals("")) {
                    MessageDialog.show(LoginActivity.this, "提示", "用户名不能为空");
                    showSoftInputFromWindow(LoginActivity.this, editLoginName);
                    return;
                }
                if (password.equals("")) {
                    MessageDialog.show(LoginActivity.this, "提示", "密码不能为空");
                    showSoftInputFromWindow(LoginActivity.this, editPassword);
                    return;
                }
                LoginDataSever.login(LoginActivity.this, handler, userName, password);
                hideSoftInputFromWindow(LoginActivity.this);
            }
        });
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UpdateConfig updateConfig = new UpdateConfig();
//                updateConfig.setForce(false);//强制更新
//                updateConfig.setCheckWifi(true);
//                updateConfig.setNeedCheckMd5(true);
//                updateConfig.setShowNotification(false);
//                updateConfig.setNotifyImgRes(R.drawable.logo);
//                updateConfig.setAlwaysShowDownLoadDialog(true);
//                updateConfig.setDebug(false);
//                updateConfig.setShowDownloadingToast(false);
//                UiConfig uiConfig = new UiConfig();
//
////                uiConfig.setUiType(UiType.PLENTIFUL);
//                uiConfig.setUiType(UiType.CUSTOM);//自定义UI
//                uiConfig.setCustomLayoutId(R.layout.view_update_dialog_custom);
//
//                UpdateAppUtils
//                        .getInstance()
//                        .apkUrl(HttpRequestUrl.UpdateAppUrl)
//                        .updateTitle("我要更新")
//                        .updateContent("1、快来升级最新版本\n2、这次更漂亮了\n3、快点来吧")
//                        .uiConfig(uiConfig)
//                        .updateConfig(updateConfig)
//                        .setOnInitUiListener(new OnInitUiListener() {
//                            @Override
//                            public void onInitUpdateUi(@org.jetbrains.annotations.Nullable View view, @NotNull UpdateConfig updateConfig, @NotNull UiConfig uiConfig) {
//                                TextView tvUpdate = view.findViewById(R.id.tv_update_title);
//                                tvUpdate.setText("版本更新啦");
//                                TextView tvVersion = view.findViewById(R.id.tv_version_name);
//                                tvVersion.setText("V_1.00.00");
//                                TextView tvSure = view.findViewById(R.id.btn_update_sure);
//                                tvSure.setText("立即更新");
//                            }
//                        }).update();
//                UpdateAppUtils.getInstance().deleteInstalledApk();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        MessageDialog.reset();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}

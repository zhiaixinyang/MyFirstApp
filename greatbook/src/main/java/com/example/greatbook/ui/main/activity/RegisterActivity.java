package com.example.greatbook.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.bumptech.glide.Glide;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.beans.leancloud.User;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtil;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/10/20.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,PopupWindow.OnDismissListener{
    @BindView(R.id.btn_login) TextView btnLogin;
    @BindView(R.id.et_account) EditText etAccount;
    @BindView(R.id.et_password) EditText etPassWord;
    @BindView(R.id.iv_avatar) ImageView ivAvatar;
    @BindView(R.id.et_name) EditText etName;

    private String imagePath;
    private Bitmap bmp;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils=null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
        TransWindowUtils.setTransWindow(this);
        btnLogin.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.iv_avatar:
                Intent openImage=new Intent(Intent.ACTION_PICK,null);
                openImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(openImage, IntentConstants.OPEN_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK&&requestCode==IntentConstants.OPEN_IMAGE){
            if (data!=null){
                Uri selectImageUri=data.getData();
                imagePath = FileUtils.getPathUrlFromUri(App.getInstance().getContext(),selectImageUri);
                bmp= BitmapCompressUtils.zoomImage(FileUtils.getBitmap(imagePath), 150,150);
                if (selectImageUri!=null){
                    GlideUtils.load(App.getInstance().getContext(),FileUtils.getByteFromBitmap(bmp),ivAvatar);
                }
            }
        }
    }

    private void login() {
        //先判断网路问题
        if (NetUtil.isNetworkAvailable()){
            if (!StringUtil.isEmpty(imagePath)
                    && !StringUtil.isEmpty(etAccount.getText().toString())
                    && !StringUtil.isEmpty(etPassWord.getText().toString())
                    && !StringUtil.isEmpty(etName.getText().toString())) {
                waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
                final User user = new User();
                user.setUsername(etAccount.getText().toString());
                user.setPassword(etPassWord.getText().toString());
                AVFile avFile = new AVFile(FileUtils.getFileName(imagePath), FileUtils.getByteFromBitmap(bmp));
                user.setAvatar(avFile);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            user.logInInBackground(etAccount.getText().toString(),
                                    etPassWord.getText().toString(),
                                    new LogInCallback<AVUser>() {
                                        @Override
                                        public void done(AVUser avUser, AVException e) {
                                            if (e == null) {
                                                ToastUtil.toastShort("注册并登陆成功。");
                                                SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putInt("count", 1);
                                                editor.commit();
                                                Intent intent = new Intent(App.getInstance().getContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.login_in, R.anim.login_out);
                                            }else{
                                                waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                                                ToastUtil.toastShort("注册失败，请保证网络连接并重试。");
                                            }
                                        }
                                    });
                        }else{
                            waitNetPopupWindowUtils.hideWaitNetPopupWindow(RegisterActivity.this);
                            ToastUtil.toastShort("账号重复/网络连接有问题");
                        }
                    }
                });
            }else{
                ToastUtil.toastShort("请完成相关信息填写。");
            }
        } else{
            ToastUtil.toastShort("未连接网络！");
        }
    }

    @Override
    public void onDismiss() {
        TransWindowUtils.setBackgroundAlpha(this,1f);
    }
}

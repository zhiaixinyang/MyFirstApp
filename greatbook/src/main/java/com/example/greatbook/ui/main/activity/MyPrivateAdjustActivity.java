package com.example.greatbook.ui.main.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.beans.leancloud.User;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.main.presenter.MyPrivateAdjustPresenter;
import com.example.greatbook.ui.main.presenter.MyPrivateAdjustPresenterImpl;
import com.example.greatbook.ui.main.view.MyPrivateAdjustView;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.BlurBitmap;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtil;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;
import com.example.greatbook.widght.CircleImageView;

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class MyPrivateAdjustActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,MyPrivateAdjustView{
    @BindView(R.id.avatar_bg) ImageView avatarBg;
    @BindView(R.id.iv_avatar_adjust) CircleImageView ivAvatar;
    @BindView(R.id.et_name_adjust) EditText etNameAdjust;
    @BindView(R.id.btn_ok_adjust) TextView btnOk;
    @BindView(R.id.srf_adjust) SwipeRefreshLayout srfAdjust;
    @BindView(R.id.btn_getphoto) TextView btnGetPhoto;
    @BindView(R.id.btn_takephoto) TextView btnTakePhoto;

    private User user;
    private String avatarUrl;
    private MyPrivateAdjustPresenter myPrivateAdjustPresenter;

    private Bitmap bitmap;
    private WaitNetPopupWindowUtils waitNetPopupWindowUtils;
    @Override
    public int getLayoutId() {
        return R.layout.activity_private_adjust;
    }

    @Override
    public void init() {
        TransWindowUtils.setTransWindow(this);
        user=AVUser.getCurrentUser(User.class);
        avatarUrl=user.getAvatar().getUrl();
        myPrivateAdjustPresenter=new MyPrivateAdjustPresenterImpl(this);
        setUserName();
        GlideUtils.load(App.getInstance().getContext(),user.getAvatar().getUrl(),ivAvatar);
        onRefresh();
        btnOk.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        srfAdjust.setOnRefreshListener(this);
        btnTakePhoto.setOnClickListener(this);
        btnGetPhoto.setOnClickListener(this);
        //记录按钮位置，避免被动画执行完后，View位置累计变化。
        oneX=btnTakePhoto.getX();
        twoX=btnGetPhoto.getX();
    }

    private void setUserName() {
        if (StringUtil.isEmpty(user.getName())){
            etNameAdjust.setText("书心用户");
        }else {
            etNameAdjust.setText(user.getName());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok_adjust:
                adjust();
                break;
            case R.id.iv_avatar_adjust:
                openPhoto();
                break;
            case R.id.btn_takephoto:
                takePhoto();
                break;
            case R.id.btn_getphoto:
                getPhoto();
                break;
        }
    }
    float oneX;
    float twoX;
    private boolean isOpen=false;
    private int tag=1;
    private void openPhoto() {
        if (tag==1) {
            tag++;
            isOpen=true;
            if (isOpen) {
                btnGetPhoto.setVisibility(View.VISIBLE);
                btnTakePhoto.setVisibility(View.VISIBLE);

                ValueAnimator one = new ObjectAnimator().ofFloat(btnTakePhoto, "translationX", btnTakePhoto.getX() - 200, oneX);
                one.setInterpolator(new BounceInterpolator());
                ValueAnimator two = new ObjectAnimator().ofFloat(btnGetPhoto, "translationX", btnTakePhoto.getX() + 200,twoX);
                two.setInterpolator(new BounceInterpolator());
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        one, two
                );
                set.setDuration(500).start();
                isOpen=false;
            }
        }else if(tag==2){
            btnGetPhoto.setVisibility(View.GONE);
            btnTakePhoto.setVisibility(View.GONE);
            ValueAnimator one=ObjectAnimator.ofFloat(btnGetPhoto,"alpha",1f,0f);
            ValueAnimator two=ObjectAnimator.ofFloat(btnTakePhoto,"alpha",1f,0f);
            AnimatorSet set=new AnimatorSet();
            set.playTogether(one,two);
            set.setDuration(500).start();
            tag--;
        }
    }

    private void takePhoto() {
        Intent takePhoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE,null);
        startActivityForResult(takePhoto, IntentConstants.OPEN_IMAGE_OPEN_CAMERA);
    }

    private void getPhoto() {
        Intent getPhoto=new Intent(Intent.ACTION_PICK,null);
        getPhoto.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        MyPrivateAdjustActivity.this.startActivityForResult(getPhoto, IntentConstants.OPEN_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IntentConstants.OPEN_IMAGE&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            if (uri!=null) {
                try {
                    bitmap = BitmapCompressUtils.zoomImage
                            (MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri),200,200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this)
                            .load(FileUtils.getByteFromBitmap(bitmap))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .fitCenter()
                            .into(ivAvatar);
                avatarBg.setImageBitmap(BlurBitmap.blur(this,bitmap));
                btnGetPhoto.setVisibility(View.GONE);
                btnTakePhoto.setVisibility(View.GONE);
            }
        }else if(requestCode== IntentConstants.OPEN_IMAGE_OPEN_CAMERA&&resultCode==RESULT_OK){
            //拿到拍到的照片
            bitmap =BitmapCompressUtils.zoomImage((Bitmap) data.getParcelableExtra("data"), 200,200);
            avatarBg.setImageBitmap(BlurBitmap.blur(this,bitmap));
            Glide.with(this)
                    .load(FileUtils.getByteFromBitmap(bitmap))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fitCenter()
                    .into(ivAvatar);
            btnGetPhoto.setVisibility(View.GONE);
            btnTakePhoto.setVisibility(View.GONE);
        }
    }

    private void adjust() {
        user.setName(etNameAdjust.getText().toString());
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
        waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
        if (bitmap!=null){
            user.setAvatar(new AVFile(FileUtils.getRandomFileName(),FileUtils.getByteFromBitmap(bitmap)));
        }
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    ToastUtil.toastShort("修改成功。");
                    finish();
                    waitNetPopupWindowUtils.hideWaitNetPopupWindow(MyPrivateAdjustActivity.this);
                }else{
                    ToastUtil.toastShort("修改失败，请重试一下。");
                    waitNetPopupWindowUtils.hideWaitNetPopupWindow(MyPrivateAdjustActivity.this);
                }
            }
        });
    }

    @Override
    public void initAvatar(Bitmap avatar) {
        avatarBg.setImageBitmap(BlurBitmap.blur(this,avatar));
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfAdjust.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srfAdjust.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        myPrivateAdjustPresenter.getAvatarBitmap(avatarUrl);
    }

}

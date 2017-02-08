package com.example.greatbook.ui.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.btn_back) TextView btnBack;
    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.iv_photo) ImageView ivPhoto;
    @BindView(R.id.btn_takephoto) TextView btnTakePhoto;
    @BindView(R.id.btn_getphoto) TextView btnGetPhoto;
    @BindView(R.id.btn_ok) TextView btnOk;

    private Bitmap bitmap;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_talk_about;
    }

    @Override
    public void init() {
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
        btnBack.setOnClickListener(this);
        btnGetPhoto.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IntentConstants.OPEN_IMAGE_MESSAGEPHOTO&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            if (uri!=null) {
                ivPhoto.setImageURI(uri);
                try {
                    bitmap = BitmapCompressUtils.zoomImage
                            (MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri),200,200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode== IntentConstants.OPEN_IMAGE_OPEN_CAMERA&&resultCode==RESULT_OK){
            //拿到拍到的照片
            bitmap =BitmapCompressUtils.zoomImage((Bitmap) data.getParcelableExtra("data"), 200,200);
            Glide.with(this)
                    .load(FileUtils.getByteFromBitmap(bitmap))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fitCenter()
                    .into(ivPhoto);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                sendOk();
                break;
            case R.id.btn_getphoto:
                getPhoto();
                break;
            case R.id.btn_takephoto:
                takePhoto();
                break;
            case R.id.btn_back:
                back();
                break;
        }
    }

    private void takePhoto() {
        Intent openCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCamera,IntentConstants.OPEN_IMAGE_OPEN_CAMERA);
    }

    private void getPhoto() {
        Intent getPhoto=new Intent(Intent.ACTION_PICK,null);
        getPhoto.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(getPhoto, IntentConstants.OPEN_IMAGE_MESSAGEPHOTO);
    }

    private void sendOk() {
        //网络可用
        if (NetUtil.isNetworkAvailable()) {
            if (bitmap!=null&&!StringUtils.isEmpty(etContent.getText())) {
                waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
                    final TalkAboutBean talkAboutBean = new TalkAboutBean();
                    AVFile avFile = new AVFile(FileUtils.getRandomFileName(),FileUtils.getByteFromBitmap(bitmap));
                    talkAboutBean.setContentPhoto(avFile);
                    talkAboutBean.setContent(etContent.getText().toString());
                    talkAboutBean.setBelongId(AVUser.getCurrentUser().getObjectId());
                    talkAboutBean.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                ToastUtil.toastShort("发布成功！");
                                back();
                            }
                            else{
                                ToastUtil.toastShort("错误："+e);
                            }
                        }
                    });
            }else {
                ToastUtil.toastShort("请上传一张图片！");
            }
        }else {
            ToastUtil.toastShort("网络未连接！");
        }
    }


    private void back() {
        Intent back=new Intent(this, MainActivity.class);
        back.putExtra(IntentConstants.BACK_TALK_ABOUT,1);
        startActivity(back);
        overridePendingTransition(R.anim.login_in, R.anim.login_out);
        finish();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }
}

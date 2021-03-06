package com.benmu.framework.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.benmu.framework.BMWXEnvironment;
import com.benmu.framework.constant.Constant;
import com.benmu.framework.manager.impl.FileManager;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.taobao.weex.WXEnvironment;

import java.io.File;

/**
 * 增加资源判断的Glide Created by Carry on 2017/9/13.
 */

public class BMHookGlide {

    private static final String LOCAL_SCHEME = "bmlocal";


    public static DrawableTypeRequest<String> load(Context context, String url) {

        return Glide.with(context).load(handleResource(url));
    }

    private static String handleResource(String url) {
        if (TextUtils.isEmpty(url)) return "";
        Uri imageUri = Uri.parse(url);
        if (LOCAL_SCHEME.equalsIgnoreCase(imageUri.getScheme())) {
            return loadLocalImage(imageUri);
        }
        return url;
    }

    private static String loadLocalImage(Uri imageUri) {
        if (Constant.INTERCEPTOR_ACTIVE.equalsIgnoreCase(SharePreferenceUtil.getInterceptorActive
                (WXEnvironment.getApplication()))) {
            String path = "pages" + File.separator + imageUri.getHost() + File.separator +
                    imageUri.getPath();
            return FileManager.getPathBundleDir(WXEnvironment.getApplication(), path)
                    .getAbsolutePath();

        } else {
            return String.format("%s/fe/dist/%s%s", BMWXEnvironment.mPlatformConfig.getUrl()
                    .getJsServer
                            (), imageUri.getHost(), imageUri.getPath());
        }
    }


}

package com.example.asanre.searchmoviedb.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asanre.searchmoviedb.R;

public class GlideHelper {

    private static String baseListUrl;

    /**
     * load image with less quality for a list
     *
     * @param imageView
     * @param context
     * @param url
     */
    public static void loadListImage(ImageView imageView, Context context, String url) {

        setBaseUrls(context);
        Glide.with(context)
                .load(getFormattedLowImageUrl(url))
                .apply(new RequestOptions().error(R.drawable.ic_image_holder)
                        .placeholder(R.drawable.ic_image_holder)
                        .override(110, 130)
                        .centerCrop())
                .into(imageView);
    }

    private static void setBaseUrls(Context context) {

        if (deviceOnWifi(context)) {
            baseListUrl = "http://image.tmdb.org/t/p/w185%s";
        } else {
            baseListUrl = "http://image.tmdb.org/t/p/w92%s";
        }
    }

    private static boolean deviceOnWifi(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI);
            return networkInfo.isConnected();
        }

        return false;
    }

    private static String getFormattedLowImageUrl(String path) {

        return String.format(baseListUrl, path);
    }
}

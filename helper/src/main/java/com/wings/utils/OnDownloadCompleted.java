package com.wings.utils;

import android.net.Uri;


/**
 * Purpose: Listener while downloading completes
 *
 * @author NikunjD
 * Created on June 10, 2019
 * Modified on June 19, 2019
 */
public interface OnDownloadCompleted {
    void onDownloadCompleted(Uri downloadedAt);
}

package com.wings.helper;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.wings.utils.OnDownloadCompleted;

/**
 * Purpose: Automatic download file
 *
 * @author NikunjD
 * Created on June 12, 2019
 * Modified on June 12, 2019
 */

public class DownloadHelper {

    private OnDownloadCompleted onDownloadCompleted;

    /**
     * Download file and store at your convenient space
     *
     * @param context                            context
     * @param originPath                         download url
     * @param destinationStoragePathWithFileName path to store downloads (path must be with filename with extension)
     *                                           if null then it will store in DOWNLOADS folder.
     * @param notificationTitle                  notification title
     * @param notificationProgressDescription    notification progress description
     * @param notificationVisibility             notification need to visible after download completes?
     * @param onDownloadCompletedListener        download listener will fire after download completes if not null.
     *                                           <p>Note:
     *                                           -> To get notify in your class while Download Completes, you have to implements OnDownloadCompleted listener.
     *                                           <p>
     */
    public void downloadFile(Context context, final String originPath,
                             final Uri destinationStoragePathWithFileName,
                             String notificationTitle,
                             String notificationProgressDescription,
                             boolean notificationVisibility,
                             OnDownloadCompleted onDownloadCompletedListener) {

        this.onDownloadCompleted = onDownloadCompletedListener;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(originPath));
        request.setTitle(notificationTitle);
        request.setDescription(notificationProgressDescription);
        request.allowScanningByMediaScanner();
        if (notificationVisibility) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        if (destinationStoragePathWithFileName != null) {
            request.setDestinationUri(destinationStoragePathWithFileName);
        } else {
            String fileName = String.valueOf(DateTimeHelper.getSystemCurrentTimeInMills());
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        }
        final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        final long downloadId;
        if (manager != null) {
            downloadId = manager.enqueue(request);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    boolean downloading = true;

                    while (downloading) {

                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);

                        Cursor cursor = manager.query(q);
                        try {
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int bytes_downloaded = cursor.getInt(cursor
                                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloading = false;
                                }

                                int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                                if (dl_progress == 100) {
                                    if (onDownloadCompleted != null) {
                                        onDownloadCompleted.onDownloadCompleted(destinationStoragePathWithFileName);
                                    }
                                }
                                cursor.close();
                            } else {
                                downloading = false;
                            }

                        } catch (Exception e) {
                            downloading = false;
                            if (cursor != null) {
                                cursor.close();
                            }
                        }

                    }
                }
            }).start();
        }
    }

}

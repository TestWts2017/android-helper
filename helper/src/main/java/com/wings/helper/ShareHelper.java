package com.wings.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Purpose: Share any MIME type of data using Intent
 *
 * @author HetalD
 * Created On June 11,2019
 * Modified On June 19,2019
 */
public class ShareHelper {

    /**
     * Share any MIME type of file with particular application
     *
     * @param context      Context to activity
     * @param URI          URI of sharing file
     * @param recipient    String[] - Recipient array to send mail with file
     * @param mailSubject  String - Mail subject to send
     * @param mailBodyText String -  Mail body
     * @param chooserTitle String - Title of chooser dialog
     */
    public static void shareFile(Context context, Uri URI, String[] recipient,
                                 String mailSubject, String mailBodyText, String chooserTitle) {

        String mimeType = null;
        if (URI.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(URI);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(URI.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(mimeType);
        shareIntent.putExtra(Intent.EXTRA_EMAIL, recipient);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
        if (URI != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, URI);
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, mailBodyText);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(shareIntent, chooserTitle));
    }
}

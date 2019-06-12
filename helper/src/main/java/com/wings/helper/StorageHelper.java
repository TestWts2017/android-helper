package com.wings.helper;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.wings.utils.EncryptConfiguration;
import com.wings.utils.ImmutablePair;
import com.wings.utils.SecurityUtil;
import com.wings.utils.SizeUnit;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Cipher;

/**
 * Purpose: Task for storage
 * Ref. @link https://github.com/sromku/android-storage/blob/master/storage/src/main/java/com/snatik/storage/Storage.java
 *
 * @author NikunjD
 * Created on June 10, 2019
 * Modified on June 11, 2019
 */
public class StorageHelper {

    private static final String TAG = "StorageHelper";
    private EncryptConfiguration mConfiguration;
    private final Context mContext;

    /**
     * Storage Helper - Before use this class methods check for storage permissions
     *
     * @param context context
     */
    public StorageHelper(Context context) {
        mContext = context;
    }

    /**
     * Get external storage directory
     *
     * @return path of storage directory
     */
    public String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Get external storage directory
     *
     * @param publicDirectory public directory
     * @return path of storage directory
     */
    public String getExternalStorageDirectory(String publicDirectory) {
        return Environment.getExternalStoragePublicDirectory(publicDirectory).getAbsolutePath();
    }

    /**
     * Get internal root directory
     *
     * @return path of root directory
     */
    public String getInternalRootDirectory() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Get internal file directory
     *
     * @return path of internal file directory
     */
    public String getInternalFilesDirectory() {
        return mContext.getFilesDir().getAbsolutePath();
    }

    /**
     * Get internal cache directory
     *
     * @return path of internal cache directory
     */
    public String getInternalCacheDirectory() {
        return mContext.getCacheDir().getAbsolutePath();
    }

    /**
     * Check for external storage writable or not
     *
     * @return <code>true</code> if writable; <code>false</code> otherwise
     */
    public static boolean isExternalWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Create directory
     *
     * @param path directory path
     * @return <code>true</code> if directory created; <code>false</code> otherwise
     */
    public boolean createDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            LogHelper.w(TAG, "Directory '" + path + "' already exists");
            return false;
        }
        return directory.mkdirs();
    }

    /**
     * Create file at directory
     *
     * @param context    context
     * @param folderName folder name
     * @param fileName   file name
     * @param extension  extension of file
     * @return path for file
     */
    public String createFileAtDirectory(Context context, String folderName, String fileName, String extension) {

        File directory = new File(Environment.getExternalStorageDirectory(),
                folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File actualFile = new File(directory, fileName + "." + extension);
        try {
            actualFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualFile.getAbsolutePath();
    }


    /**
     * Delete directory
     *
     * @param path path for directory
     * @return <code>true</code> if directory deleted; <code>false</code> otherwise
     */
    public boolean deleteDirectory(String path) {
        return deleteDirectoryImpl(path);
    }

    /**
     * Check for directory is exist or not
     *
     * @param path path for directory
     * @return <code>true</code> if directory exist; <code>false</code> otherwise
     */
    public boolean isDirectoryExists(String path) {
        return new File(path).exists();
    }


    /**
     * Create file with string content
     *
     * @param path    file path
     * @param content file content as a string
     * @return <code>true</code> if file created; <code>false</code> otherwise
     */
    public boolean createFile(String path, String content) {
        return createFile(path, content.getBytes());
    }

    /**
     * Create file with byte
     *
     * @param path    file path
     * @param content file content as a byte array
     * @return <code>true</code> if file created; <code>false</code> otherwise
     */

    public boolean createFile(String path, byte[] content) {
        try {
            OutputStream stream = new FileOutputStream(new File(path));

            // encrypt if needed
            if (mConfiguration != null && mConfiguration.isEncrypted()) {
                content = encrypt(content, Cipher.ENCRYPT_MODE);
            }

            stream.write(content);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            LogHelper.e(TAG, "Failed create file");
            return false;
        }
        return true;
    }

    /**
     * Create file with bitmap
     *
     * @param path   file path
     * @param bitmap image bitmap
     * @return <code>true</code> if file created; <code>false</code> otherwise
     */
    public boolean createFile(String path, Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return createFile(path, byteArray);
    }


    /**
     * Delete file
     *
     * @param path file path
     * @return <code>true</code> if file deleted; <code>false</code> otherwise
     */

    public boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * Check for file is exist or not
     *
     * @param path file path
     * @return <code>true</code> if file exist; <code>false</code> otherwise
     */
    public boolean isFileExist(String path) {
        return new File(path).exists();
    }

    /**
     * Read file
     *
     * @param path file path
     * @return file content
     */
    public byte[] readFile(String path) {
        final FileInputStream stream;
        try {
            stream = new FileInputStream(new File(path));
            return readFile(stream);
        } catch (FileNotFoundException e) {
            LogHelper.e(TAG, "Failed to read file to input stream");
            return null;
        }
    }

    /**
     * Read text file
     *
     * @param path file path
     * @return content
     */
    public String readTextFile(String path) {
        byte[] bytes = readFile(path);
        return new String(bytes);
    }

    /**
     * Append content
     *
     * @param path    file path
     * @param content content
     */
    public void appendFile(String path, String content) {
        appendFile(path, content.getBytes());
    }

    /**
     * Append to file
     *
     * @param path  file path
     * @param bytes content
     */
    public void appendFile(String path, byte[] bytes) {
        if (!isFileExist(path)) {
            LogHelper.w(TAG, "Impossible to append content, because such file doesn't exist");
            return;
        }

        try {
            FileOutputStream stream = new FileOutputStream(new File(path), true);
            stream.write(bytes);
            stream.write(System.getProperty("line.separator").getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            LogHelper.e(TAG, "Failed to append content to file");
        }
    }

    /**
     * Get Files
     *
     * @param path file path
     * @return list of files
     */
    public List<File> getNestedFiles(String path) {
        File file = new File(path);
        List<File> out = new ArrayList<File>();
        getDirectoryFilesImpl(file, out);
        return out;
    }

    /**
     * Get Files
     *
     * @param dir file path
     * @return list of files
     */
    public List<File> getFiles(String dir) {
        return getFiles(dir, null);
    }


    /**
     * Get files
     *
     * @param dir        directory name
     * @param matchRegex regex
     * @return list of files
     */
    public List<File> getFiles(String dir, final String matchRegex) {
        File file = new File(dir);
        File[] files = null;
        if (matchRegex != null) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String fileName) {
                    return fileName.matches(matchRegex);
                }
            };
            files = file.listFiles(filter);
        } else {
            files = file.listFiles();
        }
        return files != null ? Arrays.asList(files) : null;
    }

    /**
     * Get file
     *
     * @param path file path
     * @return file
     */
    public File getFile(String path) {
        return new File(path);
    }

    /**
     * Rename file
     *
     * @param fromPath actual path
     * @param toPath   new path
     * @return <code>true</code> if file renamed; <code>false</code> otherwise
     */
    public boolean rename(String fromPath, String toPath) {
        File file = getFile(fromPath);
        File newFile = new File(toPath);
        return file.renameTo(newFile);
    }

    /**
     * Get size of file
     *
     * @param file file name
     * @param unit SizeUnit.B, SizeUnit.KB, SizeUnit.MB, SizeUnit.GB, SizeUnit.TB
     * @return file size
     */
    public double getSize(File file, SizeUnit unit) {
        long length = file.length();
        return (double) length / (double) unit.inBytes();
    }

    /**
     * Get readable size
     *
     * @param file file
     * @return readable size unit
     */
    public String getReadableSize(File file) {
        long length = file.length();
        return SizeUnit.readableSizeUnit(length);
    }

    /**
     * Get free space from storage
     *
     * @param dir      path in the desired file system
     * @param sizeUnit SizeUnit.B, SizeUnit.KB, SizeUnit.MB, SizeUnit.GB, SizeUnit.TB
     * @return free space size
     */
    public long getFreeSpace(String dir, SizeUnit sizeUnit) {
        StatFs statFs = new StatFs(dir);
        long availableBlocks;
        long blockSize;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
        } else {
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }
        long freeBytes = availableBlocks * blockSize;
        return freeBytes / sizeUnit.inBytes();
    }

    /**
     * Get used space
     *
     * @param dir      path in the desired file system
     * @param sizeUnit SizeUnit.B, SizeUnit.KB, SizeUnit.MB, SizeUnit.GB, SizeUnit.TB
     * @return used space size
     */
    public long getUsedSpace(String dir, SizeUnit sizeUnit) {
        StatFs statFs = new StatFs(dir);
        long availableBlocks;
        long blockSize;
        long totalBlocks;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
        } else {
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
        }
        long usedBytes = totalBlocks * blockSize - availableBlocks * blockSize;
        return usedBytes / sizeUnit.inBytes();
    }


    /**
     * Copy file
     *
     * @param fromPath copy from
     * @param toPath   copy to
     * @return <code>true</code> if file copied; <code>false</code> otherwise
     */
    public boolean copy(String fromPath, String toPath) {
        File file = getFile(fromPath);
        if (!file.isFile()) {
            return false;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(file);
            outStream = new FileOutputStream(new File(toPath));
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            LogHelper.e(TAG, "Failed copy");
            return false;
        } finally {
            closeSilently(inStream);
            closeSilently(outStream);
        }
        return true;
    }

    /**
     * Move file
     *
     * @param fromPath move from
     * @param toPath   move to
     * @return <code>true</code> if file moved; <code>false</code> otherwise
     */
    public boolean move(String fromPath, String toPath) {
        if (copy(fromPath, toPath)) {
            return getFile(fromPath).delete();
        }
        return false;
    }

    /**
     * Read file
     *
     * @param stream input stream
     * @return file content
     */
    protected byte[] readFile(final FileInputStream stream) {
        class Reader extends Thread {
            byte[] array = null;
        }

        Reader reader = new Reader() {
            public void run() {
                LinkedList<ImmutablePair<byte[], Integer>> chunks
                        = new LinkedList<ImmutablePair<byte[], Integer>>();

                // read the file and build chunks
                int size = 0;
                int globalSize = 0;
                do {
                    try {
                        int chunkSize = mConfiguration != null ? mConfiguration.getChuckSize() : 8192;
                        // read chunk
                        byte[] buffer = new byte[chunkSize];
                        size = stream.read(buffer, 0, chunkSize);
                        if (size > 0) {
                            globalSize += size;

                            // add chunk to list
                            chunks.add(new ImmutablePair<byte[], Integer>(buffer, size));
                        }
                    } catch (Exception e) {
                        // very bad
                    }
                } while (size > 0);

                try {
                    stream.close();
                } catch (Exception e) {
                    // very bad
                }

                array = new byte[globalSize];

                // append all chunks to one array
                int offset = 0;
                for (ImmutablePair<byte[], Integer> chunk : chunks) {
                    // flush chunk to array
                    System.arraycopy(chunk.element1, 0, array, offset, chunk.element2);
                    offset += chunk.element2;
                }
            }
        };

        reader.start();
        try {
            reader.join();
        } catch (InterruptedException e) {
            LogHelper.e(TAG, "Failed on reading file from storage while the locking Thread");
            return null;
        }

        if (mConfiguration != null && mConfiguration.isEncrypted()) {
            return encrypt(reader.array, Cipher.DECRYPT_MODE);
        } else {
            return reader.array;
        }
    }

    /**
     * Encrypt or Descrypt the content. <br>
     *
     * @param content        The content to encrypt or descrypt.
     * @param encryptionMode Use: {@link Cipher#ENCRYPT_MODE} or
     *                       {@link Cipher#DECRYPT_MODE}
     * @return encrypted / decrypted content
     */
    private synchronized byte[] encrypt(byte[] content, int encryptionMode) {
        final byte[] secretKey = mConfiguration.getSecretKey();
        final byte[] ivx = mConfiguration.getIvParameter();
        return SecurityUtil.encrypt(content, encryptionMode, secretKey, ivx);
    }

    /**
     * Delete the directory and all sub content.
     *
     * @param path The absolute directory path. For example:
     *             <i>mnt/sdcard/NewFolder/</i>.
     * @return <code>true</code> if directory was deleted; <code>false</code> otherwise
     */
    private boolean deleteDirectoryImpl(String path) {
        File directory = new File(path);

        // If the directory exists then delete
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return true;
            }
            // Run on all sub files and folders and delete them
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectoryImpl(files[i].getAbsolutePath());
                } else {
                    files[i].delete();
                }
            }
        }
        return directory.delete();
    }

    /**
     * Get all files under the directory
     *
     * @param directory directory name
     * @param out       list of file
     */
    private void getDirectoryFilesImpl(File directory, List<File> out) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return;
            } else {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        getDirectoryFilesImpl(files[i], out);
                    } else {
                        out.add(files[i]);
                    }
                }
            }
        }
    }

    /**
     * Close input and output stream
     *
     * @param closeable closable
     */
    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }


    /**
     * Get actual path from URI
     * This is for ***onActivityResult*** URI only.
     *
     * @param context    context
     * @param contentUri image uri
     * @return path of image uri
     */
    public static String getRealStoragePathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else {
                return "";
            }
        } catch (Exception e) {
            LogHelper.w(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri file uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }


    /**
     * Get mime type of file
     *
     * @param file file
     * @return The MIME type for the given file.
     */
    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

}

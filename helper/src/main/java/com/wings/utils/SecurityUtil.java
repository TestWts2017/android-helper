package com.wings.utils;

import com.wings.helper.LogHelper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Purpose: Security utils for encryption, xor and more
 *
 * @author NikunjD
 * Created on June 10, 2019
 * Modified on June 10, 2019
 */
public class SecurityUtil {

    private static final String TAG = "SecurityUtil";

    /**
     * Encrypt or Descrypt the content. <br>
     *
     * @param content        The content to encrypt or descrypt.
     * @param encryptionMode Use: {@link Cipher#ENCRYPT_MODE} or
     *                       {@link Cipher#DECRYPT_MODE}
     * @param secretKey      Set the secret key for encryption of file content.
     *                       <b>Important: The length must be 16 long</b>. <i>Uses SHA-256
     *                       to generate a hash from your key and trim the result to 128
     *                       bit (16 bytes)</i>
     * @param ivx            This is not have to be secret. It used just for better
     *                       randomizing the cipher. You have to use the same IV parameter
     *                       within the same encrypted and written files. Means, if you
     *                       want to have the same content after descryption then the same
     *                       IV must be used. <i>About this parameter from wiki:
     *                       https://en.wikipedia.org/wiki/Block_cipher_modes_of_operation
     *                       #Initialization_vector_.28IV.29</i> <b>Important: The length
     *                       must be 16 long</b>
     * @return
     */
    public static byte[] encrypt(byte[] content, int encryptionMode, final byte[] secretKey, final byte[] ivx) {
        if (secretKey.length != 16 || ivx.length != 16) {
            LogHelper.w(TAG, "Set the encryption parameters correctly. The must be 16 length long each");
            return null;
        }

        try {
            SecretKey secretkey = new SecretKeySpec(secretKey, CipherAlgorithmType.AES.getAlgorithmName());
            IvParameterSpec IV = new IvParameterSpec(ivx);
            String transformation = CipherTransformationType.AES_CBC_PKCS5Padding;
            Cipher decipher = Cipher.getInstance(transformation);
            decipher.init(encryptionMode, secretkey, IV);
            return decipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt - Unknown Algorithm");
            return null;
        } catch (NoSuchPaddingException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt- Unknown Padding");
            return null;
        } catch (InvalidKeyException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt - Invalid Key");
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt - Invalid Algorithm Parameter");
            return null;
        } catch (IllegalBlockSizeException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt");
            return null;
        } catch (BadPaddingException e) {
            LogHelper.e(TAG, "Failed to encrypt/decrypt");
            return null;
        }
    }

    /**
     * Do xor operation on the string with the key
     *
     * @param msg The string to xor on
     * @param key The key by which the xor will work
     * @return The string after xor
     */
    public String xor(String msg, String key) {
        try {
            final String UTF_8 = "UTF-8";
            byte[] msgArray;

            msgArray = msg.getBytes(UTF_8);

            byte[] keyArray = key.getBytes(UTF_8);

            byte[] out = new byte[msgArray.length];
            for (int i = 0; i < msgArray.length; i++) {
                out[i] = (byte) (msgArray[i] ^ keyArray[i % keyArray.length]);
            }
            return new String(out, UTF_8);
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

}

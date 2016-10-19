package com.nnk.upstream.util;



import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 *  AES-128-CBC加解密模式
 * @author libai
 */
public class AESUtils {
	
	/**
	 * 加密
	 * @param encData 要加密的数据
	 * @param secretKey 密钥 ,16位的数字和字母
	 * @param vector 初始化向量,16位的数字和字母
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String encData ,String secretKey,String vector)  {
		
		if(secretKey == null) {
            throw new IllegalArgumentException("AES key is empty");
		}
		if(secretKey.length() != 16) {
            throw new IllegalArgumentException("AES key length is not equals 16");
		}
		byte[] raw = secretKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = null;// "算法/模式/补码方式"
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(encData.getBytes());
//		for (int i = 0; i < encrypted.length; i++) {
//			System.out.print(encrypted[i]);
//		}
//		System.out.println(encrypted.toString());

		return ObjectSerializer.encodeBytes(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
	}
		
	/**
	 * 转字节数组
	 * @param str
	 * @return
	 */
	public static byte[] decodeBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			char c = str.charAt(i);
			bytes[i / 2] = (byte) ((c - 'a') << 4);
			c = str.charAt(i + 1);
			bytes[i / 2] += (c - 'a');
		}
		return bytes;
	}	
}

package com.nnk.template.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {

	private static final String DEFAULT_CHARSET = "GBK";

	public static String encode(String str) {
		return encode(str, DEFAULT_CHARSET);
	}

	public static String encode(String str, String charsetName) {
		String encodeSql = null;
		try {
			byte[] enbytes = new Base64().encode(str.getBytes(charsetName));
			encodeSql = new String(enbytes);
		} catch (UnsupportedEncodingException e) {
		}
		return encodeSql;
	}

	public static String decode(String str) {
		return decode(str, DEFAULT_CHARSET);
	}

	public static String decode(String str, String charsetName) {
		byte[] debytes = new Base64().decode(str.getBytes());
		String decodeSql = null;
		try {
			decodeSql = new String(debytes, charsetName);
		} catch (UnsupportedEncodingException e) {
		}
		return decodeSql;
	}

	public static byte[] decodeBytes(String str) {
		byte[] debytes = new Base64().decode(str.getBytes());
		return debytes;
	}

	public static String encodeToString(byte[] binaryData) {
		return new Base64().encodeToString(binaryData);
	}

}

/**
 * <b>����:</b>com.nnk.smsrecv.utils</br>
 */
package com.nnk.template.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * <b>�����ƣ�</b>ShellRunner<br/>
 * <b>��������</b>TODO<br/>
 * <b>�����ˣ�</b>y<br/>
 * <b>�޸��ˣ�</b>y<br/>
 * <b>�޸�ʱ�䣺</b>2016-2-24 ����10:40:30<br/>
 * <b>�޸ı�ע��</b><br/>
 * <b>�汾@version:</b></br/>
 */
public class ShellRunner {
	
	public static String CallCmd(String locationCmd){
		Process p = null;
		InputStreamReader in = null;
		BufferedReader bf = null;
		
		try {
			p = Runtime.getRuntime().exec(new String[]{"cmd","/C",locationCmd});
			in = new InputStreamReader(p.getInputStream());
			bf = new BufferedReader(in);
			String text = "";
			String line = null;
			while ((line = bf.readLine()) != null)
				text = text + line +"\n";
				return text;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}

/**
 * <b>包名:</b>com.nnk.smsrecv.utils</br>
 */
package com.nnk.template.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * <b>类名称：</b>ShellRunner<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>y<br/>
 * <b>修改人：</b>y<br/>
 * <b>修改时间：</b>2016-2-24 上午10:40:30<br/>
 * <b>修改备注：</b><br/>
 * <b>版本@version:</b></br/>
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

/**
 * <b>����:</b>demo</br>
 */
package com.nnk.template.win;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

/**
 * 
 * <b>�����ƣ�</b>User32Ext<br/>
 * <b>��������</b>TODO<br/>
 * <b>�����ˣ�</b>y<br/>
 * <b>�޸��ˣ�</b>y<br/>
 * <b>�޸�ʱ�䣺</b>2016-5-10 ����11:22:21<br/>
 * <b>�޸ı�ע��</b><br/>
 * <b>�汾@version:</b></br/>
 */
public interface User32Ext extends User32 {
	User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);
    
	/**
	* ���Ҵ���
	* @param lpParent ��Ҫ���Ҵ��ڵĸ�����
	* @param lpChild ��Ҫ���Ҵ��ڵ��Ӵ���
	* @param lpClassName ����
	* @param lpWindowName ������
	* @return �ҵ��Ĵ��ڵľ��
	*/
	HWND FindWindowEx(HWND lpParent, HWND lpChild, String lpClassName, String lpWindowName);
	  
	/**
	* ��ȡ���洰�ڣ��������Ϊ���д��ڵ�root
	* @return ��ȡ�Ĵ��ڵľ��
	*/
	HWND GetDesktopWindow();
	      
	/**
	* �����¼���Ϣ
	* @param hWnd �ؼ��ľ��
	* @param dwFlags �¼�����
	* @param bVk ���ⰴ����
	* @param dwExtraInfo ��չ��Ϣ����0����
	* @return
	*/
	int SendMessage(HWND hWnd, int dwFlags, byte bVk, int dwExtraInfo);
	  
	/**
	* �����¼���Ϣ
	* @param hWnd �ؼ��ľ��
	* @param Msg �¼�����
	* @param wParam ��0����
	* @param lParam ��Ҫ���͵���Ϣ������ǵ��������null
	* @return
	*/
	int SendMessage(HWND hWnd, int Msg, int wParam, String lParam);
	      
	/**
	* ���ͼ����¼�
	* @param bVk ���ⰴ����
	* @param bScan �� ((byte)0) ����
	* @param dwFlags �����¼�����
	* @param dwExtraInfo ��0����
	*/
	void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
	      
	/**
	* ����ָ�����ڣ�����꽹�㶨λ��ָ�����ڣ�
	* @param hWnd �輤��Ĵ��ڵľ��
	* @param fAltTab �Ƿ���С�����ڻ�ԭ
	*/
	void SwitchToThisWindow(HWND hWnd, boolean fAltTab);

}

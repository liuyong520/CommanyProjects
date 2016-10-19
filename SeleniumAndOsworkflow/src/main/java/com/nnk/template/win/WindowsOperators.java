/**
 * <b>包名:</b>demo</br>
 */
package com.nnk.template.win;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.W32APIOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.nnk.template.win.Win32MessageConstants.*;

/**
 * 
 * <b>类名称：</b>WindowsOperators<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>y<br/>
 * <b>修改人：</b>y<br/>
 * <b>修改时间：</b>2016-5-10 上午09:12:27<br/>
 * <b>修改备注：</b><br/>
 * <b>版本@version:</b></br/>
 */
public class WindowsOperators {
    public static final Logger log = Logger.getLogger(WindowsOperators.class);
	final protected static User32Ext user32 = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);



    private static final class finalHandleContainer
    {  
        private HWND handle;
    }  
	// -----------------------privates-------------------------------  

	    /**
	     * get browser frame's window handle by class name and window title
	     *
	     * @param browserClassName
	     *            : browser window's class name, you can get it by spy++
	     * @param browserTitle
	     * @return
	     */
	    private static HWND getWindowHandle(String browserClassName,
                                            String browserTitle) {

	        HWND deskTopHandle = user32.GetDesktopWindow();

	        char[] winClass = new char[100];
	        user32.GetClassName(deskTopHandle, winClass, 100);

	        //log.info(winClass);

	        if( deskTopHandle == null )
	            throw new RuntimeException("Can not find the desktop's window handle!");


	        return getSpecifiedWindowHandle(deskTopHandle,browserClassName,browserTitle);
	    }


    /** In term of  edit control, its class name changes very time. And I come up with one solution: the window class name with only one instance may be the alipay edit, others are not definitely. But times always change, so maybe this is not a permanently valid way! Cautions!
     * Get several potential edit window. As sending messages to all the sub windows are not efficient, so it is necessary to shrink the range.
     * @param browserWindowHandle
     * @return
     */
    private static List<HWND> getPotentialEditHandle(HWND browserWindowHandle) {

        Map<String,List<HWND>> candidates = getChildWindowClassHandleMap(browserWindowHandle);
        List<HWND> resultList = new ArrayList<HWND>();

        for(Iterator it =candidates.entrySet().iterator();it.hasNext();)
        {
            Map.Entry<String, List<HWND>> next = (Map.Entry<String, List<HWND>>) it.next();
            List<HWND> tempList = next.getValue();
            if(tempList.size()==1)
            {
                resultList.add(tempList.get(0));
            }else{
                resultList.addAll(tempList);
            }
        }

        return resultList;
    }


	    /** search the children windows of the father window specified by father-handle and find the child window with specified className and substring of title
	     * @param fatherHandle  handle of the father window
	     * @param className  className of the child window
	     * @param title title of the child window
	     * @return
	     */
	    private static HWND getSpecifiedWindowHandle(final HWND fatherHandle, final String className,final String title)
	    {
	        final finalHandleContainer windowHandleContainer = new finalHandleContainer();
            if(null == className || className.length() <= 0) {
                return null;
            }
            HWND[] result = new HWND[1];
            Win32Util.findHandle(result, fatherHandle, className,title);
            windowHandleContainer.handle = result[0];
	        return windowHandleContainer.handle;
	    }

	      
	    /** get all children's class-names and handles under specified father windows, and store them in a map.
	     * @param fatherHandle the handle of father window
	     * @return a map stores every class name and its window handles, e.g., key="CLASSONE", value="12312"
	     */
	    private static Map<String,List<HWND>> getChildWindowClassHandleMap(HWND fatherHandle)
	    {
	        final finalHandleContainer alipayWindowHandleContainer = new finalHandleContainer();

	        final Map<String,List<HWND>> resultMap = new HashMap<String,List<HWND>>();

	        boolean result = user32.EnumChildWindows(fatherHandle, new WNDENUMPROC() {
	            @Override
	            public boolean callback(HWND hWnd, Pointer data) {
	                // TODO Auto-generated method stub

	                char[] winClass = new char[100];
	                user32.GetClassName(hWnd, winClass, 100);
	                if(user32.IsWindowVisible(hWnd))
	                {
	                    String tempClassName = Native.toString(winClass);
	                    if(resultMap.containsKey(tempClassName))
	                    {
	                        resultMap.get(tempClassName).add(hWnd);
	                    }
	                    else
	                    {
	                        List<HWND> tempHWNDList = new ArrayList<HWND>();
	                        tempHWNDList.add(hWnd);
	                        resultMap.put(tempClassName, tempHWNDList);
	                    }
	                }
	                return true;
	            }
	        }, Pointer.NULL);

	        return resultMap;
	    }

	      
	    // -----------------------publics--------------------------------  
	    /** 
	     * Set password to all potential alipay password edit controls embedded in the browser page 
	     *  
	     * @param browserClassName 
	     *            : browser's class name. e.g., MozillaWindowClass for firefox 
	     * @param browserTitleOrSubTitle 
	     *            : browser frame's title, or you can just specified a sub-title 
	     *            which can mark the alipay window uniquely. E.g., frame's title 
	     *            is "123456-scs**0s90", you can just utilize "234" if it is ok. 
	     * @param className 
	     *            : alipay edit frame's class name. 
	     * @param title 
	     *            : alipay edit frame's title. 
	     * @param input
	     *            : the password. 
	     * @return 
	     * @throws InterruptedException  
	     */  
	    public static boolean setInput(String browserClassName,
                                       String browserTitleOrSubTitle, String windClassName, String input) throws InterruptedException {
	        HWND browserWindowHandle = getWindowHandle(browserClassName,
                    browserTitleOrSubTitle);
	  
	        log.info("------------------------------------------------------------------------------------------------------");  
	          
	        List<HWND> list = getPotentialEditHandle(browserWindowHandle);
	        for (HWND hwnd : list) {
	        	char[] winClass = new char[100];  
                char[] winText = new char[200];  
                user32.GetClassName(hwnd, winClass, 100);  
                user32.GetWindowText(hwnd, winText, 200);  
                String windClass = Native.toString(winClass);
                if(windClass.equals(windClassName)){
                	user32.SwitchToThisWindow(hwnd, true);
                	user32.SetFocus(hwnd);
                	for(char c:input.toCharArray()){
                	    TimeUnit.MILLISECONDS.sleep(500);  
                		user32.SendMessage(hwnd,Win32MessageConstants.WM_CHAR, (byte)c,0);
                	}
                }  
                log.debug("Class Name:"+ Native.toString(winClass)+"   Title:"+ Native.toString(winText));
			}
//	        System.err.println(list);
	        
//	        for (char c : password.toCharArray()) {
//	            TimeUnit.MILLISECONDS.sleep(500);
//	            for(HWND handle:list)
//	            	user32.SwitchToThisWindow(hWnd, fAltTab);
//	            	user32.SendInput(dword, WinUser.INPUT.INPUT_KEYBOARD,);
//	                user32.SendMessag(handle, WinUser.WM_CHAR, (byte) c, 0);
//	        }
	  
	        return  true;  
	    }
    public static boolean keyDown(String browserClassName,
                                      String browserTitleOrSubTitle,String windowClassName,int[][]keyCombination) throws InterruptedException {
        HWND browserWindowHandle = getWindowHandle(browserClassName,
                browserTitleOrSubTitle);
        List<HWND> list = getPotentialEditHandle(browserWindowHandle);
        for (HWND hwnd : list) {
            char[] winClass = new char[100];
            char[] winText = new char[200];
            user32.GetClassName(hwnd, winClass, 100);
            user32.GetWindowText(hwnd, winText, 200);
            String windClass = Native.toString(winClass);
            if(windClass.equals(windowClassName) && Native.toString(winText).contains(browserTitleOrSubTitle)) {
                user32.SwitchToThisWindow(hwnd, true);
                user32.SetFocus(hwnd);
                for (int[] keys : keyCombination) {
                    for (int i = 0; i < keys.length; i++) {
                        user32.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYDOWN, 0); // key down
                    }
                    for (int i = keys.length - 1; i >= 0; i--) {
                        user32.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYUP, 0); // key up
                    }
                }
             Thread.sleep(5000);
             Win32Util.simulateEnter(hwnd);
            }
            log.debug("Class Name:" + Native.toString(winClass) + "   Title:" + Native.toString(winText));
        }
        return true;
    }
    public static String getWindowsTitleName(String browserClassName, String browserTitleOrSubTitle, String windowsClassName) {
        //先找浏览器父窗口
        HWND browserWindowHandle = getWindowHandle(browserClassName,
                browserTitleOrSubTitle);
        List<HWND> list = getPotentialEditHandle(browserWindowHandle);
        for (HWND hwnd : list) {
            char[] winClass = new char[100];
            char[] winText = new char[200];
            user32.GetClassName(hwnd, winClass, 100);
            user32.GetWindowText(hwnd, winText, 200);
            String windClass = Native.toString(winClass);
            log.info("Class Name:" + Native.toString(winClass) + "   Title:" + Native.toString(winText));
            if(windClass.equals(windowsClassName) && Native.toString(winText).contains(browserTitleOrSubTitle)) {
                user32.SwitchToThisWindow(hwnd, true);
                user32.SetFocus(hwnd);
                return Native.toString(winText);
            }
        }
        return null;

    }
}

package com.nnk.template.win;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.W32APIOptions;
import org.apache.log4j.Logger;

import static com.nnk.template.win.Win32MessageConstants.*;
/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/26
 * Time: 10:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class Win32Util {
    public static final Logger log = Logger.getLogger(Win32Util.class);
    private static final int N_MAX_COUNT = 512;
    final protected static User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);
    private Win32Util() {
    }

    /**
     * �����濪ʼ����ָ��������������ڳ�ʱ��ʱ�䷶Χ�ڣ����δ�ҵ��κ�ƥ�������򷴸�����
     * @param className ���������
     * @param timeout ��ʱʱ��
     * @param unit ��ʱʱ��ĵ�λ
     * @return ����ƥ�������ľ�������ƥ����������һ�������ص�һ�����ҵĵ��ģ����δ�ҵ���ʱ�򷵻�<code>null</code>
     */
    public static HWND findHandleByClassName(String className, long timeout, TimeUnit unit) {
        return findHandleByClassName(USER32EXT.GetDesktopWindow(), className, timeout, unit);
    }

    /**
     * �����濪ʼ����ָ�����������
     * @param className ���������
     * @return ����ƥ�������ľ�������ƥ����������һ�������ص�һ�����ҵĵ��ģ����δ�ҵ��κ�ƥ���򷵻�<code>null</code>
     */
    public static HWND findHandleByClassName(String className) {
        return findHandleByClassName(USER32EXT.GetDesktopWindow(), className);
    }

    /**
     * ��ָ��λ�ÿ�ʼ����ָ�����������
     * @param root �����������ʼλ�õ�����ľ�������Ϊ<code>null</code>������濪ʼ����
     * @param className ���������
     * @param timeout ��ʱʱ��
     * @param unit ��ʱʱ��ĵ�λ
     * @return ����ƥ�������ľ�������ƥ����������һ�������ص�һ�����ҵĵ��ģ����δ�ҵ���ʱ�򷵻�<code>null</code>
     */
    public static HWND findHandleByClassName(HWND root, String className, long timeout, TimeUnit unit) {
        if(null == className || className.length() <= 0) {
            return null;
        }
        long start = System.currentTimeMillis();
        HWND hwnd = findHandleByClassName(root, className);
        while(null == hwnd && (System.currentTimeMillis() - start < unit.toMillis(timeout))) {
            hwnd = findHandleByClassName(root, className);
        }
        return hwnd;
    }

    /**
     * ��ָ��λ�ÿ�ʼ����ָ�����������
     * @param root �����������ʼλ�õ�����ľ�������Ϊ<code>null</code>������濪ʼ����
     * @param className ���������
     * @return ����ƥ�������ľ�������ƥ����������һ�������ص�һ�����ҵĵ��ģ����δ�ҵ��κ�ƥ���򷵻�<code>null</code>
     */
    public static HWND findHandleByClassName(HWND root, String className) {
        if(null == className || className.length() <= 0) {
            return null;
        }
        HWND[] result = new HWND[1];
        findHandle(result, root, className);
        return result[0];
    }

    /**
     *
     * @param target
     *        �ҵ�Ŀ�괰�ڵĴ�ŵ�ַ
     * @param root
     *        ������
     * @param className
     *        ��������className���������spy++ ��ȡ
     * @return
     */
    public static boolean findHandle(final HWND[] target, HWND root, final String className) {
        if(null == root) {
            root = USER32EXT.GetDesktopWindow();
        }
        return USER32EXT.EnumChildWindows(root, new WNDENUMPROC() {

            @Override
            public boolean callback(HWND hwnd, Pointer pointer) {
                char[] winClass = new char[N_MAX_COUNT];
                USER32EXT.GetClassName(hwnd, winClass, N_MAX_COUNT);
                if(USER32EXT.IsWindowVisible(hwnd) && className.equals(Native.toString(winClass))) {
                    target[0] = hwnd;
                    return false;
                } else {
                    return target[0] == null || findHandle(target, hwnd, className);
                }
            }

        }, Pointer.NULL);
    }
    /**
     *
     * @param target
     *        �ҵ�Ŀ�괰�ڵĴ�ŵ�ַ
     * @param root
     *        ������
     * @param className
     *        ��������className���������spy++ ��ȡ
     * @param title
     *        ���ڱ���
     * @return
     */
    public static boolean findHandle(final HWND[] target, HWND root, final String className,final String title) {
        if(null == root) {
            root = USER32EXT.GetDesktopWindow();
        }
        return USER32EXT.EnumChildWindows(root, new WNDENUMPROC() {

            @Override
            public boolean callback(HWND hwnd, Pointer pointer) {
                char[] winClass = new char[N_MAX_COUNT];
                USER32EXT.GetClassName(hwnd, winClass, N_MAX_COUNT);
                char[] winText = new char[N_MAX_COUNT];
                USER32EXT.GetWindowText(hwnd, winText, N_MAX_COUNT);
                log.debug("Class Name:"+ Native.toString(winClass)+"   Title:"+ Native.toString(winText));
                if(USER32EXT.IsWindowVisible(hwnd) && className.equals(Native.toString(winClass))&& Native.toString(winText).contains(title)) {
                    target[0] = hwnd;
                    return false;
                } else {
                    return target[0] == null || findHandle(target, hwnd, className,title);
                }
            }

        }, Pointer.NULL);

    }

    /**
     * ģ����̰����¼����첽�¼���ʹ��win32 keybd_event��ÿ�η���KEYEVENTF_KEYDOWN��KEYEVENTF_KEYUP�����¼���Ĭ��10�볬ʱ
     * @param hwnd �����̲�����������
     * @param keyCombination ���̵����ⰴ���루<a href="http://msdn.microsoft.com/ZH-CN/library/windows/desktop/dd375731.aspx">Virtual-Key Code</a>��������ʹ��{@link java.awt.event.KeyEvent}</br>
     * 						��ά�����һά�е�һ��Ԫ��Ϊһ�ΰ���������������ϲ������ڶ�ά�е�һ��Ԫ��Ϊһ�������¼�����һ�����ⰴ����
     * @return ���̰����¼�����windows��Ϣ���гɹ�����<code>true</code>�����̰����¼�����windows��Ϣ����ʧ�ܻ�ʱ����<code>false</code>
     */
    public static boolean simulateKeyboardEvent(HWND hwnd, int[][] keyCombination) {
        if(null == hwnd) {
            return false;
        }
        USER32EXT.SwitchToThisWindow(hwnd, true);
        USER32EXT.SetFocus(hwnd);
        for(int[] keys : keyCombination) {
            for(int i = 0; i < keys.length; i++) {
                USER32EXT.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYDOWN, 0); // key down
            }
            for(int i = keys.length - 1; i >= 0; i--) {
                USER32EXT.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYUP, 0); // key up
            }
        }
        return true;
    }

    /**
     * ģ���ַ����룬ͬ���¼���ʹ��win32 SendMessage API����WM_CHAR�¼���Ĭ��10�볬ʱ
     * @param hwnd �������ַ�������ľ��
     * @param content ��������ݡ��ַ����ᱻת����<code>char[]</code>������ַ�����
     * @return �ַ������¼����ͳɹ�����<code>true</code>���ַ������¼�����ʧ�ܻ�ʱ����<code>false</code>
     */
    public static boolean simulateCharInput(final HWND hwnd, final String content) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    for(char c : content.toCharArray()) {
                        Thread.sleep(5);
                        USER32EXT.SendMessage(hwnd, WM_CHAR, (byte) c, 0);
                    }
                    return true;
                }

            });
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean simulateCharInput(final HWND hwnd, final String content, final long sleepMillisPreCharInput) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    for(char c : content.toCharArray()) {
                        Thread.sleep(sleepMillisPreCharInput);
                        USER32EXT.SendMessage(hwnd, WM_CHAR, (byte) c, 0);
                    }
                    return true;
                }

            });
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * ģ���ı����룬ͬ���¼���ʹ��win32 SendMessage API����WM_SETTEXT�¼���Ĭ��10�볬ʱ
     * @param hwnd �������ı�������ľ��
     * @param content ������ı�����
     * @return �ı������¼����ͳɹ�����<code>true</code>���ı������¼�����ʧ�ܻ�ʱ����<code>false</code>
     */
    public static boolean simulateTextInput(final HWND hwnd, final String content) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    USER32EXT.SendMessage(hwnd, WM_SETTEXT, 0, content);
                    return true;
                }

            });
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * ģ���������ͬ���¼���ʹ��win32 SendMessage API����BM_CLICK�¼���Ĭ��10�볬ʱ
     * @param hwnd �����������ľ��
     * @return ����¼����ͳɹ�����<code>true</code>������¼�����ʧ�ܻ�ʱ����<code>false</code>
     */
    public static boolean simulateClick(final HWND hwnd) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SendMessage(hwnd, BM_CLICK, 0, null);
                    return true;
                }

            });
        } catch(Exception e) {
            return false;
        }
    }
    /**
     * ģ�����enter���룬ͬ���¼���ʹ��win32 SendMessage API����BM_CLICK�¼���Ĭ��10�볬ʱ
     * @param hwnd �����������ľ��
     * @return ����¼����ͳɹ�����<code>true</code>������¼�����ʧ�ܻ�ʱ����<code>false</code>
     */
    public static boolean simulateEnter(final HWND hwnd) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SendMessage(hwnd, WinUser.WM_KEYDOWN, VK_RETURN, null);
                    return true;
                }

            });
        } catch(Exception e) {
            return false;
        }
    }
    private static <T> T execute(Callable<T> callable) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<T> task = executor.submit(callable);
            return task.get(10, TimeUnit.SECONDS);
        } finally {
            executor.shutdown();
        }
    }
}

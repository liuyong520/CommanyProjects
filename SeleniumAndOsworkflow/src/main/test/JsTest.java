import com.nnk.utils.js.JsExecutor;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/2
 * Time: 11:31
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class JsTest {
    @Test
    public void testJs() throws Exception {
        JsExecutor.executeJsfile("D:\\ideaWorkSpace\\SeleniumAndOsworkflow\\src\\main\\resources\\google.js","iimPlay","google");
    }
}

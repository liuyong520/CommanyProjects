import com.nnk.template.selenium.SeleniumUtils;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/1
 * Time: 19:16
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class Regex{
    @Test
    public void testRegex() throws Exception {
        String text = "${heello}";
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()&&matcher.groupCount()>=1) {
            String content = matcher.group(1);
            System.out.println(content);
        }

    }
}

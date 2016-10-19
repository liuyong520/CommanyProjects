import com.nnk.template.entity.OperationDescprition;
import com.nnk.template.function.Contstant;
import com.nnk.template.util.XmlMapPharser;
import com.nnk.utils.http.utils.StringUtil;
import com.nnk.utils.js.JsoupUtils;
import com.opensymphony.module.propertyset.PropertySet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.print.attribute.standard.OrientationRequested;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/10/10
 * Time: 9:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class PharseHtmlTest {
    public static final Logger log = Logger.getLogger(PharseHtmlTest.class);
    public static Map<String,Object> Context = new HashMap<String, Object>();

    @BeforeMethod
    public void setUp() throws Exception {
        Context.put("optList",new ArrayList<OperationDescprition>());
    }

    @Test
    public void testPharseHtml() throws IOException {
        String htmlContent = FileUtils.readFileToString(new File("D:\\ideaWorkSpace\\SeleniumAndOsworkflow\\src\\main\\resources\\2-90.html"));
        Document document = JsoupUtils.parseHtmlByString(htmlContent);

        Elements element = JsoupUtils.getElementsByTagName(document,"link");
        Element element1 = element.first();
        //获取访问浏览器的基础地址
        String baseUrl = element1.attr("href");
        log.info("baseUrl:" + baseUrl);
        log.info("begin to open the browser");
        log.info("open the browser and get baseUrl:" + baseUrl);
        Elements elements = JsoupUtils.getElementsByTagName(document, "tbody");

        Elements trs = JsoupUtils.getElementsByTagName(elements.first(),"tr");
        String t = JsoupUtils.getSingElementHtml(trs);
        int i = 0;
        for (Element tr :trs){
//            if(++i<=10)continue;
            parseHtmltrTag(tr,Context);
        }
        System.out.println(Context);
    }

    @Test
    public void testSelenium(){
        
    }

    public static void parseHtmltrTag(Element tr,Map context){
        int first = 0;
        boolean condition = false;
        boolean loop = false;
        boolean common = false;
        Elements elements = tr.children();
        Element firstNode = elements.first();
        String attr = firstNode.attr("type");
        if(StringUtil.isEmpty(attr)||"common".equals(attr)){
            common = true;
        }else if("condition".equals(attr)){
            condition=true;
        }else if("loop".equals(attr)){
            loop = true;
        }
         if(condition){
            parseTagtrCondtion(elements,context);
        }else if(loop){
            parseTagtrLoop(elements,context);
        }else if(common){
            parseTagtrCommon(elements,context);
        }
    }

    private static void parseTagtrCommon(Elements elements, Map context) {
        //获取最后一次操作
        OperationDescprition lastest = getlastest(context);
        String[] parent = new String[4];
        OperationDescprition parentDescrition = null;
        int k = 0;
        for(Element element:elements){
            Elements ext = element.getElementsByTag("ext");
            if(ext!=null && ext.size()!=0){
                //如果有级联附属操作，且级联操作只能有一步
                Iterator it = ext.iterator();
                String[] tmp = new String[4];
                int i = 0;
                while(it.hasNext()){
                    Element extOpt = (Element) it.next();
                    tmp[i] = extOpt.text();
                    if(i>=3){
                        OperationDescprition extdecription = new OperationDescprition(tmp[0],"common",tmp[1],tmp[2],tmp[3]);
                        extdecription.setCascade(true);
                        //clear tmp;
                        tmp = new String[4];

                        i = 0;
                        //如果上级操作不为空
                        if(parentDescrition!=null){
                            //关联起来
                            parentDescrition.setNext(extdecription);
                            extdecription.setLast(parentDescrition);
                        }
                    }
                    i++;
                }
            //如果没有表示不是没有附加操作
            }else {
                parent[k] = element.text();

                if(k >= 2){
                    parentDescrition = new OperationDescprition(parent[0],"common",parent[1],parent[2],parent[3]);
                    parent = new String [4];

                    k = 0;
                    if(lastest!=null){
                        lastest.setNext(parentDescrition);
                        parentDescrition.setLast(lastest);
                    }
                    List<OperationDescprition> list = (List<OperationDescprition>) context.get("optList");
                    list.add(parentDescrition);
                }
                k++;
            }
        }

    }

    private static void parseTagtrLoop(Elements elements, Map context) {

    }
    private static OperationDescprition getlastest(Map context){
        List<OperationDescprition> list = (List) context.get("optList");
        OperationDescprition lastest = null;
        if(list!=null && !list.isEmpty()){
            lastest = list.get(list.size()-1);
        }
        return lastest;
    }
    private static void parseTagtrCondtion(Elements elements, Map context) {
        OperationDescprition lastest = getlastest(context);
        Iterator it = elements.iterator();
        System.out.println(elements.html());
        String textAll = elements.text();
        String[] temp = new String[4];
        Stack<OperationDescprition> stack = new Stack<OperationDescprition>();
        System.out.println(elements.size());
        int i = 0;
        while (it.hasNext()){
            Element element = (Element) it.next();
            String text = element.text();
            if(StringUtil.isNotEmpty(text)){
                temp[i++] = text;
            }else {
                int m = i;
                while(4-m > 0){
                    temp[m]="";
                    m++;
                }
                //如果是条件语句的条件声明操作则跳过。
                if ("IFTestStart".equals(temp[0]) && "Equal".equals(temp[1])) {
                    temp = new String[4];
                    i = 0;
                    continue;
                }else if("IFTestEnd".equals(temp[0])){
                    break;
                } else {
                    OperationDescprition conditiondescription = null;
                    conditiondescription = new OperationDescprition(temp[0], "condition", temp[1], temp[2],temp[3]);
                    conditiondescription.setCondition(true);
                    if(stack.isEmpty()){
                        stack.push(conditiondescription);
                    }else {
                        OperationDescprition olddescprition = stack.pop();
                        if(olddescprition.getRight()!=null && olddescprition.getLeft()!=null){
                            throw new IllegalArgumentException("the config file configuration condition is error");
                        }else if(olddescprition.getRight()==null){
                            olddescprition.setRight(conditiondescription);
                        }else if(olddescprition.getLeft()==null){
                            olddescprition.setLeft(conditiondescription);
                        }
                        stack.push(olddescprition);
                    }
                }
                temp = new String[4];
                i = 0;
            }
        }
        OperationDescprition olddescprition = stack.pop();

        if(lastest!=null){
            lastest.setNext(olddescprition);
            olddescprition.setLast(lastest);
        }
        List<OperationDescprition> list = (List<OperationDescprition>) context.get("optList");
        list.add(olddescprition);

    }

    public static void parseHtmltdTag(String tdstring,Map context){

    }
    public static String getMatchedByRegex(String value) {
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()&&matcher.groupCount()>=1) {
            String content = matcher.group(1);
            return content;
        }
        return null;
    }
}

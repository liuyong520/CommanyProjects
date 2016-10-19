import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import junit.framework.TestCase;

import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/17
 * Time: 16:32
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class OsworkflowTest extends TestCase {
    public void testLoadxml() throws WorkflowException {
        BasicWorkflow workflow = new BasicWorkflow("test");

        String name = getClass().getResource("/wf_login.xml").toString();
        System.out.println("name:" + name);
        long id = workflow.initialize(name, 100, null);
        int[] availableActions = workflow.getAvailableActions(id, null);
        System.out.println(availableActions.length);
        Map args =  workflow.getConfiguration().getPersistenceArgs();
        System.out.println(args);
        for (Object key:args.keySet()){
            System.out.println("key"+key+"value:"+args.get(key));
        }
        PropertySet propertySet = workflow.getPropertySet(id);
        System.out.println(propertySet);
        Properties properties = workflow.getPersistenceProperties();
        System.out.println(properties);
    }
}

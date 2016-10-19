import com.opensymphony.workflow.basic.BasicWorkflow;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/12
 * Time: 14:32
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class LoginTest extends TestCase {
    public void testLogin() throws Exception {


        BasicWorkflow workflow = new BasicWorkflow("test");
        String name = getClass().getResource("/wf_login.xml").toString();
        System.out.println("name:" + name);
        long id = workflow.initialize(name, 100, null);

        int[] availableActions = workflow.getAvailableActions(id, null);
        System.out.println(availableActions.length);
        assertEquals("Unexpected number of available actions", 1, availableActions.length);
        assertEquals("Unexpected available action", 1, availableActions[0]);
        workflow.doAction(id,1,null);
        System.out.println("CurrentSteps" + workflow.getCurrentSteps(id));
        System.out.println("historySteps" + workflow.getHistorySteps(id));
        workflow.doAction(id,3,null);

        System.out.println("CurrentSteps" + workflow.getCurrentSteps(id));
        System.out.println("historySteps" + workflow.getHistorySteps(id));
    }
}

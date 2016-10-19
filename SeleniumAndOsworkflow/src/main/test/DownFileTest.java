import com.opensymphony.workflow.basic.BasicWorkflow;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/24
 * Time: 9:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class DownFileTest extends TestCase{
    public void testDownLoadFile() throws Exception {
        BasicWorkflow workflow = new BasicWorkflow("test");
        String name = getClass().getResource("/wf_fileDown.xml").toString();
        System.out.println("name:" + name);
        long id = workflow.initialize(name, 1, null);

        int[] availableActions = workflow.getAvailableActions(id, null);
        System.out.println(availableActions.length);
        assertEquals("Unexpected number of available actions", 1, availableActions.length);
        assertEquals("Unexpected available action", 2, availableActions[0]);
        workflow.doAction(id,2,null);


    }
}

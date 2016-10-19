import com.opensymphony.user.Group;
import com.opensymphony.user.UserManager;
import com.opensymphony.workflow.basic.BasicWorkflow;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/11
 * Time: 14:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class Mytest1 extends TestCase {
    public void testInitOsworkflow() throws Exception {

        UserManager um = UserManager.getInstance();    //
        com.opensymphony.user.User test = null;
        test = um.createUser("test"); //新建用户test
        Group  foos = um.createGroup("foos");//新建用户haha
        test.addToGroup(foos);   //将test添加到haha组中
        BasicWorkflow workflow = new BasicWorkflow("test");
        String name = getClass().getResource("/wf_mytest1.xml").toString();
        System.out.println("name:" + name);
        long id = workflow.initialize(name, 100, null);
        int[] availableActions = workflow.getAvailableActions(id, null);

        System.out.println(availableActions.length);
        for (int i :availableActions){
            System.out.println("id:"+i);
        }
        assertEquals("Unexpected number of available actions", 1, availableActions.length);
        assertEquals("Unexpected available action", 1, availableActions[0]);
        workflow.doAction(id,1,null);

        System.out.println("CurrentSteps" + workflow.getCurrentSteps(id));
        System.out.println("historySteps" + workflow.getHistorySteps(id));
    }

    public void testlength() throws Exception {
        String str="001330003~BS~16081710312110001~20160817~103123~201608170926220000000000071308~9970~1001~交易成功~20160817~E409BAB57E7A42EAE41DCC439BF8AB18";
        System.out.println(str.getBytes().length);
    }
}

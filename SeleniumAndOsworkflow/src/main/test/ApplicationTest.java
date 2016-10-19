import com.nnk.template.Appliaction;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/18
 * Time: 14:39
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationTest extends TestCase {

    public void testLoadProperties() throws Exception {
        BasicWorkflow workflow = Appliaction.getBasicWorkflowInstance();
        PropertySet propertySet = workflow.getPropertySet(1);
        System.out.println(propertySet);
        String name = getClass().getResource("/wf_login.xml").toString();
        System.out.println("name:" + name);
        long id = workflow.initialize(name,1, null);
        WorkflowDescriptor descriptor = workflow.getWorkflowDescriptor(name);

        int[] availableActions = workflow.getAvailableActions(id, null);
        System.out.println(availableActions.length);
//        assertEquals("Unexpected number of available actions", 1, availableActions.length);
//        assertEquals("Unexpected available action", 1, availableActions[0]);
        List<StepDescriptor> stepList =  workflow.getCurrentSteps(id);
        System.out.println(stepList);
        System.out.println("availableAction:" + Arrays.toString(availableActions));
        for (int i:availableActions) {
            workflow.doAction(id, i, null);
            System.out.println("actionid ------>" + i);
        }
        availableActions = workflow.getAvailableActions(id, null);
        System.out.println("available:" + availableActions.length);
        System.out.println("CurrentSteps" + workflow.getCurrentSteps(id));
        System.out.println("historySteps" + workflow.getHistorySteps(id));
        workflow.doAction(id,3,null);
        System.out.println("CurrentSteps" + workflow.getCurrentSteps(id));
        System.out.println("historySteps" + workflow.getHistorySteps(id));
    }
}

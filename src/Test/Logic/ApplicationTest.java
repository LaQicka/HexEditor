package Test.Logic;

import gui.CenterPanel;
import logic.Application;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @Test
    public void Test_offsetCount(){
        Application app = new Application(4);
        CenterPanel center = new CenterPanel(app);
        int offset = center.offsetCount(10,4);
        assertEquals(8,offset);
        offset = center.offsetCount(15,4);
        assertEquals(12,offset);

        offset = center.offsetCount(5,4);
        assertEquals(4,offset);

        offset = center.offsetCount(11,4);
        assertEquals(9,offset);
    }

    @Test
    public void Test_lengthCount(){
        Application app = new Application(4);
        CenterPanel center = new CenterPanel(app);
        String selected = "ffff\nff";
        int length = center.lengthCount(7,selected);
        assertEquals(6,length);

        selected = "fff\nffff\nff";
        length = center.lengthCount(11,selected);
        assertEquals(9,length);
    }
}

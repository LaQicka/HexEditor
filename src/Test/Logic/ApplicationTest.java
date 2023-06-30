package Test.Logic;

import gui.CenterPanel;
import logic.Application;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @Test
    public void Test_offsetCount(){
        /*

        Text Field    \n

        00 01 02 03 | 04            00 01 02 03
        05 06 07 08 | 09   ======>  04 05 06 07
        10 11 12 13 | 14            08 09 10 11
        15 16 17 18 | 19            12 13 14 15

         */
        Application app = new Application(4);
        CenterPanel center = new CenterPanel(app);

//      Nothing Change Test
        int offset = center.offsetCount(1,4);
        assertEquals(1,offset);
//      Single '\n' deletion
        offset = center.offsetCount(6,4);
        assertEquals(5,offset);
//      Many '\n' deletion
        offset = center.offsetCount(16,4);
        assertEquals(13,offset);
//      If caret stay on '\n'
        offset = center.offsetCount(4,4);
        assertEquals(3,offset);
//      If caret stay on '\n' and need to delete some '\n' before it
        offset = center.offsetCount(14,4);
        assertEquals(11,offset);
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

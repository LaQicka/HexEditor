package Test.FileStaff;

import FileStaff.AppFileReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppFileReaderImplTest {

//    private AppFileReader reader;



    @Test
    //  Тестирование чтения файла по страницам(по 1024 байта)
    public void Test_Read(){
        ArrayList<Integer> TestData = new ArrayList<>(Arrays.asList(76, 111, 114, 101, 109, 32, 105, 112, 115, 117));
        AppFileReader reader = new AppFileReader();
        reader.setFilename("C:\\Users\\LaQicka\\Desktop\\Projects\\HexEditor\\src\\Test\\FileStaff\\TestInput");
        ArrayList<Integer> data;
        data = reader.getData(0);
        ArrayList<Integer> OutputData = new ArrayList<>();
        for(int i=0;i<10;i++)OutputData.add(data.get(i));
        assertEquals(TestData,OutputData);

        TestData = new ArrayList<>(Arrays.asList(32, 73, 110, 32, 115, 97, 103, 105, 116, 116));
        data = reader.getData(1);
        OutputData.clear();
        for(int i=0;i<10;i++)OutputData.add(data.get(i));
        assertEquals(TestData,OutputData);
    }

}

package FileStaff;

import java.io.*;
import java.util.ArrayList;

// Класс записи/чтения файлов в байтовом виде
public class AppFileReader {
    private String filename;
    private String sourceFilePath;
    private String tempDir;
    private ArrayList<Integer> data;
    private int bytesPerPage = 1024;
    private long fileSize;
    private int pagesAmount;
    private boolean[] pages;
    private int lastIndex;

//
    public AppFileReader(){

    }

//
    public String getFilename() {
        return filename;
    }

//  Установка исходного файла
    public void setFilename(String filename) {
        try{
            this.data = new ArrayList<>();
            File file = new File(filename);

            this.sourceFilePath = file.getPath();
            this.filename = filename;

            this.fileSize = file.length();
            this.pagesAmount = (int) (this.fileSize/bytesPerPage);
            if(this.fileSize%bytesPerPage != 0)this.pagesAmount++;

            this.pages = new boolean[pagesAmount];

            this.tempDir = file.getPath() + file.getName() + "_TEMPFILES";
            File directory = new File(tempDir);
            directory.mkdir();

        }catch (Exception e){
            System.out.print(e);
        }

    }

//  Чтение определенной страницы из файла
    public void read(int pageIndex) {
        try{
            data.clear();
            // Если страница уже использовалась в текущей сессии, то открываем ее из временных файлов
            if(pages[pageIndex]){
                System.out.println("READ FROM BUFFER" + pageIndex);
                InputStream iStream = new FileInputStream(tempDir+"\\"+String.valueOf(pageIndex));
                int value;
                while ((value = iStream.read()) !=-1 )data.add(value);
                iStream.close();
            }
            // Если страница открывается впервые, то читаем ее из исходного файла
            else {
                System.out.println("READ FROM FILE" + pageIndex);
                RandomAccessFile file = new RandomAccessFile(filename,"r");
                file.seek(pageIndex*bytesPerPage);

                byte[] buf;
                int buf_size = 0;
                if(this.fileSize-pageIndex*bytesPerPage < bytesPerPage)buf_size = (int) (this.fileSize-pageIndex*bytesPerPage);
                else buf_size = bytesPerPage;
                buf = new byte[bytesPerPage];

                file.read(buf);

                for(int i=0;i<buf_size;i++)data.add((int)buf[i]);

                file.close();
            }

        }catch (Exception e){
            System.out.println("READ  :" + e);
        }
    }

//  Метод записи страницы в промежуточный файл
    public void write(int pageIndex, ArrayList<Integer> data){
    try{
        FileOutputStream output = new FileOutputStream(tempDir+"\\"+String.valueOf(pageIndex));

        for(int i=0;i<data.size();i++){
            output.write(data.get(i));
        }

        output.close();
        this.pages[pageIndex] = true;
    }catch (Exception e){
        System.out.println("WRITE :" + e);
    }
}

    public void save(){
        try{
            String finishedFile = this.sourceFilePath + "FINISHED";
            File newFile = new File(finishedFile);
            newFile.createNewFile();

            RandomAccessFile finishedFileRAF = new RandomAccessFile(finishedFile,"rw");
            RandomAccessFile sourceFile = new RandomAccessFile(filename,"r");
            int lastIndex = 0;
            for(int i=0;i<this.pagesAmount;i++){
                if(pages[i]){
                    ArrayList<Integer> dataPart = new ArrayList<>();
                    InputStream iStream = new FileInputStream(tempDir+"\\"+String.valueOf(i));
                    int value;
                    while ((value = iStream.read()) !=-1 )dataPart.add(value);

                    byte[] buf = new byte[dataPart.size()];
                    for(int j = 0;j<dataPart.size();j++)buf[j] = (byte)(int)dataPart.get(j);

                    finishedFileRAF.seek(lastIndex);
                    finishedFileRAF.write(buf);
                    lastIndex+=dataPart.size();

                    iStream.close();
                }
                else {
                    byte[] buf;
                    int buf_size = 0;
                    if(this.fileSize-i*bytesPerPage < bytesPerPage)buf_size = (int) (this.fileSize-i*bytesPerPage);
                    else buf_size = bytesPerPage;
                    buf = new byte[bytesPerPage];

                    sourceFile.seek(i*bytesPerPage);
                    sourceFile.read(buf);

                    finishedFileRAF.seek(lastIndex);
                    finishedFileRAF.write(buf);
                    lastIndex+=buf_size;
                }
            }

            finishedFileRAF.close();
            sourceFile.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }

//
    public ArrayList<Integer> getData(int i) {
        this.read(i);
        return data;
    }

//
    public void setData(ArrayList<Integer> data){ this.data = data; }

//
    public int getPageAmount(){
        return this.pagesAmount;
    }

//
    public long getFileSize(){
        return this.fileSize;
    }
}

package FileStaff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Класс записи/чтения файлов в байтовом виде
public class AppFileReader {
    private String filename;
    private ArrayList<Integer> data;
    private int bytesPerPage = 1024;
    private long fileSize;
    private int pagesAmount;
    private int lastIndex;
    public AppFileReader(){

    }
//
    public String getFilename() {
        return filename;
    }

//  Чтение из файла по имени
    public void setFilename(String filename) {
        try{
            this.data = new ArrayList<>();
            RandomAccessFile file = new RandomAccessFile(filename,"r");
            this.filename = filename;
            this.fileSize = file.length();
            this.pagesAmount = (int) (this.fileSize/bytesPerPage);
            if(this.fileSize%bytesPerPage != 0)this.pagesAmount++;
            file.close();
        }catch (Exception e){
            System.out.print(e);
        }

    }

//
    public ArrayList<Integer> getData(int i) {
        this.read(i);
        return data;
    }

//
    public void setData(ArrayList<Integer> data){ this.data = data; }

    public void read(int index) {
        try{
            this.lastIndex = index*bytesPerPage;
            RandomAccessFile file = new RandomAccessFile(filename,"r");
            file.seek(this.lastIndex);
            byte[] buf;
            int buf_size = 0;

            if(this.fileSize>lastIndex + bytesPerPage) {
                buf = new byte[bytesPerPage];
                buf_size = bytesPerPage;
            }
            else {
                buf = new byte[(int) (this.fileSize - lastIndex)];
                buf_size = (int) (this.fileSize - lastIndex);
            }

            file.read(buf);
            data.clear();
            for(int j=0;j<buf_size;j++)data.add((int) buf[j]);
            System.out.println("Read From " + this.lastIndex);
            file.close();
        }catch (Exception e){
            System.out.println("READ  :" + e);
        }
    }

    public void write(int index, ArrayList<Integer> data){
        try{
            RandomAccessFile file = new RandomAccessFile(filename,"rw");

            file.seek(this.lastIndex);
//            file.write(,0,bytesPerPage);

            for(int i=0;i<data.size();i+=bytesPerPage){
                List<Integer> dataPart;
                byte[] buf;
                if(i+bytesPerPage <= data.size()){
                    dataPart = data.subList(i,i+bytesPerPage);
                }else{
                    dataPart = data.subList(i,data.size());
                }
                buf = new byte[dataPart.size()];
                for(int j=0;j<dataPart.size();j++)buf[j] = (byte) (int) dataPart.get(j);

                System.out.println("Write To " + (i+this.lastIndex) + " // " + dataPart.size());
                file.seek(i+this.lastIndex);
                file.write(buf);
            }

            this.fileSize = file.length();
            this.pagesAmount = (int) (this.fileSize/bytesPerPage);
            if(this.fileSize%bytesPerPage != 0)this.pagesAmount++;
            data.clear();
            file.close();
        }catch (Exception e){
            System.out.println("WRITE :" + e);
        }
    }
    public int getPageAmount(){
        return this.pagesAmount;
    }
    public long getFileSize(){
        return this.fileSize;
    }
}

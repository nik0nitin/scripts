import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class FilesMerger {
    public static void main(String[] args) {
        System.out.println("Add Directory to merge files");
        Console con = System.console();
        String directory = con.readLine();
        ArrayList<File> filesArray = new ArrayList<>();
        ArrayList<FileInputStream> fileInputStreams = new ArrayList<>();
        try {
            File file = new File(directory);
            if (file.exists()) {
                for (File f : file.listFiles()) {
                    if (f.isFile()) {
                        filesArray.add(f);
                        fileInputStreams.add(new FileInputStream(f));
                    }
                }
            }

            Enumeration<FileInputStream> e = Collections.enumeration(fileInputStreams);
            SequenceInputStream sis = new SequenceInputStream(e);

//            SequenceInputStream sis = new SequenceInputStream((Enumeration<? extends InputStream>) fileInputStreams);
            System.out.println("Provide final File name with extension.");
            String finalFileName = con.readLine();
            FileOutputStream fos = new FileOutputStream(directory + "\\" + finalFileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int ch;
            while ((ch = sis.read()) != -1) {
                bos.write(ch);
            }
            sis.close();
            bos.close();
            System.out.println("------------------------");
            System.out.println("File merged successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

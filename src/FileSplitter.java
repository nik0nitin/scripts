import java.io.*;

public class FileSplitter {
    public static void main(String[] args) {
        Console con = System.console();

        System.out.println("Provide your file path:");
        String path = con.readLine();

        System.out.println("Provide name of the file:");
        String fileName = con.readLine();
        //-----------------------------------------------------//
        File file = new File(path + "\\" + fileName);
        long fileSize = 0;
        if(file.exists()){
            fileSize =  file.length(); //in bytes
            System.out.println("Current File Size: " + fileSize/1024 + "kb");
        }
        else{
            System.out.println("No file with the name: " + fileName + " found.");
            System.exit(0);
        }

        System.out.println("Define New File Size in kb.");
        int desiredFileSizeKB = Integer.parseInt(con.readLine());
        int fileSizeKB  =(int) fileSize/1024;

        System.out.println("Your file Type is: " + getFileType(fileName));



        int[] arr = getSmallerSize(fileSizeKB, desiredFileSizeKB);
        System.out.println("Total new files: " + arr.length);
        for(int a : arr){
            System.out.println(a);
        }


        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            //path, fileName -> File file object
            String newDir = path + "\\" + fileName + "_inParts";
            File newDirFile = new File(newDir);
            if (!newDirFile.exists()) {
                newDirFile.mkdir(); // Create the directory if it doesn't exist
            }

            int bufferSize = 1024; // 1KB buffer
            byte[] bufferArray = new byte[bufferSize];
            int bytesRead;

            for(int i = 0; i< arr.length; i++){
                int j = i + 1;
                String partFileName = newDir + "\\" + "part_" + j + getFileType(fileName);
                FileOutputStream fos = new FileOutputStream(partFileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int bytesToWrite = arr[i] * 1024; //kb to bytes
                int bytesWritten = 0;

                if(i == arr.length - 1){
                    while((bytesRead = bis.read(bufferArray)) != -1){
                        bos.write(bufferArray, 0 , bytesRead);
                    }
                }
                else {
                    while (bytesWritten < bytesToWrite && (bytesRead = bis.read(bufferArray, 0, Math.min(bufferSize, bytesToWrite - bytesWritten))) != -1) {
                        bos.write(bufferArray, 0, bytesRead);
                        bytesWritten += bytesRead;
                    }
                }
                bos.flush();
                bos.close();
            }
            bis.close();
            System.out.println("---------------------------");
            System.out.println("File Splitted Successfully.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String getFileType(String fileName){
        int dotIndex = fileName.lastIndexOf('.');
        String extension = fileName.substring(dotIndex);
        return extension;
    }
    public static int[] getSmallerSize(int fileSize, int newFileSize){
        int fullParts = fileSize/newFileSize;
        int lastPartSize = fileSize % newFileSize;
        int[] arr;
        if(lastPartSize == 0){
            arr = new int[fullParts];
            for(int i = 0;  i<arr.length; i++){
                arr[i] = newFileSize;
            }
        }
        else{
            arr = new int[fullParts + 1];
            for(int i = 0; i<arr.length; i++){
                if(i == arr.length - 1){
                    arr[i] = lastPartSize;
                }
                else{
                    arr[i] = newFileSize;
                }
            }
        }
        return arr;
    }


}

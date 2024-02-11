package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileReader {

    public static List<Object[]> scanForFiles(String exportPath) throws Exception {
        File dir = new File(exportPath);

        if (dir.isDirectory()) {
            List<Object[]> result = new ArrayList<>();
            scanDirectory(dir,result);
            return result;
        } else {
            throw new Exception("Export path is not directory");
        }

    }



    public static void moveToFolderDone(String exportPath,File file) throws IOException {
        File done = new File(exportPath,"done");
        if(!done.exists()){
            done.mkdir();
        }
        Files.move(file.toPath(), new File(done,file.getName()).toPath());
    }

    public static void moveToFolderError(String exportPath,File file) throws IOException {
        File error = new File(exportPath,"error");
        if(!error.exists()){
            error.mkdir();
        }
        Files.move(file.toPath(), new File(error,file.getName()).toPath());
    }


    /**
     *
     * @param subDir
     * @param arrays
     */
    private static void scanDirectory(File subDir, List<Object[]> arrays) throws IOException {
        File[] files = subDir.listFiles();

        for(File file:files){
            if(file.isFile()){
                arrays.add(parseIdLastnameAndName(file));
            }else{
                scanDirectory(file,arrays);
            }
        }
    }




    private static Object[] parseIdLastnameAndName(File file) throws IOException {
        String name = file.getName();

        String[] parsedIdNameLastname = name.split("_");
        FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(),"creationTime");

        return new Object[]{parsedIdNameLastname[0],parsedIdNameLastname[1],parsedIdNameLastname[2],creationTime.toString(),file};
    }
}

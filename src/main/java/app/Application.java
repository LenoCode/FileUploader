package app;

import org.apache.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class Application {
    private final String host;
    private final int port;
    private final String localPath;
    private boolean status = false;


    private long timeout = 1000 * 10;
    public Application(String localPath,String host,int port) {
        this.localPath = localPath;
        this.host = host;
        this.port = port;
    }


    public void run() throws Exception {
        status = true;

        while(status){
            List<Object[]>  array = FileReader.scanForFiles(localPath);
            Uploader.uploader(array, host, port, new CallbackResponse() {
                @Override
                public void callback(File file, HttpResponse httpResponse) {
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        try {
                            FileReader.moveToFolderDone(localPath,file);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                    }else{
                        try {
                            FileReader.moveToFolderError(localPath,file);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            });
            Thread.sleep(timeout);
        }
    }





}

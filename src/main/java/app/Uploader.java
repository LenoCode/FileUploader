package app;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class Uploader {


    /**
     *
     * @param arrays
     * @return
     */
    public static void uploader(List<Object[]> arrays,String host,int port,final CallbackResponse callbackResponse){

       arrays.stream().forEach(file->{
           String json = mapToJson(Map.of("name",(String)file[1],"lastname",(String)file[2],"createTime",(String)file[3]));
           final MultipartEntityBuilder builder  = MultipartEntityBuilder.create();

           builder.setMode(HttpMultipartMode.STRICT);
           builder.addTextBody("details",json,ContentType.APPLICATION_JSON);
           builder.addBinaryBody("file",(File)file[4],ContentType.DEFAULT_BINARY,((File)file[4]).getName());

           final HttpPost httpPost = new HttpPost("http://"+host+":"+port+"/api/v1/upload/");
           try(CloseableHttpClient client = HttpClientBuilder.create().build()) {

               final HttpEntity entity = builder.build();
               httpPost.setEntity(entity);
               HttpResponse httpResponse = client.execute(httpPost);

               callbackResponse.callback((File)file[4],httpResponse );

           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       });
    }




    private static String mapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }









}

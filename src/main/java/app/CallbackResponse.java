package app;

import org.apache.http.HttpResponse;

import java.io.File;

public interface CallbackResponse {


    void callback(File file, HttpResponse httpResponse);
}

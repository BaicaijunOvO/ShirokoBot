package ovo.baicaijun.ShirokoBot.Network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class NetworkUtil {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)  // 设置连接超时时间
            .readTimeout(15, TimeUnit.SECONDS)     // 设置读取超时时间
            .writeTimeout(15, TimeUnit.SECONDS)    // 设置写入超时时间
            .build();
    // 异步GET请求
    public static void sendGetRequest(String urlString, final NetworkCallback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                } else {
                    callback.onFailure("Request failed with code: " + response.code());
                }
            }
        });
    }

    // 异步POST请求
    public static void sendPostRequest(String urlString, String json, final NetworkCallback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .post(RequestBody.create(json, MediaType.get("application/json; charset=utf-8")))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                } else {
                    callback.onFailure("Request failed with code: " + response.code());
                }
            }
        });
    }
    // 文件上传 - 使用Multipart
    public static void uploadFile(String urlString, File file, final NetworkCallback callback) {
        // 创建 Multipart 请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                } else {
                    callback.onFailure("Upload failed with code: " + response.code());
                }
            }
        });
    }
    // 文件下载
    public static void downloadFile(String urlString, final String savePath, final NetworkCallback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response.isSuccessful()) {
                    // 获取输入流
                    InputStream inputStream = response.body().byteStream();
                    // 输出到文件
                    FileOutputStream fos = new FileOutputStream(new File(savePath));
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    fos.flush();
                    fos.close();
                    inputStream.close();

                    callback.onResponse("Download completed successfully.");
                } else {
                    callback.onFailure("Download failed with code: " + response.code());
                }
            }
        });
    }
    // 网络请求回调接口
    public interface NetworkCallback {
        void onResponse(String response);

        void onFailure(String error);
    }
}

package ovo.baicaijun.ShirokoBot.Network;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class NetworkUtil {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    // ========== 异步 GET 请求 ==========
    
    /**
     * 异步GET请求（不带cookie）
     */
    public static void sendGetRequest(String urlString, final NetworkCallback callback) {
        Request request = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json, text/plain, */*")
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
                if (callback != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    } else {
                        callback.onFailure("Request failed with code: " + response.code());
                    }
                }
                response.close();
            }
        });
    }

    /**
     * 异步GET请求（带cookie）
     */
    public static void sendGetRequest(String urlString, String cookie, final NetworkCallback callback) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json, text/plain, */*");
        
        if (cookie != null && !cookie.isEmpty()) {
            requestBuilder.header("Cookie", cookie);
        }
        
        Request request = requestBuilder.build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    } else {
                        callback.onFailure("Request failed with code: " + response.code());
                    }
                }
                response.close();
            }
        });
    }
    
    // ========== 异步 POST 请求 ==========

    /**
     * 异步POST请求（不带cookie）
     */
    public static void sendPostRequest(String urlString, String json, final NetworkCallback callback) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .post(body)
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
                if (callback != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    } else {
                        callback.onFailure("Request failed with code: " + response.code());
                    }
                }
                response.close();
            }
        });
    }
    
    /**
     * 异步POST请求（带cookie）
     */
    public static void sendPostRequest(String urlString, String json, String cookie, final NetworkCallback callback) {
        RequestBody body = RequestBody.create(json, JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json, text/plain, */*")
                .post(body);
        
        if (cookie != null && !cookie.isEmpty()) {
            requestBuilder.header("Cookie", cookie);
        }
        
        Request request = requestBuilder.build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    } else {
                        callback.onFailure("Request failed with code: " + response.code());
                    }
                }
                response.close();
            }
        });
    }
    
    /**
     * 异步POST请求（带自定义headers和cookie）
     */
    public static void sendPostRequest(String urlString, String json, String cookie, Map<String, String> headers, final NetworkCallback callback) {
        RequestBody body = RequestBody.create(json, JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .post(body);
        
        // 添加cookie
        if (cookie != null && !cookie.isEmpty()) {
            requestBuilder.header("Cookie", cookie);
        }
        
        // 添加自定义headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        
        Request request = requestBuilder.build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(e.getMessage());
                }
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    } else {
                        callback.onFailure("Request failed with code: " + response.code());
                    }
                }
                response.close();
            }
        });
    }
    
    // ========== CompletableFuture 风格的异步方法 ==========
    
    /**
     * 异步POST请求（返回CompletableFuture）
     * @param urlString 请求URL
     * @param json JSON数据
     * @return CompletableFuture<String> 响应内容
     */
    public static CompletableFuture<String> postAsync(String urlString, String json) {
        return postAsync(urlString, json, null, null);
    }
    
    /**
     * 异步POST请求（带cookie，返回CompletableFuture）
     * @param urlString 请求URL
     * @param json JSON数据
     * @param cookie Cookie字符串
     * @return CompletableFuture<String> 响应内容
     */
    public static CompletableFuture<String> postAsync(String urlString, String json, String cookie) {
        return postAsync(urlString, json, cookie, null);
    }
    
    /**
     * 异步POST请求（带cookie和自定义headers，返回CompletableFuture）
     * @param urlString 请求URL
     * @param json JSON数据
     * @param cookie Cookie字符串
     * @param headers 自定义请求头
     * @return CompletableFuture<String> 响应内容
     */
    public static CompletableFuture<String> postAsync(String urlString, String json, String cookie, Map<String, String> headers) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        RequestBody body = RequestBody.create(json, JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .post(body);
        
        // 添加cookie
        if (cookie != null && !cookie.isEmpty()) {
            requestBuilder.header("Cookie", cookie);
        }
        
        // 添加自定义headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        
        Request request = requestBuilder.build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        future.complete(response.body().string());
                    } else {
                        future.completeExceptionally(new RuntimeException("Request failed with code: " + response.code()));
                    }
                } finally {
                    response.close();
                }
            }
        });
        
        return future;
    }
    
    /**
     * 异步GET请求（返回CompletableFuture）
     * @param urlString 请求URL
     * @return CompletableFuture<String> 响应内容
     */
    public static CompletableFuture<String> getAsync(String urlString) {
        return getAsync(urlString, null);
    }
    
    /**
     * 异步GET请求（带cookie，返回CompletableFuture）
     * @param urlString 请求URL
     * @param cookie Cookie字符串
     * @return CompletableFuture<String> 响应内容
     */
    public static CompletableFuture<String> getAsync(String urlString, String cookie) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlString)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json, text/plain, */*");
        
        if (cookie != null && !cookie.isEmpty()) {
            requestBuilder.header("Cookie", cookie);
        }
        
        Request request = requestBuilder.build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        future.complete(response.body().string());
                    } else {
                        future.completeExceptionally(new RuntimeException("Request failed with code: " + response.code()));
                    }
                } finally {
                    response.close();
                }
            }
        });
        
        return future;
    }
    
    // ========== 工具方法 ==========
    
    /**
     * 关闭客户端（释放资源）
     */
    public static void shutdown() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
    
    /**
     * 网络请求回调接口
     */
    public interface NetworkCallback {
        void onResponse(String response);
        void onFailure(String error);
    }
}
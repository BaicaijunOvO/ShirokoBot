package ovo.baicaijun.ShirokoBot.Config;

import org.eclipse.jetty.util.log.Log;
import org.json.JSONObject;
import ovo.baicaijun.ShirokoBot.Log.Logger;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Bot 配置管理类
 * 统一管理所有配置项的读取和解析
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/20 10:26
 */
public class BotConfig {
    // 配置文件路径
    private static String configPath;
    
    // 基础配置
    public static Boolean debugMode = false;
    public static int port = 8080;
    public static String commandStart = "/";
    public static String version = "1.2";
    
    // 适配器配置
    public static String adapterType = "onebotv11";
    public static String milkyUri = "http://localhost:8080/milky";
    public static String accessToken = "";
    public static String cookie = "";
    
    // 原始配置 Map（用于访问自定义配置）
    private static Map<String, Object> configMap = new HashMap<>();
    
    /**
     * 初始化配置
     * @param path 配置文件路径
     */
    public static void init(String path) {
        configPath = path;
        loadConfig();
    }
    
    /**
     * 加载配置文件
     */
    private static void loadConfig() {
        try {
            // 读取配置文件
            configMap = ConfigUtil.readConfig(configPath);
            
            // 如果配置为空，创建默认配置
            if (configMap.isEmpty()) {
                createDefaultConfig();
                configMap = ConfigUtil.readConfig(configPath);
            }
            
            // 解析配置
            parseConfig();
            
            // 打印配置信息
            Logger.info("配置加载完成");
            Logger.info("Debug 模式: " + debugMode);
            Logger.info("端口: " + port);
            Logger.info("命令前缀: " + commandStart);
            Logger.info("适配器类型: " + adapterType);
            Logger.info("AccessToken: " + accessToken);
            if ("milky".equalsIgnoreCase(adapterType)) {
                Logger.info("Milky 服务器: " + milkyUri);
            }
            
        } catch (IOException e) {
            Logger.error("配置文件加载失败: " + e.getMessage());
            throw new RuntimeException("配置文件加载失败", e);
        }
    }
    
    /**
     * 创建默认配置文件
     */
    private static void createDefaultConfig() throws IOException {
        Map<String, Object> defaultConfig = new HashMap<>();
        
        // 基础配置
        defaultConfig.put("version", "1.2");
        defaultConfig.put("debug", false);
        defaultConfig.put("port", 8080);
        defaultConfig.put("commandStart", "/");

        // 适配器配置
        Map<String, Object> adapterConfig = new HashMap<>();
        adapterConfig.put("type", "onebotv11");
        adapterConfig.put("milkyUri", "http://localhost:8080/milky");
        adapterConfig.put("milkyAccessToken",generateRandomString());
        defaultConfig.put("adapter", adapterConfig);
        
        // 写入配置文件
        ConfigUtil.writeConfig(configPath, defaultConfig);
        Logger.info("默认配置文件已创建: " + configPath);
    }
    
    /**
     * 解析配置项
     */
    private static void parseConfig() {
        // 解析基础配置
        debugMode = getBoolean("debug", false);
        port = getInt("port", 8080);
        commandStart = getString("commandStart", "/");
        version = getString("version", "1.2");
        
        // 解析适配器配置
        parseAdapterConfig();
    }
    
    /**
     * 解析适配器配置
     */
    private static void parseAdapterConfig() {
        Object adapterObj = configMap.get("adapter");
        
        if (adapterObj == null) {
            // 没有配置，使用默认值
            adapterType = "onebotv11";
            milkyUri = "http://localhost:8080/milky";
            accessToken = generateRandomString();
            Logger.warn("未找到适配器配置，使用默认值: " + adapterType);
            
        } else if (adapterObj instanceof Map) {
            // 对象格式配置
            @SuppressWarnings("unchecked")
            Map<String, Object> adapterMap = (Map<String, Object>) adapterObj;
            
            adapterType = (String) adapterMap.getOrDefault("type", "onebotv11");
            milkyUri = (String) adapterMap.getOrDefault("milkyUrl", "http://localhost:8080/milky");
            accessToken =  adapterMap.getOrDefault("milkyAccessToken",generateRandomString()).toString();
            
        } else if (adapterObj instanceof String) {
            // 字符串格式配置（向后兼容）
            adapterType = (String) adapterObj;
            milkyUri = "http://localhost:8080/milky";
            Logger.warn("适配器配置使用旧格式，建议更新为对象格式");
            
        } else {
            // 未知格式
            Logger.error("适配器配置格式错误，使用默认值");
            adapterType = "onebotv11";
            milkyUri = "http://localhost:8080/milky";
            accessToken = generateRandomString();
        }

        JSONObject cookieJson = new JSONObject();
        cookieJson.put("Authorization", "Bearer " + accessToken);
        cookie = cookieJson.toString();
    }

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    
    // ========== 配置访问方法 ==========
    
    /**
     * 获取字符串配置
     */
    public static String getString(String key, String defaultValue) {
        Object value = configMap.get(key);
        return value != null ? value.toString() : defaultValue;
    }
    
    /**
     * 获取整数配置
     */
    public static int getInt(String key, int defaultValue) {
        Object value = configMap.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }
    
    /**
     * 获取布尔配置
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        Object value = configMap.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return defaultValue;
    }
    
    /**
     * 获取对象配置
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(String key) {
        Object value = configMap.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return new HashMap<>();
    }
    
    /**
     * 获取原始配置值
     */
    public static Object get(String key) {
        return configMap.get(key);
    }
    
    /**
     * 检查配置项是否存在
     */
    public static boolean has(String key) {
        return configMap.containsKey(key);
    }
    
    /**
     * 获取所有配置
     */
    public static Map<String, Object> getAll() {
        return new HashMap<>(configMap);
    }
    
    /**
     * 重新加载配置
     */
    public static void reload() {
        Logger.info("重新加载配置文件...");
        loadConfig();
    }
    
    /**
     * 更新配置项
     */
    public static void set(String key, Object value) {
        try {
            ConfigUtil.updateConfig(configPath, key, value);
            configMap.put(key, value);
            Logger.info("配置项已更新: " + key + " = " + value);
        } catch (IOException e) {
            Logger.error("更新配置失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取配置文件路径
     */
    public static String getConfigPath() {
        return configPath;
    }
}

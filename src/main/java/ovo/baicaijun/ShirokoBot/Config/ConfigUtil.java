package ovo.baicaijun.ShirokoBot.Config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/4/20 10:28
 */
public class ConfigUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // 美化输出

    /**
     * 从文件读取 JSON 配置
     * @param filePath 文件路径
     * @return 配置数据的 Map
     * @throws IOException 如果文件读取失败
     */
    public static Map<String, Object> readConfig(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            // 如果文件不存在，返回空 Map
            return new HashMap<>();
        }
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 将配置写入 JSON 文件
     * @param filePath 文件路径
     * @param config 配置数据的 Map
     * @throws IOException 如果文件写入失败
     */
    public static void writeConfig(String filePath, Map<String, Object> config) throws IOException {
        File file = new File(filePath);
        // 如果父目录不存在，创建它
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        objectMapper.writeValue(file, config);
    }

    /**
     * 更新 JSON 配置文件中的某个键值
     * @param filePath 文件路径
     * @param key 键
     * @param value 值
     * @throws IOException 如果文件操作失败
     */
    public static void updateConfig(String filePath, String key, Object value) throws IOException {
        Map<String, Object> config = readConfig(filePath);
        config.put(key, value);
        writeConfig(filePath, config);
    }

    /**
     * 从 JSON 配置文件中获取值
     * @param filePath 文件路径
     * @param key 键
     * @return 对应的值，如果不存在返回 null
     * @throws IOException 如果文件读取失败
     */
    public static Object getValue(String filePath, String key) throws IOException {
        Map<String, Object> config = readConfig(filePath);
        return config.get(key);
    }

    /**
     * 从 JSON 配置文件中删除某个键
     * @param filePath 文件路径
     * @param key 要删除的键
     * @throws IOException 如果文件操作失败
     */
    public static void removeKey(String filePath, String key) throws IOException {
        Map<String, Object> config = readConfig(filePath);
        config.remove(key);
        writeConfig(filePath, config);
    }

    /**
     * 检查 JSON 配置文件中是否包含某个键
     * @param filePath 文件路径
     * @param key 要检查的键
     * @return 如果包含返回 true，否则返回 false
     * @throws IOException 如果文件读取失败
     */
    public static boolean containsKey(String filePath, String key) throws IOException {
        Map<String, Object> config = readConfig(filePath);
        return config.containsKey(key);
    }
}

package ovo.baicaijun.ShirokoBot.Milky.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Milky 消息转换器
 * 将类似 CQ 码的 MILKY 码转换为 JSON 格式
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageConverter {
    
    // MILKY 码正则表达式: [MILKY:type,key1=value1,key2=value2]
    private static final Pattern MILKY_PATTERN = Pattern.compile("\\[MILKY:([^,\\]]+)(?:,([^\\]]+))?\\]");
    
    /**
     * 将包含 MILKY 码的字符串转换为消息段数组
     * @param message 包含 MILKY 码的消息字符串
     * @return JSON 消息段数组
     */
    public static JSONArray convertToSegments(String message) {
        JSONArray segments = new JSONArray();
        
        Matcher matcher = MILKY_PATTERN.matcher(message);
        int lastEnd = 0;
        
        while (matcher.find()) {
            // 添加 MILKY 码之前的文本
            if (matcher.start() > lastEnd) {
                String text = message.substring(lastEnd, matcher.start());
                if (!text.isEmpty()) {
                    segments.put(createTextSegment(text));
                }
            }
            
            // 解析 MILKY 码
            String type = matcher.group(1);
            String params = matcher.group(2);
            
            JSONObject segment = parseMilkyCode(type, params);
            if (segment != null) {
                segments.put(segment);
            }
            
            lastEnd = matcher.end();
        }
        
        // 添加最后的文本
        if (lastEnd < message.length()) {
            String text = message.substring(lastEnd);
            if (!text.isEmpty()) {
                segments.put(createTextSegment(text));
            }
        }
        
        // 如果没有任何段，创建一个纯文本段
        if (segments.length() == 0) {
            segments.put(createTextSegment(message));
        }
        
        return segments;
    }
    
    /**
     * 创建文本消息段
     */
    private static JSONObject createTextSegment(String text) {
        JSONObject segment = new JSONObject();
        segment.put("type", "text");
        
        JSONObject data = new JSONObject();
        data.put("text", text);
        segment.put("data", data);
        
        return segment;
    }
    
    /**
     * 解析 MILKY 码
     */
    private static JSONObject parseMilkyCode(String type, String params) {
        JSONObject segment = new JSONObject();
        segment.put("type", type);
        
        JSONObject data = new JSONObject();
        
        // 解析参数
        if (params != null && !params.isEmpty()) {
            String[] pairs = params.split(",");
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    String key = kv[0].trim();
                    String value = kv[1].trim();
                    
                    // 尝试转换为数字或布尔值
                    if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                        data.put(key, Boolean.parseBoolean(value));
                    } else {
                        try {
                            data.put(key, Long.parseLong(value));
                        } catch (NumberFormatException e) {
                            data.put(key, value);
                        }
                    }
                }
            }
        }
        
        segment.put("data", data);
        return segment;
    }
}

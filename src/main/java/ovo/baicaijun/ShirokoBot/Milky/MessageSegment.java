package ovo.baicaijun.ShirokoBot.Milky;

/**
 * Milky 消息段构建器
 * 使用类似 CQ 码的格式，在发送时转换为 JSON
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class MessageSegment {
    
    /**
     * 创建图片消息段
     * @param file 文件 URI，支持 file:// http(s):// base64:// 三种格式
     */
    public static String image(String file) {
        return "[MILKY:image,uri=" + file + "]";
    }
    
    /**
     * 创建图片消息段（带类型）
     * @param file 文件 URI
     * @param subType 图片类型: normal, sticker
     */
    public static String image(String file, String subType) {
        return "[MILKY:image,uri=" + file + ",sub_type=" + subType + "]";
    }
    
    /**
     * 创建表情消息段
     * @param id 表情 ID
     */
    public static String face(String id) {
        return "[MILKY:face,face_id=" + id + "]";
    }
    
    /**
     * 创建表情消息段（超级表情）
     * @param id 表情 ID
     * @param isLarge 是否为超级表情
     */
    public static String face(String id, boolean isLarge) {
        return "[MILKY:face,face_id=" + id + ",is_large=" + isLarge + "]";
    }
    
    /**
     * 创建语音消息段
     * @param file 文件 URI，支持 file:// http(s):// base64:// 三种格式
     */
    public static String record(String file) {
        return "[MILKY:record,uri=" + file + "]";
    }
    
    /**
     * 创建视频消息段
     * @param file 文件 URI，支持 file:// http(s):// base64:// 三种格式
     */
    public static String video(String file) {
        return "[MILKY:video,uri=" + file + "]";
    }
    
    /**
     * 创建视频消息段（带封面）
     * @param file 文件 URI
     * @param thumbUri 封面图片 URI
     */
    public static String video(String file, String thumbUri) {
        return "[MILKY:video,uri=" + file + ",thumb_uri=" + thumbUri + "]";
    }
    
    /**
     * 创建 @ 消息段
     * @param qq QQ 号
     */
    public static String at(String qq) {
        return "[MILKY:mention,user_id=" + qq + "]";
    }
    
    /**
     * 创建 @ 消息段
     * @param qq QQ 号
     */
    public static String at(long qq) {
        return "[MILKY:mention,user_id=" + qq + "]";
    }
    
    /**
     * 创建 @ 全体成员消息段
     */
    public static String atAll() {
        return "[MILKY:mention_all]";
    }
    
    /**
     * 创建回复消息段
     * @param messageSeq 被引用的消息序列号
     */
    public static String reply(long messageSeq) {
        return "[MILKY:reply,message_seq=" + messageSeq + "]";
    }
}

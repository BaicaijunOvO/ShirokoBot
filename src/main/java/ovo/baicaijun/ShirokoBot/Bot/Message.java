package ovo.baicaijun.ShirokoBot.Bot;

/**
 * 消息回执结构体
 * 封装消息发送后的返回信息
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class Message {
    private final Long messageId;
    private final boolean success;
    private final String errorMessage;
    private final long timestamp;

    /**
     * 成功的消息回执
     */
    public Message(Long messageId) {
        this.messageId = messageId;
        this.success = messageId != null;
        this.errorMessage = null;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 失败的消息回执
     */
    public Message(String errorMessage) {
        this.messageId = null;
        this.success = false;
        this.errorMessage = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 完整构造函数
     */
    public Message(Long messageId, boolean success, String errorMessage) {
        this.messageId = messageId;
        this.success = success;
        this.errorMessage = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 获取消息ID
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * 是否发送成功
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 获取错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 获取时间戳
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 是否有错误
     */
    public boolean hasError() {
        return errorMessage != null;
    }

    @Override
    public String toString() {
        if (success) {
            return "Message{messageId=" + messageId + ", success=true, timestamp=" + timestamp + "}";
        } else {
            return "Message{success=false, error='" + errorMessage + "', timestamp=" + timestamp + "}";
        }
    }

    /**
     * 创建成功的消息回执
     */
    public static Message success(Long messageId) {
        return new Message(messageId);
    }

    /**
     * 创建失败的消息回执
     */
    public static Message failure(String errorMessage) {
        return new Message(errorMessage);
    }
}

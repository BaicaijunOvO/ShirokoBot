package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

import org.json.JSONObject;

/**
 * 群文件上传事件
 * notice.group_upload
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public class GroupUploadEvent extends NoticeEvent {
    private final long groupId;
    private final long userId;
    private final JSONObject file;

    public GroupUploadEvent(long selfId, long time, long groupId, long userId, JSONObject file) {
        super("group_upload", selfId, time);
        this.groupId = groupId;
        this.userId = userId;
        this.file = file;
    }

    public long getGroupId() {
        return groupId;
    }

    public long getUserId() {
        return userId;
    }

    public JSONObject getFile() {
        return file;
    }

    public String getFileName() {
        return file != null ? file.optString("name", "") : "";
    }

    public long getFileSize() {
        return file != null ? file.optLong("size", 0) : 0;
    }

    public String getFileId() {
        return file != null ? file.optString("id", "") : "";
    }
}

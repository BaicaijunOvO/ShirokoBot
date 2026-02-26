package ovo.baicaijun.ShirokoBot.OneBot.v11.event.notice;

import ovo.baicaijun.ShirokoBot.OneBot.v11.event.OneBotEvent;

/**
 * 通知事件基类
 * 
 * @Author BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 */
public abstract class NoticeEvent extends OneBotEvent {
    private final String noticeType;

    public NoticeEvent(String noticeType, long selfId, long time) {
        super("NoticeEvent", selfId, time);
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }
}

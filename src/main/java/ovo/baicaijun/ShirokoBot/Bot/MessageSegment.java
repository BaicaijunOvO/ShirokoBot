package ovo.baicaijun.ShirokoBot.Bot;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2025/3/16 下午5:55
 */
public class MessageSegment {

    public static String image(String file){
        String cq = "[CQ:image,file=" + file + "]";
        return cq;
    }

    public static String face(String id){
        String cq = "[CQ:face,id=" + id + "]";
        return cq;
    }

    public static String record(String file){
        String cq = "[CQ:record,file=" + file + "]";
        return cq;
    }

    public static String video(String file){
        String cq = "[CQ:video,file=" + file + "]";
        return cq;
    }

    public static String at(String qq){
        String cq = "[CQ:at,qq=" + qq + "]";
        return cq;
    }

    public static String reply(long message_id){
        String cq = "[CQ:reply,file=" + message_id + "]";
        return cq;
    }







}

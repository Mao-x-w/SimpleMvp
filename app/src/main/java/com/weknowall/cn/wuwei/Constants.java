package com.weknowall.cn.wuwei;

/**
 * User: laomao
 * Date: 2017-02-14
 * Time: 10-19
 */

public class Constants {
    public static String SmsAppKey="1a5693df03c70";
    public static String SmsAppSecret="00e4b47a6902d1b29a0e971e69f9ec39";

    public static final String[] RECOMMEND_TAGS={"recommend_0","recommend_1","recommend_2"};
    public static final String[] DISCOVER_TAGS={"discover_0","discover_1","discover_2","discover_3","discover_4"};
    public static final String[] TOPIC_TAGS={"topic_0"};
    public static final String[] TAGS={RECOMMEND_TAGS[0],RECOMMEND_TAGS[1],RECOMMEND_TAGS[2]
            ,DISCOVER_TAGS[0],DISCOVER_TAGS[1],DISCOVER_TAGS[2],DISCOVER_TAGS[3],DISCOVER_TAGS[4]
            ,TOPIC_TAGS[0]};

    public interface IntentExtra{
        String VIDEO_DATA="video_data";
        String COMMENT_REPLY_DATA="comment_reply_data";
        String COMMENT_DATA="comment_reply_data";
        String COMMENT_POSITION ="position";
        String COMMENT_TAG_POSITION ="tag_position";
        String USER_DATA ="user_data";
    }
}

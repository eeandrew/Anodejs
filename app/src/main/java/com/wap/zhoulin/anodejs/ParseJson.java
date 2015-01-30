package com.wap.zhoulin.anodejs;

import android.util.Log;

import com.wap.zhoulin.anodejs.CnodeListFragment.ReplyItem;
import com.wap.zhoulin.anodejs.CnodeListFragment.SumaryItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoulin on 2014/12/24.
 */
public class ParseJson {
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_AUTHOR_ID = "author_id";
    private static final String TAG_TAB = "tab";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_TITLE = "title";
    private static final String TAG_LAST_REPLY_AT = "last_reply_at";
    private static final String TAG_GOOD = "good";
    private static final String TAG_TOP = "top";
    private static final String TAG_REPLY_COUNT = "reply_count";
    private static final String TAG_VISIT_COUNT = "visit_count";
    private static final String TAG_CREATE_AT = "create_at";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_LOGINNAME = "loginname";
    private static final String TAG_AVATAR_URL = "avatar_url";
    private static final String TAG_REPLIES = "replies";
    private static final String TAG_UPS = "ups";


    public static List<SumaryItem> parseJSON(String rawJSONData) {
        JSONArray posts;
        List<SumaryItem> list = new ArrayList<SumaryItem>();
        Log.d("ANODEJS RAW DATA",rawJSONData);
        try {
            JSONObject jsonObj = new JSONObject(rawJSONData);
            Log.d("ANODEJS jsonobj",jsonObj.toString());
            posts = jsonObj.getJSONArray(TAG_DATA);
            for(int i=0;i<posts.length();i++){
                SumaryItem item = new SumaryItem();
                JSONObject post = posts.getJSONObject(i);
                item.setId(post.getString(TAG_ID));
                item.setAuthor_id(post.getString(TAG_AUTHOR_ID));
                item.setTab(post.getString(TAG_TAB));
                item.setContent(post.getString(TAG_CONTENT));
                item.setTitle(post.getString(TAG_TITLE));
                item.setLast_replay_at(post.getString(TAG_LAST_REPLY_AT));
                item.setGood(post.getBoolean(TAG_GOOD));
                item.setTop(post.getBoolean(TAG_TOP));
                item.setReply_count(post.getInt(TAG_REPLY_COUNT));
                item.setVisit_count(post.getInt(TAG_VISIT_COUNT));
                item.setCreate_at(post.getString(TAG_CREATE_AT));
                JSONObject author = post.getJSONObject(TAG_AUTHOR);
                item.setLoginname(author.getString(TAG_LOGINNAME));
                item.setAvatar_url(author.getString(TAG_AVATAR_URL));
                list.add(item);
            }
        }catch(Exception e){
                Log.d("ANODEJS", e.toString());
        }finally{
            return list;
        }
    }

    public static Map<String,Object> parseReplyJSON(String rawJSONData) {
        JSONObject topic;
        Map<String,Object> mTopic = new HashMap<>();
        Log.d("ANODEJS RAW DATA",rawJSONData);
        try {
                topic = new JSONObject(rawJSONData);
                //Get POST First
                JSONObject post = topic.getJSONObject(TAG_DATA);
                SumaryItem item = new SumaryItem();
                item.setId(post.getString(TAG_ID));
                item.setAuthor_id(post.getString(TAG_AUTHOR_ID));
                item.setTab(post.getString(TAG_TAB));
                item.setContent(post.getString(TAG_CONTENT));
                item.setTitle(post.getString(TAG_TITLE));
                item.setLast_replay_at(post.getString(TAG_LAST_REPLY_AT));
                item.setGood(post.getBoolean(TAG_GOOD));
                item.setTop(post.getBoolean(TAG_TOP));
                item.setReply_count(post.getInt(TAG_REPLY_COUNT));
                item.setVisit_count(post.getInt(TAG_VISIT_COUNT));
                item.setCreate_at(post.getString(TAG_CREATE_AT));
                //Author object
                JSONObject author = post.getJSONObject(TAG_AUTHOR);
                item.setLoginname(author.getString(TAG_LOGINNAME));
                item.setAvatar_url(author.getString(TAG_AVATAR_URL));
                mTopic.put("POST",item);
                //Reply object
                JSONArray replies = post.getJSONArray(TAG_REPLIES);
                List<ReplyItem> mReplies = new ArrayList<>();
                for(int i=0;i<replies.length();i++){
                    ReplyItem mReply = new ReplyItem();
                    JSONObject reply = replies.getJSONObject(i);
                    mReply.setReply_id(reply.getString(TAG_ID));
                    mReply.setReply_content(reply.getString(TAG_CONTENT));
                    mReply.setReply_create_at(reply.getString(TAG_CREATE_AT));
                    JSONObject replayAuthor = reply.getJSONObject(TAG_AUTHOR);
                    mReply.setReply_avatar_url(replayAuthor.getString(TAG_AVATAR_URL));
                    mReply.setReply_loginname(replayAuthor.getString(TAG_LOGINNAME));
                    mReplies.add(mReply);
                }
               mTopic.put("REPLIES",mReplies);
        }catch(Exception e){
            Log.d("ANODEJS", e.toString());
        }finally{
            return mTopic;
        }
    }

    private static String addPrefixToInvalidUrl(String avatarUrl) {
        if(!avatarUrl.startsWith("http")){
            return "http:" + avatarUrl;
        }else {
            return avatarUrl;
        }
    }
}

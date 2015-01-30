package com.wap.zhoulin.anodejs.CnodeListFragment;

import java.util.List;

/**
 * Created by zhoulin on 2014/12/25.
 */
public class ReplyItem {
    private String reply_id;
    private String reply_loginname;
    private String reply_avatar_url;
    private String reply_content;
    private List<String> ups;
    private String reply_create_at;


    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_loginname() {
        return reply_loginname;
    }

    public void setReply_loginname(String reply_loginname) {
        this.reply_loginname = reply_loginname;
    }

    public String getReply_avatar_url() {
        return reply_avatar_url;
    }

    public void setReply_avatar_url(String reply_avatar_url) {
        this.reply_avatar_url = reply_avatar_url;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public List<String> getUps() {
        return ups;
    }

    public void setUps(List<String> ups) {
        this.ups = ups;
    }

    public String getReply_create_at() {
        return reply_create_at;
    }

    public void setReply_create_at(String reply_create_at) {
        this.reply_create_at = reply_create_at;
    }
}

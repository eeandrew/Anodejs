package com.wap.zhoulin.anodejs.CnodeListFragment;

/**
 * Created by zhoulin on 2014/12/23.
 */
public class SumaryItem {
    // This id is the post id
    private String id;
    private String author_id;
    private String tab;
    private String content;
    private String title;
    private String last_replay_at;
    private boolean good;
    private boolean top;
    private int reply_count;
    private int visit_count;
    private String create_at;
    private String loginname;
    private String avatar_url;

    public String getId() {
        return id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getTab() {
        return tab;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getLast_replay_at() {
        return last_replay_at;
    }

    public boolean isGood() {
        return good;
    }

    public boolean isTop() {
        return top;
    }

    public int getReply_count() {
        return reply_count;
    }

    public int getVisit_count() {
        return visit_count;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLast_replay_at(String last_replay_at) {
        this.last_replay_at = last_replay_at;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public void setVisit_count(int visit_count) {
        this.visit_count = visit_count;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}

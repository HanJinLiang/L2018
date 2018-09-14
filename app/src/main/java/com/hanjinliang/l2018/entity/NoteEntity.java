package com.hanjinliang.l2018.entity;

import java.util.List;

/**
 * Created by Administrator on 2018-09-11.
 * 笔记实体类
 */
public class NoteEntity{
    private String userId;
    private String articleTag;
    private String articleTitle;
    private String articleUrl;
    private String articleId;
    private String articleContent;
    private String createDate;
    private String userName;
    private String userPic;
    private String articlePic;
    private List<String> customerArticlePic;

    public List<String> getCustomerArticlePic() {
        return customerArticlePic;
    }

    public void setCustomerArticlePic(List<String> customerArticlePic) {
        this.customerArticlePic = customerArticlePic;
    }

    public String getArticlePic() {
        return articlePic;
    }

    public void setArticlePic(String articlePic) {
        this.articlePic = articlePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleTag() {
        return articleTag;
    }

    public void setArticleTag(String articleTag) {
        this.articleTag = articleTag;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}

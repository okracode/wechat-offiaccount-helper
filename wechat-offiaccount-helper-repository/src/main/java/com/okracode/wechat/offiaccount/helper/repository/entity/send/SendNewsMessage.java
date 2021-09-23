package com.okracode.wechat.offiaccount.helper.repository.entity.send;

import java.util.List;

/**
 * @author Eric Ren
 * @ClassName: NewsMessage
 * @Description: 图文消息
 * @date May 8, 2017
 */
public class SendNewsMessage extends SendBaseMessage {

    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}

package org.jsoupDemo;

public abstract class ChapterInfo {
    /**
     * 章节名称
     */
    public String chapterName;
    /**
     * 章节内容
     */
    public String chapterContent;
    /**
     * 获取章节的url
     */
    public String chapterUrl;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    /**
     * 构建章节内容信息
     */
    public abstract void buildChapterContent();
}

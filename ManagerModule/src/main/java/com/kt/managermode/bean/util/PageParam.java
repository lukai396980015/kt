package com.kt.managermode.bean.util;

import java.io.Serializable;

public class PageParam implements Serializable
{
    /**
     * TODO 添加字段注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 每页显示行数
     */
    private int rows;
    
    /**
     * 页码
     */
    private int page;
    
    /**
     * 排序规则
     */
    private String sord;
    /**
     * 排序字段
     */
    private String sidx;
    
    /**
     * 页码偏移，用来获取当前数据的起始条数
     */
    private int offset;
    
    /**
     * 
     */
    private String _search;

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public String getSord()
    {
        return sord;
    }

    public void setSord(String sord)
    {
        this.sord = sord;
    }

    public String getSidx()
    {
        return sidx;
    }

    public void setSidx(String sidx)
    {
        this.sidx = sidx;
    }

    public String get_search()
    {
        return _search;
    }

    public void set_search(String _search)
    {
        this._search = _search;
    }

    public int getOffset()
    {
        int offset = (this.page - 1) * this.rows;
        offset = offset > 0 ? offset : 0;
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
}

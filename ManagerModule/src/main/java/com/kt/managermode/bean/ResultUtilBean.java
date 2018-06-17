package com.kt.managermode.bean;

import java.util.List;

public class ResultUtilBean<T>
{
    private int page;
    private Integer total;
    private int records;
    private List<T> rows;
    
    /**
     * 
     * 封装返回参数
     *
     * @author 鲁凯
     * @param page  当前页码
     * @param count 总页数
     * @param records 每页条数
     * @param rows  记录
     * @return
     */
    public static <T> ResultUtilBean getResult(int page,Integer count,int records,List<T> rows)
    {
        ResultUtilBean result = new ResultUtilBean();
        result.page=page;
        if(count!=null)
        {
            result.total=(count/records)+(count%records>0?1:0);
        }else
        {
            result.total=0;
        }
        result.records=records;
        result.rows=rows;
        return result;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }

    public int getRecords()
    {
        return records;
    }

    public void setRecords(int records)
    {
        this.records = records;
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }
    
}

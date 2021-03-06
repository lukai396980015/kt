package com.kt.managermode.bean.system;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.kt.managermode.bean.util.PageParam;

import java.sql.Timestamp;

public class MenuBean extends PageParam
{
    // 角色权限表id
    private int permissionId;
    
    // 权限名称
    private String permissionName;
    
    // url
    private String permissionUrl;
    
    // 图标
    private String permissionIcon;
    
    // 父级id
    private int permissionParentId;
    
    // 排序
    private String permissionOrder;
    
    // 状态
    private String permissionStatus;
    
    private Timestamp permissionCreatetime;

    private Timestamp permissionLastUpdatetime;
    
    public int getPermissionId()
    {
        return permissionId;
    }
    
    public void setPermissionId(int permissionId)
    {
        this.permissionId = permissionId;
    }
    
    public String getPermissionName()
    {
        return permissionName;
    }
    
    public void setPermissionName(String permissionName)
    {
        this.permissionName = permissionName;
    }
    
    public String getPermissionUrl()
    {
        return permissionUrl;
    }
    
    public void setPermissionUrl(String permissionUrl)
    {
        this.permissionUrl = permissionUrl;
    }
    
    public String getPermissionIcon()
    {
        return permissionIcon;
    }
    
    public void setPermissionIcon(String permissionIcon)
    {
        this.permissionIcon = permissionIcon;
    }
    
    public int getPermissionParentId()
    {
        return permissionParentId;
    }
    
    public void setPermissionParentId(int permissionParentId)
    {
        this.permissionParentId = permissionParentId;
    }
    
    public String getPermissionOrder()
    {
        return permissionOrder;
    }
    
    public void setPermissionOrder(String permissionOrder)
    {
        this.permissionOrder = permissionOrder;
    }
    
    public String getPermissionStatus()
    {
        return permissionStatus;
    }
    
    public void setPermissionStatus(String permissionStatus)
    {
        this.permissionStatus = permissionStatus;
    }

    public Timestamp getPermissionCreatetime()
    {
        return permissionCreatetime;
    }

    public void setPermissionCreatetime(Timestamp permissionCreatetime)
    {
        this.permissionCreatetime = permissionCreatetime;
    }

    public Timestamp getPermissionLastUpdatetime()
    {
        return permissionLastUpdatetime;
    }

    public void setPermissionLastUpdatetime(Timestamp permissionLastUpdatetime)
    {
        this.permissionLastUpdatetime = permissionLastUpdatetime;
    }
}

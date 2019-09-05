package com.mtx.common.util.page;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.RequestUtil;
import com.mtx.common.util.base.StringUtil;
import com.mtx.common.util.base.ToolUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 */
public class PageFactory<T> {
    public Page<T> defaultAll() {
        HttpServletRequest request = RequestUtil.getRequest();
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        if(ToolUtil.isEmpty(sort)){
            Page<T> page = new Page<>();
            page.setOpenSort(false);
            return page;
        }else{
            //驼峰转下划线
            sort= StringUtil.humpToLine(sort);
            Page<T> page = new Page<>();
            page.setOrderByField(sort);
            if(SystemConstant.ASC.equals(order)){
                page.setAsc(true);
            }else{
                page.setAsc(false);
            }
            return page;
        }
    }

    public Page<T> defaultAppPage(int currentPage) {
        Page<T> page = new Page<T>();
        page.setSize(20);
        page.setCurrent(currentPage);
        page.setOpenSort(false);

        return page;
    }

    public Page<T> defaultPage() {
        HttpServletRequest request = RequestUtil.getRequest();
        int limit = Integer.valueOf(request.getParameter("limit"));
        int offset = Integer.valueOf(request.getParameter("offset"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        if(ToolUtil.isEmpty(sort)){
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        }else{
            //驼峰转下划线
            sort= StringUtil.humpToLine(sort);
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if(SystemConstant.ASC.equals(order)){
                page.setAsc(true);
            }else{
                page.setAsc(false);
            }
            return page;
        }
    }


}

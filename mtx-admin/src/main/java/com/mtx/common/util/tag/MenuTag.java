package com.mtx.common.util.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 类描述：菜单标签
 */
public class MenuTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    protected Map<Integer, List<Menu>> menuFun;//菜单Map
    private String style="fineui";//菜单样式

    public void setStyle(String style) {
        this.style = style;
    }

    public void setMenuFun(Map<Integer, List<Menu>> menuFun) {
        this.menuFun = menuFun;
    }

    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspTagException {
        JspWriter out = null;
        StringBuffer endString = null;
        try {
            out = this.pageContext.getOut();
            endString = end();
            out.print(endString.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.clearBuffer();
            } catch (Exception e2) {
            }
        }
        return EVAL_PAGE;
    }

    public StringBuffer end() {

        StringBuffer sb = new StringBuffer();

        sb.append(ListtoMenu.getFineuiMultistageTree(menuFun));


        return sb;
    }



}

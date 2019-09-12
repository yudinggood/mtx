package com.mtx.common.util.tag;

import com.mtx.common.util.base.TypeConversionUtil;

import java.util.List;
import java.util.Map;


/**
 * 动态菜单栏生成
 */
public class ListtoMenu {

/*<li class='menu-item'>
	<a href="system/permission/index">
		<i class="fa fa fa-calendar"></i><span class="menu-text">权限设置</span>
	</a>
</li>
<li class='menu-item'>
	<a href="swagger-ui.html">
		<i class="fa fa fa-bullhorn"></i><span class="menu-text">Swagger查看</span>
	</a>
</li>
<li class='menu-item'>
	<a href="druid/index.html">
		<i class="fa fa fa-bullhorn"></i><span class="menu-text">Druid查看</span>
	</a>
</li>*/
		/**
		 * 获取fineUI菜单树
		 * @param map
		 * @return
		 */
		public static String getFineuiMultistageTree(Map<Integer, List<Menu>> map) {
			if(map==null||map.size()==0||!map.containsKey(0)){
				return "<div style=\"text-align:center;margin-top:30px;\"><span >不具有任何权限,<br>请找管理员分配权限</span></div>";
			}
			StringBuffer menuString = new StringBuffer();
			List<Menu> list = map.get(0);
			int curIndex = 0;
			for (Menu function : list) {
				String order = TypeConversionUtil.objectToString(function.getPermissionOrder());
				menuString.append("<li class='menu-item'>");

				if(!function.hasSubFunction(map)){
					if(function.getIcon()!=null&&!"".equals(function.getIcon().trim())){
						menuString.append("<a href=\""+function.getUri()+"\"><i class=\"fa "+function.getIcon()+"\"></i>");
					}else{
						menuString.append("<a href=\""+function.getUri()+"\"><i class=\"fa fa-columns\"></i>");
					}
				}else {
					if(function.getIcon()!=null&&!"".equals(function.getIcon().trim())){
						menuString.append("<a href><i class=\"fa "+function.getIcon()+"\"></i>");
					}else{
						menuString.append("<a href><i class=\"fa fa-columns\"></i>");
					}
				}

				menuString.append("<span class=\"menu-text\">");
				menuString.append(function.getName());
				menuString.append("</span>");
				if(!function.hasSubFunction(map)){
					menuString.append("</a></li>");
				}else{
					menuString.append("<i class=\"icon-font icon-right\"></i>");
					menuString.append("</a><ul  class=\"menu-item-child\" id='menu-child-"+order+ "' >");
					menuString.append(getFineuiSubMenu(function,1,map));
					menuString.append("</ul></li>");
				}
				curIndex++;
			}
			return String.valueOf(menuString);
		}
		
		private static String getFineuiSubMenu(Menu parent, int level, Map<Integer, List<Menu>> map) {
			StringBuffer menuString = new StringBuffer();
			List<Menu> list = map.get(level);
			for (Menu function : list) {
				if (function.getPermissionPid().equals(parent.getPermissionId())){
					menuString.append(getLeafOfFineuiTree(function,map));
				}
			}
			return String.valueOf(menuString);
		}
		
		private static String getLeafOfFineuiTree(Menu function, Map<Integer, List<Menu>> map) {
			StringBuffer menuString = new StringBuffer();
			String name = function.getName() ;
			menuString.append("<li> <a class=\"F_menuItem\" href=\"").append(function.getUri()).append("\">");
			if(!function.hasSubFunction(map)){

				if(function.getIcon()!=null&&!"".equals(function.getIcon().trim())){
					menuString.append("<i class=\"fa "+function.getIcon()+"\"></i>");
				}
				menuString.append("<span>");
				menuString.append(name);
				menuString.append("</span>");

				menuString.append("</a>");
				menuString.append("</li>");
			}else {

				if(function.getIcon()!=null&&!"".equals(function.getIcon().trim())){
					menuString.append("<i class=\"fa "+function.getIcon()+"\"></i>");
				}else{
					menuString.append("<i class=\"fa fa-columns\"></i>");
				}
				menuString.append("<span>");
				menuString.append(name);
				menuString.append("</span>");

				menuString.append("<i class=\"icon-font icon-right\"></i>");
				menuString.append("</a>");
				menuString.append("<ul class=\"menu-item-child\" >");
				menuString.append(getFineuiSubMenu(function,2,map));
				menuString.append("</ul></li>");
			}
			return String.valueOf(menuString);
		}



}
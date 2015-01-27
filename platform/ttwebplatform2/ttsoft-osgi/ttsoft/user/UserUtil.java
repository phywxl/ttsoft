package ttsoft.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ttsoft.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserUtil {
	private static Logger _logger = LogManager.getLogger(UserUtil.class);
	
	/**
	 * EasyUI树节点类
	 * @author wangxianliang
	 */
	public static class EasyUITreeNode {
		private String id;
		private String text;
		private String iconCls;
		private String state = "open";
		private boolean checked;
		private List<EasyUITreeNode> children;
		private Map<String, Object> attributes;
		
		public EasyUITreeNode() {
		}
		
		public EasyUITreeNode(String id, String text, String iconCls, String state, boolean checked, List<EasyUITreeNode> children, Map<String, Object> attributes) {
			this.id = id; 
			this.text = text;
			if (iconCls == null) {
				this.iconCls = "";
			} else {
				this.iconCls = iconCls;
			}
			if (state == null) {
				this.state = "open"; 
			} else {
				this.state = state; 
			}
			this.checked = checked; 
			if (children == null) {
				this.children = new ArrayList<EasyUITreeNode>();  
			} else {
				this.children = children; 
			}
			if (attributes == null) {
				this.attributes = new HashMap<String, Object>();
			} else {
				this.attributes = attributes;
			}
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getIconCls() {
			return iconCls;
		}
		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public List<EasyUITreeNode> getChildren() {
			return children;
		}
		public void setChildren(List<EasyUITreeNode> children) {
			this.children = children;
		}
		public void addChild(EasyUITreeNode child) {
			if (child == null)
				return;
			if (this.children == null)
				this.children = new ArrayList<EasyUITreeNode>();
			this.children.add(child);
		}
		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		@Override
		public String toString() {
			return "EasyUITreeNode [id=" + id + ", text=" + text + ", iconCls="
					+ iconCls + ", state=" + state + ", checked=" + checked
					+ ", children=" + children + ", attributes=" + attributes
					+ "]";
		}
	}
	/**
	 * 树形OrgVo对象转为EasyUITreeNode树对象
	 * @param orgVos
	 * @return
	 */
	public static List<EasyUITreeNode> treeOrgVoToEasyUITreeNode(List<OrgVo> orgVos) {
		if (orgVos == null || orgVos.size() == 0)
			return Collections.EMPTY_LIST;
		
		Integer index = 1;
		OrgVo orgVo = null;
		EasyUITreeNode node = null;
		List<EasyUITreeNode> nodes = new ArrayList<EasyUITreeNode>();
		for (int i = 0; i < orgVos.size(); i++) {
			orgVo = orgVos.get(i);
			if (orgVo != null) {
				nodes.add(convertOrgVoToEasyUITreeNode(index, orgVo, null));
			}
		}
		
		return nodes;
	}
	private static EasyUITreeNode convertOrgVoToEasyUITreeNode(Integer index, OrgVo orgVo, EasyUITreeNode parentEasyUITreeNode) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", orgVo.getOrgAddress() == null ? "" : orgVo.getOrgAddress());
		EasyUITreeNode node = new EasyUITreeNode(orgVo.getOrgId(), orgVo.getOrgName(), null, "open", false, null, attributes);
		if (parentEasyUITreeNode != null) {
			parentEasyUITreeNode.addChild(node);
		}
		index = (index + 1);
		if (orgVo.getChildOrgVos() != null) {
			OrgVo c = null;
			for (int i = 0; i < orgVo.getChildOrgVos().size(); i++) {
				c = orgVo.getChildOrgVos().get(i);
				if (c != null) {
					convertOrgVoToEasyUITreeNode(index, c, node);
				}
			}
		}
		
		return node;
	}
	/**
	 * 树形MenuVo对象转为EasyUITreeNode树对象
	 * @param menuVos
	 * @return
	 */
	public static List<EasyUITreeNode> treeMenuVoToEasyUITreeNode(List<MenuVo> menuVos) {
		if (menuVos == null || menuVos.size() == 0)
			return Collections.EMPTY_LIST;
		
		Integer index = 1;
		MenuVo menuVo = null;
		EasyUITreeNode node = null;
		List<EasyUITreeNode> nodes = new ArrayList<EasyUITreeNode>();
		for (int i = 0; i < menuVos.size(); i++) {
			menuVo = menuVos.get(i);
			if (menuVo != null) {
				nodes.add(convertMenuVoToEasyUITreeNode(index, menuVo, null));
			}
		}
		
		return nodes;
	}
	private static EasyUITreeNode convertMenuVoToEasyUITreeNode(Integer index, MenuVo menuVo, EasyUITreeNode parentEasyUITreeNode) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", menuVo.getResValue() == null ? "" : menuVo.getResValue());
		EasyUITreeNode node = new EasyUITreeNode(menuVo.getMenuId(), menuVo.getResName(), null, "open", false, null, attributes);
		if (parentEasyUITreeNode != null) {
			parentEasyUITreeNode.addChild(node);
		}
		index = (index + 1);
		if (menuVo.getChildMenuVos() != null) {
			MenuVo c = null;
			for (int i = 0; i < menuVo.getChildMenuVos().size(); i++) {
				c = menuVo.getChildMenuVos().get(i);
				if (c != null) {
					convertMenuVoToEasyUITreeNode(index, c, node);
				}
			}
		}
		
		return node;
	}
	/**
	 * 平坦MenuVo对象转为EasyUITreeNode树对象
	 * @param menuVos
	 * @return
	 */
	public static List<EasyUITreeNode> flatMenuVoToEasyUITreeNode(List<MenuVo> menuVos) {
		if (menuVos == null || menuVos.size() == 0)
			return Collections.EMPTY_LIST;
		
		for (MenuVo menuVo : menuVos) {
			if (menuVo == null)
				continue;
			if (menuVo.getParentMenuId() == null) {
				if (menuVo.getParentMenuVo() != null && menuVo.getParentMenuVo().getMenuId() != null)
					menuVo.setParentMenuId(menuVo.getParentMenuVo().getMenuId());
			}
			menuVo.setParentMenuVo(null);
			menuVo.setChildMenuVos(null);
		}
		
		for (MenuVo menuVo : menuVos) {
			if (menuVo == null)
				continue;
			
			for (MenuVo menuVo2 : menuVos) {
				if (menuVo == menuVo2)
					continue;
				
				if (menuVo2.getParentMenuId() != null && !menuVo2.getParentMenuId().trim().equals("") 
						&& menuVo.getMenuId() != null && !menuVo.getMenuId().trim().equals("") 
						&& menuVo2.getParentMenuId().trim().equals(menuVo.getMenuId().trim())) {
					if (menuVo.getChildMenuVos() == null || !menuVo.getChildMenuVos().contains(menuVo2))
						menuVo.addChildMenuVo(menuVo2);
					menuVo2.setParentMenuVo(menuVo);
				}
				
				if (menuVo.getParentMenuId() != null && !menuVo.getParentMenuId().trim().equals("") 
						&& menuVo2.getMenuId() != null && !menuVo2.getMenuId().trim().equals("") 
						&& menuVo.getParentMenuId().trim().equals(menuVo2.getMenuId().trim())) {
					if (menuVo2.getChildMenuVos() == null || !menuVo2.getChildMenuVos().contains(menuVo))
						menuVo2.addChildMenuVo(menuVo);
					menuVo.setParentMenuVo(menuVo2);
				}
			}
		}
		
		List<MenuVo> noParentMenuVo = new ArrayList<MenuVo>();
		for (MenuVo menuVo : menuVos) {
			if (menuVo == null)
				continue;
			
			if (menuVo.getParentMenuVo() == null)
				noParentMenuVo.add(menuVo);
		}
		
		return treeMenuVoToEasyUITreeNode(noParentMenuVo);
	}
	
	/**
	 * 判断启用标志
	 * @param enabled
	 * @return
	 */
	public static boolean isEnabled(String enabled) {
		return CommonUtil.isEnabled(enabled);
	}
	
	public static void main(String[] args) {
		List<MenuVo> menuVos = new ArrayList<MenuVo>();
		MenuVo p = new MenuVo();
		menuVos.add(p);
		p.setResName("父节点");
		p.setResValue("父节点Url");
		MenuVo c = new MenuVo();
		p.addChildMenuVo(c);
		c.setResName("子节点");
		c.setResValue("子节点Url");
		System.out.println(JSONArray.fromObject(UserUtil.treeMenuVoToEasyUITreeNode(menuVos)));
		
		System.out.println();
		
		menuVos = new ArrayList<MenuVo>();
		c = new MenuVo();
		menuVos.add(c);
		c.setParentMenuId("1");
		c.setResName("子节点");
		c.setResValue("子节点Url");
		p = new MenuVo();
		menuVos.add(p);
		p.setMenuId("1");
		p.setResName("父节点");
		p.setResValue("父节点Url");
		
		System.out.println(JSONArray.fromObject(UserUtil.flatMenuVoToEasyUITreeNode(menuVos)));
	}
}

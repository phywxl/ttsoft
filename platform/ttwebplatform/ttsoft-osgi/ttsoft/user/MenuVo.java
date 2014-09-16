package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuVo implements Serializable {
	//数据权限
	public static String DataPower_AddMenu_Btn      = "DataPower_USR_Menu_AddBtn";           //增加按钮
	public static String DataPower_ModMenu_Btn      = "DataPower_USR_Menu_ModBtn";           //修改按钮
	public static String DataPower_ModLocked_Btn    = "DataPower_USR_Menu_LockedBtn";        //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn   = "DataPower_USR_Menu_EnabedBtn";        //修改-启用按钮
	public static String DataPower_DelMenu_Btn      = "DataPower_USR_Menu_DelBtn";           //删除按钮
	public static String DataPower_ConfigBelongRole_Btn  = "DataPower_USR_Menu_ConfigBelongRoleBtn";              //配置隶属角色按钮
	public static String DataPower_ConfigManagedUser_Btn  = "DataPower_USR_Menu_ConfigManagedUserBtn";              //配置管理用户按钮
	public static String DataPower_ConfigBelongRes_Btn  = "DataPower_USR_Menu_ConfigBelongResBtn";              //配置隶属资源按钮
	
	//基本信息
	private String menuId;         //菜单ID
	private Double menuOrders;     //序号（排序用）

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public Double getMenuOrders() {
		return menuOrders;
	}
	public void setMenuOrders(Double menuOrders) {
		this.menuOrders = menuOrders;
	}
	
	//绑定的资源
	private String resId;      //菜单绑定的资源ID
	private String resName;    //菜单绑定的资源名称
	private String resValue;   //菜单绑定的资源值
	private ResVo  resVo;      //菜单绑定的资源值对象

	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public ResVo getResVo() {
		return resVo;
	}
	public void setResVo(ResVo resVo) {
		this.resVo = resVo;
	}
	public String getResValue() {
		return resValue;
	}
	public void setResValue(String resValue) {
		this.resValue = resValue;
	}
	
	
	//隶属的菜单组
	private String menuGroupId;     //菜单组ID
	private MenuGroupVo menuGroupVo;//菜单组值对象
	
	public String getMenuGroupId() {
		return menuGroupId;
	}
	public void setMenuGroupId(String menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	public MenuGroupVo getMenuGroupVo() {
		return menuGroupVo;
	}
	public void setMenuGroupVo(MenuGroupVo menuGroupVo) {
		this.menuGroupVo = menuGroupVo;
	}
	

	//父菜单
	private String parentMenuId; //父菜单ID
	private MenuVo parentMenuVo;   //父菜单值对象

	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public MenuVo getParentMenuVo() {
		return parentMenuVo;
	}
	public void setParentMenuVo(MenuVo parentMenuVo) {
		this.parentMenuVo = parentMenuVo;
	}


	//子菜单
	private List<String> childMenuIds; //子菜单ID
	private List<MenuVo> childMenuVos;    //子菜单值对象

	public List<String> getChildMenuIds() {
		return childMenuIds;
	}
	public void setChildMenuIds(List<String> childMenuIds) {
		this.childMenuIds = childMenuIds;
	}
	public List<MenuVo> getChildMenuVos() {
		return childMenuVos;
	}
	public void setChildMenuVos(List<MenuVo> childMenuVos) {
		this.childMenuVos = childMenuVos;
	}
	public void addChildMenuVo(MenuVo childMenuVo) {
		if (childMenuVo == null)
			return;
		if (this.childMenuVos == null)
			this.childMenuVos = new ArrayList<MenuVo>();
		this.childMenuVos.add(childMenuVo);
	}
	
	
	//备用字段
	private String standbyField0;//备用字段0
	private String standbyField1;//备用字段1
	private String standbyField2;//备用字段2
	private String standbyField3;//备用字段3
	private String standbyField4;//备用字段4
	private String standbyField5;//备用字段5
	private String standbyField6;//备用字段6
	private String standbyField7;//备用字段7
	private String standbyField8;//备用字段8
	private String standbyField9;//备用字段9
	public String getStandbyField0() {
		return standbyField0;
	}
	public void setStandbyField0(String standbyField0) {
		this.standbyField0 = standbyField0;
	}
	public String getStandbyField1() {
		return standbyField1;
	}
	public void setStandbyField1(String standbyField1) {
		this.standbyField1 = standbyField1;
	}
	public String getStandbyField2() {
		return standbyField2;
	}
	public void setStandbyField2(String standbyField2) {
		this.standbyField2 = standbyField2;
	}
	public String getStandbyField3() {
		return standbyField3;
	}
	public void setStandbyField3(String standbyField3) {
		this.standbyField3 = standbyField3;
	}
	public String getStandbyField4() {
		return standbyField4;
	}
	public void setStandbyField4(String standbyField4) {
		this.standbyField4 = standbyField4;
	}
	public String getStandbyField5() {
		return standbyField5;
	}
	public void setStandbyField5(String standbyField5) {
		this.standbyField5 = standbyField5;
	}
	public String getStandbyField6() {
		return standbyField6;
	}
	public void setStandbyField6(String standbyField6) {
		this.standbyField6 = standbyField6;
	}
	public String getStandbyField7() {
		return standbyField7;
	}
	public void setStandbyField7(String standbyField7) {
		this.standbyField7 = standbyField7;
	}
	public String getStandbyField8() {
		return standbyField8;
	}
	public void setStandbyField8(String standbyField8) {
		this.standbyField8 = standbyField8;
	}
	public String getStandbyField9() {
		return standbyField9;
	}
	public void setStandbyField9(String standbyField9) {
		this.standbyField9 = standbyField9;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuVo other = (MenuVo) obj;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MenuVo [菜单ID=" + menuId + ", 菜单组ID=" + menuGroupId
				+ ", 顺序=" + menuOrders + ", 资源名称=" + resName
				+ ", 资源ID=" + resId + ", 父菜单ID=" + parentMenuId + "]";
	}
}

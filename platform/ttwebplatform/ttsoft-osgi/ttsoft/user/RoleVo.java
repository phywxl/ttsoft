package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoleVo implements Serializable {
	//数据权限
	public static String DataPower_AddRole_Btn = "DataPower_USR_Role_AddBtn";          //增加按钮
	public static String DataPower_ModRole_Btn = "DataPower_USR_Role_ModBtn";          //修改按钮
	public static String DataPower_ModLocked_Btn = "DataPower_USR_Role_LockedBtn";     //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn = "DataPower_USR_Role_EnabedBtn";    //修改-启用按钮
	public static String DataPower_DelRole_Btn = "DataPower_USR_Role_DelBtn";          //删除按钮
	public static String DataPower_ConfigBelongUser_Btn   = "DataPower_USR_Role_ConfigBelongUserBtn";           //配置隶属用户按钮
	public static String DataPower_ConfigManagedUser_Btn  = "DataPower_USR_Role_ConfigManagedUserBtn";          //配置管理用户按钮
	public static String DataPower_ConfigBelongRes_Btn  = "DataPower_USR_Role_ConfigBelongResBtn";              //配置隶属资源按钮
	public static String DataPower_ConfigBelongMenuGroup_Btn  = "DataPower_USR_Role_ConfigBelongMenuGroupBtn";              //配置隶属菜单组按钮
	public static String DataPower_ConfigBelongMenu_Btn  = "DataPower_USR_Role_ConfigBelongMenuBtn";              //配置隶属菜单按钮
		
	//基本信息
	private String roleId;          //角色ID
	private String roleCode;        //角色编码
	private String roleName;        //角色名称
	private Date   roleStartDate;   //期限起始日期
	private Date   roleEndDate;     //期限终止日期
	private String roleTemped;      //是否临时(Y/N)
	private String roleLocked;      //是否锁定(Y/N)
	private String roleDesc;        //描述
	private String roleEnabled;     //是否有效
	private Double roleOrders;      //序号（排序用）
	private Date   roleCreateDate;  //创建日期
	private String roleCreateName;  //创建帐号（创建人）（userId）
	private Date   roleOperateTime; //操作时间（插入，更新）
	private String roleOperateName; //操作帐号（userId）
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getRoleStartDate() {
		return roleStartDate;
	}
	public void setRoleStartDate(Date roleStartDate) {
		this.roleStartDate = roleStartDate;
	}
	public Date getRoleEndDate() {
		return roleEndDate;
	}
	public void setRoleEndDate(Date roleEndDate) {
		this.roleEndDate = roleEndDate;
	}
	public String getRoleTemped() {
		return roleTemped;
	}
	public void setRoleTemped(String roleTemped) {
		this.roleTemped = roleTemped;
	}
	public String getRoleLocked() {
		return roleLocked;
	}
	public void setRoleLocked(String roleLocked) {
		this.roleLocked = roleLocked;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleEnabled() {
		return roleEnabled;
	}
	public void setRoleEnabled(String roleEnabled) {
		this.roleEnabled = roleEnabled;
	}
	public Double getRoleOrders() {
		return roleOrders;
	}
	public void setRoleOrders(Double roleOrders) {
		this.roleOrders = roleOrders;
	}
	public Date getRoleCreateDate() {
		return roleCreateDate;
	}
	public void setRoleCreateDate(Date roleCreateDate) {
		this.roleCreateDate = roleCreateDate;
	}
	public String getRoleCreateName() {
		return roleCreateName;
	}
	public void setRoleCreateName(String roleCreateName) {
		this.roleCreateName = roleCreateName;
	}
	public Date getRoleOperateTime() {
		return roleOperateTime;
	}
	public void setRoleOperateTime(Date roleOperateTime) {
		this.roleOperateTime = roleOperateTime;
	}
	public String getRoleOperateName() {
		return roleOperateName;
	}
	public void setRoleOperateName(String roleOperateName) {
		this.roleOperateName = roleOperateName;
	}
	
	
	//资源
	private List<String> resIds;
	private List<ResVo>  resVos;

	public List<String> getResIds() {
		return resIds;
	}
	public void setResIds(List<String> resIds) {
		this.resIds = resIds;
	}
	public List<ResVo> getResVos() {
		return resVos;
	}
	public void setResVos(List<ResVo> resVos) {
		this.resVos = resVos;
	}
	public void addResVo(ResVo resVo) {
		if (resVo == null)
			return;
		if (this.resVos == null)
			this.resVos = new ArrayList<ResVo>();
		
		this.resVos.add(resVo);
	}
	
	
	//默认菜单组
	private String menuGroupId;
	private MenuGroupVo menuGroupVo;

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
		
		
	//拥有的菜单组定义
	private List<MenuGroupVo> menuGroupVos;
	
	public List<MenuGroupVo> getMenuGroupVos() {
		return menuGroupVos;
	}
	public void setMenuGroupVos(List<MenuGroupVo> menuGroupVos) {
		this.menuGroupVos = menuGroupVos;
	}
	public void addMenuGroupVo(MenuGroupVo menuGroupVo) {
		if (menuGroupVo == null)
			return;
		
		if (this.menuGroupVos == null)
			this.menuGroupVos = new ArrayList<MenuGroupVo>();
		this.menuGroupVos.add(menuGroupVo);
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
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		RoleVo other = (RoleVo) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RoleVo [角色ID=" + roleId + ", 编码=" + roleCode
				+ ", 名称=" + roleName + ", 期限起始日期=" + roleStartDate
				+ ", 期限终止日期=" + roleEndDate + ", 是否临时=" + roleTemped
				+ ", 是否锁定=" + roleLocked + ", 描述=" + roleDesc
				+ ", 是否有效=" + roleEnabled + ", 序号=" + roleOrders
				+ ", 创建日期=" + roleCreateDate + ", 创建人="
				+ roleCreateName + ", 操作时间=" + roleOperateTime
				+ ", 操作人=" + roleOperateName + "]";
	}	
}

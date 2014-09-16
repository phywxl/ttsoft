package ttsoft.user;

import java.io.Serializable;
import java.util.Date;

public class RoleMenuGroupVo implements Serializable {
	//基本信息，双主键：roleId、menuGroupId
	private String      roleId;                             //角色ID
	private UserVo      roleVo;                             //角色值对象
	private String      menuGroupId;                        //菜单组ID
	private MenuGroupVo menuGroupVo;                        //菜单组值对象
	private String      roleMenuGroupDefaulted;             //是否默认(Y/N)，一个用户仅有一个默认菜单组
	private Date        roleMenuGroupStartDate;             //期限起始日期
	private Date        roleMenuGroupEndDate;               //期限终止日期
	private String      roleMenuGroupTemped;                //是否临时(Y/N)
	private String      roleMenuGroupLocked;                //是否锁定(Y/N)
	private String      roleMenuGroupDesc;                  //描述
	private String      roleMenuGroupEnabled;               //是否有效
	private Double      roleMenuGroupOrders;                //序号（排序用）
	private Date        roleMenuGroupCreateDate;            //创建日期
	private String      roleMenuGroupCreateName;            //创建帐号（创建人）（userId）
	private Date        roleMenuGroupOperateTime;           //操作时间（插入，更新）
	private String      roleMenuGroupOperateName;           //操作帐号（userId）
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public UserVo getRoleVo() {
		return roleVo;
	}
	public void setRoleVo(UserVo roleVo) {
		this.roleVo = roleVo;
	}
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
	public String getRoleMenuGroupDefaulted() {
		return roleMenuGroupDefaulted;
	}
	public void setRoleMenuGroupDefaulted(String roleMenuGroupDefaulted) {
		this.roleMenuGroupDefaulted = roleMenuGroupDefaulted;
	}
	public Date getRoleMenuGroupStartDate() {
		return roleMenuGroupStartDate;
	}
	public void setRoleMenuGroupStartDate(Date roleMenuGroupStartDate) {
		this.roleMenuGroupStartDate = roleMenuGroupStartDate;
	}
	public Date getRoleMenuGroupEndDate() {
		return roleMenuGroupEndDate;
	}
	public void setRoleMenuGroupEndDate(Date roleMenuGroupEndDate) {
		this.roleMenuGroupEndDate = roleMenuGroupEndDate;
	}
	public String getRoleMenuGroupTemped() {
		return roleMenuGroupTemped;
	}
	public void setRoleMenuGroupTemped(String roleMenuGroupTemped) {
		this.roleMenuGroupTemped = roleMenuGroupTemped;
	}
	public String getRoleMenuGroupLocked() {
		return roleMenuGroupLocked;
	}
	public void setRoleMenuGroupLocked(String roleMenuGroupLocked) {
		this.roleMenuGroupLocked = roleMenuGroupLocked;
	}
	public String getRoleMenuGroupDesc() {
		return roleMenuGroupDesc;
	}
	public void setRoleMenuGroupDesc(String roleMenuGroupDesc) {
		this.roleMenuGroupDesc = roleMenuGroupDesc;
	}
	public String getRoleMenuGroupEnabled() {
		return roleMenuGroupEnabled;
	}
	public void setRoleMenuGroupEnabled(String roleMenuGroupEnabled) {
		this.roleMenuGroupEnabled = roleMenuGroupEnabled;
	}
	public Double getRoleMenuGroupOrders() {
		return roleMenuGroupOrders;
	}
	public void setRoleMenuGroupOrders(Double roleMenuGroupOrders) {
		this.roleMenuGroupOrders = roleMenuGroupOrders;
	}
	public Date getRoleMenuGroupCreateDate() {
		return roleMenuGroupCreateDate;
	}
	public void setRoleMenuGroupCreateDate(Date roleMenuGroupCreateDate) {
		this.roleMenuGroupCreateDate = roleMenuGroupCreateDate;
	}
	public String getRoleMenuGroupCreateName() {
		return roleMenuGroupCreateName;
	}
	public void setRoleMenuGroupCreateName(String roleMenuGroupCreateName) {
		this.roleMenuGroupCreateName = roleMenuGroupCreateName;
	}
	public Date getRoleMenuGroupOperateTime() {
		return roleMenuGroupOperateTime;
	}
	public void setRoleMenuGroupOperateTime(Date roleMenuGroupOperateTime) {
		this.roleMenuGroupOperateTime = roleMenuGroupOperateTime;
	}
	public String getRoleMenuGroupOperateName() {
		return roleMenuGroupOperateName;
	}
	public void setRoleMenuGroupOperateName(String roleMenuGroupOperateName) {
		this.roleMenuGroupOperateName = roleMenuGroupOperateName;
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
		result = prime * result
				+ ((menuGroupId == null) ? 0 : menuGroupId.hashCode());
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
		RoleMenuGroupVo other = (RoleMenuGroupVo) obj;
		if (menuGroupId == null) {
			if (other.menuGroupId != null)
				return false;
		} else if (!menuGroupId.equals(other.menuGroupId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RoleMenuGroupVo [角色ID=" + roleId
				+ ", 菜单组ID=" + menuGroupId + ", 是否默认="
				+ roleMenuGroupDefaulted + ", 期限起始日期="
				+ roleMenuGroupStartDate + ", 期限终止日期="
				+ roleMenuGroupEndDate + ", 是否临时="
				+ roleMenuGroupTemped + ", 是否锁定="
				+ roleMenuGroupLocked + ", 描述="
				+ roleMenuGroupDesc + ", 是否有效="
				+ roleMenuGroupEnabled + ", 序号="
				+ roleMenuGroupOrders + ", 创建日期="
				+ roleMenuGroupCreateDate + ", 创建人="
				+ roleMenuGroupCreateName + ", 操作时间="
				+ roleMenuGroupOperateTime + ", 操作人="
				+ roleMenuGroupOperateName + "]";
	}
}

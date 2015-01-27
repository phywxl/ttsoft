package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.components.Set;

import ttsoft.osgi.config.TtOsgiConfiguration;

public class UserVo implements Serializable {
	private static Logger _logger = LogManager.getLogger(UserVo.class);
	//数据权限
	public static String DataPower_AddUser_Btn = "DataPower_USR_User_AddBtn";          //增加按钮
	public static String DataPower_ModUser_Btn = "DataPower_USR_User_ModBtn";          //修改按钮
	public static String DataPower_ModLocked_Btn = "DataPower_USR_User_LockedBtn";     //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn = "DataPower_USR_User_EnabedBtn";    //修改-启用按钮
	public static String DataPower_DelUser_Btn = "DataPower_USR_User_DelBtn";          //删除按钮
	public static String DataPower_ConfigBelongRole_Btn   = "DataPower_USR_User_ConfigBelongRoleBtn";           //配置隶属角色按钮
	public static String DataPower_ConfigManagedRole_Btn  = "DataPower_USR_User_ConfigManagedRoleBtn";          //配置可管理角色按钮
	public static String DataPower_ConfigManagedRes_Btn  = "DataPower_USR_User_ConfigManagedResBtn";            //配置可管理资源按钮
	public static String DataPower_ConfigManagedMenuGroup_Btn  = "DataPower_USR_User_ConfigManagedMenuGroupBtn";            //配置可管理菜单组按钮
	public static String DataPower_ConfigManagedMenu_Btn  = "DataPower_USR_User_ConfigManagedMenuBtn";          //配置可管理菜单按钮
	
	//基本信息
	private String userId;          //用户ID
	private String userCode;        //用户编码
	private String userLoginName;   //登录帐号（登录名）
	private String userPassword;    //登录密码
	private String userEmail;       //邮箱
	private String userAdName;      //ad帐号
	private String userAdPassword;  //ad密码
	private String userLdapName;    //ldap帐号
	private Date   userStartDate;   //期限起始日期
	private Date   userEndDate;     //期限终止日期
	private String userTemped;      //是否临时(Y/N)
	private String userLocked;      //是否锁定(Y/N)
	private String userDesc;        //描述
	private String userEnabled;     //是否有效
	private Double userOrders;      //序号（排序用）
	private Date   userCreateDate;  //创建日期
	private String userCreateName;  //创建帐号（创建人）（userId）
	private Date   userOperateTime; //操作时间（插入，更新）
	private String userOperateName; //操作帐号（userId）

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserAdName() {
		return userAdName;
	}
	public void setUserAdName(String userAdName) {
		this.userAdName = userAdName;
	}
	public String getUserAdPassword() {
		return userAdPassword;
	}
	public void setUserAdPassword(String userAdPassword) {
		this.userAdPassword = userAdPassword;
	}
	public String getUserLdapName() {
		return userLdapName;
	}
	public void setUserLdapName(String userLdapName) {
		this.userLdapName = userLdapName;
	}
	public Date getUserStartDate() {
		return userStartDate;
	}
	public void setUserStartDate(Date userStartDate) {
		this.userStartDate = userStartDate;
	}
	public Date getUserEndDate() {
		return userEndDate;
	}
	public void setUserEndDate(Date userEndDate) {
		this.userEndDate = userEndDate;
	}
	public String getUserTemped() {
		return userTemped;
	}
	public void setUserTemped(String userTemped) {
		this.userTemped = userTemped;
	}
	public String getUserLocked() {
		return userLocked;
	}
	public void setUserLocked(String userLocked) {
		this.userLocked = userLocked;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getUserEnabled() {
		return userEnabled;
	}
	public void setUserEnabled(String userEnabled) {
		this.userEnabled = userEnabled;
	}
	public Double getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(Double userOrders) {
		this.userOrders = userOrders;
	}
	public Date getUserCreateDate() {
		return userCreateDate;
	}
	public void setUserCreateDate(Date userCreateDate) {
		this.userCreateDate = userCreateDate;
	}
	public String getUserCreateName() {
		return userCreateName;
	}
	public void setUserCreateName(String userCreateName) {
		this.userCreateName = userCreateName;
	}
	public Date getUserOperateTime() {
		return userOperateTime;
	}
	public void setUserOperateTime(Date userOperateTime) {
		this.userOperateTime = userOperateTime;
	}
	public String getUserOperateName() {
		return userOperateName;
	}
	public void setUserOperateName(String userOperateName) {
		this.userOperateName = userOperateName;
	}
	
	
	//关联人员
	private String   personId;   //人员ID
	private PersonVo personVo;   //人员值对象

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public PersonVo getPersonVo() {
		return personVo;
	}
	public void setPersonVo(PersonVo personVo) {
		this.personVo = personVo;
	}


	//拥有角色
	private List<String> roleIds;
	private List<RoleVo> roleVos;

	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public List<RoleVo> getRoleVos() {
		return roleVos;
	}
	public void setRoleVos(List<RoleVo> roleVos) {
		this.roleVos = roleVos;
	}
	public void addRoleVo(RoleVo roleVo) {
		if (roleVo == null)
			return;
		if (this.roleVos == null) {
			this.roleVos = new ArrayList<RoleVo>();
		}
		this.roleVos.add(roleVo);
	}
	
	
	//可管理角色
	private List<String> managedRoleIds;
	private List<RoleVo> managedRoleVos;

	public List<String> getManagedRoleIds() {
		return managedRoleIds;
	}
	public void setManagedRoleIds(List<String> managedRoleIds) {
		this.managedRoleIds = managedRoleIds;
	}
	public List<RoleVo> getManagedRoleVos() {
		return managedRoleVos;
	}
	public void setManagedRoleVos(List<RoleVo> managedRoleVos) {
		this.managedRoleVos = managedRoleVos;
	}
	public void addManagedRoleVo(RoleVo managedRoleVo) {
		if (managedRoleVo == null)
			return;
		if (this.managedRoleVos == null) {
			this.managedRoleVos = new ArrayList<RoleVo>();
		}
		this.managedRoleVos.add(managedRoleVo);
	}


	//可管理资源
	private List<ResVo> managedResIds;
	private List<ResVo> managedResVos;

	public List<ResVo> getManagedResIds() {
		return managedResIds;
	}
	public void setManagedResIds(List<ResVo> managedResIds) {
		this.managedResIds = managedResIds;
	}
	public List<ResVo> getManagedResVos() {
		return managedResVos;
	}
	public void setManagedResVos(List<ResVo> managedResVos) {
		this.managedResVos = managedResVos;
	}
	public void addManagedResVo(ResVo resVo) {
		if (resVo == null)
			return;
		if (this.managedResVos == null) {
			this.managedResVos = new ArrayList<ResVo>();
		}
		this.managedResVos.add(resVo);
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
		
		
	//拥有的菜单组定义 - 放置在角色中
	/*private List<UserMenuGroupVo> userMenuGroupVos;

	public List<UserMenuGroupVo> getUserMenuGroupVos() {
		return userMenuGroupVos;
	}
	public void setUserMenuGroupVos(List<UserMenuGroupVo> userMenuGroupVos) {
		this.userMenuGroupVos = userMenuGroupVos;
	}
	public void addUserMenuGroupVo(UserMenuGroupVo userMenuGroupVo) {
		if (userMenuGroupVo == null)
			return;
		
		if (this.userMenuGroupVos == null)
			this.userMenuGroupVos = new ArrayList<UserMenuGroupVo>();
		this.userMenuGroupVos.add(userMenuGroupVo);
	}*/

	/**
	 * 得到默认组菜单树
	 * @return
	 * @throws Exception
	 */
	public List<MenuVo> getMenuWithTree() throws Exception {
		//用户默认菜单组
		if (this.menuGroupVo != null && this.menuGroupVo.getMenuVos() != null && this.menuGroupVo.getMenuVos().size() > 0) {
			return this.menuGroupVo.getMenuVos();
		}
		
		//角色默认菜单组
		if (this.roleVos != null) {
			for (RoleVo roleVo : this.roleVos) {
				if (roleVo != null && roleVo.getMenuGroupVo() != null && 
						roleVo.getMenuGroupVo().getMenuVos() != null && 
						roleVo.getMenuGroupVo().getMenuVos().size() > 0) {
					return roleVo.getMenuGroupVo().getMenuVos();
				}
			}
		}
		
		return Collections.EMPTY_LIST;
	}
	/**
	 * 得到特定组的菜单树
	 * @param menuGroupCode
	 * @return
	 * @throws Exception
	 */
	public List<MenuVo> getMenuWithTree(String menuGroupCode) throws Exception {
		if (menuGroupCode == null || (menuGroupCode = menuGroupCode.trim()).equals("")) {
			return this.getMenuWithTree();
		}
		
		//用户默认菜单组
		if (this.menuGroupVo != null && this.menuGroupVo.getMenuGroupCode() != null 
				&& menuGroupCode.equals(this.menuGroupVo.getMenuGroupCode().trim()) 
				&& this.menuGroupVo.getMenuVos() != null && this.menuGroupVo.getMenuVos().size() > 0) {
			return this.menuGroupVo.getMenuVos();
		}
		
		//角色菜单组
		if (this.roleVos != null) {
			//角色默认菜单组
			for (RoleVo roleVo : this.roleVos) {
				if (roleVo != null && roleVo.getMenuGroupVo() != null 
						&& roleVo.getMenuGroupVo().getMenuGroupCode() != null 
						&& menuGroupCode.equals(roleVo.getMenuGroupVo().getMenuGroupCode().trim()) 
						&& roleVo.getMenuGroupVo().getMenuVos() != null && roleVo.getMenuGroupVo().getMenuVos().size() > 0) {
					return roleVo.getMenuGroupVo().getMenuVos();
				}
			}
			
			for (RoleVo roleVo : this.roleVos) {
				if (roleVo != null && roleVo.getMenuGroupVos() != null && roleVo.getMenuGroupVos().size() > 0) {
					for (MenuGroupVo menuGroupVo : roleVo.getMenuGroupVos()) {
						if (menuGroupVo != null && menuGroupVo.getMenuGroupCode() != null && !menuGroupVo.getMenuGroupCode().trim().equals("") 
								&& menuGroupCode.equals(menuGroupVo.getMenuGroupCode().trim())
								&& menuGroupVo.getMenuVos() != null
								&& menuGroupVo.getMenuVos().size() > 0) {
							return menuGroupVo.getMenuVos();
						}
					}
				}
			}
		}
		
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * 根据资源编码获得数据权限
	 * @param resCode 资源编码
	 * @return 资源值对象
	 */
	public ResVo getDataPower(String resCode) {
		if (resCode == null || (resCode = resCode.trim()).equals("")) {
			_logger.warn("参数resCode为空.");
			return null;
		}
		
		if (this.roleVos == null || this.roleVos.size() == 0) {
			_logger.warn(this + " 没有关联任何角色.");
			return null;
		}
		
		int count = 0;
		ResVo dataPowerResVo = null;
		
		//获得数据权限资源类型Id
		TtOsgiConfiguration config = TtOsgiConfiguration.getConfig();
		String dataPowerResId = config.getString("ttsoft.usr.restype.datapower.id", "1383B87B29D24F8F8D0C2A66CD425320");
		
		//循环用户关联的角色
		for (RoleVo roleVo : this.roleVos) {
			if (roleVo != null && roleVo.getResVos() != null) {
				//循环角色关联的资源
				for (ResVo resVo : roleVo.getResVos()) {
					//如果资源为数据权限资源且资源编码与参数一致
					if (resVo != null && resVo.getResTypeId() != null 
							&& !resVo.getResTypeId().trim().equals("") 
							&& resVo.getResTypeId().trim().equals(dataPowerResId)
							&& resVo.getResCode() != null
							&& !resVo.getResCode().trim().equals("") 
							&& resVo.getResCode().trim().equals(resCode) ) {
						count = count + 1;
						if (count == 1) {
							dataPowerResVo = resVo;
						}
					}
				}
			} else {
				_logger.warn(roleVo + " 没有关联任何资源.");
			}
		}
		
		if (count > 1) {
			_logger.warn(this + " 关联了多个编码为 " + resCode + " 的数据权限资源.");
		}
		
		return dataPowerResVo;
	}
	
	/**
	 * 获得第一个数据权限
	 * @return 资源值对象
	 */
	public List<ResVo> getDataPowers() {
		if (this.roleVos == null || this.roleVos.size() == 0) {
			_logger.warn(this + " 没有关联任何角色.");
			return null;
		}
		
		//获得数据权限资源类型Id
		TtOsgiConfiguration config = TtOsgiConfiguration.getConfig();
		String dataPowerResId = config.getString("ttsoft.usr.restype.datapower.id", "1383B87B29D24F8F8D0C2A66CD425320");
		
		//循环用户关联的角色
		List<ResVo> resVos = new ArrayList<ResVo>();
		for (RoleVo roleVo : this.roleVos) {
			if (roleVo != null && roleVo.getResVos() != null) {
				//循环角色关联的资源
				for (ResVo resVo : roleVo.getResVos()) {
					//如果资源为数据权限资源且资源编码与参数一致
					if (resVo != null && resVo.getResTypeId() != null 
							&& !resVo.getResTypeId().trim().equals("") 
							&& resVo.getResTypeId().trim().equals(dataPowerResId) ) {
						resVos.add(resVo);
					}
				}
			} else {
				_logger.warn(roleVo + " 没有关联任何资源.");
			}
		}
		
		return resVos;
	}
	
	
	//可管理菜单组
	private List<MenuGroupVo> managedMenuGroupVos;

	public List<MenuGroupVo> getManagedMenuGroupVos() {
		return managedMenuGroupVos;
	}
	public void setManagedMenuGroupVos(List<MenuGroupVo> managedMenuGroupVos) {
		this.managedMenuGroupVos = managedMenuGroupVos;
	}
	public void addManagedMenuGroupVos(MenuGroupVo managedMenuGroupVo) {
		if (managedMenuGroupVo == null)
			return;
		
		if (this.managedMenuGroupVos == null)
			this.managedMenuGroupVos = new ArrayList<MenuGroupVo>();
		this.managedMenuGroupVos.add(managedMenuGroupVo);
	}
	
	
	//可管理用户
	private List<UserVo> managedUserVos;
	public List<UserVo> getManagedUserVos() {
		return managedUserVos;
	}
	public void setManagedUserVos(List<UserVo> managedUserVos) {
		this.managedUserVos = managedUserVos;
	}
	public void addManagedUserVos(UserVo managedUserVo) {
		if (managedUserVo == null)
			return;
		
		if (this.managedUserVos == null)
			this.managedUserVos = new ArrayList<UserVo>();
		this.managedUserVos.add(managedUserVo);
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
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserVo other = (UserVo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserVo [用户ID=" + userId + ", 编码=" + userCode
				+ ", 帐号=" + userLoginName + ", 密码="
				+ userPassword + ", 邮箱=" + userEmail + ", ad帐号="
				+ userAdName + ", ad密码=" + userAdPassword
				+ ", ldap帐号=" + userLdapName + ", 期限起始日期="
				+ userStartDate + ", 期限终止日期=" + userEndDate
				+ ", 是否临时=" + userTemped + ", 是否锁定=" + userLocked
				+ ", 描述=" + userDesc + ", 是否有效=" + userEnabled
				+ ", 序号=" + userOrders + ", 创建日期="
				+ userCreateDate + ", 创建人=" + userCreateName
				+ ", 操作时间=" + userOperateTime + ", 操作人="
				+ userOperateName + ", 人员ID=" + personId 
				+ ", 备用字段0=" + standbyField0 + ", 备用字段1=" + standbyField1
				+ ", 备用字段2=" + standbyField2 + ", 备用字段3=" + standbyField3
				+ ", 备用字段4=" + standbyField4 + ", 备用字段5=" + standbyField5
				+ ", 备用字段6=" + standbyField6 + ", 备用字段7=" + standbyField7
				+ ", 备用字段8=" + standbyField8 + ", 备用字段9=" + standbyField9
				+ "]";
	}
}

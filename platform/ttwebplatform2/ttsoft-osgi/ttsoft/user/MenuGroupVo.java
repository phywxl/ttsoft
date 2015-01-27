package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MenuGroupVo implements Serializable {
	private static Logger _logger = LogManager.getLogger(MenuGroupVo.class);
	
	//数据权限
	public static String DataPower_AddMenuGroup_Btn = "DataPower_USR_MenuGroup_AddBtn";           //增加按钮
	public static String DataPower_ModMenuGroup_Btn = "DataPower_USR_MenuGroup_ModBtn";           //修改按钮
	public static String DataPower_ModLocked_Btn    = "DataPower_USR_MenuGroup_LockedBtn";        //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn   = "DataPower_USR_MenuGroup_EnabedBtn";        //修改-启用按钮
	public static String DataPower_DelMenuGroup_Btn = "DataPower_USR_MenuGroup_DelBtn";           //删除按钮
	public static String DataPower_ConfigManagedUser_Btn  = "DataPower_USR_MenuGroup_ConfigManagedUserBtn";            //配置管理用户按钮
	public static String DataPower_ConfigBelongRole_Btn  = "DataPower_USR_MenuGroup_ConfigBelongRoleBtn";              //配置隶属角色按钮
	
	//基本信息
	private String menuGroupId;          //菜单组ID
	private String menuGroupCode;        //菜单组编码
	private String menuGroupName;        //菜单组名称
	private Date   menuGroupStartDate;   //期限起始日期
	private Date   menuGroupEndDate;     //期限终止日期
	private String menuGroupTemped;      //是否临时(Y/N)
	private String menuGroupLocked;      //是否锁定(Y/N)
	private String menuGroupDesc;        //描述
	private String menuGroupEnabled;     //是否有效
	private Double menuGroupOrders;      //序号（排序用）
	private Date   menuGroupCreateDate;  //创建日期
	private String menuGroupCreateName;  //创建帐号（创建人）（userId）
	private Date   menuGroupOperateTime; //操作时间（插入，更新）
	private String menuGroupOperateName; //操作帐号（userId）
	
	public String getMenuGroupId() {
		return menuGroupId;
	}
	public void setMenuGroupId(String menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	public String getMenuGroupCode() {
		return menuGroupCode;
	}
	public void setMenuGroupCode(String menuGroupCode) {
		this.menuGroupCode = menuGroupCode;
	}
	public String getMenuGroupName() {
		return menuGroupName;
	}
	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}
	public Date getMenuGroupStartDate() {
		return menuGroupStartDate;
	}
	public void setMenuGroupStartDate(Date menuGroupStartDate) {
		this.menuGroupStartDate = menuGroupStartDate;
	}
	public Date getMenuGroupEndDate() {
		return menuGroupEndDate;
	}
	public void setMenuGroupEndDate(Date menuGroupEndDate) {
		this.menuGroupEndDate = menuGroupEndDate;
	}
	public String getMenuGroupTemped() {
		return menuGroupTemped;
	}
	public void setMenuGroupTemped(String menuGroupTemped) {
		this.menuGroupTemped = menuGroupTemped;
	}
	public String getMenuGroupLocked() {
		return menuGroupLocked;
	}
	public void setMenuGroupLocked(String menuGroupLocked) {
		this.menuGroupLocked = menuGroupLocked;
	}
	public String getMenuGroupDesc() {
		return menuGroupDesc;
	}
	public void setMenuGroupDesc(String menuGroupDesc) {
		this.menuGroupDesc = menuGroupDesc;
	}
	public String getMenuGroupEnabled() {
		return menuGroupEnabled;
	}
	public void setMenuGroupEnabled(String menuGroupEnabled) {
		this.menuGroupEnabled = menuGroupEnabled;
	}
	public Double getMenuGroupOrders() {
		return menuGroupOrders;
	}
	public void setMenuGroupOrders(Double menuGroupOrders) {
		this.menuGroupOrders = menuGroupOrders;
	}
	public Date getMenuGroupCreateDate() {
		return menuGroupCreateDate;
	}
	public void setMenuGroupCreateDate(Date menuGroupCreateDate) {
		this.menuGroupCreateDate = menuGroupCreateDate;
	}
	public String getMenuGroupCreateName() {
		return menuGroupCreateName;
	}
	public void setMenuGroupCreateName(String menuGroupCreateName) {
		this.menuGroupCreateName = menuGroupCreateName;
	}
	public Date getMenuGroupOperateTime() {
		return menuGroupOperateTime;
	}
	public void setMenuGroupOperateTime(Date menuGroupOperateTime) {
		this.menuGroupOperateTime = menuGroupOperateTime;
	}
	public String getMenuGroupOperateName() {
		return menuGroupOperateName;
	}
	public void setMenuGroupOperateName(String menuGroupOperateName) {
		this.menuGroupOperateName = menuGroupOperateName;
	}
	
	
	//关联资源
	private List<String> menuIds;
	private List<MenuVo> menuVos;
	
	
	public List<String> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}
	public List<MenuVo> getMenuVos() {
		return menuVos;
	}
	public void setMenuVos(List<MenuVo> menuVos) {
		this.menuVos = menuVos;
	}
	public void addMenuVos(MenuVo menuVo) {
		if (menuVo == null)
			return;
		
		if (this.menuVos == null)
			this.menuVos = new ArrayList<MenuVo>();
		
		this.menuVos.add(menuVo);
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
		MenuGroupVo other = (MenuGroupVo) obj;
		if (menuGroupId == null) {
			if (other.menuGroupId != null)
				return false;
		} else if (!menuGroupId.equals(other.menuGroupId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MenuGroupVo [菜单组ID=" + menuGroupId + ", 编码="
				+ menuGroupCode + ", 名称=" + menuGroupName
				+ ", 期限起始日期=" + menuGroupStartDate
				+ ", 期限终止日期=" + menuGroupEndDate
				+ ", 是否临时=" + menuGroupTemped + ", 是否锁定="
				+ menuGroupLocked + ", 描述=" + menuGroupDesc
				+ ", 是否有效=" + menuGroupEnabled
				+ ", 序号=" + menuGroupOrders
				+ ", 创建日期=" + menuGroupCreateDate
				+ ", 创建人=" + menuGroupCreateName
				+ ", 操作时间=" + menuGroupOperateTime
				+ ", 操作人=" + menuGroupOperateName + "]";
	}
}

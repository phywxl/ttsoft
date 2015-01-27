package ttsoft.user;

import java.io.Serializable;
import java.util.Date;

public class UserMenuGroupVo implements Serializable {
	//基本信息，双主键：userId、menuGroupId
	private String      userId;                             //用户ID
	private UserVo      userVo;                             //用户值对象
	private String      menuGroupId;                        //菜单组ID
	private MenuGroupVo menuGroupVo;                        //菜单组值对象
	private String      userMenuGroupDefaulted;             //是否默认(Y/N)，一个用户仅有一个默认菜单组
	private Date        userMenuGroupStartDate;             //期限起始日期
	private Date        userMenuGroupEndDate;               //期限终止日期
	private String      userMenuGroupTemped;                //是否临时(Y/N)
	private String      userMenuGroupLocked;                //是否锁定(Y/N)
	private String      userMenuGroupDesc;                  //描述
	private String      userMenuGroupEnabled;               //是否有效
	private Double      userMenuGroupOrders;                //序号（排序用）
	private Date        userMenuGroupCreateDate;            //创建日期
	private String      userMenuGroupCreateName;            //创建帐号（创建人）（userId）
	private Date        userMenuGroupOperateTime;           //操作时间（插入，更新）
	private String      userMenuGroupOperateName;           //操作帐号（userId）
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMenuGroupId() {
		return menuGroupId;
	}
	public void setMenuGroupId(String menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	public UserVo getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	public MenuGroupVo getMenuGroupVo() {
		return menuGroupVo;
	}
	public void setMenuGroupVo(MenuGroupVo menuGroupVo) {
		this.menuGroupVo = menuGroupVo;
	}
	public String getUserMenuGroupDefaulted() {
		return userMenuGroupDefaulted;
	}
	public void setUserMenuGroupDefaulted(String userMenuGroupDefaulted) {
		this.userMenuGroupDefaulted = userMenuGroupDefaulted;
	}
	public Date getUserMenuGroupStartDate() {
		return userMenuGroupStartDate;
	}
	public void setUserMenuGroupStartDate(Date userMenuGroupStartDate) {
		this.userMenuGroupStartDate = userMenuGroupStartDate;
	}
	public Date getUserMenuGroupEndDate() {
		return userMenuGroupEndDate;
	}
	public void setUserMenuGroupEndDate(Date userMenuGroupEndDate) {
		this.userMenuGroupEndDate = userMenuGroupEndDate;
	}
	public String getUserMenuGroupTemped() {
		return userMenuGroupTemped;
	}
	public void setUserMenuGroupTemped(String userMenuGroupTemped) {
		this.userMenuGroupTemped = userMenuGroupTemped;
	}
	public String getUserMenuGroupLocked() {
		return userMenuGroupLocked;
	}
	public void setUserMenuGroupLocked(String userMenuGroupLocked) {
		this.userMenuGroupLocked = userMenuGroupLocked;
	}
	public String getUserMenuGroupDesc() {
		return userMenuGroupDesc;
	}
	public void setUserMenuGroupDesc(String userMenuGroupDesc) {
		this.userMenuGroupDesc = userMenuGroupDesc;
	}
	public String getUserMenuGroupEnabled() {
		return userMenuGroupEnabled;
	}
	public void setUserMenuGroupEnabled(String userMenuGroupEnabled) {
		this.userMenuGroupEnabled = userMenuGroupEnabled;
	}
	public Double getUserMenuGroupOrders() {
		return userMenuGroupOrders;
	}
	public void setUserMenuGroupOrders(Double userMenuGroupOrders) {
		this.userMenuGroupOrders = userMenuGroupOrders;
	}
	public Date getUserMenuGroupCreateDate() {
		return userMenuGroupCreateDate;
	}
	public void setUserMenuGroupCreateDate(Date userMenuGroupCreateDate) {
		this.userMenuGroupCreateDate = userMenuGroupCreateDate;
	}
	public String getUserMenuGroupCreateName() {
		return userMenuGroupCreateName;
	}
	public void setUserMenuGroupCreateName(String userMenuGroupCreateName) {
		this.userMenuGroupCreateName = userMenuGroupCreateName;
	}
	public Date getUserMenuGroupOperateTime() {
		return userMenuGroupOperateTime;
	}
	public void setUserMenuGroupOperateTime(Date userMenuGroupOperateTime) {
		this.userMenuGroupOperateTime = userMenuGroupOperateTime;
	}
	public String getUserMenuGroupOperateName() {
		return userMenuGroupOperateName;
	}
	public void setUserMenuGroupOperateName(String userMenuGroupOperateName) {
		this.userMenuGroupOperateName = userMenuGroupOperateName;
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
		UserMenuGroupVo other = (UserMenuGroupVo) obj;
		if (menuGroupId == null) {
			if (other.menuGroupId != null)
				return false;
		} else if (!menuGroupId.equals(other.menuGroupId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserMenuGroupVo [用户ID=" + userId + ", 菜单组ID="
				+ menuGroupId + ", 是否默认="
				+ userMenuGroupDefaulted + ", 期限起始日期="
				+ userMenuGroupStartDate + ", 期限终止日期="
				+ userMenuGroupEndDate + ", 是否临时="
				+ userMenuGroupTemped + ", 是否锁定="
				+ userMenuGroupLocked + ", 描述="
				+ userMenuGroupDesc + ", 是否有效="
				+ userMenuGroupEnabled + ", 序号="
				+ userMenuGroupOrders + ", 创建日期="
				+ userMenuGroupCreateDate + ", 创建人="
				+ userMenuGroupCreateName + ", 操作时间="
				+ userMenuGroupOperateTime + ", 操作人="
				+ userMenuGroupOperateName + "]";
	}
}

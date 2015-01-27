package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonVo implements Serializable {
	//数据权限
	public static String DataPower_AddPerson_Btn  = "DataPower_USR_Person_AddBtn";          //增加按钮
	public static String DataPower_ModPerson_Btn  = "DataPower_USR_Person_ModBtn";          //修改按钮
	public static String DataPower_ModLocked_Btn  = "DataPower_USR_Person_LockedBtn";       //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn = "DataPower_USR_Person_EnabedBtn";       //修改-启用按钮
	public static String DataPower_DelPerson_Btn  = "DataPower_USR_Person_DelBtn";          //删除按钮
	public static String DataPower_ConfigBelongOrg_Btn   = "DataPower_USR_Person_ConfigBelongOrgBtn";           //配置隶属机构按钮
	public static String DataPower_ConfigManagedOrg_Btn  = "DataPower_USR_Person_ConfigManagedOrgBtn";          //配置可管理机构按钮
	public static String DataPower_ConfigManagedPerson_Btn  = "DataPower_USR_Person_ConfigManagedPersonBtn";    //配置可管理人员按钮

	//基本信息
	private String personId;          //人员ID
	private String personCode;        //人员编码
	private String personEnName;      //人员英文名称
	private String personZhName;      //人员中文名称
	private String personSex;         //人员性别
	private String personIdCard;      //人员身份证
	private String personMobile;      //移动电话
	private Date   personStartDate;   //期限起始日期
	private Date   personEndDate;     //期限终止日期
	private String personTemped;      //是否临时(Y/N)
	private String personLocked;      //是否锁定(Y/N)
	private String personDesc;        //描述
	private String personEnabled;     //是否有效
	private Double personOrders;      //序号（排序用）
	private Date   personCreateDate;  //创建日期
	private String personCreateName;  //创建帐号（创建人）（userId）
	private Date   personOperateTime; //操作时间（插入，更新）
	private String personOperateName; //操作帐号（userId）

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPersonEnName() {
		return personEnName;
	}
	public void setPersonEnName(String personEnName) {
		this.personEnName = personEnName;
	}
	public String getPersonZhName() {
		return personZhName;
	}
	public void setPersonZhName(String personZhName) {
		this.personZhName = personZhName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getPersonIdCard() {
		return personIdCard;
	}
	public void setPersonIdCard(String personIdCard) {
		this.personIdCard = personIdCard;
	}
	public String getPersonMobile() {
		return personMobile;
	}
	public void setPersonMobile(String personMobile) {
		this.personMobile = personMobile;
	}
	public Date getPersonStartDate() {
		return personStartDate;
	}
	public void setPersonStartDate(Date personStartDate) {
		this.personStartDate = personStartDate;
	}
	public Date getPersonEndDate() {
		return personEndDate;
	}
	public void setPersonEndDate(Date personEndDate) {
		this.personEndDate = personEndDate;
	}
	public String getPersonTemped() {
		return personTemped;
	}
	public void setPersonTemped(String personTemped) {
		this.personTemped = personTemped;
	}
	public String getPersonLocked() {
		return personLocked;
	}
	public void setPersonLocked(String personLocked) {
		this.personLocked = personLocked;
	}
	public String getPersonDesc() {
		return personDesc;
	}
	public void setPersonDesc(String personDesc) {
		this.personDesc = personDesc;
	}
	public String getPersonEnabled() {
		return personEnabled;
	}
	public void setPersonEnabled(String personEnabled) {
		this.personEnabled = personEnabled;
	}
	public Double getPersonOrders() {
		return personOrders;
	}
	public void setPersonOrders(Double personOrders) {
		this.personOrders = personOrders;
	}
	public Date getPersonCreateDate() {
		return personCreateDate;
	}
	public void setPersonCreateDate(Date personCreateDate) {
		this.personCreateDate = personCreateDate;
	}
	public String getPersonCreateName() {
		return personCreateName;
	}
	public void setPersonCreateName(String personCreateName) {
		this.personCreateName = personCreateName;
	}
	public Date getPersonOperateTime() {
		return personOperateTime;
	}
	public void setPersonOperateTime(Date personOperateTime) {
		this.personOperateTime = personOperateTime;
	}
	public String getPersonOperateName() {
		return personOperateName;
	}
	public void setPersonOperateName(String personOperateName) {
		this.personOperateName = personOperateName;
	}
	
	
	//主隶属机构信息
	private String orgId;  //主隶属机构ID
	private OrgVo  orgVo;  //主隶属机构值对象

	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public OrgVo getOrgVo() {
		return orgVo;
	}
	public void setOrgVo(OrgVo orgVo) {
		this.orgVo = orgVo;
	}
	
	
	//隶属机构信息，包括主隶属机构
	private List<String> belongOrgIds; //隶属机构ID
	private List<OrgVo> belongOrgVos;  //隶属机构值对象

	public List<String> getBelongOrgIds() {
		return belongOrgIds;
	}
	public void setBelongOrgIds(List<String> belongOrgIds) {
		this.belongOrgIds = belongOrgIds;
	}
	public List<OrgVo> getBelongOrgVos() {
		return belongOrgVos;
	}
	public void setBelongOrgVos(List<OrgVo> belongOrgVos) {
		this.belongOrgVos = belongOrgVos;
	}
	public void addBelongOrgVo(OrgVo belongOrgVo) {
		if (belongOrgVo == null)
			return;
		if (this.belongOrgVos == null)
			this.belongOrgVos = new ArrayList<OrgVo>();
		this.belongOrgVos.add(belongOrgVo);
	}


	//可管理机构信息
	//为了后台增删改查机构和子机构，谁建立的机构默认可管理，超级人员默认可管理
	private List<String> managedOrgIds;  //可管理的机构ID
	private List<OrgVo>  managedOrgVos;  //可管理的机构值对象
	
	public List<String> getManagedOrgIds() {
		return managedOrgIds;
	}
	public void setManagedOrgIds(List<String> managedOrgIds) {
		this.managedOrgIds = managedOrgIds;
	}
	public List<OrgVo> getManagedOrgVos() {
		return managedOrgVos;
	}
	public void setManagedOrgVos(List<OrgVo> managedOrgVos) {
		this.managedOrgVos = managedOrgVos;
	}
	public void addManagedOrgVo(OrgVo managedOrgVo) {
		if (managedOrgVo == null)
			return;
		if (this.managedOrgVos == null)
			this.managedOrgVos = new ArrayList<OrgVo>();
		this.managedOrgVos.add(managedOrgVo);
	}


	//可管理人员信息
	//为了后台增删改查人员，谁建立的人员默认可管理，超级人员默认可管理
	private List<String>    managedPersonIds;  //可管理的人员ID
	private List<PersonVo>  managedPersonVos;  //可管理的人员值对象

	public List<String> getManagedPersonIds() {
		return managedPersonIds;
	}
	public void setManagedPersonIds(List<String> managedPersonIds) {
		this.managedPersonIds = managedPersonIds;
	}
	public List<PersonVo> getManagedPersonVos() {
		return managedPersonVos;
	}
	public void setManagedPersonVos(List<PersonVo> managedPersonVos) {
		this.managedPersonVos = managedPersonVos;
	}
	public void addManagedPersonVo(PersonVo managedPersonVo) {
		if (managedPersonVo == null)
			return;
		if (this.managedPersonVos == null)
			this.managedPersonVos = new ArrayList<PersonVo>();
		this.managedPersonVos.add(managedPersonVo);
	}
	
	
	//关联的用户
	private List<String> userIds;
	private List<UserVo> userVos;

	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	public List<UserVo> getUserVos() {
		return userVos;
	}
	public void setUserVos(List<UserVo> userVos) {
		this.userVos = userVos;
	}
	public void addUserVo(UserVo userVo) {
		if (userVo == null)
			return;
		if (this.userVos == null)
			this.userVos = new ArrayList<UserVo>();
		this.userVos.add(userVo);
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
				+ ((personId == null) ? 0 : personId.hashCode());
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
		PersonVo other = (PersonVo) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PersonVo [人员ID=" + personId + ", 编码=" + personCode + ", 隶属机构ID="
				+ orgId + ", 英文名称=" + personEnName + ", 中文名称="
				+ personZhName + ", 性别=" + personSex + ", 身份证="
				+ personIdCard + ", 移动电话=" + personMobile
				+ ", 期限起始日期=" + personStartDate + ", 期限终止日期="
				+ personEndDate + ", 是否临时=" + personTemped
				+ ", 是否锁定=" + personLocked + ", 描述="
				+ personDesc + ", 是否有效=" + personEnabled
				+ ", 序号=" + personOrders + ", 创建日期="
				+ personCreateDate + ", 创建人=" + personCreateName
				+ ", 操作时间=" + personOperateTime
				+ ", 操作人=" + personOperateName
				+ ", 备用字段0=" + standbyField0 + ", 备用字段1=" + standbyField1
				+ ", 备用字段2=" + standbyField2 + ", 备用字段3=" + standbyField3
				+ ", 备用字段4=" + standbyField4 + ", 备用字段5=" + standbyField5
				+ ", 备用字段6=" + standbyField6 + ", 备用字段7=" + standbyField7
				+ ", 备用字段8=" + standbyField8 + ", 备用字段9=" + standbyField9
				+ "]";
	}
}

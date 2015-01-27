package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrgVo implements Serializable {
	//数据权限
	public static String DataPower_AddOrg_Btn                  = "DataPower_USR_Org_AddBtn";                          //增加按钮
	public static String DataPower_ModOrg_Btn                  = "DataPower_USR_Org_ModBtn";                          //修改按钮
	public static String DataPower_ModLocked_Btn               = "DataPower_USR_Org_LockedBtn";                       //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn              = "DataPower_USR_Org_EnabedBtn";                       //修改-启用按钮
	public static String DataPower_DelOrg_Btn                  = "DataPower_USR_Org_DelBtn";                          //删除按钮
	public static String DataPower_ConfigBelongPerson_Btn      = "DataPower_USR_Org_ConfigBelongPersonBtn";           //配置隶属人员按钮
	public static String DataPower_ConfigManagedPerson_Btn     = "DataPower_USR_Org_ConfigManagedPersonBtn";          //配置可管理人员按钮

	//基本信息
	private String orgId;          //组织机构ID
	private String orgCode;        //机构编码
	private String orgName;        //机构名称
	private String orgAddress;     //机构地址
	private String orgZipCode;     //机构邮编
	private String orgTel;         //机构电话
	private String orgFax;         //机构传真
	private String orgEmail;       //机构Email
	private String orgType;        //机构类型
	private String orgLevel;       //机构级别
	private Date   orgStartDate;   //期限起始日期
	private Date   orgEndDate;     //期限终止日期
	private String orgTemped;      //是否临时(Y/N)
	private String orgLocked;      //是否锁定(Y/N)
	private String orgDesc;        //描述
	private String orgEnabled;     //是否有效
	private Double orgOrders;      //序号（排序用）
	private Date   orgCreateDate;  //创建日期
	private String orgCreateName;  //创建帐号（创建人）（userId）
	private Date   orgOperateTime; //操作时间（插入，更新）
	private String orgOperateName; //操作帐号（userId）

	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getOrgZipCode() {
		return orgZipCode;
	}
	public void setOrgZipCode(String orgZipCode) {
		this.orgZipCode = orgZipCode;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getOrgFax() {
		return orgFax;
	}
	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Date getOrgStartDate() {
		return orgStartDate;
	}
	public void setOrgStartDate(Date orgStartDate) {
		this.orgStartDate = orgStartDate;
	}
	public Date getOrgEndDate() {
		return orgEndDate;
	}
	public void setOrgEndDate(Date orgEndDate) {
		this.orgEndDate = orgEndDate;
	}
	public String getOrgTemped() {
		return orgTemped;
	}
	public void setOrgTemped(String orgTemped) {
		this.orgTemped = orgTemped;
	}
	public String getOrgLocked() {
		return orgLocked;
	}
	public void setOrgLocked(String orgLocked) {
		this.orgLocked = orgLocked;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getOrgEnabled() {
		return orgEnabled;
	}
	public void setOrgEnabled(String orgEnabled) {
		this.orgEnabled = orgEnabled;
	}
	public Double getOrgOrders() {
		return orgOrders;
	}
	public void setOrgOrders(Double orgOrders) {
		this.orgOrders = orgOrders;
	}
	public Date getOrgCreateDate() {
		return orgCreateDate;
	}
	public void setOrgCreateDate(Date orgCreateDate) {
		this.orgCreateDate = orgCreateDate;
	}
	public String getOrgCreateName() {
		return orgCreateName;
	}
	public void setOrgCreateName(String orgCreateName) {
		this.orgCreateName = orgCreateName;
	}
	public Date getOrgOperateTime() {
		return orgOperateTime;
	}
	public void setOrgOperateTime(Date orgOperateTime) {
		this.orgOperateTime = orgOperateTime;
	}
	public String getOrgOperateName() {
		return orgOperateName;
	}
	public void setOrgOperateName(String orgOperateName) {
		this.orgOperateName = orgOperateName;
	}

	
	//父机构信息
	private String parentOrgId; //父机构ID
	private OrgVo  parentOrgVo; //父机构值对象

	public String getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	public OrgVo getParentOrgVo() {
		return parentOrgVo;
	}
	public void setParentOrgVo(OrgVo parentOrgVo) {
		this.parentOrgVo = parentOrgVo;
	}


	//子机构信息
	private List<OrgVo> childOrgVos; //子机构值对象
	
	public List<OrgVo> getChildOrgVos() {
		return childOrgVos;
	}
	public void setChildOrgVos(List<OrgVo> childOrgVos) {
		this.childOrgVos = childOrgVos;
	}
	public void addChildOrgVo(OrgVo childOrgVo) {
		if (childOrgVo == null)
			return;
		if (this.childOrgVos == null)
			this.childOrgVos = new ArrayList<OrgVo>();
		this.childOrgVos.add(childOrgVo);
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
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
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
		OrgVo other = (OrgVo) obj;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OrgVo [机构ID=" + orgId + ", 父机构ID=" + parentOrgId 
				+ ", 编码=" + orgCode + ", 名称=" + orgName 
				+ ", 地址=" + orgAddress + ", 邮编=" + orgZipCode 
				+ ", 电话=" + orgTel + ", 传真=" + orgFax
				+ ", Email=" + orgEmail + ", 类型=" + orgType
				+ ", 级别=" + orgLevel + ", 起始日期=" + orgStartDate
				+ ", 终止日期=" + orgEndDate + ", 是否临时=" + orgTemped
				+ ", 是否锁定=" + orgLocked + ", 描述=" + orgDesc
				+ ", 是否有效=" + orgEnabled + ", 序号=" + orgOrders
				+ ", 创建日期=" + orgCreateDate + ", 创建人=" + orgCreateName 
				+ ", 操作时间=" + orgOperateTime
				+ ", 操作人=" + orgOperateName 
				+ ", 备用字段0=" + standbyField0 + ", 备用字段1=" + standbyField1
				+ ", 备用字段2=" + standbyField2 + ", 备用字段3=" + standbyField3
				+ ", 备用字段4=" + standbyField4 + ", 备用字段5=" + standbyField5
				+ ", 备用字段6=" + standbyField6 + ", 备用字段7=" + standbyField7
				+ ", 备用字段8=" + standbyField8 + ", 备用字段9=" + standbyField9
				+ "]";
	}
	
}

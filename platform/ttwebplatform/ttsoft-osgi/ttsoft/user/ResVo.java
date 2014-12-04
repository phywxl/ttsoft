package ttsoft.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ResVo implements Serializable {
	private static Logger _logger = LogManager.getLogger(ResVo.class);
	
	//数据权限
	public static String DataPower_AddRes_Btn = "DataPower_USR_Res_AddBtn";           //增加按钮
	public static String DataPower_ModRes_Btn = "DataPower_USR_Res_ModBtn";           //修改按钮
	public static String DataPower_ModLocked_Btn = "DataPower_USR_Res_LockedBtn";     //修改-锁定按钮
	public static String DataPower_ModEnabled_Btn = "DataPower_USR_Res_EnabedBtn";    //修改-启用按钮
	public static String DataPower_DelRes_Btn = "DataPower_USR_Res_DelBtn";           //删除按钮
	public static String DataPower_ConfigManagedUser_Btn  = "DataPower_USR_Res_ConfigManagedUserBtn";            //配置管理用户按钮
	public static String DataPower_ConfigBelongRole_Btn  = "DataPower_USR_Res_ConfigBelongRoleBtn";              //配置隶属角色按钮
	
	//基本信息
	private String resId;          //资源ID
	private String resCode;        //资源编码
	private String resName;        //资源名称
	private String resValue;       //资源值
	private String resTypeId;      //资源类型
	private String resVisibled;    //资源可见性(Y/N)
	private Date   resStartDate;   //期限起始日期
	private Date   resEndDate;     //期限终止日期
	private String resTemped;      //是否临时(Y/N)
	private String resLocked;      //是否锁定(Y/N)
	private String resDesc;        //描述
	private String resEnabled;     //是否有效
	private Double resOrders;      //序号（排序用）
	private Date   resCreateDate;  //创建日期
	private String resCreateName;  //创建帐号（创建人）（userId）
	private Date   resOperateTime; //操作时间（插入，更新）
	private String resOperateName; //操作帐号（userId）
	
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResValue() {
		return resValue;
	}
	public void setResValue(String resValue) {
		this.resValue = resValue;
	}
	public String getResTypeId() {
		return resTypeId;
	}
	public void setResTypeId(String resTypeId) {
		this.resTypeId = resTypeId;
	}
	public String getResVisibled() {
		return resVisibled;
	}
	public void setResVisibled(String resVisibled) {
		this.resVisibled = resVisibled;
	}
	public Date getResStartDate() {
		return resStartDate;
	}
	public void setResStartDate(Date resStartDate) {
		this.resStartDate = resStartDate;
	}
	public Date getResEndDate() {
		return resEndDate;
	}
	public void setResEndDate(Date resEndDate) {
		this.resEndDate = resEndDate;
	}
	public String getResTemped() {
		return resTemped;
	}
	public void setResTemped(String resTemped) {
		this.resTemped = resTemped;
	}
	public String getResLocked() {
		return resLocked;
	}
	public void setResLocked(String resLocked) {
		this.resLocked = resLocked;
	}
	public String getResDesc() {
		return resDesc;
	}
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	public String getResEnabled() {
		return resEnabled;
	}
	public void setResEnabled(String resEnabled) {
		this.resEnabled = resEnabled;
	}
	public Double getResOrders() {
		return resOrders;
	}
	public void setResOrders(Double resOrders) {
		this.resOrders = resOrders;
	}
	public Date getResCreateDate() {
		return resCreateDate;
	}
	public void setResCreateDate(Date resCreateDate) {
		this.resCreateDate = resCreateDate;
	}
	public String getResCreateName() {
		return resCreateName;
	}
	public void setResCreateName(String resCreateName) {
		this.resCreateName = resCreateName;
	}
	public Date getResOperateTime() {
		return resOperateTime;
	}
	public void setResOperateTime(Date resOperateTime) {
		this.resOperateTime = resOperateTime;
	}
	public String getResOperateName() {
		return resOperateName;
	}
	public void setResOperateName(String resOperateName) {
		this.resOperateName = resOperateName;
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
	
	//备用字段
	private String standbyField10;//备用字段10
	private String standbyField11;//备用字段11
	private String standbyField12;//备用字段12
	private String standbyField13;//备用字段13
	private String standbyField14;//备用字段14
	private String standbyField15;//备用字段15
	public String getStandbyField10() {
		return standbyField10;
	}
	public void setStandbyField10(String standbyField10) {
		this.standbyField10 = standbyField10;
	}
	public String getStandbyField11() {
		return standbyField11;
	}
	public void setStandbyField11(String standbyField11) {
		this.standbyField11 = standbyField11;
	}
	public String getStandbyField12() {
		return standbyField12;
	}
	public void setStandbyField12(String standbyField12) {
		this.standbyField12 = standbyField12;
	}
	public String getStandbyField13() {
		return standbyField13;
	}
	public void setStandbyField13(String standbyField13) {
		this.standbyField13 = standbyField13;
	}
	public String getStandbyField14() {
		return standbyField14;
	}
	public void setStandbyField14(String standbyField14) {
		this.standbyField14 = standbyField14;
	}
	public String getStandbyField15() {
		return standbyField15;
	}
	public void setStandbyField15(String standbyField15) {
		this.standbyField15 = standbyField15;
	}


	//资源类型信息
	public static List<ResTypeVo> ResTypes = new ArrayList<ResTypeVo>();
	static {
		ResTypes.add(ResTypeVo.MENU);
		ResTypes.add(ResTypeVo.DATARULE);
		ResTypes.add(ResTypeVo.WEBACCESSRULE);
		ResTypes.add(ResTypeVo.PLACEHOLDER);
		ResTypes.add(ResTypeVo.MODULEENTRY);
		ResTypes.add(ResTypeVo.PORTAL);
	}
	
	public static ResTypeVo getResType(String resTypeId) throws Exception {
		String t = null;
		if (resTypeId == null || (resTypeId = resTypeId.trim()).equals("")) {
			t = "参数为空.";
			_logger.error(t);
			throw new Exception(t);
		}
		
		for (ResTypeVo resType : ResTypes) {
			if (resTypeId.equals(resType.getResTypeId())) {
				return resType;
			}
		}
		
		t = "根据 " + resTypeId + " 无法得到资源类型.";
		_logger.error(t);
		throw new Exception(t);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
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
		ResVo other = (ResVo) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ResVo [资源ID=" + resId + ", 编码=" + resCode + ", 名称="
				+ resName + ", 资源值=" + resValue + ", 资源类型="
				+ resTypeId + ", 可见性=" + resVisibled
				+ ", 期限起始日期=" + resStartDate + ", 期限终止日期="
				+ resEndDate + ", 是否临时=" + resTemped + ", 是否锁定="
				+ resLocked + ", 描述=" + resDesc + ", 是否有效="
				+ resEnabled + ", 序号=" + resOrders + ", 创建日期="
				+ resCreateDate + ", 创建人=" + resCreateName
				+ ", 操作时间=" + resOperateTime + ", 操作人="
				+ resOperateName 
				+ ", 备用字段0=" + standbyField0 + ", 备用字段1=" + standbyField1
				+ ", 备用字段2=" + standbyField2 + ", 备用字段3=" + standbyField3
				+ ", 备用字段4=" + standbyField4 + ", 备用字段5=" + standbyField5
				+ ", 备用字段6=" + standbyField6 + ", 备用字段7=" + standbyField7
				+ ", 备用字段8=" + standbyField8 + ", 备用字段9=" + standbyField9
				+ "]";
	}	
}

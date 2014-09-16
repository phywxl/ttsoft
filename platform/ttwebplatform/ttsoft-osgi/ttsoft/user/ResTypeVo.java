package ttsoft.user;

import java.util.Date;

public class ResTypeVo {
	public static ResTypeVo MENU = new ResTypeVo("MENU", "MENU", "菜单", "", 5.0);
	public static ResTypeVo DATARULE = new ResTypeVo("DATARULE", "DATARULE", "数据规则", "", 10.0);
	public static ResTypeVo WEBACCESSRULE = new ResTypeVo("WEBACCESSRULE", "WEBACCESSRULE", "Web访问规则", "", 15.0);
	public static ResTypeVo PLACEHOLDER = new ResTypeVo("PLACEHOLDER", "PLACEHOLDER", "占位符", "", 20.0);
	
	private String resTypeId;
	private String resTypeCode;
	private String resTypeName;
	private String resTypeDesc;
	private Double resTypeOrders;
	private Date resTypeCreateDate;
	private String resTypeCreateName;
	private Date resTypeOperateTime;
	private String resTypeOperateName;
	
	public ResTypeVo() {}
	public ResTypeVo(String resTypeId, String resTypeCode, String resTypeName, String resTypeDesc, Double resTypeOrders) {
		this.resTypeId = resTypeId;
		this.resTypeCode = resTypeCode;
		this.resTypeName = resTypeName;
		this.resTypeDesc = resTypeDesc;
		this.resTypeOrders = resTypeOrders;
	}
	public String getResTypeId() {
		return resTypeId;
	}
	public void setResTypeId(String resTypeId) {
		this.resTypeId = resTypeId;
	}
	public String getResTypeName() {
		return resTypeName;
	}
	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}
	public String getResTypeDesc() {
		return resTypeDesc;
	}
	public void setResTypeDesc(String resTypeDesc) {
		this.resTypeDesc = resTypeDesc;
	}
	public String getResTypeCode() {
		return resTypeCode;
	}
	public void setResTypeCode(String resTypeCode) {
		this.resTypeCode = resTypeCode;
	}
	public Double getResTypeOrders() {
		return resTypeOrders;
	}
	public void setResTypeOrders(Double resTypeOrders) {
		this.resTypeOrders = resTypeOrders;
	}
	public Date getResTypeCreateDate() {
		return resTypeCreateDate;
	}
	public void setResTypeCreateDate(Date resTypeCreateDate) {
		this.resTypeCreateDate = resTypeCreateDate;
	}
	public String getResTypeCreateName() {
		return resTypeCreateName;
	}
	public void setResTypeCreateName(String resTypeCreateName) {
		this.resTypeCreateName = resTypeCreateName;
	}
	public Date getResTypeOperateTime() {
		return resTypeOperateTime;
	}
	public void setResTypeOperateTime(Date resTypeOperateTime) {
		this.resTypeOperateTime = resTypeOperateTime;
	}
	public String getResTypeOperateName() {
		return resTypeOperateName;
	}
	public void setResTypeOperateName(String resTypeOperateName) {
		this.resTypeOperateName = resTypeOperateName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resTypeId == null) ? 0 : resTypeId.hashCode());
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
		ResTypeVo other = (ResTypeVo) obj;
		if (resTypeId == null) {
			if (other.resTypeId != null)
				return false;
		} else if (!resTypeId.equals(other.resTypeId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ResType [ID=" + resTypeId
				+ ", 编码=" + resTypeCode 
				+ ", 名称=" + resTypeName 
				+ ", 描述=" + resTypeDesc 
				+ "]";
	}
}

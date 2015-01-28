package ttsoft.osgi.datasource.expose;

import java.io.Serializable;

public class FieldVo implements Serializable {
	//基本信息
	private String cid;   //字段ID
	private String cmc;   //字段名称
	private String cdm;   //字段代码
	private String clx;   //字段类型
	private String cfkbz; //非空标志
	private Integer ckd;  //宽度
	private Integer cjd;  //小数
	
	public FieldVo() {}
	public FieldVo(String cid, String cmc, String cdm, String clx, String cfkbz, Integer ckd, Integer cjd, String tid) {
		this.cid = cid; this.cmc = cmc; this.cdm = cdm; this.clx = clx; this.cfkbz = cfkbz; this.ckd = ckd; this.cjd = cjd; this.tid = tid;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCmc() {
		return cmc;
	}
	public void setCmc(String cmc) {
		this.cmc = cmc;
	}
	public String getCdm() {
		return cdm;
	}
	public void setCdm(String cdm) {
		this.cdm = cdm;
	}
	public String getClx() {
		return clx;
	}
	public void setClx(String clx) {
		this.clx = clx;
	}
	public String getCfkbz() {
		return cfkbz;
	}
	public void setCfkbz(String cfkbz) {
		this.cfkbz = cfkbz;
	}
	public Integer getCkd() {
		return ckd;
	}
	public void setCkd(Integer ckd) {
		this.ckd = ckd;
	}
	public Integer getCjd() {
		return cjd;
	}
	public void setCjd(Integer cjd) {
		this.cjd = cjd;
	}

	//表
	private String tid;     //表ID
	private TableVo tableVo;//表值对象

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public TableVo getTableVo() {
		return tableVo;
	}
	public void setTableVo(TableVo tableVo) {
		this.tableVo = tableVo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
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
		FieldVo other = (FieldVo) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FiledVo [字段ID=" + cid + ", 名称=" + cmc + ", 代码=" + cdm
				+ ", 类型=" + clx + ", 非空标志=" + cfkbz + ", 宽度=" + ckd
				+ ", 小数=" + cjd + ", 表ID=" + tid + "]";
	}
}

package ttsoft.osgi.datasource.bean;

public class FieldVo extends TableVo {
	//基本信息
	private String cid;
	private String cmc;
	private String cdm;
	private String clx;
	private String cfkbz;
	private Integer ckd;
	private Integer cjd;
	
	public FieldVo() {}
	public FieldVo(String cid, String cmc, String cdm, String clx, String cfkbz, Integer ckd, Integer cjd) {
		this.cid = cid; this.cmc = cmc; this.cdm = cdm; this.clx = clx; this.cfkbz = cfkbz; this.ckd = ckd; this.cjd = cjd;
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

	//表信息
	private String tid;
	private TableVo tableVo;

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
		int result = super.hashCode();
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
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
		return "FieldVo [字段标识=" + cid + ", 名称=" + cmc + ", 代码=" + cdm
				+ ", 类型=" + clx + ", 非空=" + cfkbz + ", 宽度=" + ckd
				+ ", 精度=" + cjd + ", 表标识=" + tid + "]";
	}
}

package ttsoft.osgi.datasource.bean;

import java.util.ArrayList;
import java.util.List;

public class TableVo {
	//基本信息
	private String tid;
	private String tmc;
	private String tlx;
	private String tdm;
	private String bz;
	
	public TableVo() {}
	public TableVo(String tid, String tmc, String tlx, String tdm, String bz, String dsId) {
		this.tid = tid; this.tmc = tmc; this.tlx = tlx; this.tdm = tdm; this.bz = bz; this.dsId = dsId;
	}

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTmc() {
		return tmc;
	}
	public void setTmc(String tmc) {
		this.tmc = tmc;
	}
	public String getTlx() {
		return tlx;
	}
	public void setTlx(String tlx) {
		this.tlx = tlx;
	}
	public String getTdm() {
		return tdm;
	}
	public void setTdm(String tdm) {
		this.tdm = tdm;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

	//数据源信息
	private String dsId;
	private DataSourceVo dsVo;

	public String getDsId() {
		return dsId;
	}
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	public DataSourceVo getDsVo() {
		return dsVo;
	}
	public void setDsVo(DataSourceVo dsVo) {
		this.dsVo = dsVo;
	}
	
	//拥有的字段
	private List<FieldVo> fieldVos;
	public List<FieldVo> getFieldVos() {
		return fieldVos;
	}
	public void setFieldVos(List<FieldVo> fieldVos) {
		this.fieldVos = fieldVos;
	}
	public void addFieldVo(FieldVo fieldVo) {
		if (fieldVo == null)
			return;
		if (this.fieldVos == null)
			this.fieldVos = new ArrayList<FieldVo>();
		if (!this.fieldVos.contains(fieldVo))
			this.fieldVos.add(fieldVo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
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
		TableVo other = (TableVo) obj;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TableVo [表标识=" + tid + ", 名称=" + tmc + ", 类型=" + tlx + ", 代码=" + tdm
				+ ", 备注=" + bz + ", 数据源标识=" + dsId + "]";
	}
}

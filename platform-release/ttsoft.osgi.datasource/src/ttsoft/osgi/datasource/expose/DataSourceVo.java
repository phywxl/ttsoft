package ttsoft.osgi.datasource.expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataSourceVo implements Serializable {
	private String dsId;              //数据源标识
	private String dsMc;              //数据源名称
	private String dsBm;              //数据源编码
	private String dsDc;              //驱动类名
	private String dsIp;              //IP
	private Integer dsPort;           //端口
	private String dsNm;              //数据库名称
	private String dsUrl;             //JDBCURL
	private String dsUser;            //用户
	private Double xh = new Double(1);//序号
	
	public DataSourceVo()  {
	}
	public DataSourceVo(String dsId, String dsMc, String dsBm, String dsDc, String dsIp, Integer dsPort, String dsNm, String dsUrl, String dsUser, Double xh)  {
		this.dsId = dsId; this.dsMc = dsMc; this.dsBm = dsBm; this.dsDc = dsDc; 
		this.dsIp = dsIp; this.dsPort = dsPort; this.dsNm = dsNm;
		this.dsUrl = dsUrl; this.dsUser = dsUser; this.xh = xh;
	}
	
	public String getDsId() {
		return dsId;
	}
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	public String getDsMc() {
		return dsMc;
	}
	public void setDsMc(String dsMc) {
		this.dsMc = dsMc;
	}
	public String getDsBm() {
		return dsBm;
	}
	public void setDsBm(String dsBm) {
		this.dsBm = dsBm;
	}
	public String getDsDc() {
		return dsDc;
	}
	public void setDsDc(String dsDc) {
		this.dsDc = dsDc;
	}
	public String getDsUrl() {
		return dsUrl;
	}
	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}
	public String getDsUser() {
		return dsUser;
	}
	public void setDsUser(String dsUser) {
		this.dsUser = dsUser;
	}
	public Double getXh() {
		return xh;
	}
	public void setXh(Double xh) {
		this.xh = xh;
	}
	
	//拥有的表
	private List<TableVo> tableVos;
	public List<TableVo> getTableVos() {
		return tableVos;
	}
	public void setTableVos(List<TableVo> tableVos) {
		this.tableVos = tableVos;
	}
	public void addTableVo(TableVo tableVo) {
		if (tableVo == null)
			return;
		if (this.tableVos == null)
			this.tableVos = new ArrayList<TableVo>();
		this.tableVos.add(tableVo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsId == null) ? 0 : dsId.hashCode());
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
		DataSourceVo other = (DataSourceVo) obj;
		if (dsId == null) {
			if (other.dsId != null)
				return false;
		} else if (!dsId.equals(other.dsId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DataSourceVo [名称=" + dsMc + ", 编码=" + dsBm + ", 驱动类="
				+ dsDc + ", IP=" + dsIp + ", 端口=" + dsPort 
				+ ", 数据库名称=" + dsNm + ", JDBC URL=" + dsUrl 
				+ ", 用户=" + dsUser + "]";
	}
}

package ttsoft.osgi.datasource.bean;

import java.io.Serializable;

public class DataSourceVo implements Serializable {
	private String dsId;              //数据源标识
	private String dsMc;              //数据源名称
	private String dsBm;              //数据源编码
	private String dsLb;              //数据源类别
	private String dsLx;              //数据源类型
	private String dsDc;              //驱动类名
	private String dsIp;              //IP
	private Integer dsPort;           //端口
	private String dsNm;              //数据库名称
	private String dsUrl;             //JDBCURL
	private String dsUser;            //用户
	private String dsPass;            //密码
	private Integer dsMin;            //连接池最小数
	private Integer dsMax;            //连接池最大数
	private Integer dsInit;           //连接池初始数
	private Integer dsInc;            //连接池增长数
	private Double xh = new Double(1);//序号
	private String qybz;              //启用标志 Y/N

	
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
	public String getDsLb() {
		return dsLb;
	}
	public void setDsLb(String dsLb) {
		this.dsLb = dsLb;
	}
	public String getDsLx() {
		return dsLx;
	}
	public void setDsLx(String dsLx) {
		this.dsLx = dsLx;
	}
	public String getDsDc() {
		return dsDc;
	}
	public void setDsDc(String dsDc) {
		this.dsDc = dsDc;
	}
	public String getDsIp() {
		return dsIp;
	}
	public void setDsIp(String dsIp) {
		this.dsIp = dsIp;
	}
	public Integer getDsPort() {
		return dsPort;
	}
	public void setDsPort(Integer dsPort) {
		this.dsPort = dsPort;
	}
	public String getDsNm() {
		return dsNm;
	}
	public void setDsNm(String dsNm) {
		this.dsNm = dsNm;
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
	public String getDsPass() {
		return dsPass;
	}
	public void setDsPass(String dsPass) {
		this.dsPass = dsPass;
	}
	public Integer getDsMin() {
		return dsMin;
	}
	public void setDsMin(Integer dsMin) {
		this.dsMin = dsMin;
	}
	public Integer getDsMax() {
		return dsMax;
	}
	public void setDsMax(Integer dsMax) {
		this.dsMax = dsMax;
	}
	public Integer getDsInit() {
		return dsInit;
	}
	public void setDsInit(Integer dsInit) {
		this.dsInit = dsInit;
	}
	public Integer getDsInc() {
		return dsInc;
	}
	public void setDsInc(Integer dsInc) {
		this.dsInc = dsInc;
	}
	public Double getXh() {
		return xh;
	}
	public void setXh(Double xh) {
		this.xh = xh;
	}
	public String getQybz() {
		return qybz;
	}
	public void setQybz(String qybz) {
		this.qybz = qybz;
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
		return "DataSourceVo [数据源标识=" + dsId + ", 名称=" + dsMc + ", 编码="
				+ dsBm + ", 类别=" + dsLb + ", 类型=" + dsLx
				+ ", JDBC驱动类=" + dsDc
				+ ", IP=" + dsIp + ", 端口=" + dsPort + ", 数据库名称=" + dsNm
				+ ", JDBCURL=" + dsUrl
				+ ", 用户=" + dsUser + ", 连接池最小数=" + dsMin
				+ ", 连接池最大数=" + dsMax + ", 连接池初始数=" + dsInit + ", 连接池增长数="
				+ dsInc + ", 启用标志=" + qybz + "]";
	}
}

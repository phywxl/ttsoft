package ttsoft.osgi.datasource.expose.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ttsoft.osgi.base.BundleContextPropertyPlaceholderConfigurer;

import ttsoft.osgi.datasource.expose.DataSourceVo;
import ttsoft.osgi.datasource.expose.IDataSource;
import ttsoft.osgi.datasource.internal.InitDataSourceManage;

public class DataSourceImpl implements IDataSource {
	private Logger _logger = LogManager.getLogger(DataSourceImpl.class);

	/**
	 * 查询所有可用的数据源定义
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataSourceVo>  findAll()  throws Exception {
		List<DataSourceVo> rvo = new ArrayList<DataSourceVo>();
		//获得所有启用的数据源
		List<ttsoft.osgi.datasource.bean.DataSourceVo> vos = this.initDataSourceManage.findAllEnabled();
		if (vos != null && vos.size() > 0) {
			ttsoft.osgi.datasource.bean.DataSourceVo vo = null;
			DataSourceVo v = null;
			for (int i = 0; i < vos.size(); i++) {
				vo = vos.get(i);
				if (vo != null) {
					v = new DataSourceVo(vo.getDsId(), vo.getDsMc(), vo.getDsBm(), vo.getDsDc(), vo.getDsIp(), vo.getDsPort(), vo.getDsNm(), vo.getDsUrl(), vo.getDsUser(), vo.getXh());
					rvo.add(v);
				}
			}
		} else {
			_logger.warn("没有找到可用的数据源！");
		}
		
		//系统初始数据源
		String dsDc = bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.driverClassName");
		String dsIp = bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.ip");
		Integer dsPort = Integer.valueOf(bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.port"));
		String dsNm = bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.instance");
		String dsUrl = bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.url");
		String dsUser = bundleContextPropertyPlaceholderConfigurer.getProperty("jdbc.username");
		DataSourceVo fix = new DataSourceVo(this.getFixId(), "系统初始数据源", this.fixServiceName, dsDc, dsIp, dsPort, dsNm, dsUrl, dsUser, 9999.9);
		rvo.add(fix);
		
		return rvo;
	}
	/**
	 * 查询所有可用的数据源定义
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, DataSourceVo> findAllMap() throws Exception {
		List<DataSourceVo> rvo = this.findAll();
		Map<String, DataSourceVo> map = new HashMap<String, DataSourceVo>();
		if (rvo != null) {
			String dsBm = null;
			for (DataSourceVo vo : rvo) {
				if (vo != null && (dsBm = vo.getDsBm()) != null) {
					map.put(dsBm, vo);
				}
			}
		}
		
		return map;
	}
	/**
	 * 获得系统固有的数据源定义
	 * @return 
	 * @throws Exception
	 */
	@Override
	public DataSourceVo findFix()  throws Exception {
		if (this.fixServiceName == null || this.fixServiceName.trim().equals("")) {
			return null;
		}
		
		Map<String, DataSourceVo> map = this.findAllMap();
		if (map == null)
			return null;
		
		return map.get(this.fixServiceName.trim());
	}
	/**
	 * 获得特定数据源的连接池
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataSource getDataSource(String serviceName) throws Exception {
		String m  = null;
		if (serviceName == null || (serviceName = serviceName.trim()).equals("")) {
			m = "参数为空！";
			_logger.error(m);
			throw new Exception(m);
		}
		
		if (fixServiceName != null && fixDataSource != null && fixServiceName.equals(serviceName)) {
			return fixDataSource;
		}
		
		return this.initDataSourceManage.getDataSourceByDsBm(serviceName);
	}	
	

	//固定的连接池
	private String     fixId;
	private String     fixServiceName;
	private DataSource fixDataSource;
	
	public String getFixId() {
		return fixId;
	}
	public void setFixId(String fixId) {
		this.fixId = fixId;
	}
	public String getFixServiceName() {
		return fixServiceName;
	}
	public void setFixServiceName(String fixServiceName) {
		this.fixServiceName = fixServiceName;
	}
	public DataSource getFixDataSource() {
		return fixDataSource;
	}
	public void setFixDataSource(DataSource fixDataSource) {
		this.fixDataSource = fixDataSource;
	}
	
	//初始化数据源c3p0连接池和OSGI服务的管理类
	private InitDataSourceManage initDataSourceManage;	
	public InitDataSourceManage getInitDataSourceManage() {
		return initDataSourceManage;
	}
	public void setInitDataSourceManage(InitDataSourceManage initDataSourceManage) {
		this.initDataSourceManage = initDataSourceManage;
	}
	
	//属性设置类
	private BundleContextPropertyPlaceholderConfigurer bundleContextPropertyPlaceholderConfigurer;
	public BundleContextPropertyPlaceholderConfigurer getBundleContextPropertyPlaceholderConfigurer() {
		return bundleContextPropertyPlaceholderConfigurer;
	}
	public void setBundleContextPropertyPlaceholderConfigurer(
			BundleContextPropertyPlaceholderConfigurer bundleContextPropertyPlaceholderConfigurer) {
		this.bundleContextPropertyPlaceholderConfigurer = bundleContextPropertyPlaceholderConfigurer;
	}
}

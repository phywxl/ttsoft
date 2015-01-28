package ttsoft.osgi.datasource.internal;

import java.beans.PropertyVetoException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.osgi.framework.BundleContext;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.osgi.context.BundleContextAware;
import org.osgi.framework.ServiceRegistration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import ttsoft.osgi.datasource.bean.DataSourceVo;
import ttsoft.osgi.datasource.dao.IDataSourceManage;

public class InitDataSourceManage extends SqlMapClientDaoSupport implements BundleContextAware {
	private Logger _logger = LogManager.getLogger(InitDataSourceManage.class);
	
	private static BundleContext bundleContext; //OSGI容器上下文
	private int minPoolSize = 1;                     //连接池最小数
	private int maxPoolSize = 15;                    //连接池最大数
	private int initialPoolSize = 2;                 //连接池初始数
	private int acquireIncrement = 2;                //连接池增长数
	//Hold住连接池
	private Map<String, PoolHold> serviceRegistrations = new HashMap<String, PoolHold>();
	
	/**
	 * 初始化操作
	 * 获取已有的数据源定义，发布为OSGI服务
	 */
	public void start() {
		if (InitDataSourceManage.bundleContext == null) {
			_logger.error("没有设置OSGI容器上下文.");
			return;
		}
		
		try {
			//查询所有启用的数据源源定义
			List<DataSourceVo> dsvos = this.findAllEnabled();
			if (dsvos != null && dsvos.size() > 0) {
				/*
				String dsBm;          //数据源编码
				String dsDc;          //驱动类名
				String dsUrl;         //JDBCURL
				String dsUser;        //用户
				String dsPass;        //密码
				int dsMin;            //连接池最小数
				int dsMax;            //连接池最大数
				int dsInit;           //连接池初始数
				int dsInc;            //连接池增长数
				ServiceRegistration sr = null;
				*/
				for (DataSourceVo vo : dsvos) {
					if (vo != null) {
						this.initilize(vo);
						/*
						if (vo.getDsBm() == null || (dsBm = vo.getDsBm().trim()).equals("")) {
							_logger.error(vo + " 缺少编码数据，需要数据源编码作为服务名称.");
							continue;
						}
						if (vo.getDsDc() == null || (dsDc = vo.getDsDc().trim()).equals("")) {
							_logger.error(vo + " 缺少jdbc驱动类名.");
							continue;
						}
						if (vo.getDsUrl() == null || (dsUrl = vo.getDsUrl().trim()).equals("")) {
							_logger.error(vo + " 缺少jdbc url.");
							continue;
						}
						if (vo.getDsUser() == null || (dsUser = vo.getDsUser().trim()).equals("")) {
							_logger.error(vo + " 缺少数据库用户.");
							continue;
						}
						if (vo.getDsPass() == null || (dsPass = vo.getDsPass().trim()).equals("")) {
							_logger.error(vo + " 缺少数据库用户密码.");
							continue;
						}
						if (vo.getDsMin() == null || (dsMin = vo.getDsMin().intValue()) <= 0) {
							dsMin = this.minPoolSize;
						}
						if (vo.getDsMax() == null || (dsMax = vo.getDsMax().intValue()) <= 0) {
							dsMax = this.maxPoolSize;
						}
						if (vo.getDsInit() == null || (dsInit = vo.getDsInit().intValue()) <= 0) {
							dsInit = this.initialPoolSize;
						}
						if (vo.getDsInc() == null || (dsInc = vo.getDsInc().intValue()) <= 0) {
							dsInc = this.acquireIncrement;
						}
						//建立数据源
						ComboPooledDataSource pool = new ComboPooledDataSource();
						pool.setDriverClass(dsDc);
						pool.setJdbcUrl(dsUrl);
						pool.setUser(dsUser);
						pool.setPassword(dsPass);
						pool.setMinPoolSize(dsMin);
						pool.setMaxPoolSize(dsMax);
						pool.setInitialPoolSize(dsInit);
						pool.setAcquireIncrement(dsInc);
						pool.setIdleConnectionTestPeriod(300);
						pool.setTestConnectionOnCheckout(true);
						pool.setMaxStatements(0);
						//注册OSGI服务
						Dictionary<String, Object> props = new Hashtable<String, Object>();
						props.put("Bundle-SymbolicName", InitDataSourceManage.bundleContext.getBundle().getSymbolicName());
						props.put("Bundle-Version", InitDataSourceManage.bundleContext.getBundle().getVersion().toString());
						props.put("org.springframework.osgi.bean.name", dsBm);
						sr = InitDataSourceManage.bundleContext.registerService(javax.sql.DataSource.class, pool, props);
						//HOLD住必要的对象，销毁时用
						serviceRegistrations.put(dsBm, new PoolHold(sr, pool));
						*/
					}
				}
			} else {
				if (_logger.isDebugEnabled()) {
					_logger.debug("没有找到任何数据源定义数据.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
	}
	
	/**
	 * 销毁时
	 * 注销OSGI服务，关闭数据源连接池
	 */
	public void destroy() {
		InitDataSourceManage.bundleContext = null;
		
		if (serviceRegistrations.size() > 0) {
			for (PoolHold hold : serviceRegistrations.values()) {
				//注销OSGI服务
				if (hold.serviceRegistration != null) {
					hold.serviceRegistration.unregister();
				}
				//关闭数据源连接池
				if (hold.pool != null) {
					hold.pool.close();
				}
			}
		}
	}
	/**
	 * 销毁特定编码的连接池和服务
	 * @param dsBm
	 * @throws Exception
	 */
	public void destroyByDsBm(String dsBm) throws Exception {
		if(dsBm == null || (dsBm = dsBm.trim()).equals(""))
			return;
		
		if (this.serviceRegistrations.size() == 0)
			return;
		
		PoolHold hold = this.serviceRegistrations.get(dsBm);
		if (hold != null) {
			//注销OSGI服务
			if (hold.serviceRegistration != null) {
				hold.serviceRegistration.unregister();
			}
			//关闭数据源连接池
			if (hold.pool != null) {
				hold.pool.close();
			}
			this.serviceRegistrations.remove(dsBm);
		}
	}
	/**
	 * 建立特定的连接池和服务
	 * @param dsvo
	 */
	public void initilize(DataSourceVo vo) {
		if ( vo == null) return;
		
		String dsBm;          //数据源编码
		if ( (dsBm = vo.getDsBm()) == null || (dsBm = dsBm.trim()).equals("")) {
			_logger.error(vo + " 缺少编码数据，需要数据源编码作为服务名称！");
			return;
		}
		String qybz;          //数据源启用标志
		if ( (qybz = vo.getQybz()) == null || (qybz = qybz.trim()).equals("")) {
			_logger.error(vo + " 缺少启用标志，默认禁用！");
			return;
		}
		//已有连接池和服务
		if (this.serviceRegistrations.get(dsBm) != null)
			return;
		
		//建立新的连接池和服务
		String dsDc = null;          //驱动类名
		String dsUrl;         //JDBCURL
		String dsUser;        //用户
		String dsPass;        //密码
		int dsMin;            //连接池最小数
		int dsMax;            //连接池最大数
		int dsInit;           //连接池初始数
		int dsInc;            //连接池增长数
		ServiceRegistration sr = null;
		if (vo.getDsDc() == null || (dsDc = vo.getDsDc().trim()).equals("")) {
			_logger.error(vo + " 缺少jdbc驱动类名.");
			return;
		}
		if (vo.getDsUrl() == null || (dsUrl = vo.getDsUrl().trim()).equals("")) {
			_logger.error(vo + " 缺少jdbc url.");
			return;
		}
		if (vo.getDsUser() == null || (dsUser = vo.getDsUser().trim()).equals("")) {
			_logger.error(vo + " 缺少数据库用户.");
			return;
		}
		if (vo.getDsPass() == null || (dsPass = vo.getDsPass().trim()).equals("")) {
			_logger.error(vo + " 缺少数据库用户密码.");
			return;
		}
		if (vo.getDsMin() == null || (dsMin = vo.getDsMin().intValue()) <= 0) {
			dsMin = this.minPoolSize;
		}
		if (vo.getDsMax() == null || (dsMax = vo.getDsMax().intValue()) <= 0) {
			dsMax = this.maxPoolSize;
		}
		if (vo.getDsInit() == null || (dsInit = vo.getDsInit().intValue()) <= 0) {
			dsInit = this.initialPoolSize;
		}
		if (vo.getDsInc() == null || (dsInc = vo.getDsInc().intValue()) <= 0) {
			dsInc = this.acquireIncrement;
		}
		//建立数据源
		ComboPooledDataSource pool = new ComboPooledDataSource();
		try {
			pool.setDriverClass(dsDc);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			_logger.error(e);
			return;
		}
		pool.setJdbcUrl(dsUrl);
		pool.setUser(dsUser);
		pool.setPassword(dsPass);
		pool.setMinPoolSize(dsMin);
		pool.setMaxPoolSize(dsMax);
		pool.setInitialPoolSize(dsInit);
		pool.setAcquireIncrement(dsInc);
		pool.setIdleConnectionTestPeriod(300);
		pool.setTestConnectionOnCheckout(true);
		pool.setMaxStatements(0);
		//注册OSGI服务
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("Bundle-SymbolicName", InitDataSourceManage.bundleContext.getBundle().getSymbolicName());
		props.put("Bundle-Version", InitDataSourceManage.bundleContext.getBundle().getVersion().toString());
		props.put("org.springframework.osgi.bean.name", dsBm);
		sr = InitDataSourceManage.bundleContext.registerService(javax.sql.DataSource.class, pool, props);
		//HOLD住必要的对象，销毁时用
		serviceRegistrations.put(dsBm, new PoolHold(sr, pool));
		
	}
	
	public DataSource getDataSourceByDsBm(String dsBm) {
		if (dsBm == null || (dsBm = dsBm.trim()).equals("")) {
			_logger.warn("数据源编码参数为空.");
			return null;
		}
		if (this.serviceRegistrations.size() == 0) {
			_logger.warn("没有动态注册的连接池.");
			return null;
		}
		
		PoolHold hold = this.serviceRegistrations.get(dsBm);
		if (hold == null) {
			_logger.warn("没有 " + dsBm + " 连接池.");
			return null;
		}
		
		return hold.pool;
	}
	
	/**
	 * 查询所有启用的数据源
	 * @return
	 * @throws Exception
	 */
	public List<DataSourceVo> findAllEnabled() throws Exception {
		return this.getSqlMapClientTemplate().queryForList("osgi.datasource.ds.queryAllEnabled");
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		InitDataSourceManage.bundleContext = bundleContext;
	}
	
	private class PoolHold {
		public ServiceRegistration serviceRegistration;
		public ComboPooledDataSource pool;
		public PoolHold(ServiceRegistration serviceRegistration, ComboPooledDataSource pool) {
			this.serviceRegistration = serviceRegistration;
			this.pool = pool;
		}
	}
}

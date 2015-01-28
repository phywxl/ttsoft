package ttsoft.osgi.datasource.expose;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.osgi.io.OsgiBundleResource;

public class DataSourceUtil implements BundleContextAware {
	private static Logger _logger = LogManager.getLogger(DataSourceUtil.class);
	
	private static Map<DataSource, Map<String, SqlMapClientTemplate>> sqlMapClientTemplates = new HashMap<DataSource, Map<String, SqlMapClientTemplate>>();
	
	/**
	 * 获得SqlMapClientTemplate
	 * @param dataSource 数据源
	 * @param configLocation SqlMapClient配置文件，形如：osgibundle:ttsoft/osgi/datasource/dao/ibatis/ibatis.xml，在哪里调用此方法，配置放在哪个插件
	 * @return
	 * @throws Exception
	 */
	public static SqlMapClientTemplate getSqlMapClientTemplate(DataSource dataSource, String configLocation) throws Exception {
		String m = null;
		if (dataSource == null) {
			m = "参数dataSource为空.";
			_logger.warn(m);
			throw new Exception();
		}
		if (configLocation == null || (configLocation = configLocation.trim()).equals("")) {
			m = "参数configLocation为空.";
			_logger.warn(m);
			throw new Exception();
		}
		
		SqlMapClientTemplate sqlMapClientTemplate = null;
		Map<String, SqlMapClientTemplate> sqlMapClientTemplateLocations = null;
		if ( (sqlMapClientTemplate = ( (sqlMapClientTemplateLocations = sqlMapClientTemplates.get(dataSource)) != null ? sqlMapClientTemplateLocations.get(configLocation) : null)) != null )
			return sqlMapClientTemplate;
		
		DefaultLobHandler lobHandler = new DefaultLobHandler();
		
		//new ClassPathResource(configLocation, DataSourceUtil.class.getClassLoader())
		OsgiBundleResource osgiBundleResource = new OsgiBundleResource(DataSourceUtil.bundleContext.getBundle(), configLocation);
		
		SqlMapClientFactoryBean sqlMapClient = new SqlMapClientFactoryBean();
		sqlMapClient.setLobHandler(lobHandler);
		sqlMapClient.setDataSource(dataSource);
		sqlMapClient.setConfigLocation(osgiBundleResource);
		sqlMapClient.afterPropertiesSet();
		
		sqlMapClientTemplate = new SqlMapClientTemplate();
		if (sqlMapClientTemplateLocations == null) {
			sqlMapClientTemplateLocations = new HashMap<String, SqlMapClientTemplate>();
			sqlMapClientTemplates.put(dataSource, sqlMapClientTemplateLocations);
		}
		sqlMapClientTemplateLocations.put(configLocation, sqlMapClientTemplate);
		sqlMapClientTemplate.setSqlMapClient(sqlMapClient.getObject());
		sqlMapClientTemplate.afterPropertiesSet();
		
		return sqlMapClientTemplate;
	}
	public static void omitSqlMapClientTemplate(DataSource dataSource, String configLocation) throws Exception {
		
	}
	
	/**
	 * 获得数据源服务
	 * @return
	 * @throws Exception
	 */
	public static IDataSource getDataSourceManage() throws Exception {
		return getService(IDataSource.class, null);
	}
	
	/**
	 * 获得表服务
	 * @return
	 * @throws Exception
	 */
	public static ITable getTableManage() throws Exception {
		return getService(ITable.class, null);
	}
	
	private static <T> T getService(Class<T> clazz, Map<String, String> kvs) throws Exception {
		String m = null;
		//无OSGI容器上下文，抛出异常
		if (DataSourceUtil.bundleContext == null) {
			m = BunduleContextNullStr;
			_logger.error(m);
			throw new Exception(m);
		}
		if (clazz == null) {
			m = "参数clazz为空.";
			_logger.error(m);
			throw new Exception(m);
		}
		//获得所有服务引用集合
		ServiceReference[] srs = null;
		String filter = null;
		try {
			if (kvs != null && kvs.size() > 0) {
				String v = null;
				
				filter = "";
				int count = 0;
				for (String key : kvs.keySet()) {
					if (key != null && !(key = key.trim()).equals("") 
							&& (v = kvs.get(key)) != null && !(v = v.trim()).equals("") ) {
						filter += "(" + key + "=" + v+ ")";
						count ++;
					}
				}
				if (count > 1) {
					filter = "(&" + filter + ")";
				}
				if (filter.equals("")) {
					filter = null;
				}
			}
			srs = DataSourceUtil.bundleContext.getServiceReferences(clazz.getName(), filter);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			_logger.error(e);
			throw new Exception(e);
		}
		if (srs == null || srs.length == 0) {
			m = "无法找到 " + clazz.getName() + (filter != null ? filter : "") + " 服务引用集合.";
			_logger.error(m);
			throw new Exception(m);
		}

		//循环服务引用
		Object obj = null;
		ServiceReference sr = null;
		for (int i = 0; i < srs.length; i++) {
			sr = srs[i];
			if (sr == null)
				continue;
			//得到服务
			obj = DataSourceUtil.bundleContext.getService(sr);
			if (obj == null || !clazz.isInstance(obj))
				continue;
			
			return (T)obj;
		}

		
		//没有找到服务
		m = "无法找到 " + clazz.getName() + (filter != null ? filter : "") +  " 服务.";
		_logger.error(m);
		throw new Exception(m);
	}

	private static String BunduleContextNullStr = "bundleContext为空，无OSGI容器上下文无法继续操作。";
	private static BundleContext bundleContext;
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		DataSourceUtil.bundleContext = bundleContext;
	}
	
	public void destroy() {
		sqlMapClientTemplates.clear();
		DataSourceUtil.bundleContext = null;
	}
}

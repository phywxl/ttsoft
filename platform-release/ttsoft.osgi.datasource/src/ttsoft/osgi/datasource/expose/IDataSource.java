package ttsoft.osgi.datasource.expose;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public interface IDataSource {
	/**
	 * 查询所有可用的数据源定义
	 * @return
	 * @throws Exception
	 */
	public List<DataSourceVo>  findAll()  throws Exception;
	/**
	 * 查询所有可用的数据源定义
	 * @return
	 * @throws Exception
	 */
	public Map<String, DataSourceVo> findAllMap() throws Exception;
	/**
	 * 获得系统固有的数据源定义
	 * @return 
	 * @throws Exception
	 */
	public DataSourceVo findFix()  throws Exception;
	/**
	 * 获得特定数据源的连接池
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	public DataSource          getDataSource(String serviceName) throws Exception;
}

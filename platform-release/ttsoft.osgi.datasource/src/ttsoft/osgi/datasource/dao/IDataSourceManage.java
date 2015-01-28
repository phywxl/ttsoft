package ttsoft.osgi.datasource.dao;

import java.util.List;
import java.util.Map;

import ttsoft.osgi.datasource.bean.DataSourceVo;

public interface IDataSourceManage {
	/**
	 * 查询数据源数量
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int                selectDsPageCount(Map<String, Object> params) throws Exception;
	/**
	 * 查询数据源数据(分页)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<DataSourceVo> selectDsPageData(Map<String, Object> params) throws Exception;
	/**
	 * 更新数据源启用标志(Y/N)
	 * @param dsIds 数据源ID集
	 * @return
	 * @throws Exception
	 */
	public int                updateQygz(List<String> dsIds, String qygz) throws Exception;
	/**
	 * 删除数据源
	 * @param dsIds 数据源ID集
	 * @return
	 * @throws Exception
	 */
	public int                delete(List<String> dsIds) throws Exception;
	/**
	 * 建立新的数据源定义
	 * @param dataSourceVo
	 * @return
	 * @throws Exception
	 */
	public void               insert(DataSourceVo dataSourceVo) throws Exception;
	/**
	 * 根据ID查询数据源定义数据
	 * @param dsId
	 * @return
	 * @throws Exception
	 */
	public DataSourceVo       selectDsById(String dsId) throws Exception;
	/**
	 * 更新数据源定义
	 * @param dataSourceVo
	 * @throws Exception
	 */
	public void               update(DataSourceVo dataSourceVo) throws Exception;
}

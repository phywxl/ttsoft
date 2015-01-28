package ttsoft.osgi.datasource.expose;

public interface ITable {
	/**
	 * 获得特定的表定义
	 * @param dsId 数据源ID
	 * @param tdn 表代码
	 * @return
	 * @throws Exception
	 */
	public TableVo findTableByName(String dsId, String tdm) throws Exception;
}

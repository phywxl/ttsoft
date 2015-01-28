package ttsoft.osgi.datasource.dao;

import java.util.List;

import ttsoft.osgi.datasource.bean.TableVo;

public interface ITableManage {
	/**
	 * 查询表定义信息
	 * @param tableVo 查询条件
	 * @return
	 * @throws Exception
	 */
	public List<TableVo> selectTable(TableVo tableVo) throws Exception;
}

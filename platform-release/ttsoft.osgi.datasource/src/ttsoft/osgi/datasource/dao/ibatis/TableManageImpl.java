package ttsoft.osgi.datasource.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ttsoft.osgi.datasource.bean.FieldVo;
import ttsoft.osgi.datasource.bean.TableVo;
import ttsoft.osgi.datasource.dao.ITableManage;

public class TableManageImpl  extends SqlMapClientDaoSupport implements ITableManage {
	private Logger _logger = LogManager.getLogger(TableManageImpl.class);

	/**
	 * 查询表定义信息
	 * @param tableVo 查询条件
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TableVo> selectTable(TableVo tableVo) throws Exception {
		Map params = new HashMap();
		params.put("tableVo", tableVo);
		
		List<FieldVo> fVos = this.getSqlMapClientTemplate().queryForList("osgi.datasource.table.selectTable", params);
		
		List<TableVo> tableVos = new ArrayList<TableVo>();
		if (fVos == null || fVos.size() == 0) {
			return tableVos;
		}
		
		Map<String, TableVo> tVos = new HashMap<String, TableVo>();
		TableVo tVo = null;
		for (FieldVo fVo : fVos) {
			if (fVo != null && fVo.getTid() != null && !fVo.getTid().trim().equals("") ) {
				tVo = tVos.get(fVo.getTid().trim());
				if (tVo == null) {
					tVo = new TableVo();
					tVos.put(fVo.getTid().trim(), tVo);
					tVo.setTid(fVo.getTid().trim());
					tVo.setTmc(fVo.getTmc());
					tVo.setTlx(fVo.getTlx());
					tVo.setTdm(fVo.getTdm());
					tVo.setBz(fVo.getBz());
					tVo.setDsId(fVo.getDsId());
				}
				tVo.addFieldVo(fVo);
			}
		}
		tableVos.addAll(tVos.values());
		
		return tableVos;
	}
}

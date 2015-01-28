package ttsoft.osgi.datasource.expose.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ttsoft.osgi.datasource.dao.ITableManage;
import ttsoft.osgi.datasource.expose.FieldVo;
import ttsoft.osgi.datasource.expose.ITable;
import ttsoft.osgi.datasource.expose.TableVo;

public class TableImpl implements ITable {
	private Logger _logger = LogManager.getLogger(TableImpl.class);
	
	/**
	 * 获得特定的表定义
	 * @param dsId 数据源ID
	 * @param tdn 表代码
	 * @return
	 * @throws Exception
	 */
	@Override
	public TableVo findTableByName(String dsId, String tdm) throws Exception {
		String m = null;
		if (dsId == null || (dsId = dsId.trim()).equals("")) {
			m = "参数dsId为空";
			_logger.warn(m);
			return null;
		}
		if (tdm == null || (tdm = tdm.trim()).equals("")) {
			m = "参数tdm为空";
			_logger.warn(m);
			return null;
		}
		
		ttsoft.osgi.datasource.bean.TableVo v = new ttsoft.osgi.datasource.bean.TableVo();
		v.setDsId(dsId);
		v.setTdm(tdm);
		List<ttsoft.osgi.datasource.bean.TableVo> tVos = null;
		try {
			tVos = this.tableManage.selectTable(v);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			return null;
		}
		
		if (tVos == null || tVos.size() == 0) {
			m = "没有表定义 - dsId=" + dsId + ",tdm=" + tdm;
			_logger.warn(m);
			return null;
		}
		
		if (tVos.size() > 1) {
			m = "表定义多于1个 - dsId=" + dsId + ",tdm=" + tdm;
			_logger.warn(m);
		}
		
		ttsoft.osgi.datasource.bean.TableVo tVo = null;
		for (ttsoft.osgi.datasource.bean.TableVo t : tVos) {
			if (t != null) {
				tVo = t;
				break;
			}
		}
		
		if (tVo == null) {
			return null;
		}
		
		TableVo table = new TableVo();
		table.setTid(tVo.getTid()); table.setTmc(tVo.getTmc()); table.setTlx(tVo.getTlx()); table.setTdm(tVo.getTdm()); 
		table.setDsId(tVo.getDsId());
		
		FieldVo field = null;
		if (tVo.getFieldVos() != null) {
			for (ttsoft.osgi.datasource.bean.FieldVo fVo : tVo.getFieldVos()) {
				if (fVo != null) {
					field = new FieldVo();
					table.addFieldVo(field);
					field.setTid(table.getTid()); field.setTableVo(table);
					field.setCid(fVo.getCid()); field.setCmc(fVo.getCmc()); field.setCdm(fVo.getCdm()); field.setClx(fVo.getClx()); 
					field.setCfkbz(fVo.getCfkbz()); field.setCkd(fVo.getCkd()); field.setCjd(fVo.getCjd());
				}
			}
		}
		
		return null;
	}

	//表操作Dao
	private ITableManage tableManage;
	public ITableManage getTableManage() {
		return tableManage;
	}
	public void setTableManage(ITableManage tableManage) {
		this.tableManage = tableManage;
	}
	
}

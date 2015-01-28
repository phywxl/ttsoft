package ttsoft.osgi.datasource.dao.ibatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ttsoft.osgi.datasource.bean.DataSourceVo;
import ttsoft.osgi.datasource.dao.IDataSourceManage;
import ttsoft.osgi.datasource.internal.InitDataSourceManage;

public class DataSourceManageImpl extends SqlMapClientDaoSupport implements IDataSourceManage {
	private Logger _logger = LogManager.getLogger(DataSourceManageImpl.class);
	
	/**
	 * 查询数据源数量
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int                selectDsPageCount(Map<String, Object> params) throws Exception {
		if (_logger.isDebugEnabled()) {
			_logger.debug("params = " + params);
		}
		return (Integer)this.getSqlMapClientTemplate().queryForObject("osgi.datasource.ds.selectDsPageCount", params);
	}
	/**
	 * 查询数据源数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<DataSourceVo> selectDsPageData(Map<String, Object> params) throws Exception {
		if (_logger.isDebugEnabled()) {
			_logger.debug("params = " + params);
		}
		return this.getSqlMapClientTemplate().queryForList("osgi.datasource.ds.selectDsPageData", params);
	}
	/**
	 * 更新数据源启用标志(Y/N)
	 * @param dsIds 数据源ID集
	 * @return
	 * @throws Exception
	 */
	public int                updateQygz(List<String> dsIds, String qybz)  throws Exception {
		int r = 0;
		if (dsIds != null && dsIds.size() > 0 && qybz != null && !(qybz = qybz.trim()).equals("")) {
			List<String> ids = new ArrayList<String>();
			for (String id : dsIds) {
				if (id != null && !(id = id.trim()).equals("")) {
					ids.add(id);
				}
			}
			if (ids.size() == 0)
				return 0;
			
			if (qybz.equalsIgnoreCase("y") || qybz.equals("1")) {
				qybz = "Y";
			} else {
				qybz = "N";
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", ids);
			params.put("qybz", qybz);
			
			r = this.getSqlMapClientTemplate().update("osgi.datasource.ds.updateQybz", params);
			
			List<DataSourceVo> vos = this.getSqlMapClientTemplate().queryForList("osgi.datasource.ds.selectDSVoByIds", ids);
			if (vos != null) {
				for (DataSourceVo vo : vos) {
					//销毁连接池和服务
					if (qybz.equals("N")) {
						if (vo.getDsBm() != null && !vo.getDsBm().trim().equals("")) {
							try {
								this.initDataSourceManage.destroyByDsBm(vo.getDsBm().trim());
							} catch (Exception e) {
								e.printStackTrace();
								_logger.error(e);
							}
						}
					} 
					//建立连接池和服务
					else {
						try {
							this.initDataSourceManage.initilize(vo);
						} catch (Exception e) {
							e.printStackTrace();
							_logger.error(e);
						}
					}
				}
			}
			
		}
		return r;
	}
	/**
	 * 删除数据源
	 * @param dsIds 数据源ID集
	 * @return
	 * @throws Exception
	 */
	public int                delete(List<String> dsIds) throws Exception {
		int r = 0;
		if (dsIds != null && dsIds.size() > 0) {
			List<String> ids = new ArrayList<String>();
			for (String id : dsIds) {
				if (id != null && !(id = id.trim()).equals("")) {
					ids.add(id);
				}
			}
			if (ids.size() == 0)
				return 0;
			
			
			List<DataSourceVo> vos = this.getSqlMapClientTemplate().queryForList("osgi.datasource.ds.selectDSVoByIds", ids);
			
			r = this.getSqlMapClientTemplate().delete("osgi.datasource.ds.deleteDSVoByIds", ids);
			
			if (vos != null) {
				for (DataSourceVo vo : vos) {
					if (vo.getDsBm() != null && !vo.getDsBm().trim().equals("")) {
						try {
							this.initDataSourceManage.destroyByDsBm(vo.getDsBm().trim());
						} catch (Exception e) {
							e.printStackTrace();
							_logger.error(e);
						}
					}
				}
			}
		}
		return r;
	}
	
	/**
	 * 建立新的数据源定义
	 * @param dataSourceVo
	 * @return
	 * @throws Exception
	 */
	public void               insert(DataSourceVo dataSourceVo) throws Exception {
		this.getSqlMapClientTemplate().insert("osgi.datasource.ds.insertDSVo", dataSourceVo);
		this.initDataSourceManage.initilize(dataSourceVo);
	}
	
	/**
	 * 根据ID查询数据源定义数据
	 * @param dsId
	 * @return
	 * @throws Exception
	 */
	public DataSourceVo       selectDsById(String dsId) throws Exception {
		if (dsId == null || (dsId = dsId.trim()).equals("")) {
			return null;
		}
		
		return (DataSourceVo)this.getSqlMapClientTemplate().queryForObject("osgi.datasource.ds.selectDsById", dsId);
	}
	
	/**
	 * 更新数据源定义
	 * @param dataSourceVo
	 * @throws Exception
	 */
	public void               update(DataSourceVo dataSourceVo) throws Exception {
		this.getSqlMapClientTemplate().update("osgi.datasource.ds.updateDSVo", dataSourceVo);
		try {
			this.initDataSourceManage.destroyByDsBm(dataSourceVo.getDsBm());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		this.initDataSourceManage.initilize(dataSourceVo);
	}
	
	//初始化数据源c3p0连接池和OSGI服务
	private InitDataSourceManage initDataSourceManage;	
	public InitDataSourceManage getInitDataSourceManage() {
		return initDataSourceManage;
	}
	public void setInitDataSourceManage(InitDataSourceManage initDataSourceManage) {
		this.initDataSourceManage = initDataSourceManage;
	}
	
	
}

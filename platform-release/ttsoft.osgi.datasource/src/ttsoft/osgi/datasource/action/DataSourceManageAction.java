package ttsoft.osgi.datasource.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import ttsoft.osgi.datasource.bean.DataSourceVo;
import ttsoft.osgi.datasource.dao.IDataSourceManage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DataSourceManageAction extends ActionSupport {
	private Logger _logger = LogManager.getLogger(DataSourceManageAction.class);
	
	/**
	 * 显示分页页面
	 * @return
	 */
	public String dspage() {
		return Action.SUCCESS;
	}
	/**
	 * 获得分页数据
	 * @return
	 */
	public String dspagedata() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", this.getPage());
		params.put("rows", this.getRows());
		params.put("vo", this.getDsvo());
		
		Map<String, Object> dt = new HashMap<String, Object>();
		try {
			dt.put("total", this.dataSourceManage.selectDsPageCount(params));
			dt.put("rows", this.dataSourceManage.selectDsPageData(params));
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("total", 0);
			dt.put("rows", "[]");
		}
		
		this.jsonValue = JSONObject.fromObject(dt);
		return Action.SUCCESS;
	}
	/**
	 * 启用/禁用数据源
	 */
	public String status() {
		Map dt = new HashMap();

		try {
			if (this.dsIds != null && this.dsIds.length > 0) {
				this.dataSourceManage.updateQygz(Arrays.asList(this.dsIds), this.dsvo.getQybz());
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}

		dt.put("ok", true);
		this.jsonValue = JSONObject.fromObject(dt);
		return Action.SUCCESS;
	}
	/**
	 * 删除数据源
	 * @return
	 */
	public String delete() {
		Map dt = new HashMap();

		try {
			if (this.dsIds != null && this.dsIds.length > 0) {
				this.dataSourceManage.delete(Arrays.asList(this.dsIds));
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}

		dt.put("ok", true);
		this.jsonValue = JSONObject.fromObject(dt);
		return Action.SUCCESS;
	}
	/**
	 * 显示数据源增加页面
	 * @return
	 */
	public String dsaddpage() {
		return Action.SUCCESS;
	}
	/**
	 * 增加数据源数据
	 * @return
	 */
	public String insert() {
		Map dt = new HashMap();
		//没有正常提交数据
		if (this.dsvo == null) {
			dt.put("ok", false);
			dt.put("msg", "没有提交任何数据.");
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		//设置ID
		dsvo.setDsId(UUID.randomUUID().toString().replaceAll("-", ""));
		//验证VO
		try {
			this.validDataSourceVo(this.dsvo);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		//插入数据库
		try {
			this.dataSourceManage.insert(this.dsvo);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		
		dt.put("ok", true);
		this.jsonValue = JSONObject.fromObject(dt);
		return Action.SUCCESS;
	}
	/**
	 * 显示修改数据源页面
	 * @return
	 */
	public String dsmodpage() {
		if (this.dsId == null || this.dsId.trim().equals("")) {
			this.dsvo = null;
			return Action.SUCCESS;
		}
		try {
			this.dsvo = this.dataSourceManage.selectDsById(this.dsId.trim());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			this.dsvo = null;
		}
		return Action.SUCCESS;
	}
	/**
	 * 更新数据源数据
	 * @return
	 */
	public String update() {
		Map dt = new HashMap();
		//没有正常提交数据
		if (this.dsvo == null) {
			dt.put("ok", false);
			dt.put("msg", "没有提交任何数据.");
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		//验证VO
		try {
			this.validDataSourceVo(this.dsvo);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		//插入数据库
		try {
			this.dataSourceManage.update(this.dsvo);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
			dt.put("ok", false);
			dt.put("msg", e.getMessage());
			dt.put("exception", e.toString());
			this.jsonValue = JSONObject.fromObject(dt);
			return Action.SUCCESS;
		}
		
		dt.put("ok", true);
		this.jsonValue = JSONObject.fromObject(dt);
		return Action.SUCCESS;
	}
	/**
	 * 验证数据源VO
	 * @param vo
	 * @throws Exception
	 */
	private void validDataSourceVo(DataSourceVo vo) throws Exception {
		if (vo != null) {
			if (vo.getDsId() == null || vo.getDsId().trim().equals("") || vo.getDsId().length() > 32) {
				throw new Exception(vo + " ID为空或长超过32.");
			}
			if (vo.getDsMc() != null && vo.getDsMc().length() > 64) {
				throw new Exception(vo + " 名称长超过64.");
			}
			if (vo.getDsBm() == null || vo.getDsBm().trim().equals("") || vo.getDsBm().length() > 64) {
				throw new Exception(vo + " 编码为空或长超过64.");
			}
			if (vo.getDsLb() != null && vo.getDsLb().length() > 32) {
				throw new Exception(vo + " 类别长超过32.");
			}
			if (vo.getDsLx() != null && vo.getDsLx().length() > 16) {
				throw new Exception(vo + " 类型长超过16.");
			}
			if (vo.getDsDc() == null || vo.getDsDc().trim().equals("") || vo.getDsDc().length() > 64) {
				throw new Exception(vo + " 驱动类名为空或长超过64.");
			}
			if (vo.getDsIp() == null || vo.getDsIp().trim().equals("") || vo.getDsIp().length() > 32) {
				throw new Exception(vo + " IP为空或长超过32.");
			}
			if (vo.getDsPort() == null || vo.getDsPort() < 1) {
				throw new Exception(vo + " 端口为空或小于1.");
			}
			if (vo.getDsNm() == null || vo.getDsNm().trim().equals("") || vo.getDsNm().length() > 64) {
				throw new Exception(vo + " 数据库名称为空或长超过64.");
			}
			if (vo.getDsUrl() == null || vo.getDsUrl().trim().equals("")) {
				if (vo.getDsLx() != null && !vo.getDsLx().trim().equals("")) {
					vo.setDsLx(vo.getDsLx().trim());
					if (vo.getDsLx().equalsIgnoreCase("oracle")) {
						vo.setDsUrl("jdbc:oracle:thin:@" + vo.getDsIp().trim() + ":" + vo.getDsPort() + ":" + vo.getDsNm().trim());
					} else if (vo.getDsLx().equalsIgnoreCase("mysql")) {
						vo.setDsUrl("jdbc:mysql://" + vo.getDsIp().trim() + ":" + vo.getDsPort() + "/" + vo.getDsNm().trim() + "?autoReconnect=true");
					} else if (vo.getDsLx().equalsIgnoreCase("sqlserver")) {
						vo.setDsUrl("jdbc:sqlserver://" + vo.getDsIp().trim() + ":" + vo.getDsPort() + ";DatabaseName=" + vo.getDsNm().trim());
					} else {
						vo.setDsUrl("jdbc:oracle:thin:@" + vo.getDsIp().trim() + ":" + vo.getDsPort() + ":" + vo.getDsNm().trim());
					}
				} else {
					vo.setDsUrl("jdbc:oracle:thin:@" + vo.getDsIp().trim() + ":" + vo.getDsPort() + ":" + vo.getDsNm().trim());
				}
			}
			if (vo.getDsUrl() == null || vo.getDsUrl().trim().equals("") || vo.getDsUrl().length() > 512) {
				throw new Exception(vo + " JDBC URL为空或长超过512.");
			}
			if (vo.getDsUser() == null || vo.getDsUser().trim().equals("") || vo.getDsUser().length() > 16) {
				throw new Exception(vo + " 用户为空或长超过16.");
			}
			if (vo.getDsPass() == null || vo.getDsPass().trim().equals("") || vo.getDsPass().length() > 16) {
				throw new Exception(vo + " 密码为空或长超过16.");
			}
			if (vo.getQybz() == null || vo.getQybz().trim().equals("") || vo.getQybz().length() > 1) {
				throw new Exception(vo + " 启用标志为空或长超过1.");
			}
		}
	}
	
	//数据源DB操作
	private IDataSourceManage dataSourceManage;
	public IDataSourceManage getDataSourceManage() {
		return dataSourceManage;
	}
	public void setDataSourceManage(IDataSourceManage dataSourceManage) {
		this.dataSourceManage = dataSourceManage;
	}
	
	//EasyUI 分页用户参数
	int page = 1;
	int rows = 10;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//VO对象
	private DataSourceVo dsvo;
	public DataSourceVo getDsvo() {
		return dsvo;
	}
	public void setDsvo(DataSourceVo dsvo) {
		this.dsvo = dsvo;
	}
	
	//json对象
	private JSONObject jsonValue;
	private JSONArray jsonValues;
	public JSONObject getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(JSONObject jsonValue) {
		this.jsonValue = jsonValue;
	}
	public JSONArray getJsonValues() {
		return jsonValues;
	}
	public void setJsonValues(JSONArray jsonValues) {
		this.jsonValues = jsonValues;
	}
	
	//数据源ID集
	private String[] dsIds;
	public String[] getDsIds() {
		return dsIds;
	}
	public void setDsIds(String[] dsIds) {
		this.dsIds = dsIds;
	}
	
	//数据源ID
	private String dsId;
	public String getDsId() {
		return dsId;
	}
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	
	
}

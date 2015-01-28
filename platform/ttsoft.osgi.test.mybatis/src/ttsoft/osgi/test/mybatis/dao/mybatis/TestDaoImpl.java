package ttsoft.osgi.test.mybatis.dao.mybatis;

import org.apache.ibatis.session.SqlSession;

import ttsoft.osgi.test.mybatis.dao.ITestDao;
import ttsoft.osgi.test.mybatis.vo.Test;


public class TestDaoImpl implements ITestDao {
	private SqlSession session;
	public SqlSession getSession() {
		return session;
	}
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public void addTest() {
		Test t = new Test();
		t.setId(1);
		t.setFirstName("fname");
		t.setLastName("lname");
		this.session.insert("com.hd.ec.demo.mybatis.dao.mybatis.TestTable.doInsert", t);
	}
	
	
}

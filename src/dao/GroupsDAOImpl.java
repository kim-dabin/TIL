package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.Group;

public class GroupsDAOImpl implements GroupsDAO {
	private SqlSession session;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public Group select(int no) {
		// TODO Auto-generated method stub
		return session.selectOne("groups.select", no);
	}
	
	@Override
	public int update(Group group) {
		// TODO Auto-generated method stub
		return session.update("groups.update", group);
	}
	
	@Override
	public int delete(int no) {
		// TODO Auto-generated method stub
		return session.delete("groups.delete", no);
	}
	@Override
	public List<Group> selectList() {
		// TODO Auto-generated method stub
		return session.selectList("groups.selectList");
	}
	
	@Override
	public int insert(Group group) {
		// TODO Auto-generated method stub
		return session.insert("groups.insert", group);
	}
}

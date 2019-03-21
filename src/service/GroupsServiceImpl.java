package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import dao.GroupsDAO;
import dao.IdolsDAO;
import vo.Group;

public class GroupsServiceImpl implements GroupsService {
	
	private GroupsDAO groupsDAO;
	private IdolsDAO idolsDAO;
	
	public void setGroupsDAO(GroupsDAO groupsDAO) {
		this.groupsDAO = groupsDAO;
	}
	public void setIdolsDAO(IdolsDAO idolsDAO) {
		this.idolsDAO = idolsDAO;
	}
	@Override
	public Group getGroup(int no) {
		// TODO Auto-generated method stub
		return groupsDAO.select(no);
	}
	@Override
	public boolean update(Group group) {
		// TODO Auto-generated method stub
		if(groupsDAO.update(group)>0) 	return true;
		
		return false;
	}
	
	@Transactional
	@Override
	public boolean remove(int no) {
		// TODO Auto-generated method stub
		if(idolsDAO.selectSearch(no)!=null) {
			idolsDAO.deleteIdols(no);
		} 
		
		if(groupsDAO.delete(no)>0) {
			return true;
			
		}
		
		
		return false;
	}
	
	@Override
	public List<Group> getList() {
		// TODO Auto-generated method stub
		return groupsDAO.selectList();
	}
	@Override
	public boolean register(Group group) {
		// TODO Auto-generated method stub
		if(groupsDAO.insert(group)>0) {
			return true;
		}
		
		return false;
	}
}

package service;

import java.util.List;

import dao.IdolsDAO;
import vo.Idol;

public class IdolsServiceImpl implements IdolsService{

	private IdolsDAO idolsDAO;
	
	public void setIdolsDAO(IdolsDAO idolsDAO) {
		this.idolsDAO = idolsDAO;
	}
	
	@Override
	public List<Idol> searchIdol(String name) {
		// TODO Auto-generated method stub
		return idolsDAO.searchIdol(name);
	}
	
	@Override
	public boolean delete(int no) {
		// TODO Auto-generated method stub
		
		if(idolsDAO.delete(no)>0) return true;
		return false;
	}
	@Override
	public boolean update(Idol idol) {
		// TODO Auto-generated method stub
		
		if(idolsDAO.update(idol)>0) return true;
		
		return false;
	}
	
	@Override
	public boolean register(Idol idol) {
		// TODO Auto-generated method stub
		if(idolsDAO.insert(idol)>0) return true;
		return false;
	}
	
	@Override
	public List<Idol> getList() {
		// TODO Auto-generated method stub
		return idolsDAO.selectList();
	}
	
	@Override
	public Idol getIdol(int no) {
		// TODO Auto-generated method stub
		return idolsDAO.selectOne(no);
	}
}

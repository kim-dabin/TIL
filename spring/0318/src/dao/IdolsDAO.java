package dao;

import java.util.List;

import vo.Idol;

public interface IdolsDAO {
	public List<Idol> selectList();
	public Idol selectOne(int no);
	public int insert(Idol idol);
	public int deleteIdols(int groupNo);
	public List<Idol> selectSearch(int groupNo);
	public int update(Idol idol);
	public int delete(int no);
	public List<Idol> searchIdol(String name);
}

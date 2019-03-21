package service;

import java.util.List;

import vo.Idol;

public interface IdolsService {
	public List<Idol> getList();
	public Idol getIdol(int no);
	public boolean register(Idol idol);
	public boolean update(Idol idol);
	public boolean delete(int no);
}

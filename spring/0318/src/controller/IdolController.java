package controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import service.GroupsService;
import service.IdolsService;
import util.FileUploadUtil;
import vo.Idol;

@Controller
public class IdolController {
	private IdolsService idolsService;
	private GroupsService groupsService;
	private FileUploadUtil fileUploadUtil;
	
	public void setFileUploadUtil(FileUploadUtil fileUploadUtil) {
		this.fileUploadUtil = fileUploadUtil;
	}
	
	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}
	
	public void setIdolsService(IdolsService idolsService) {
		this.idolsService = idolsService;
	}
	
	@RequestMapping(value="/ajax/searchIdol.db")
	@ResponseBody
	public List<Idol> search(String name) {
		//System.out.println("idol name"+name);
		List<Idol> idols = idolsService.searchIdol(name);
		if(idols==null) {
			System.out.println("idol null");
		}
		
		return idols;
	}
	
	@RequestMapping("/deleteIdol.db")
	public String delete(int no) {
		idolsService.delete(no);
		return "redirect:/index.db";
	}
	
	
	@RequestMapping(value="/updateIdol.db", method=RequestMethod.POST)
	public String update(Idol idol, HttpServletRequest request, MultipartFile idolImg) {
		System.out.println(idol.getNo());
		if(idolImg.getOriginalFilename()!="") {
			String imgName = fileUploadUtil.uploadImg(request, idolImg);
			idol.setProfile("/upload/"+imgName);
		}else {
		//	System.out.println("idol image name "+idolsService.getIdol(idol.getNo()).getProfile());
			idol.setProfile(idolsService.getIdol(idol.getNo()).getProfile());
		}
		
		idolsService.update(idol);
		
		return "redirect:/idol.db?no="+idol.getNo();
	} 
	
	@RequestMapping(value="/updateIdol.db", method=RequestMethod.GET)
	public String updateForm(Model model, int no) {
		
		model.addAttribute("status", "수정");
		model.addAttribute("idol", idolsService.getIdol(no));
		model.addAttribute("groups", groupsService.getList());
		return "idolForm";
	}
	
	@RequestMapping(value="/insertIdol.db" ,method=RequestMethod.POST)
	public String insertIdol(HttpServletRequest request, MultipartFile idolImg, Idol idol) {
		
		String imgName = fileUploadUtil.uploadImg(request, idolImg);
		idol.setProfile("/upload/"+imgName);
		idolsService.register(idol);
		
	//	System.out.println("idol profile"+idol.getProfile());
		
	//	return "idolForm";
	return "redirect:/idol.db?no="+idol.getNo();
	}
	
	@RequestMapping(value="/insertIdol.db", method=RequestMethod.GET)
	public String insertForm(int groupNo, Model model) {
		model.addAttribute("status", "등록");
		model.addAttribute("groups", groupsService.getList());
		model.addAttribute("selectGroupNo", groupNo);
		
		return "idolForm";
	}
	
	@RequestMapping("/idol.db")
	public String idolDetail(int no, Model model) {
		System.out.println(no);
		Idol idol = idolsService.getIdol(no);
		System.out.println(idol.getNo());
		
		model.addAttribute("idol", idol );
		return"idolDetail";
	}
}



package controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import service.GroupsService;
import service.IdolsService;
import vo.Group;
import vo.Idol;

@Controller
public class GroupController {
	
	private GroupsService groupsService;
	private IdolsService idolsSerivce;
	
	public void setIdolsSerivce(IdolsService idolsSerivce) {
		this.idolsSerivce = idolsSerivce;
	}
	
	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}
	
	public static String uploadImg(HttpServletRequest request, MultipartFile image) {
		String root  = request.getServletContext().getRealPath("/");
		System.out.println("root: "+root);
		String uploadPath = root+"upload"+File.separator;
		System.out.println("uploadPath: "+uploadPath);
		String uuid = UUID.randomUUID().toString();
		System.out.println("uuid: "+uuid);
		String original = image.getOriginalFilename();
		String exe = original.substring(original.indexOf("."));
		File file = new File(uploadPath+uuid+exe);
		
		try {
			image.transferTo(file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return uuid+exe;
	}
	
	@RequestMapping(value="/updateGroup.html", method=RequestMethod.GET)
	public String updateForm(int no, Model model) {
		model.addAttribute("status", "수정");
		model.addAttribute("group", groupsService.getGroup(no));
		
		
		return "groupForm";
	}
	
	@RequestMapping(value="/updateGroup.html", method=RequestMethod.POST)
	public String update(Group group,HttpServletRequest request, MultipartFile image) {
		System.out.println(image.getOriginalFilename());
		
	
		if(image.getOriginalFilename()!="") {
			System.out.println("들어옴 "+image.getOriginalFilename());
		String imgName = uploadImg(request, image);
		System.out.println("imge name "+imgName);
		group.setProfile("/upload/"+imgName); 
		}else {
			//System.out.println(groupsService.getGroup(group.getNo()).getProfile());
			group.setProfile(groupsService.getGroup(group.getNo()).getProfile());
		}
		
		groupsService.update(group);
		
		return "redirect:/index.html";
	}
	
	@RequestMapping("/deleteGroup.html")
	public String delete(int no) {
		groupsService.remove(no);
		
		return "redirect:/index.html";
	}
	
	@RequestMapping("/index.html")
	public void groupPage(Model model ) {
		List<Group> groups = groupsService.getList();
		List<Idol> idols = null;
		
		model.addAttribute("groups", groups);
		
		
			idols =  idolsSerivce.getList();
				model.addAttribute("idols",idols);	
		

	
		
	}
	@RequestMapping(value="/insertGroup.html", method=RequestMethod.GET)
	public String insertForm(Model model) {
		model.addAttribute("status","등록");
		return "groupForm";
	}
	@RequestMapping(value="/insertGroup.html", method=RequestMethod.POST)
	public String insertGroup(HttpServletRequest request, MultipartFile image, Group group ) {
		String imageName = uploadImg(request, image);
		group.setProfile("/upload/"+imageName); 
		groupsService.register(group);
		
		return "redirect:/index.html";
	}
	
}

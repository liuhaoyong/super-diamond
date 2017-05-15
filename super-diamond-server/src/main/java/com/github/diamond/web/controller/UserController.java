package com.github.diamond.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.github.diamond.web.common.SuperDiamondEnumCode;
import com.github.diamond.web.model.ConfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.diamond.utils.MD5;
import com.github.diamond.utils.SessionHolder;
import com.github.diamond.web.service.UserService;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/index")
	public void queryUsers(ModelMap modelMap) {
		List<ConfUser> users = userService.queryUsers();
		
		modelMap.addAttribute("users", users);
	}
	
	@RequestMapping("/user/new")
	public void newUser() {
	}
	
	@RequestMapping(value="/user/save", method={RequestMethod.POST})
	public String saveUser(ConfUser user, String repassword, HttpSession session) {
		user.setPassword(MD5.getInstance().getMD5String(user.getPassword()));
		userService.saveUser(user);
		return "redirect:/user/index";
	}
	
	@RequestMapping("/user/delete")
	public String deleteUser(long id, HttpSession session) {
		userService.deleteUser(id);
		session.setAttribute("message", SuperDiamondEnumCode.OPERA_DELETE_SUCCES.getMsg());
		return "redirect:/user/index";
	}
	
	@RequestMapping("/user/password")
	public void password() {
	}
	
	@RequestMapping("/user/updatePassword")
	public String updatePassword(String password, String newpassword, HttpSession session) {
		String oldPassword = MD5.getInstance().getMD5String(password);
		ConfUser user = (ConfUser) SessionHolder.getSession().getAttribute("sessionUser");
		
		if(!oldPassword.equals(user.getPassword()))
			session.setAttribute("message", SuperDiamondEnumCode.OLD_PASSWORD_INCORRECT.getMsg());
		else {
			userService.updatePassword(user.getId(), MD5.getInstance().getMD5String(newpassword));
			session.setAttribute("message", SuperDiamondEnumCode.PASSWORD_UPDATE_SUCCESS.getMsg());
		}
		return "redirect:/user/password";
	}
}

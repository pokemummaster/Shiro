package pers.huangyuhui.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.huangyuhui.shiro.bean.User;

/**
 * @project: ssm-shiro
 * @description: 用户控制器
 * @author: 黄宇辉
 * @date: 8/10/2019-12:06 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Controller
public class UserController {

    /**
     * @description: 跳转到用户登录页
     * @date: 2019-08-11 8:53 AM
     * @return: java.lang.String
     */
    @RequestMapping("/loginView")
    public String goLoginView() {
        return "/view/user/login";
    }

    /**
     * @description: 处理用户登录请求:Shiro认证操作
     * @param: user
     * @param: model
     * @date: 2019-08-11 8:53 AM
     * @return: java.lang.String
     */
    @RequestMapping("/login")
    public String login(User user, boolean rememberMe, Model model) {
        System.out.println("[/login]------------>" + user.toString() + "\nrememberMe------------>" + rememberMe);
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据,并设置rememberMe功能
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), rememberMe);
        //执行登录
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("errorMsg", "登录失败:账户信息错误 !");
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("errorMsg", "登录失败:密码信息错误 !");
        } catch (AuthenticationException e) {
            model.addAttribute("errorMsg", "登录失败:账户/密码信息错误 !");
        }
        if (subject.isAuthenticated()) {
            return "index"; //用户主页
        } else {
            token.clear();
            return "/view/user/login"; //重新登录
        }
    }
}
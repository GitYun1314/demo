package intelligent_express_cabinets.demo.controller;


import intelligent_express_cabinets.demo.common.returnBean;
import intelligent_express_cabinets.demo.entity.UserRole;
import intelligent_express_cabinets.demo.entity.Users;
import intelligent_express_cabinets.demo.service.IUserRoleService;
import intelligent_express_cabinets.demo.service.IUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/system/users")
public class UsersController {

    @Resource
    private IUsersService usersService;

    @Resource
    private IUserRoleService userRoleService;

    @ApiOperation(value = "新用户注册")
    @PostMapping("/register")
    public returnBean userRegister(@RequestBody Users users){
        Users user = usersService.getUserByUsername(users.getUsername());
        if (user==null){
            //先把用户输入的明文密码进行加密
            PasswordEncoder pe = new BCryptPasswordEncoder();
            String encode = pe.encode(users.getPassword());
            users.setPassword(encode);
            users.setUserStatus(0);
            Boolean booleans = usersService.save(users);
            if (booleans==true){
                Users user1 = usersService.getUserByUsername(users.getUsername());
                Integer userId = user1.getUserId();
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(2);
                Boolean bool= userRoleService.save(userRole);
                if (bool=true){
                    return returnBean.success("新用户注册成功!");
                }
                else {
                    return returnBean.error("新用户注册失败!");
                }
            }
        }
        return returnBean.error("此新用户的账号已经存在!");
    }
}

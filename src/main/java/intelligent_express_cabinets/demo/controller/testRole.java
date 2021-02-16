package intelligent_express_cabinets.demo.controller;

import intelligent_express_cabinets.demo.entity.Roles;
import intelligent_express_cabinets.demo.service.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "testRoleController")
@RestController
public class testRole {

    @Autowired
    private IUsersService usersService;
    @ApiOperation(value = "测试菜单")
    @GetMapping("/system/role/{userId}")
    public List<Roles> testRoles(@PathVariable Integer userId){
        List<Roles> roles = usersService.getRoles(userId);
        return roles;
    }

}

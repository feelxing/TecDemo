package hello;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MEV on 2017/3/6.
 */
@RestController
@RequestMapping(value = "feel",method = RequestMethod.POST)
public class feelController {

    static Map<Long, FeelUser> users = Collections.synchronizedMap(new HashMap<Long, FeelUser>());
    @ApiOperation(value = "测试" ,nickname = "测试")
    @RequestMapping(value = "/first" ,produces = "application/json")
    public String getList(@PathVariable String name){
        return  name;
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象" )
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value="/two", method=RequestMethod.POST,produces = "application/text")
    public String deleteUser(@RequestBody  String id) {
        System.out.println(id);
        return "success";
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @RequestMapping(value="postUser", method=RequestMethod.POST)
    public FeelUser postUser(@RequestBody FeelUser user) {
        users.put(user.getId(), user);
        return user;
    }

}

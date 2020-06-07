package com.kuang.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.kuang.pojo.User;
import com.kuang.service.IndexService;
import com.kuang.utils.FaceUtil;
import com.kuang.utils.baidu_face.com.baidu.ai.aip.utils.FaceAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class foreController {

    @Autowired
    private IndexService indexService;

    /**
     * 人脸登录
     *
     * 使用注解@ReqeustBody对数据接收
     */
    @RequestMapping("/login/receiveData")
    @ResponseBody
    public String getfaceLogin(@RequestBody String base64,
                               HttpSession session) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode(base64, "UTF-8");
        int startIndex = decode.indexOf("base64,") + 7;
        int endIndex = decode.lastIndexOf("\"");
        String baseString = decode.substring(startIndex, endIndex);

        JSONObject json = new JSONObject();
        if (FaceUtil.checkFace(baseString)) {
            //此时表示已经验证成功,并将user_id（人脸库）赋值给uuid
            String uuid = FaceUtil.uuid;

            //通过对应uuid找到对应对象
            User user = indexService.get(uuid);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userNickName",indexService.getUser(user.getUserId()).getUserNickName());

            json.put("msg", "登录成功");
            json.put("success", true);
            return json.toJSONString();
        } else {
            json.put("msg", "未匹配到符合人脸");
            //验证失败
            json.put("success", false);
            return json.toJSONString();
        }
    }

    /**
     * 登录
     *
     * @param user_name 用户名
     * @param user_password 用户密码
     * @param model 登录失败时，返回给前端显示的数据msg
     * @param session 用户与服务器通讯
     * @return 跳转页面
     */
    @RequestMapping(value = "/login/doLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String login(@RequestParam("user_name") String user_name,
                        @RequestParam("user_password") String user_password,
                        Model model,
                        HttpSession session) throws IOException {
        User users = indexService.login(user_name, user_password);
        if(users != null){
            session.setAttribute("userId", users.getUserId());
            session.setAttribute("userNickName",indexService.getUser(users.getUserId()).getUserNickName());
            System.out.println(users.getUserId());
            //跳转到网站首页
            return "redirect:/index";
        }
        else {
            model.addAttribute("error_msg","用户名或密码错误");
            return "fore/login";
        }

    }

    /**
     * 注册
     *
     *
     * @param user_name 用户名
     * @param user_password 用户密码
     * @param user_nickname 用户昵称
     * @return 人脸注册页面
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String addUser(@RequestParam("user_nickname")String user_nickname,
                          @RequestParam("user_name")String user_name,
                          @RequestParam("user_password")String user_password
    ){
        User user = new User()
                .setUser_nickname(user_nickname)
                .setUser_name(user_name)
                .setUser_password(user_password);
//        将user信息暂存到FaceUtil的user中
        FaceUtil.user = user;

        return "fore/faceRegister";
    }

    /**
     * 实现人脸注册
     *
     * @param base64 base64人脸图片字符串信息
     * @return 响应数据
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    @RequestMapping("/register/receiveData")
    @ResponseBody
    public String getfaceRegister(@RequestBody String base64) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode(base64, "utf-8");
        int startIndex = decode.indexOf("base64,") + 7;
        int endIndex = decode.lastIndexOf("\"");
        String baseString = decode.substring(startIndex, endIndex);

        String add = FaceAdd.add(baseString);

        String substring = add.substring(add.indexOf("result:") + 1);
        int error_code = (int) JSONObject.parseObject(substring).get("error_code");
        JSONObject jsonObject = new JSONObject();
        if (error_code != 0) {
            String tip = "将摄像头对准并且保持光线明亮，可以尝试离电脑距离变小";
            jsonObject.put("success", false);
            jsonObject.put("msg", tip);
        } else {
            String tip = "录入成功,将前往登录界面";
            User user = FaceUtil.user.setUuid(FaceUtil.uuid);
            //整合之前的注册信息存入数据库
            indexService.add(user);
            jsonObject.put("success", true);
            jsonObject.put("msg", tip);
        }
        return jsonObject.toJSONString();
    }


}

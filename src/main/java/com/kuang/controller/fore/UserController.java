package com.kuang.controller.fore;

import java.net.URLDecoder;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.kuang.pojo.*;
import com.kuang.service.TaskService;
import com.kuang.service.UserService;
import com.kuang.utils.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

/**
 * @author HP
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/user/personalPage")
    public String goToPersonalPage() {
        return "/fore/personalPage";
    }

    /**
     * @param model 传入个人介绍页信息
     * @param request 通过session获取useId
     * @return 前往个人介绍页
     */
    @RequestMapping("/user/personalInfo")
    public String goToPersonalInfo(Model model, HttpServletRequest request) {
        Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute("userId"));
        if (userId.isPresent()) {
            ModelUtil<User> modelUtil = new ModelUtil<>();
            User user = userService.get(userId.get());
            List<String> lists = new ArrayList<>();
            if (user.getUserProfilePhoto() == null) {
                lists.add("https://i.niupic.com/images/2020/05/18/7Sqi.jpeg");
            } else {
                lists.add(new String(user.getUserProfilePhoto(), StandardCharsets.UTF_8));
            }
            modelUtil.setBase64(lists);
            modelUtil.setObj(user);
            model.addAttribute("modelUtil", modelUtil);
            return "/fore/personalInfo";
        } else {
            return "/fore/login";
        }
    }

    @RequestMapping("/user/personalSpace")
    public String goToPersonalSpace(Model model, HttpServletRequest request) {
        List<ModelUtil> m = new ArrayList<>();
        Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute("userId"));
        if (!userId.isPresent()) {
            return "/fore/login";
        }
        List<Task> tasks = taskService.getAllTasksByUserId(userId.get());
        tasks.forEach(t -> {
            ModelUtil<Task> modelUtil = new ModelUtil<>();
            List<TaskPictures> taskPictures = taskService.getAllTaskPictures(t.getTaskId());
            List<String> bases = new ArrayList<>();
            taskPictures.forEach(p -> bases.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
            modelUtil.setObj(t);
            modelUtil.setBase64(bases);
            m.add(modelUtil);
        });
        model.addAttribute("modelUtils", m);
        return "/fore/personalSpace";
    }

    /**
     * 发布页
     *
     * @param model
     * @return 发布页
     */
    @RequestMapping("/user/publish")
    public String goToPublishPage(Model model) {
        List<Sorts> parentSorts = taskService.getParentSort();
        model.addAttribute("sonSorts", taskService.getSons(parentSorts.get(0).getSortName()));
        model.addAttribute("parentSorts", parentSorts);
        return "/fore/publish";
    }

    /**
     * 使用ajax对子分类进行更新
     *
     * @param parentSort 父分类主键
     * @param model      模型对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/sonSort", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String sort(@RequestParam String parentSort, Model model) {
        JSONObject jsonObject = new JSONObject();
        int parent = Integer.parseInt(parentSort);
        List<Sorts> sonSorts = taskService.getSons(parent);
        Sorts[] sorts = new Sorts[sonSorts.size()];
        model.addAttribute("sonSorts", sorts);
        jsonObject.put("success", true);
        jsonObject.put("sonSorts", sonSorts);
        return jsonObject.toJSONString();
    }


    /**
     * @param editJson 用户编辑的个人信息json串
     * @return 个人主页
     */
    @ResponseBody
    @RequestMapping(value = "/user/edit/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String saveUserInfo(@RequestBody String editJson, HttpServletRequest request) throws UnsupportedEncodingException {
        Optional<String> optional = Optional.ofNullable(editJson);
        JSONObject json = new JSONObject();
        if (!optional.isPresent()) {
            json.put("success", false);
            return json.toJSONString();
        }

        JSONObject jsonObject = JSONObject.parseObject(URLDecoder.decode(optional.get().substring(optional.get().indexOf("=") + 1), "utf-8"));
        Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute("userId"));
        if (!userId.isPresent()) {
            json.put("msg","用户登录状态异常");
            json.put("success", false);
            return json.toJSONString();
        }
        String userQqNumber = jsonObject.getString("userQqNumber");
        String userName = jsonObject.getString("userName");
        String userNickName = jsonObject.getString("userNickName");
        String userPassword = jsonObject.getString("userPassword");
        String userEmail = jsonObject.getString("userEmail");
        Timestamp userRegistrationTime = userService.get(userId.get()).getUserRegistrationTime();
        Timestamp userBirthday = Timestamp.valueOf(jsonObject.getString("userBirthday") + " 0:0:0");
        String userTelephoneNumber = jsonObject.getString("userTelephoneNumber");
        String userSignature = jsonObject.getString("userSignature");
        byte[] headData = jsonObject.getString("userHeadImageData").getBytes();

        User user = new User();
        user.setUserId(userId.get()).setUserName(userName).setUserQqNumber(userQqNumber).setUserPassword(userPassword).setUserEmail(userEmail)
                .setUserProfilePhoto(headData).setUserRegistrationTime(userRegistrationTime).setUserBirthday(userBirthday)
                .setUserTelephoneNumber(userTelephoneNumber).setUserNickName(userNickName).setPersonalSignature(userSignature);
        boolean updateResult = userService.update(user);
        json.put("success", updateResult);
        request.getSession().setAttribute("userNickName", userNickName);
        return json.toJSONString();
    }

    /**
     * @param file    用户上传的图片
     * @param session 用户获取登录者id
     * @return 附带有图片的json数据
     */
    @ResponseBody
    @RequestMapping(value = "/user/uploadUserHeadImage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String uploadUserHeadImage(MultipartFile file, HttpSession session) {
        JSONObject json = new JSONObject();
        try {
            byte[] bytes = file.getBytes();
            Optional<Object> userId = Optional.ofNullable(session.getAttribute("userId"));
            if (userId.isPresent()) {
                User user = userService.get((int) userId.get());
                user.setUserProfilePhoto(bytes);
            } else {
                json.put("success", false);
                json.put("picture", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toJSONString();
    }

    /**
     * @param files   用户图片
     * @param session 获取用户id
     * @return 上传结果
     */
    @ResponseBody
    @RequestMapping(value = "/user/uploadUserLikeImage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String uploadUserLikeImage(MultipartFile[] files, @NotNull HttpSession session) {
        JSONObject json = new JSONObject();
        Optional<Integer> userId = Optional.ofNullable((Integer) session.getAttribute("userId"));
        if (userId.isPresent()) {
            try {
                assert files != null;
                byte[][] pictures = new byte[files.length][];
                for (int i = 0; i < files.length; i++) {
                    UserPictures userPictures = new UserPictures();
                    userPictures.setUserPictures(files[i].getBytes());
                    userPictures.setUserId(userId.get());
                    pictures[i] = files[i].getBytes();
                    json.put("success", userService.update(userPictures));
                }
                json.put("pictures", pictures);
            } catch (IOException e) {
                json.put("pictures", null);
                json.put("success", false);
            }
        }
        return json.toJSONString();
    }


}

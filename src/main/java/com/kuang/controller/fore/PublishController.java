package com.kuang.controller.fore;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuang.pojo.Task;
import com.kuang.pojo.TaskPictures;
import com.kuang.service.SortService;
import com.kuang.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author HP
 */
@Controller
public class PublishController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SortService sortService;

    @ResponseBody
    @PostMapping("/user/uploadTaskImage")
    public String uploadTaskImage(){
        JSONObject json = new JSONObject();
        json.put("success",true);
        return json.toJSONString();
    }
    /**
     * @param request 获取会话请求
     * @return 发布成功页面---因前端页面现未完成
     */
    @ResponseBody
    @RequestMapping(value = "/user/publishSuccess",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String publishSuccess(@RequestBody String taskJson, HttpServletRequest request) throws UnsupportedEncodingException {
        Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute("userId"));
        JSONObject jsonObject = new JSONObject();
        if(!userId.isPresent()){
            jsonObject.put("success",false);
            jsonObject.put("msg","没有登录，请先前往登录");
            return jsonObject.toJSONString();
        }
        String decode = URLDecoder.decode(taskJson, "utf-8").substring(taskJson.indexOf("=") + 1);
        JSONObject json = JSONObject.parseObject(decode);
        String taskId = UUID.randomUUID().toString();
        String parentSort = sortService.getSortNameBySortId(Integer.parseInt(json.getString("parent_sort")));
        String sonSort = sortService.getSortNameBySortId(Integer.parseInt(json.getString("son_sort")));
        String title = json.getString("title");
        String time = json.getString("time") + ":00";
        time = time.replace('T',' ');
        String address = json.getString("address");
        String taskDescription = json.getString("taskDescription");
        JSONArray imageData = (JSONArray) json.get("imageData");
        Task task = new Task(taskId, userId.get(), title, taskDescription, 0, Timestamp.valueOf(time), 0, address, parentSort, sonSort);
        taskService.add(task);
        byte[][] buffer = new byte[imageData.size()][];
        for (int i = 0; i < imageData.size(); i++) {
            buffer[i] = imageData.getString(i).replace(' ','+').getBytes();
        }
        for (byte[] bytes : buffer) {
            TaskPictures taskPictures = new TaskPictures(taskId, bytes);
            taskService.add(taskPictures);
        }
        jsonObject.put("success", true);
        return jsonObject.toJSONString();
    }
}

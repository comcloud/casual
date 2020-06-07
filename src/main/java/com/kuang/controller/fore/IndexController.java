package com.kuang.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.kuang.controller.util_controller.BaseController;
import com.kuang.pojo.*;
import com.kuang.service.IndexService;
import com.kuang.utils.FaceUtil;
import com.kuang.utils.ModelUtil;
import com.kuang.utils.SpeechUtil;
import com.kuang.utils.baidu_face.com.baidu.ai.aip.utils.FaceAdd;
import com.kuang.utils.speechUtil.Sample;
import com.kuang.utils.speechUtil.jacobtest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static com.kuang.utils.picformtochange.Base64util.byte2Base64StringFun;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    private int index = 0;

    private static List<String> bigsortName;
    private static List<String> smallsortName;

    /**
     * @return 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toMainPage(Model model){
        //导航栏  大小分类
        int parent_sort_id = 0;
        Collection<Sorts> sorts = indexService.getAll(parent_sort_id);
        model.addAttribute("sorts",sorts);
        Collection<Sorts> smallsorts = indexService.getAllSmallSorts(parent_sort_id);
        model.addAttribute("smallsorts",smallsorts);

        //发布的请求
        List<ModelUtil> m = new ArrayList<>();
        List<Task> tasks = indexService.getTaskAll();
        tasks.forEach(t -> {
            ModelUtil<Task> modelUtil = new ModelUtil<>();

            List<TaskPictures> taskPictures = indexService.getAllTaskPictures(t.getTaskId());
            List<String> bases = new ArrayList<>();
            taskPictures.forEach(p -> bases.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
            modelUtil.setObj(t);
            modelUtil.setBase64(bases);
            m.add(modelUtil);
        });
        model.addAttribute("tasks", m);

        return "fore/index";
    }

    /**
     * @return 人脸注册页
     */
    @RequestMapping(value = "/user/toFaceRegister", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toFaceRegister(){
        return "fore/faceRegister";
    }


    /**
     * @return 人脸登录页
     */
    @RequestMapping(value = "/user/toFaceLogin", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toFaceLogin(){
        return "fore/faceLogin";
    }


    /**
     * @return 到登录页
     */
    @RequestMapping(value = "/user/toLogin", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toLoginPage(){
        return "fore/login";
    }

    /**
     * @return 到注册页
     */
    @RequestMapping(value = "/user/toRegister", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toRegisterPage(){
        return "fore/register";
    }

    /**
     * @return 到个人页面(的首页)
     */
    @RequestMapping(value = "/user/toPersonal", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toPersonalPage(HttpSession session){
        Object userId = BaseController.checkUser(session);
        if(userId != null){
            User user = indexService.getUser(Integer.parseInt(userId.toString()));
        }else {
            return "fore/login";
        }
        return "fore/personalPage";
    }


    /**
     * 注销账户
     *
     * @param session 用户与服务器通讯
     * @return 重定向到登录页面
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String logout(HttpSession session){
        Object object = session.getAttribute("userId");
        if(object != null){
            session.removeAttribute("userId");
            //将session设置为失效
            session.invalidate();
        }

        return "fore/login";
    }

    /**
     * 分类页
     *
     * @param sort_name 小分类名
     * @param model 传给分类页的对应小分类对象信息
     * @return 对应的分类页面
     */
    @RequestMapping(value = "/toSortsPage/{sort_name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toSortsPage(@PathVariable("sort_name")String sort_name,Model model){
        //对应的发布的请求图片遍历
        List<ModelUtil> m = new ArrayList<>();
        //小分类
        List<Task> smallSorts_pages = indexService.getTaskFromName(sort_name);
        smallSorts_pages.forEach(t -> {
            ModelUtil<Task> modelUtil = new ModelUtil<>();

            List<TaskPictures> taskPictures = indexService.getAllTaskPictures(t.getTaskId());
            List<String> bases = new ArrayList<>();
            taskPictures.forEach(p -> bases.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
            modelUtil.setObj(t);
            modelUtil.setBase64(bases);
            m.add(modelUtil);
        });
        model.addAttribute("Sorts_pages",m);

        //侧边栏
        int parent_sort_id = 0;
        //运动，学习。。。
        Collection<Sorts> sorts = indexService.getAll(parent_sort_id);
//        System.out.println(sorts);
        model.addAttribute("sorts",sorts);

        //篮球，编程，数学。。。
        Collection<Sorts> smallsorts = indexService.getAllSmallSorts(parent_sort_id);

        model.addAttribute("smallsorts",smallsorts);

        //跳转至分类好的页面
        return "fore/sortsPage";

    }


    /**
     * 搜索查询
     *
     * @param searchName 模糊查询信息
     * @param model 传给分类页的对应大分类信息
     * @return 对应的分类页面
     */
    @RequestMapping(value = "/toSearchFromName", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toSearchFromName(@RequestParam("searchName")String searchName,Model model){
        Task task = new Task()
                .setParentSort(searchName)
                .setSonSort(searchName)
                .setTaskAddress(searchName)
                .setTaskTitle(searchName);
        //对应的发布的请求图片遍历
        List<ModelUtil> m = new ArrayList<>();
        List<Task> searchTask = indexService.getTaskFromSearchName(task);
        searchTask.forEach(t -> {
            ModelUtil<Task> modelUtil = new ModelUtil<>();

            List<TaskPictures> taskPictures = indexService.getAllTaskPictures(t.getTaskId());
            List<String> bases = new ArrayList<>();
            taskPictures.forEach(p -> bases.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
            modelUtil.setObj(t);
            modelUtil.setBase64(bases);
            m.add(modelUtil);
        });
        model.addAttribute("Sorts_pages",m);
        //侧边栏
        int parent_sort_id = 0;
        //运动，学习。。。
        Collection<Sorts> sorts = indexService.getAll(parent_sort_id);
//        System.out.println(sorts);
        model.addAttribute("sorts",sorts);

        //篮球，编程，数学。。。
        Collection<Sorts> smallsorts = indexService.getAllSmallSorts(parent_sort_id);
        model.addAttribute("smallsorts",smallsorts);

        //跳转至分类好的页面
        return "fore/sortsPage";

    }


    /**
     * 大类跳分类页
     *
     * @param bigsort_name 大分类名
     * @param model 传给分类页的对应大分类信息
     * @return 对应的分类页面
     */
    @RequestMapping(value = "/toBigSortsPage/{bigsort_name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toSortsPageFromBigSorts(@PathVariable("bigsort_name")String bigsort_name,Model model){
        //对应的发布的请求图片遍历
        List<ModelUtil> m = new ArrayList<>();
        //大分类
        Collection<Task> bigSorts_pages = indexService.getTaskFromBigSortName(bigsort_name);
        bigSorts_pages.forEach(t -> {
            ModelUtil<Task> modelUtil = new ModelUtil<>();

            List<TaskPictures> taskPictures = indexService.getAllTaskPictures(t.getTaskId());
            List<String> bases = new ArrayList<>();
            taskPictures.forEach(p -> bases.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
            modelUtil.setObj(t);
            modelUtil.setBase64(bases);
            m.add(modelUtil);
        });
        model.addAttribute("Sorts_pages",m);

        //侧边栏
        int parent_sort_id = 0;
        //运动，学习。。。
        Collection<Sorts> sorts = indexService.getAll(parent_sort_id);
//        System.out.println(sorts);
        model.addAttribute("sorts",sorts);

        //篮球，编程，数学。。。
        Collection<Sorts> smallsorts = indexService.getAllSmallSorts(parent_sort_id);
        model.addAttribute("smallsorts",smallsorts);

        //跳转至分类好的页面
        return "fore/sortsPage";

    }


    /**
     * 详情页
     *
     * @param task_id 请求id
     * @param model
     * @return 跳转详情页
     */
    @RequestMapping(value = "/toDetailsPage/{task_id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toDetailsPage(@PathVariable("task_id")String task_id,
                                Model model,HttpSession session){
        Object userId = BaseController.checkUser(session);
        if(userId != null){
            User user = indexService.getUser(Integer.parseInt(userId.toString()));
        }else {
            return "fore/login";
        }

        //通过task_id获取对应图片集合
        ModelUtil<Task> modelUtil = new ModelUtil<>();
        List<TaskPictures> taskPictures = indexService.getAllTaskPictures(task_id);
        List<String> base = new ArrayList<>();
        taskPictures.forEach(p -> base.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
        modelUtil.setBase64(base);
        model.addAttribute("task_pics",modelUtil);

        Task task_detail = indexService.gettaskfromid(task_id);
        model.addAttribute("task_detail",task_detail);

        Collection<Comments> commentsCollections = indexService.getComments(task_id);
        model.addAttribute("commentsCollections",commentsCollections);

        //跳转至请求详情页
        return "fore/details";
    }

    /**
     * 点赞更新
     *
     * @param task_id 请求id
     * @param model
     * @return 跳转详情页
     */
    @RequestMapping(value = "/toGivethethumbs/{task_id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toGivethethumbs(@PathVariable("task_id")String task_id,Model model){
        indexService.addTaskLikeCount(task_id);
        //通过task_id获取对应图片集合
        ModelUtil<Task> modelUtil = new ModelUtil<>();
        List<TaskPictures> taskPictures = indexService.getAllTaskPictures(task_id);
        List<String> base = new ArrayList<>();
        taskPictures.forEach(p -> base.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
        modelUtil.setBase64(base);
        model.addAttribute("task_pics",modelUtil);

        Task task_detail = indexService.gettaskfromid(task_id);
        model.addAttribute("task_detail",task_detail);
        Collection<Comments> commentsCollections = indexService.getComments(task_id);
        model.addAttribute("commentsCollections",commentsCollections);

        //跳转至请求详情页
        return "fore/details";
    }

    /**
     * 评论更新
     *
     * @param task_id 请求id
     * @param model
     * @return 跳转详情页
     */
    @RequestMapping(value = "/toGiveAdvice/{task_id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String toGiveAdvice(@PathVariable("task_id")String task_id,
                               String commenttext,Model model,HttpSession session){
        System.out.println(task_id);
        Comments comments = new Comments().setUserId((Integer) session.getAttribute("userId"))
                .setTaskId(task_id).setCommentDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()).toString().replace('T',' ')))
                .setCommentContent(commenttext);
        indexService.addCommentFromid(comments);

        //通过task_id获取对应图片集合
        ModelUtil<Task> modelUtil = new ModelUtil<>();
        List<TaskPictures> taskPictures = indexService.getAllTaskPictures(task_id);
        List<String> base = new ArrayList<>();
        taskPictures.forEach(p -> base.add(new String(p.getTaskPicture(), StandardCharsets.UTF_8)));
        modelUtil.setBase64(base);
        model.addAttribute("task_pics",modelUtil);

        Task task_detail = indexService.gettaskfromid(task_id);
        model.addAttribute("task_detail",task_detail);
        Collection<Comments> commentsCollections = indexService.getComments(task_id);
        model.addAttribute("commentsCollections",commentsCollections);
        //跳转至请求详情页
        return "fore/details";
    }

    /**
     * @return 语音操结果
     */
    @RequestMapping(value = "/user/SpeechTiko", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String SpeechTiko(){
        //清空上次存储的信息
        SpeechUtil.setRegetResultSmall(null);
        SpeechUtil.setRegetResultBig(null);
        int parent_sort_id = 0;
        //查询大分类名
        bigsortName = indexService.getBigSortName(parent_sort_id);
        //查询小分类名
        smallsortName = indexService.getSmallSortName(parent_sort_id);
        if(index == 0){
            new jacobtest().textToSpeech("欢迎使用Tiko语音助手，请问有什么可以帮您的");
            index++;
        }
        //录音并返回字符串
        try {
            Sample.main(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < bigsortName.size();i++){
            if(Sample.getResult() == null)
                break;
            if(Sample.getResult().contains(bigsortName.get(i))
                    ||Sample.getResult().contains("查询"+bigsortName.get(i))
                    ||Sample.getResult().contains(bigsortName.get(i)+"类")
                    ||Sample.getResult().contains("查询"+bigsortName.get(i)+"分类")
                    ||Sample.getResult().contains(bigsortName.get(i)+"分类")){
                SpeechUtil.setRegetResultBig(bigsortName.get(i));
                break;
            }
        }
        for(String smallName:smallsortName){
            if(Sample.getResult() == null)
                break;
            if(Sample.getResult().contains(smallName)
                    ||Sample.getResult().contains("查询"+smallName)
                    ||Sample.getResult().contains(smallName+"类")
                    ||Sample.getResult().contains("查询"+smallName+"分类")
                    ||Sample.getResult().contains(smallName+"分类")){
                SpeechUtil.setRegetResultSmall(smallName);
                break;
            }
        }
        JSONObject json = new JSONObject();
        if(SpeechUtil.speechutil() == null &&
                SpeechUtil.getRegetResultSmall() == null &&
                SpeechUtil.getRegetResultBig() == null){
            //执行非网站操作程序
            json.put("select",1);
        }else if(SpeechUtil.getRegetResultBig() != null){
            //执行网站操作程序(大分类）
            json.put("select",2);
            json.put("info",SpeechUtil.getRegetResultBig());
        }else if(SpeechUtil.getRegetResultSmall() != null){
            //执行网站操作程序(小分类）
            json.put("select",3);
            json.put("info",SpeechUtil.getRegetResultSmall());
        }
        return json.toJSONString();
    }

}

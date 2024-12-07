package com.cn.campus.controller.wx;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.campus.entity.*;
import com.cn.campus.service.*;
import com.cn.campus.utils.response.R;
import com.cn.campus.utils.utils.AceUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  对小程序提供后端接口
 * </p>
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityClassifiedService activityClassifiedService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private LostFoundService lostFoundService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ActivityCollectService activityCollectService;

    @Autowired
    private ConfessionService confessionService;

    @Autowired
    private ConfessionCommentService confessionCommentService;

    @Autowired
    private ConfessionPraiseService confessionPraiseService;

    @Autowired
    private UserChatService userChatService;

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCollectService goodsCollectService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobPraiseService jobPraiseService;


    /**
     * 登录功能
     */
    @PostMapping("login")
    public R login(@RequestBody SysUser data){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        LambdaQueryWrapper<SysUser> lambda = qw.lambda();
        lambda.eq(SysUser::getUsername, data.getUsername());
        SysUser pUser = sysUserService.getOne(qw);
        if(pUser == null){
            return R.error("账号不存在");
        }
        String string2MD5Password = data.getPassword();
        // 判断密码
        if(!string2MD5Password.equals(pUser.getPassword())){
            return R.error("密码不正确");
        }
        if(!"2".equals(pUser.getUserType())){
            return R.error("请登录用户账号");
        }
        String status = pUser.getStatus();

        // 判断账号状态
        if(!"1".equals(status)){
            return R.error("账号已被禁用");
        }

        return R.ok().data("row",pUser);
    }


    /**
     * 注册功能
     * @param data
     * @return
     */
    @PostMapping("register")
    public R register(@RequestBody SysUser data){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        LambdaQueryWrapper<SysUser> lambda = qw.lambda();
        lambda.eq(SysUser::getUsername, data.getUsername());
        SysUser pUser = sysUserService.getOne(qw);
        if(pUser != null){
            return R.error("账号已被注册");
        }
        data.setStatus("1");
        data.setUserType("2");
        sysUserService.save(data);
        return R.ok();
    }

    /**
     * 个人信息查询
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(String id){
        SysUser ttUser = sysUserService.getById(id);
        return R.ok().data("row", ttUser);
    }

    /**
     * 个人信息修改
     */
    @PostMapping("updateUserInfo")
    public R updateUserInfo(@RequestBody SysUser ttUser){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("id", ttUser.getId());
        if(!StringUtils.isEmpty(ttUser.getPassword())){
            ttUser.setPassword(ttUser.getPassword());
        }
        sysUserService.update(ttUser, qw);
        return R.ok().data("row", ttUser);
    }

    /**
     * 意见反馈
     */
    @PostMapping("sendFeedback")
    public R sendFeedback(@RequestBody Feedback feedback){
        feedbackService.save(feedback);
        return R.ok();
    }


    /**
     * 查询添加 失物招领 和 寻物启事信息列表
     */
    @GetMapping("getLostFound")
    public R getLostFound(String type, String goodsType, String title){
        List<LostFound> list = lostFoundService.getLostFound(type, goodsType, title);
        return R.ok().data("list", list);
    }

    /**
     * 查询失物招领和寻物启事 信息详情
     * @param id
     * @return
     */
    @GetMapping("getLostFoundById/{id}")
    public R getLostFound(@PathVariable String id){
        LostFound lostFound = lostFoundService.getLostFoundById(id);
        return R.ok().data("row", lostFound);
    }

    /**
     * 发布失物招领和寻物启事
     */
    @PostMapping("sendLostFound")
    public R sendLostFound(@RequestBody LostFound lostFound){
        lostFound.setState("1");
        if("0".equals(lostFound.getIsMoney())){
            lostFound.setMoney(new BigDecimal("0"));
        }
        lostFoundService.save(lostFound);
        return R.ok().data("row", lostFound);
    }

    /**
     * 归还和认领功能
     */
    @PostMapping("LostFoundOrder")
    public R LostFoundOrder(@RequestBody LostFound lostFound){
        LostFound lostFoundNew = lostFoundService.getById(lostFound);
        lostFoundNew.setState("2");
        lostFoundNew.setGetUserId(lostFound.getGetUserId());

        lostFoundService.updateById(lostFoundNew);

        String type = lostFoundNew.getType();
        if("1".equals(type)){
            return R.ok("认领成功");
        }else{
            return R.ok("归还成功");
        }
    }


    /**
     * 查询首页轮播图
     */
    @GetMapping("getBanner")
    public R getBanner(){
        List<Banner> notices = bannerService.list(new QueryWrapper<Banner>().orderByAsc("sort"));
        List<String> notice = notices.stream().map(Banner::getImage).collect(Collectors.toList());
        return R.ok().data("list", notice);
    }

    /**
     * 查询首页公告
     */
    @GetMapping("getNotice")
    public R getNotice(){
        List<Notice> notices = noticeService.list(new QueryWrapper<Notice>().orderByAsc("sort"));
        List<String> notice = notices.stream().map(Notice::getContent).collect(Collectors.toList());
        return R.ok().data("list", notice);
    }


    /**
     *  查询首页推荐活动
     */
    @GetMapping("getActivity")
    public R getActivity(){
        List<Activity> list = activityService.list(new QueryWrapper<Activity>().eq("type","1").orderByDesc("create_time"));
        return R.ok().data("list", list);
    }

    /**
     * 通过分类id和标题查询校园活动列表
     * @param id 分类id
     * @title title 标题
     * @return
     */
    @GetMapping("getActivityListByClassifiedId")
    public R getActivityListByClassifiedId(String id, String title){
        QueryWrapper<Activity> qw = new QueryWrapper<>();
        if(!"-1".equals(id)){
            qw.eq("classified_id", id);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(title)){
            qw.like("title", title);
        }
        List<Activity> list = activityService.list(qw);
        return R.ok().data("list", list);
    }

    /**
     * 通过校园活动id查询详情
     * @param id 校园活动id
     * @return
     */
    @GetMapping("getActivityById/{id}/{userId}")
    public R getActivityById(@PathVariable String id, @PathVariable Long userId){
        Activity activity = activityService.getActivityById(id, userId);
        return R.ok().data("row", activity);
    }

    /**
     * 参与校园活动
     */
    @PostMapping("sendActivity/{id}/{userId}")
    public R joinCarSharing(@PathVariable String id, @PathVariable Long userId){
        ActivityInfo activityInfo = activityInfoService.getOne(
                new QueryWrapper<ActivityInfo>().eq("user_id", userId).eq("activity_id", id));

        if(activityInfo != null){
            return R.error("您已参与活动了");
        }

        // 判断是否活动是否已满员
        int count = activityInfoService.count(new QueryWrapper<ActivityInfo>().eq("activity_id", id));
        Activity activity = activityService.getById(id);

        if(count == activity.getSeat()){
            return R.error("活动已满，无法参与");
        }
        // 新增活动记录
        ActivityInfo activityInfoNew = new  ActivityInfo();
        activityInfoNew.setUserId(userId);
        activityInfoNew.setActivityId(id);
        activityInfoService.save(activityInfoNew);
        return R.ok("参加活动成功");
    }
    /**
     * 活动收藏 和 取消收藏
     */
    @PostMapping("activityCollect")
    public R activityCollect(@RequestBody ActivityCollect activityCollect){
        ActivityCollect activityCollectNew = activityCollectService.getOne(new QueryWrapper<ActivityCollect>()
                .eq("user_id", activityCollect.getUserId()).eq("activity_id", activityCollect.getActivityId()));
        if(activityCollectNew != null){
            activityCollectService.removeById(activityCollectNew.getId());
            return R.ok("取消收藏成功");
        }else{
            activityCollectService.save(activityCollect);
            return R.ok("收藏成功").data("data", activityCollect.getId());
        }
    }

    /**
     * 查询所有校园活动分类
     * @param
     * @return
     */
    @GetMapping("getClassifiedAll")
    public R getClassifiedAll(){
        List<ActivityClassified> list = activityClassifiedService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 我的聊天记录查询 展示在聊天列表
     * @param userId
     * @return
     */
    @GetMapping("getUserChatList/{userId}")
    public R getUserChatList(@PathVariable Long userId){
        List<UserChat> list =  userChatService.getUserChatList(userId);
        return R.ok().data("list", list);
    }

    /**
     * 查询聊天窗口聊天记录
     * @param sendUserId 发送人id
     * @param receiveUserId 接收人id
     * @return
     */
    @GetMapping("getUserChatBySendUserAndReceiveUser/{sendUserId}/{receiveUserId}")
    public R getUserChatBySendUserAndReceiveUser(@PathVariable Long sendUserId, @PathVariable Long receiveUserId){
        List<UserChat> list =  userChatService.getUserChatBySendUserAndReceiveUser(sendUserId, receiveUserId);
        return R.ok().data("list", list);
    }

    /**
     * 发布聊天消息
     */
    @PostMapping("sendUserChat")
    public R sendUserChat(@RequestBody UserChat userChat){
        userChatService.save(userChat);
        return R.ok();
    }

    /**
     * 论坛列表查询
     */
    @GetMapping("getConfessionList")
    public R getConfessionList(Long userId, String content){
        List<Confession> list = confessionService.getConfessionList(userId, content);
        return R.ok().data("list", list);
    }

    /**
     * 发布论坛
     */
    @PostMapping("sendConfession")
    public R sendConfession(@RequestBody Confession confession){
        confessionService.save(confession);
        return R.ok();
    }

    /**
     * 通过论坛id查询论坛详情
     */
    @GetMapping("getConfessionById/{id}")
    public R getConfessionById(@PathVariable String id){
        Map<String, Object> map = new HashMap<>();
        // 详情信息
        Confession confession = confessionService.getConfessionById(id);
        map.put("confession", confession);
        // 点赞量
        int praise = confessionPraiseService.count(new QueryWrapper<ConfessionPraise>().eq("confession_id", id));
        // 评论量
        int comment = confessionCommentService.count(new QueryWrapper<ConfessionComment>().eq("confession_id", id));
        map.put("praise", praise);
        map.put("comment", comment);
        return R.ok().data("row", map);
    }

    /**
     * 通过论坛id查询论坛详情评论内容
     */
    @GetMapping("getConfessionComment/{id}")
    public R getConfessionComment(@PathVariable String id){
        List<ConfessionComment> list = confessionCommentService.getComment(id);
        return R.ok().data("list", list);
    }


    /**
     * 发布论坛评论
     */
    @PostMapping("sendConfessionComment")
    public R sendComment(@RequestBody ConfessionComment confessionComment) {
        confessionCommentService.save(confessionComment);
        return R.ok();
    }

    /**
     * 兼职点赞和取消点赞
     */
    @PostMapping("jobPraise")
    public R jobPraise(@RequestBody JobPraise jobPraise) {
        JobPraise jobPraiseNew = jobPraiseService.getOne(new QueryWrapper<JobPraise>()
                .eq("user_id", jobPraise.getUserId()).eq("job_id", jobPraise.getJobId()));
        if(jobPraiseNew != null){
            jobPraiseService.removeById(jobPraiseNew.getId());
            return R.ok("取消点赞成功");
        }else{
            jobPraiseService.save(jobPraise);
            return R.ok("点赞成功").data("data", jobPraise.getId());
        }
    }

    /**
     * 兼职列表查询
     */
    @GetMapping("getJobList")
    public R getJobList(Long userId, String content){
        List<Job> list = jobService.getConfessionList(userId, content);
        return R.ok().data("list", list);
    }

    /**
     * 发布兼职
     */
    @PostMapping("sendJob")
    public R sendJob(@RequestBody Job job){
        jobService.save(job);
        return R.ok();
    }



    /**
     * 论坛点赞和取消点赞
     */
    @PostMapping("confessionPraise")
    public R sendComment(@RequestBody ConfessionPraise confessionPraise) {
        ConfessionPraise confessionPraiseNew = confessionPraiseService.getOne(new QueryWrapper<ConfessionPraise>()
                .eq("user_id", confessionPraise.getUserId()).eq("confession_id", confessionPraise.getConfessionId()));
        if(confessionPraiseNew != null){
            confessionPraiseService.removeById(confessionPraiseNew.getId());
            return R.ok("取消点赞成功");
        }else{
            confessionPraiseService.save(confessionPraise);
            return R.ok("点赞成功").data("data", confessionPraise.getId());
        }
    }

    /**
     * 查询二手商品 信息详情
     * @param id
     * @return
     */
    @GetMapping("getGoodsById/{id}/{userId}")
    public R getGoodsById(@PathVariable String id,@PathVariable String userId){
        Goods goods = goodsService.getGoodsById(id, userId);
        return R.ok().data("row", goods);
    }

    /**
     * 查询二手商品信息列表
     */
    @GetMapping("getGoods")
    public R getGoods(String goodsType, String title){
        List<Goods> list = goodsService.getGoods(goodsType, title);
        return R.ok().data("list", list);
    }

    /**
     * 发布二手商品
     */
    @PostMapping("sendGoods")
    public R sendGoods(@RequestBody Goods goods){
        goods.setState("1");
        goodsService.save(goods);
        return R.ok().data("row", goods);
    }

    /**
     * 购买二手商品
     */
    @PostMapping("goodsOrder")
    public R goodsOrder(@RequestBody Goods goods){
        Goods goodsNew = goodsService.getById(goods.getId());
        goodsNew.setState("2");
        goodsNew.setGetUserId(goods.getGetUserId());
        goodsService.updateById(goodsNew);
        return R.ok("购买成功");
    }

    /**
     * 商品收藏 和 商品收藏
     */
    @PostMapping("goodsCollect")
    public R goodsCollect(@RequestBody GoodsCollect goodsCollect){
        GoodsCollect goodsCollectNew = goodsCollectService.getOne(new QueryWrapper<GoodsCollect>()
                .eq("user_id", goodsCollect.getUserId()).eq("goods_id", goodsCollect.getGoodsId()));
        if(goodsCollectNew != null){
            goodsCollectService.removeById(goodsCollectNew.getId());
            return R.ok("取消收藏成功");
        }else{
            goodsCollectService.save(goodsCollect);
            return R.ok("收藏成功").data("data", goodsCollect.getId());
        }
    }


    /**
     * 我的页面修改个人信息
     */
    @PutMapping("updateUserinfo")
    public R updateUserinfo(@RequestBody SysUser user){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("id", user.getId());

        sysUserService.update(user, qw);
        SysUser sysUser = sysUserService.getById(user.getId());
        return R.ok().data("row",sysUser);
    }

    /**
     * 我的页面 查询我的收藏活动
     */
    @GetMapping("getActivityCollectByUserId/{userId}")
    public R getActivityCollectByUserId(@PathVariable Long userId){

        List<Activity> list = activityCollectService.getActivityCollectByUserId(userId);
        return R.ok().data("list",list);
    }

    /**
     * 我的页面 查询我的意见反馈
     */
    @GetMapping("getFeedbackByUserId/{userId}")
    public R getFeedbackByUserId(@PathVariable Long userId){
        List<Feedback> list = feedbackService.list(new QueryWrapper<Feedback>().eq("user_id", userId));
        return R.ok().data("list",list);
    }

    /**
     * 删除表白墙
     */
    @DeleteMapping("delConfession/{id}")
    public R delConfession(@PathVariable String id){
        confessionService.removeById(id);
        return R.ok();

    }
}

package com.cn.campus.controller.admin;


import com.cn.campus.entity.Feedback;
import com.cn.campus.service.FeedbackService;
import com.cn.campus.utils.page.PageResult;
import com.cn.campus.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 问题反馈表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {


    @Autowired
    private FeedbackService service;

    /**
     * 分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody Feedback data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }


    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        service.removeById(id);
        return R.ok();
    }
}


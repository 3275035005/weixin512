package com.cn.campus.controller.admin;


import com.cn.campus.entity.Activity;
import com.cn.campus.service.ActivityService;
import com.cn.campus.utils.page.PageResult;
import com.cn.campus.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 校园活动表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {


    @Autowired
    private ActivityService service;

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
            @RequestBody Activity data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Activity data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Activity data){
        service.save(data);
        return R.ok();
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


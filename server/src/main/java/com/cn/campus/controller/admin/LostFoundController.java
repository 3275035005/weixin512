package com.cn.campus.controller.admin;


import com.cn.campus.entity.LostFound;
import com.cn.campus.service.LostFoundService;
import com.cn.campus.utils.page.PageResult;
import com.cn.campus.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 失物招领和寻物启事 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/lost-found")
public class LostFoundController {


    @Autowired
    private LostFoundService service;

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
            @RequestBody LostFound data){
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


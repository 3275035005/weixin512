package com.cn.campus.controller.admin;


import com.cn.campus.entity.Banner;
import com.cn.campus.service.BannerService;
import com.cn.campus.utils.page.PageResult;
import com.cn.campus.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService service;

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
            @RequestBody Banner data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Banner data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Banner data){
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


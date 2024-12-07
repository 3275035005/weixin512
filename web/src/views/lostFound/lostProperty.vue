<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="物品标题">
        <el-input v-model="dataVo.title" placeholder="请输入物品标题"/>
      </el-form-item>
      <el-form-item label="发布用户">
        <el-select
          v-model="dataVo.userId"
          placeholder="请选择发布用户"
        >
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select
          v-model="dataVo.state" placeholder="请选择发布用户">
          <el-option label="认领中" value="1"/>
          <el-option label="已认领" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="物品类型">
        <el-select
          v-model="dataVo.goodsType" placeholder="请选择物品类型">
          <el-option label="电子产品" value="1"/>
          <el-option label="生活用品" value="2"/>
          <el-option label="学习用品" value="3"/>
          <el-option label="其它" value="4"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table

      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column align="center" label="物品图片">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.imageLists[0]"
            :preview-src-list="scope.row.imageLists">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="物品标题" />
      <el-table-column prop="content" label="物品内容" />
      <el-table-column prop="location" label="位置">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 1" type="primary">本地代管</el-tag>
          <el-tag v-if="scope.row.type == 2" type="primary">遗失处</el-tag>
          <el-tag v-if="scope.row.type == 3" type="primary">其它</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="goodsType" label="物品类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.goodsType == 1" type="primary">电子产品</el-tag>
          <el-tag v-if="scope.row.goodsType == 2" type="primary">生活用品</el-tag>
          <el-tag v-if="scope.row.goodsType == 3" type="primary">学习用品</el-tag>
          <el-tag v-if="scope.row.goodsType == 4" type="primary">其它</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="认领状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state == 1" type="primary">认领中</el-tag>
          <el-tag v-if="scope.row.state == 2" type="primary">已认领</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="hideName" label="是否匿名">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hideName == 0" type="primary">实名</el-tag>
          <el-tag v-if="scope.row.hideName == 1" type="primary">匿名</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="发布时间" />
      <el-table-column label="操作" align="center" width="250px">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import { pageQuery, deleteById } from '@/api/lostFound'
import { getUserAll } from '@/api/user'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      formData: {},
      dialogVisible: false,
      name: '',
      title: '',
      userList: [] // 存储学生列表
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.init()
  },
  methods: { // 创建具体的方法, 调用teacher.js 定义的方法
    init() {
      getUserAll().then(res => {
        this.userList = res.data.row
      })
    },
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      this.dataVo.type = '1'
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            }
          })
      })
    },

    // 清空按按钮执行的方法
    resetData() {
      this.page = 1// 当前页
      this.limit = 10// 每页记录数
      // 第一步清空条件数据
      this.dataVo = {}
      this.getList()
    }
  }
}
</script>

<template>
  <div>
    <div align="right" style="padding-bottom: 10px">
      <el-button type="primary" @click="exportExcel">导出Excel</el-button>
      <el-upload
        style="float: right;padding-left: 10px"
        class="upload-demo"
        :action="importUrl"
        accept="xls,xlsx"
        :headers="header"
        multiple
        :on-success="handleSuccess"
        :on-error="handleError"
        :on-progress="handleProgress"
        :auto-upload=true>
        <el-button size="small" type="primary">导入Excel</el-button>
      </el-upload>
    </div>
    <div>
      <el-table
        :data="tableData"
        stripe
        border
        @selection-change="handleSelectionChange"
        ref="multipleTable"
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          type="selection"
          align="center">
        </el-table-column>
        <el-table-column v-if="false">
          <template slot-scope="scope">
            {{scope.row.userId}}
          </template>
        </el-table-column>
        <el-table-column
          type="index"
          label="序号"
          align="center">
        </el-table-column>
        <el-table-column
          label="用户名"
          align="center">
          <template slot-scope="scope">
            {{scope.row.userName}}
          </template>
        </el-table-column>
        <el-table-column
          label="昵称"
          align="center">
          <template slot-scope="scope">
            {{scope.row.nickName}}
          </template>
        </el-table-column>
        <el-table-column
          label="手机号"
          align="center">
          <template slot-scope="scope">
            {{scope.row.mobile}}
          </template>
        </el-table-column>
        <el-table-column
          label="邮箱"
          align="center">
          <template slot-scope="scope">
            {{scope.row.email}}
          </template>
        </el-table-column>
        <el-table-column
          label="状态"
          align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status === '1'"
              disabled>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          label="头像"
          align="center">
          <template slot-scope="scope">
            {{scope.row.head}}
          </template>
        </el-table-column>
        <el-table-column
          label="创建时间"
          align="center">
          <template slot-scope="scope">
            {{scope.row.createTime}}
          </template>
        </el-table-column>
        <el-table-column
          label="最后一次修改信息时间"
          align="center">
          <template slot-scope="scope">
            {{scope.row.updateTime}}
          </template>
        </el-table-column>
        <el-table-column label="操作"
        align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="1"
        :page-sizes="pageSizes"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import qs from "qs";

  export default {
    name: "User",
    data() {
      return {
        tableData: [],
        pageSizes: [10, 20, 50],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        idList: [],
        fileList: [],
        importUrl: this.$axios.defaults.baseURL + "/user/import",
        header: {
          token: window.sessionStorage.getItem('access_token')
        },
      }
    },
    methods: {
      getUserList: function () {
        const url = "/user/list";
        let params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        this.$axios.get(url, {params: params}).then(res => {
            if (res.data.code === '200') {
              let list = res.data.data.list;
              this.tableData = [];
              this.total = res.data.data.total;
              for (let i = 0; i < list.length; i++) {
                this.tableData.push(list[i]);
              }
            }
          }
        ).catch(err => {
            this.$message.error({
              message: '网络不给力,请稍后再试'
            })
          }
        )
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.getUserList();
      },
      handleCurrentChange(val) {
        this.pageNum = val;
        this.getUserList();
      },
      // handlePrev() {
      //   // this.pageNum = this.pageNum - 1;
      //   this.getUserList();
      // },
      // handleNext() {
      //   // this.pageNum = this.pageNum + 1;
      //   this.getUserList();
      // },
      exportExcel() {
        if (this.idList.length === 0) {
          this.$message.error({
            message: '请选择有效的数据'
          })
          return;
        }

        let params = {
          list: this.idList
        }
        const url = "/user/export";
        let fileDownload = require("js-file-download");
        this.$axios.post(url, qs.stringify(params), {responseType: "blob"}).then(res => {
            let fileName = decodeURIComponent(res.headers['content-disposition'].split("=")[1]);
            fileDownload(res.data, fileName);
          }
        ).catch(err => {
            this.$message.error({
              message: '网络不给力,请稍后再试'
            })
          }
        )
      },
      handleSuccess(response, file, fileList) {
        if (response.code === '200') {
          this.$message.info(response.message, "密码初始化为mht");
        } else {
          this.$message.error(response.message);
        }
        this.getUserList();
        this.$store.state.isLoading = false;
      },
      handleError(err, file, fileList) {
        this.$message.error(`${file.name}上传异常`);
        this.$store.state.isLoading = false;
      },
      handleProgress(event, file, fileList) {
        this.$store.state.isLoading = true;
      },
      handleSelectionChange(val) {
        this.idList = [];
        for (let i = 0; i < val.length; i++) {
          this.idList.push(val[i].userId);
        }
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }

    },
    mounted: function () {
      this.getUserList();
    }
  }
</script>

<style scoped>

</style>

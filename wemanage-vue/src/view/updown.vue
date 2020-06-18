<template>
  <div>
    <div>
      <el-upload
        class="upload-demo"
        :action="uploadUrl"
        accept="image/jpg,image/png"
        :headers="header"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :limit="10"
        :on-exceed="handleExceed"
        :on-success="handleSuccess"
        :on-error="handleError"
        :on-progress="handleProgress"
        :auto-upload=true
        :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5M</div>
      </el-upload>
    </div>
    <h1 align="center">已上传列表</h1>
    <div>
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          label="文件名称（点击下载）"
          align="center">
          <template slot-scope="scope">
            <el-link @click="download(scope.row.filename)">{{scope.row.filename}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          label="缩略图（点击预览）"
          align="center">
          <template slot-scope="scope">
            <el-image :src="'data:image/png;base64,'+scope.row.imgBase64"
                      style="width: 100px;height: 100px"
                      :preview-src-list="srcList">
            </el-image>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" icon="el-icon-plus" @click="getFileList">刷新</el-button>
    </div>
  </div>
</template>

<script>
  import qs from "qs"

  export default {
    name: "Updown",
    data() {
      return {
        fileList: [],
        uploadUrl: this.$axios.defaults.baseURL + "/file/upload",
        header: {
          token: window.sessionStorage.getItem('access_token')
        },
        tableData: [],
        srcList: []
      };
    },
    methods: {
      handleRemove(file, fileList) {
        console.log("handleRemove");
      },
      handlePreview(file) {
        console.log("handlePreview");
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制展示 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      handleSuccess(response, file, fileList) {
        if (response.code === '200') {
          this.getFileList()
          this.$message.info(response.message);
        } else {
          this.$message.error(response.message);
        }
        this.fileList = [];
        this.$store.state.isLoading = false;
      },
      handleError(err, file, fileList) {
        this.$message.error(`${file.name}上传异常`);
        this.$store.state.isLoading = false;
      },
      handleProgress(event, file, fileList) {
        this.$store.state.isLoading = true;
      },
      getFileList() {
        const url = "/file/fileListShow";
        this.$axios.get(url).then(res => {
            if (res.data.code === '200') {
              let filenameList = res.data.data.list;
              this.tableData = [];
              this.srcList = [];
              for (let i = 0; i < filenameList.length; i++) {
                this.tableData.push(filenameList[i]);
                this.srcList.push("data:image/png;base64," + filenameList[i].imgBase64)
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
      download(filename) {
        let params = {
          fileName: filename
        }
        const url = "/file/download";
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
      }
    },
    mounted: function () {
      this.getFileList()
    }
  }
</script>

<style scoped>

</style>

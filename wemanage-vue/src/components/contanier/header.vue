<template>
  <div class="header">
    欢迎您，{{nickName}}
    <el-button type="text" icon="el-icon-switch-button" @click="logout">退出登录</el-button>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        nickName: window.sessionStorage.getItem("access_user_nick_name")
      }
    },
    methods: {
      logout: function () {
        this.$confirm('是否退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const url = "/logout";
          this.$axios.post(url).then(res => {
              if (res.data.code === '200') {
                window.sessionStorage.clear();

                this.$message({
                  type: 'success',
                  message: '即将回到登录页面!'
                });

                this.$router.push({
                  path: "/login"
                })
              }
            }
          ).catch(err => {
              this.$message.error({
                message: '网络不给力,请稍后再试'
              })
            }
          )

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消退出'
          });
        });

      }
    }
  }
</script>

<style scoped>
  .header {
    text-align: right;
    border-bottom: 1px solid #3a8ee6;
  }

</style>

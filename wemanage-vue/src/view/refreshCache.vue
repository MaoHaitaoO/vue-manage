<template>
  <div align="center">
    <el-button type="primary" icon="el-icon-edit" @click="refreshCache">刷新缓存</el-button>
  </div>
</template>

<script>
  export default {
    name: "RefreshCache",
    methods: {
      refreshCache: function () {
        const url = "/cache/refresh";
        this.$axios.get(url).then(res => {
            if (res.data.code === '200') {
              this.$message.info({
                message: '刷新缓存成功'
              })
            }
          }
        ).catch(err => {
            this.$message.error({
              message: '网络不给力,请稍后再试'
            })
          }
        )
      }
    }
  }
</script>

<style scoped>

</style>

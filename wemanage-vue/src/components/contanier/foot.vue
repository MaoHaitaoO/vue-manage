<template>
  <div class="footer">
    github link：
    <el-link target="_blank" @click="tip">
      欢迎star☆
    </el-link>
  </div>
</template>

<script>
  export default {
    name: "Foot",
    methods: {
      tip() {
        this.$notify({
          title: 'readme.md Markdown 编写中...',
          message: '(Restful)前端:Vue+Vuex+axios+ElementUI...\n 后端:SpringCloud/SpringBoot+SpringSecurity...'
        });
      }
    }
  }
</script>

<style scoped>
  .footer {
    text-align: center;
  }
</style>

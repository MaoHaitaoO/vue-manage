<template>
  <div>
    <!--    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
    <!--      <el-radio-button :label="false">展开</el-radio-button>-->
    <!--      <el-radio-button :label="true">收起</el-radio-button>-->
    <!--    </el-radio-group>-->
    <el-menu router
             :default-active="this.$route.path"
             class="el-menu-vertical-demo"
             @open="handleOpen"
             :unique-opened="uniqueOpened"
             @close="handleClose"
             :collapse="isCollapse">
      <aside-dynamic :router-list="routerList"></aside-dynamic>
    </el-menu>
  </div>
</template>

<script>
  import AsideDynamic from "./asideDynamic";

  export default {
    name: "Aside",
    components: {AsideDynamic},
    data() {
      return {
        isCollapse: false,
        uniqueOpened: true,
        routerList: []
      };
    },
    methods: {
      handleOpen(key, keyPath) {
      },
      handleClose(key, keyPath) {
      },
      queryView() {
        const url = "/permission/view";
        this.$axios.get(url).then(res => {
            if (res.data.code === '200') {
              this.routerList = res.data.data.list
            }
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
      this.queryView()
    }
  }
</script>

<style scoped>

</style>

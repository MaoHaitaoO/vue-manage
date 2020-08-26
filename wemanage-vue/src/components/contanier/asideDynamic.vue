<template>
  <div>
    <template v-for="(router,key,index) in routerList">

      <el-submenu :index=router.permissionId.toString() v-if='router.children.length > 0'>
        <template slot="title">
          <i class="el-icon-location"></i>
          <span slot="title">{{router.permissionName}}</span>
        </template>
        <aside-dynamic :router-list="router.children"></aside-dynamic>
      </el-submenu>

      <el-menu-item v-else :index=router.routerUrl>{{router.permissionName}}</el-menu-item>
    </template>
  </div>
</template>

<script>
  export default {
    name: "AsideDynamic",
    props: {
      routerList: {
        type: Array,
        required: true
      }
    },
    methods: {},
  }
</script>

<style scoped>

</style>

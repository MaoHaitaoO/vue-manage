<template>
  <div>
    <el-select v-model="value" placeholder="请选择角色" @change="queryPermissionList(value)">
      <el-option
        v-for="item in options"
        :key="item.roleId"
        :label="item.roleName"
        :value="item.roleId">
      </el-option>
    </el-select>

    <el-tree
      ref="tree"
      :data="data"
      @check-change="handleCheckChange"
      show-checkbox
      highlight-current
      node-key="permissionId"
      default-expand-all
      :default-checked-keys="defaultCheckKey"
      :props="defaultProps">
    </el-tree>
  </div>
</template>

<script>
  export default {
    name: "Permission",
    data() {
      return {
        options: [],
        value: '',
        data: [],
        defaultCheckKey: [],
        defaultProps: {
          children: 'children',
          label: 'permissionName'
        }
      }
    },
    methods: {

      queryPermissionList: function (value) {
        if (!value) {
          return;
        }
        const url = "/permission/list";
        this.$axios.get(url, {
          params: {
            roleId: value
          }
        }).then(res => {
            if (res.data.code === '200') {
              let list = res.data.data.list;
              this.data = list;
              this.defaultCheckKey = [];
              this.getDefaultCheckKey(list);
            }
          }
        ).catch(err => {
            this.$message.error({
              message: '网络不给力,请稍后再试'
            })
          }
        )
      },
      queryRoleList: function () {
        const url = "/role/list";
        this.$axios.get(url).then(res => {
            if (res.data.code === '200') {
              this.options = res.data.data.list
            }
          }
        ).catch(err => {
            this.$message.error({
              message: '网络不给力,请稍后再试'
            })
          }
        )
      },
      getDefaultCheckKey: function (list) {
        for (let i = 0; i < list.length; i++) {
          if (list[i].isHasPermission === '1') {
            this.defaultCheckKey.push(list[i].permissionId);
          }
          if (list[i].children) {
            this.getDefaultCheckKey(list[i].children);
          }
        }
      },
      handleCheckChange: function (data, isChecked, childrenHasChecked) {
        console.log(data, isChecked, childrenHasChecked);
        // 执行权限修改（isChecked = false 删除）（isChecked = true 新增）
      }
    },
    mounted: function () {
      this.queryRoleList()
    }
  }
</script>

<style scoped>

</style>

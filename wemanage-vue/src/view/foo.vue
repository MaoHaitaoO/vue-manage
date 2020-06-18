<template>
  <div class="foo">
    <h1>Foo</h1>
    <el-button type="primary" @click="test()">Vux全局loading加载测试</el-button>
  </div>
</template>

<script>
  export default {
    name: 'Foo',
    methods: {
      test() {
        const url = "/t/test";

        this.$axios.get(url).then(res => {
            if (res.data.code === '200') {
              alert("收到接口返回")
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


</style>

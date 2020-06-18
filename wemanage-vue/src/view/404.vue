<template>
  <div>
    <el-image :src="notFondImg" @click="tip" alt="按钮是假的"></el-image>
  </div>
</template>

<script>
  import notFondImg from '../assets/404.gif'

  export default {
    name: 'NotFond',
    data() {
      return {
        notFondImg: notFondImg
      }
    },
    methods: {
      tip: function () {
        this.$notify({
          title: '看不出来这是假的？',
          message: '建议去看眼科大夫'
        });
      }
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-main>
          <Main></Main>
        </el-main>
        <el-footer>
          <Foot></Foot>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
  import Foot from "../components/contanier/foot";
  import Main from "../components/contanier/main";
  import Header from "../components/contanier/header";
  import Aside from "../components/contanier/aside";

  export default {
    name: "Contanier",
    components: {Aside, Header, Main, Foot}
  }
</script>

<style scoped>
  /*.el-header, .el-footer {*/
  /*  background-color: #B3C0D1;*/
  /*  color: #333;*/
  /*  text-align: center;*/
  /*  line-height: 60px;*/
  /*}*/

  /*.el-aside {*/
  /*  background-color: #D3DCE6;*/
  /*  color: #333;*/
  /*  text-align: center;*/
  /*  line-height: 200px;*/
  /*}*/

  /*.el-main {*/
  /*  background-color: #E9EEF3;*/
  /*  color: #333;*/
  /*  text-align: center;*/
  /*  line-height: 160px;*/
  /*}*/

  /*body > .el-container {*/
  /*  margin-bottom: 40px;*/
  /*}*/

  /*.el-container:nth-child(5) .el-aside,*/
  /*.el-container:nth-child(6) .el-aside {*/
  /*  line-height: 260px;*/
  /*}*/

  /*.el-container:nth-child(7) .el-aside {*/
  /*  line-height: 320px;*/
  /*}*/
</style>


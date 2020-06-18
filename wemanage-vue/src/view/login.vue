<template>
  <div v-loading="this.$store.state.isLoading"
       element-loading-text="拼命加载中"
       element-loading-spinner="el-icon-loading"
       element-loading-background="rgba(0, 0, 0, 0.8)"
       class="background">
    <div class="login_form" style="width: 500px">
      <el-form
        :model="form" :rules="loginRules" ref="form"
        label-position="left"
        label-width="80px">
        <h1 style="text-align: center;color: white">We Manage</h1>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名" @keyup.enter.native="onSubmit('form')"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password" placeholder="密码"
                    @keyup.enter.native="onSubmit('form')"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verificationCode">
          <el-input style="width: 200px" v-model="form.verificationCode" placeholder="验证码" maxlength="4"
                    @keyup.enter.native="onSubmit('form')"></el-input>
          <img :src="imageBase64" style="float: right;width: 200px;height: 40px" @click="getVerificationCode()">
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <foot></foot>
  </div>
</template>

<script>
  import qs from "qs"
  import publickey from "../static/publickey"
  import Foot from "../components/contanier/foot";

  export default {
    name: 'Login',
    components: {Foot},
    data() {
      return {
        form: {
          username: '',
          password: '',
          verificationCode: '',
        },
        imageBase64: '',
        loginRules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          verificationCode: [
            {required: true, message: '请输入验证码', trigger: 'blur'},
            {min: 4, max: 4, message: '不符合要求', trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let params = {
              username: this.form.username,
              password: this.$RSAEncrypt(publickey.data().login_public_key, this.form.password),
              verificationCode: this.form.verificationCode
            }

            const url = "/login";
            this.$axios.post(url, qs.stringify(params)).then(res => {
                if (res.data.code === '200') {
                  window.sessionStorage.setItem("access_token", res.data.data.token);
                  window.sessionStorage.setItem("access_user_nick_name", res.data.data.nickName);

                  this.$router.push({
                    path: "/"
                  })
                }
              }
            ).catch(err => {
                this.$message.error({
                  message: '网络不给力,请稍后再试'
                })
              }
            ).finally(
              () => {
                this.$refs[formName].resetFields();
              }
            )
          } else {
            return false
          }
        });
      },
      getVerificationCode() {
        const url = "/getVerificationCode?d=" + Math.random() + new Date().getMilliseconds();
        this.$axios.get(url).then(res => {
            this.imageBase64 = 'data:image/png;base64,' + res.data.data.imgBase64
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
      this.getVerificationCode()
    }
  }

</script>

<style scoped>
  .background {
    background-image: url(../assets/login_back_ground.png);
    height: 100%;
    width: 100%;
    display: block;
    position: fixed;
    background-size: cover;
    margin: 0;
    padding: 0;
    border: 0;
  }

  .login_form {

  }
</style>

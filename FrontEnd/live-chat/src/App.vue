<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // Exemplo: Verificar se o usuário está logado
    const token = localStorage.getItem('token');
    if (!token) {
      // Se não houver token, redireciona para login
      this.$router.push('/login');
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
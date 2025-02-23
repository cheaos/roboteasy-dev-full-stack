<template>
  <div class="container">
    <h2>Login</h2>
    <input v-model="form.username" type="text" placeholder="Usuário" />
    <input v-model="form.password" type="password" placeholder="Senha" />
    <button @click="login">Entrar</button>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";

export default {
  setup() {
    const form = ref({
      username: "",
      password: "",
    });

    const error = ref("");

    const login = async () => {
      try {        
        if (!form.value.username || !form.value.password) {
          error.value = "⚠️ Preencha todos os campos!";
          return;
        }
        const response = await axios.post(
          "http://localhost:8081/user/login",{
            username : form.value.username,
            password : form.value.password,
          }
        );

        // Verifica se a API retornou o token corretamente
        if (response.data && response.data.token) {
          localStorage.setItem("token", response.data.token);
          alert("✅ Login realizado com sucesso!");
          window.location.href = "/menu"; 
        } else {
          throw new Error("Resposta inválida do servidor");
        }
      } catch (err) {
        error.value = "🚫 Usuário ou senha inválidos!";
        console.error("Erro no login:", err);
      }
    };

    return {
      form,
      error,
      login,
    };
  },
};
</script>

<style>
.container {
  width: 300px;
  margin: 100px auto;
  padding: 20px;
  text-align: center;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}
input {
  width: 100%;
  padding: 10px;
  margin: 5px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
}
button {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #2980b9;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>

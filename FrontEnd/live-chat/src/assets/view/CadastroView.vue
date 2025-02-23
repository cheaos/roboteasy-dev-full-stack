<script setup>
import { ref } from "vue";
import axios from "axios";

const form = ref({
  dsnome: "",
  cdfuncionario: "",
  cdcracha: "",
  username: "",
  password: ""
});

const mensagem = ref("");
const carregando = ref(false);

const cadastrarUsuario = async () => {
  mensagem.value = "";
  
  if (!form.value.dsnome || !form.value.cdfuncionario || !form.value.cdcracha || !form.value.username || !form.value.password) {
    mensagem.value = "Todos os campos são obrigatórios!";
    return;
  }

  try {
    carregando.value = true;

    await axios.post("http://localhost:8081/user/cadastro", form.value);

    mensagem.value = "Usuário cadastrado com sucesso!";
    form.value = { dsnome: "", cdfuncionario: "", cdcracha: "", username: "", password: "" }; // Reset do formulário
  } catch (error) {
    mensagem.value = "Erro ao cadastrar usuário!";
    console.error("Erro na requisição:", error);
  } finally {
    carregando.value = false;
  }
};
</script>
<template>
    <div class="container">
      <div class="bg-white shadow-lg rounded-xl p-8 max-w-md w-full">
        <h2 class="text-3xl font-semibold text-center text-gray-800 mb-6">Cadastro de Usuário</h2>
  
        <div v-if="mensagem" class="mb-4 p-3 text-center text-white bg-red-500 rounded-md">
          {{ mensagem }}
        </div>
  
        <div class="space-y-4">
          <div>
            <label class="block text-gray-700 font-medium">Nome</label>
            <input v-model="form.dsnome" type="text" 
              class="w-full p-3 border rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
          </div>
  
          <div>
            <label class="block text-gray-700 font-medium">Código Funcionário</label>
            <input v-model="form.cdfuncionario" type="number" min="0" 
              class="w-full p-3 border rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
          </div>
  
          <div>
            <label class="block text-gray-700 font-medium">Código do Cracha</label>
            <input v-model="form.cdcracha" type="number" min="0"
              class="w-full p-3 border rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
          </div>
  
          <div>
            <label class="block text-gray-700 font-medium">Usuário</label>
            <input v-model="form.username" type="text" 
              class="w-full p-3 border rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
          </div>
  
          <div>
            <label class="block text-gray-700 font-medium">Senha</label>
            <input v-model="form.password" type="password" 
              class="w-full p-3 border rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
          </div>
        </div>
  
        <button 
          @click="cadastrarUsuario" 
          :disabled="carregando" 
          class="w-full mt-6 p-3 text-white font-bold rounded-lg bg-gradient-to-r from-blue-500 to-blue-700 hover:from-blue-600 hover:to-blue-800 transition-all duration-300 shadow-md"
        >
          {{ carregando ? "Cadastrando..." : "Cadastrar" }}
        </button>
      </div>
    </div>
  </template>
  
<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const activeUsers = ref([]);
const activeNumbers = ref(0);
const historyMessages = ref([]);
const dtMensagem = ref("");
const isHistoryVisible = ref(false);
let interval = null;
const router = useRouter();


const checkToken = () => {
  const token = localStorage.getItem("token");
  if (!token) {
    router.push("/login");
  }
};

axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("token"); 
      router.push("/login"); 
    }
    return Promise.reject(error);
  }
);

const fetchActiveUsers = async () => {
  try {
    const [response, response1] = await Promise.all([
      axios.get("http://localhost:8081/active-users"),
      axios.get("http://localhost:8081/active-listusers")
    ]);
    activeUsers.value = response.data;
    activeNumbers.value = response1.data;
  } catch (err) {
    console.error("Erro ao buscar usuários online:", err);
  }
};

const fetchHistory = async () => {
  try {
    if (!dtMensagem.value) {
      alert("Por favor, insira uma data válida.");
      return;
    }

    const response = await axios.get("http://localhost:8081/user/buscaMensagem",{
     params: {dtmensagem: dtMensagem.value}
          }
        );    
    historyMessages.value = response.data;
  } catch (err) {
    console.error("Erro ao buscar mensagens:", err);
  }
};

onMounted(() => {
  checkToken();
  fetchActiveUsers();
  interval = setInterval(fetchActiveUsers, 5000);
});

onUnmounted(() => {
  clearInterval(interval);
});
</script>

<template>
  <nav class="container">
    <p class="online-users">👥 Usuários online: {{ activeNumbers }}</p>
    <ul class="user-list">
      <li v-for="user in activeUsers" :key="user" class="user-item"> {{ user }}</li>
    </ul>

    <RouterLink to="/cadastro" class="menu-button">Cadastrar</RouterLink>
    <RouterLink to="/chat" class="menu-button">Chat</RouterLink>
    <button @click="isHistoryVisible = !isHistoryVisible" class="menu-button">Históricos</button>
  </nav>

  <div v-if="isHistoryVisible" class="history-container">
    <input v-model="dtMensagem" type="date" class="input-date" />
    <button @click="fetchHistory" class="menu-button">Buscar Histórico</button>
    
    <div v-if="historyMessages.length > 0" class="history-grid">
      <table>
        <thead>
          <tr>
            <th>Usuário</th>          
            <th>Data</th>
            <th>Mensagem</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(msg, index) in historyMessages" :key="index">
            <td>{{ msg.username }}</td>            
            <td>{{ msg.dtmensagem }}</td>
            <td>{{ msg.dsmensagem }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 15px;
  min-height: 50vh;  
}

.menu-button {
  background: #1877f2;
  color: white;
  padding: 10px 20px;
  border-radius: 30px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s ease-in-out;
  text-align: center;
  width: 220px;
  font-size: 18px;
  border: none;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
}

.menu-button:hover {
  background: #166fe5; 
  transform: translateY(-3px); 
}

.menu-button:focus {
  outline: none; 
}

.history-container {
  position: fixed;
  top: 50px;
  right: 50px;
  width: 600px;
  height: 70%;
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  overflow-y: auto;
  z-index: 10;
}

.input-date {
  padding: 10px;
  width: 100%;
  margin-bottom: 15px;
  border-radius: 5px;
  border: 1px solid #ddd;
  font-size: 16px;
}

.history-grid table {
  width: 100%;
  margin-top: 10px;
  border-collapse: collapse;
}

.history-grid th,
.history-grid td {
  padding: 15px;
  border: 1px solid #ddd;
  font-size: 16px;
}

.history-grid th {
  background-color: #f4f4f4;
}

.history-grid td {
  text-align: center;
}
</style>

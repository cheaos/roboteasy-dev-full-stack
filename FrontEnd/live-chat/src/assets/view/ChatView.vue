<template> 
  <div class="chat-container">
    <div class="chat-header">
      <h2>Live Chat</h2>
    </div>

    <div class="messages-area">
      <ul>
        <li v-for="(msg, index) in messages" :key="index" class="message-item">
          <div class="message-bubble" :class="msg.sender === 'me' ? 'sent' : 'received'">
            {{ msg.text }}
          </div>
        </li>
      </ul>
    </div>

    <div class="input-area">
      <input
        v-model="message"
        type="text"
        class="input-message"
        placeholder="Digite sua mensagem"
        @keydown.enter="sendMessage"
      />
      <button class="send-button" @click="sendMessage">Enviar</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      socket: null,
      messages: [],
      message: "",
      username: "",
    };
  },
  mounted() {
    this.connectWebSocket();
  },
  methods: {
    connectWebSocket() {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚫 Token não encontrado!");
        return;
      }

      this.socket = new WebSocket(`ws://localhost:8081/ws?token=${token}`);

      this.socket.onopen = () => {
        console.log("✅ Conectado ao WebSocket!");
      };
      
      this.socket.onmessage = (event) => {
        console.log("📩 Nova mensagem: ", event.data);
        this.messages.push({ text: event.data, sender: 'other' });
        
        const match = event.data.match(/^💬\s([^:]+)/);
        if (match) {
          this.username = match[1];
          this.sendMessageToApi(); 
        } else {
          console.error("🚫 Nome de usuário não encontrado na mensagem.");
        }
      };

      this.socket.onerror = (error) => {
        console.error("🚫 Erro no WebSocket:", error);
      };

      this.socket.onclose = () => {
        console.log("❌ WebSocket desconectado.");
      };
    },

    sendMessageToApi() {
      if (!this.message.trim()) {
        console.log("🚫 A mensagem não pode ser vazia.");
        return;
      }

      axios.post('http://localhost:8081/user/gravaMensagem', {
        username: this.username,
        dsmensagem: this.message,
      })
      .then(response => {
        console.log("✅ Mensagem registrada com sucesso!", response);
      })
      .catch(error => {
        console.log("🚫 Erro ao registrar a mensagem:", error);
      });

      this.message = "";
    },

    sendMessage() {
      if (this.socket && this.socket.readyState === WebSocket.OPEN) {
        console.log("✉️ Enviando mensagem:", this.message);
        this.messages.push({ text: this.message, sender: 'me' });
        this.socket.send(this.message);        
      } else {
        console.error("🚫 WebSocket não está conectado!");
      }
    },
  },
};

</script>

<style scoped>

</style>

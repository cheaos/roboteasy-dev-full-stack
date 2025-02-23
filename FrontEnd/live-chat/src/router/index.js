import { createRouter, createWebHistory } from 'vue-router';
import ChatView from '@/assets/view/ChatView.vue';
import LoginView from '@/assets/view/LoginView.vue';
import MenuView from '@/assets/view/MenuView.vue';
import CadastroView from '@/assets/view/CadastroView.vue';

function isAuthenticated() {
  const token = localStorage.getItem('token'); // Obter o token do localStorage
  if (!token) {
    return false;
  }
  try {
    // Decodifica o token JWT para acessar seu payload
    const base64Url = token.split('.')[1]; // A segunda parte do token contém o payload
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Ajusta para o formato Base64
    const decoded = JSON.parse(atob(base64)); // Decodifica a base64 e converte para objeto JSON

    const expiration = decoded.exp; // Obtenha a data de expiração
    const now = Date.now() / 1000; // Obtenha o timestamp atual em segundos

    // Se a expiração do token for menor que o timestamp atual, o token está expirado
    if (expiration < now) {
      return false; // Token expirado
    }

    return true; // O token é válido
  } catch (error) {
    console.error('Erro ao decodificar o token', error);
    return false; // Se houver algum erro ao decodificar o token, considere inválido
  }
}

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/menu',
    name: 'Menu',
    component: MenuView,
  },  
  {
    path: '/cadastro',
    name: 'Cadastro',
    component: CadastroView,
  },
  {
    path: '/chat',
    name: 'chat',
    component: ChatView,
    beforeEnter: (to, from, next) => {
      // Se o usuário não estiver autenticado, redireciona para o login
      if (!isAuthenticated()) {
        next({ name: 'login' }); // Redireciona para o login
      } else {
        next(); // Caso o usuário esteja autenticado, permite a navegação
      }
    },
  },
];

const router = createRouter({
  // Substituindo `process.env.BASE_URL` por uma URL fixa
  history: createWebHistory('/'), // Use '/' ou uma URL base configurada
  routes,
});

export default router;

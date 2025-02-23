import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

module.exports = {
  devServer: {
  clientLogLevel: 'debug', // Habilita o nível de log detalhado
  open: true, // Abre automaticamente no navegador após compilar
},
  productionSourceMap: true
}
// https://vite.dev/config/
export default defineConfig({
  define: {
    __VUE_OPTIONS_API__: true,
    __VUE_PROD_DEVTOOLS__: false,
    __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: true,
    global: {},
  },
  
  
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  configureWebpack: {
    devtool: 'source-map', // Ativa os mapas de origem
  },
  chainWebpack: config => {
    config.plugin('define').tap(definitions => {
      definitions[0]['__VUE_PROD_HYDRATION_MISMATCH_DETAILS__'] = JSON.stringify(true);
      return definitions;
    });
  }  
  
})

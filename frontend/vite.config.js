import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/customer': 'http://localhost:8080',
      '/account': 'http://localhost:8080',
      '/loan': 'http://localhost:8080',
      '/banking': 'http://localhost:8080',
      '/images': 'http://localhost:8080'
    }
  }
})

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

async function apiRequest(endpoint) {
  const response = await fetch(`${API_BASE_URL}${endpoint}`)

  if (!response.ok) {
    let errorMessage = `İstek başarısız oldu (${response.status})`

    try {
      const errorData = await response.json()

      if (errorData.message) {
        errorMessage = errorData.message
      }
    } catch {
      // Sunucu JSON hata cevabı döndürmezse varsayılan mesaj kullanılır.
    }

    throw new Error(errorMessage)
  }

  return response.json()
}

export function getHealth() {
  return apiRequest('/health')
}

export function getProjects() {
  return apiRequest('/projects')
}
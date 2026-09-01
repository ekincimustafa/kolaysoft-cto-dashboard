import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [healthData, setHealthData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('http://localhost:8080/api/health')
      .then((res) => {
        if (!res.ok) throw new Error('Ağ yanıtı başarısız')
        return res.json()
      })
      .then((data) => {
        setHealthData(data)
        setLoading(false)
      })
      .catch((err) => {
        setError(err.message)
        setLoading(false)
      })
  }, [])

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif', textAlign: 'center' }}>
      <h1>Kolaysoft CTO Dashboard</h1>
      <h2>İlk Çalışan İskelet (T04)</h2>

      <div style={{ marginTop: '2rem', padding: '1.5rem', border: '1px solid #444', borderRadius: '8px', display: 'inline-block' }}>
        <h3>Backend Bağlantı Durumu</h3>
        {loading && <p>Backend'e bağlanılıyor...</p>}
        {error && <p style={{ color: 'red' }}>Hata: {error} (Backend çalışıyor mu?)</p>}
        {healthData && (
          <div style={{ color: 'lightgreen', textAlign: 'left' }}>
            <p><strong>Durum:</strong> {healthData.status}</p>
            <p><strong>Servis:</strong> {healthData.service}</p>
            <p><strong>Sunucu Zamanı:</strong> {healthData.timestamp}</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default App
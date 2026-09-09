import { useEffect, useState } from 'react'
import { getHealth } from './api/ctoDashboardApi'
import ProjectsPage from './pages/ProjectsPage'
import './App.css'

function App() {
  const [apiStatus, setApiStatus] = useState('checking')

  useEffect(() => {
    let isActive = true

    getHealth()
      .then((healthData) => {
        if (isActive) {
          setApiStatus(healthData.status === 'UP' ? 'online' : 'offline')
        }
      })
      .catch(() => {
        if (isActive) {
          setApiStatus('offline')
        }
      })

    return () => {
      isActive = false
    }
  }, [])

  const apiStatusText = {
    checking: 'API kontrol ediliyor',
    online: 'Sistem çalışıyor',
    offline: 'API bağlantısı yok',
  }

  return (
    <div className="app-shell">
      <header className="app-header">
        <div className="brand">
          <span className="brand-mark">K</span>

          <div>
            <p className="brand-name">Kolaysoft</p>
            <p className="brand-product">CTO Dashboard</p>
          </div>
        </div>

        <div className={`api-status api-status-${apiStatus}`}>
          <span className="api-status-dot" />
          {apiStatusText[apiStatus]}
        </div>
      </header>

      <main className="app-content">
        <section className="page-introduction">
          <p className="page-eyebrow">Haftalık proje durum takibi</p>
          <h1>Proje Portföyü</h1>
          <p>
            Projelerin güncel durumunu, müşterisini ve sorumlu proje
            yöneticisini tek ekrandan görüntüleyin.
          </p>
        </section>

        <ProjectsPage />
      </main>
    </div>
  )
}

export default App
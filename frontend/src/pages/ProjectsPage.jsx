import { useEffect, useState } from 'react'
import { getProjects } from '../api/ctoDashboardApi'

const projectStatusLabels = {
  ACTIVE: 'Aktif',
  COMPLETED: 'Tamamlandı',
  ON_HOLD: 'Beklemede',
  CANCELLED: 'İptal Edildi',
}

function ProjectsPage() {
  const [projects, setProjects] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  async function loadProjects() {
    try {
      setLoading(true)
      setError(null)

      const projectData = await getProjects()
      setProjects(projectData)
    } catch (requestError) {
      setError(requestError.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    let isActive = true

    getProjects()
      .then((projectData) => {
        if (isActive) {
          setProjects(projectData)
        }
      })
      .catch((requestError) => {
        if (isActive) {
          setError(requestError.message)
        }
      })
      .finally(() => {
        if (isActive) {
          setLoading(false)
        }
      })

    return () => {
      isActive = false
    }
  }, [])

  if (loading) {
    return (
      <section className="projects-section">
        <p className="state-message">Projeler yükleniyor...</p>
      </section>
    )
  }

  if (error) {
    return (
      <section className="projects-section">
        <div className="error-state">
          <p>Projeler alınamadı: {error}</p>
          <button type="button" onClick={loadProjects}>
            Tekrar Dene
          </button>
        </div>
      </section>
    )
  }

  if (projects.length === 0) {
    return (
      <section className="projects-section">
        <p className="state-message">Henüz kayıtlı proje bulunmuyor.</p>
      </section>
    )
  }

  return (
    <section className="projects-section">
      <div className="section-heading">
        <div>
          <p className="section-eyebrow">Proje yönetimi</p>
          <h2>Projeler</h2>
        </div>

        <span className="project-count">
          {projects.length} proje
        </span>
      </div>

      <div className="project-grid">
        {projects.map((project) => (
          <article className="project-card" key={project.id}>
            <div className="project-card-header">
              <span className="project-id">Proje #{project.id}</span>
              <span className={`status-badge status-${project.status.toLowerCase()}`}>
                {projectStatusLabels[project.status] || project.status}
              </span>
            </div>

            <h3>{project.name}</h3>

            <dl className="project-details">
              <div>
                <dt>Müşteri</dt>
                <dd>{project.clientName}</dd>
              </div>

              <div>
                <dt>Proje yöneticisi</dt>
                <dd>{project.managerName}</dd>
              </div>
            </dl>
          </article>
        ))}
      </div>
    </section>
  )
}

export default ProjectsPage
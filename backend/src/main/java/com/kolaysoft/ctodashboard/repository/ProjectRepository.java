package com.kolaysoft.ctodashboard.repository;

import com.kolaysoft.ctodashboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Bir Proje Yöneticisine atanmış projeleri listelemek için (Veri İzolasyonu)
    List<Project> findByManagerId(Long managerId);
}
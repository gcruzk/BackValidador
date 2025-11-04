package com.a3.prototipo.Repository;

import com.a3.prototipo.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    long countByIsMalicious(boolean isMalicious);
}
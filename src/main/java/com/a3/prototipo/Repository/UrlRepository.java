package com.a3.prototipo.Repository;

import com.a3.prototipo.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByUserEmail(String userEmail);
    
    @Query("SELECT COUNT(u) FROM Url u WHERE u.isMalicious = true")
    Long countMaliciousUrls();
    
    @Query("SELECT COUNT(u) FROM Url u")
    Long countTotalUrls();
}

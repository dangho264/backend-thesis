package com.thesis.productservice.repository;

import com.thesis.productservice.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    public Artist findByArtistName(String name);
    public boolean existsByArtistName(String name);
}

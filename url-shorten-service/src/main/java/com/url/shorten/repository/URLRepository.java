package com.url.shorten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shorten.entity.JUrl;

@Repository
public interface URLRepository extends JpaRepository<JUrl, Long> {

	public Optional<JUrl> findByLongUrl(String longUrl);
}

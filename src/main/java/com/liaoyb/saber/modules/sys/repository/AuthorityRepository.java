package com.liaoyb.saber.modules.sys.repository;

import com.liaoyb.saber.modules.sys.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

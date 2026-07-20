package com.easur.gis.repository;

import com.easur.gis.model.GeoMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoMapRepository extends JpaRepository<GeoMapEntity, Long> {}

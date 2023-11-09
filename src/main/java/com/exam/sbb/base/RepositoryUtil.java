package com.exam.sbb.base;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RepositoryUtil {
  @Modifying
  @Transactional
  @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
  void disableForeignKeyChecks();

  @Modifying
  @Transactional
  @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
  void enableForeignKeyChecks();
}

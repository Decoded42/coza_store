package com.cybersoft.cozastore.repository;

import com.cybersoft.cozastore.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer> {

    FileEntity findByName(String fileName);
}

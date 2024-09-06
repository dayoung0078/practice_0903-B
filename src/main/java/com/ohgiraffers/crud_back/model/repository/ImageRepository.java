package com.ohgiraffers.crud_back.model.repository;

import com.ohgiraffers.crud_back.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}

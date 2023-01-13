package com.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

package com.account.repository;

import com.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
    User findByUsername(String username);

    List<User> findAllByRoleDescriptionOrderByCompanyTitleAsc(String desc);
    List<User> findAllByCompanyTitleOrderByRole(String company);



}

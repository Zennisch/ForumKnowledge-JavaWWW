package com.fit.iuh.repositories;

import com.fit.iuh.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    public List<User> findByNameContaining(String keyword);

    @Query("SELECT u FROM User u WHERE u.email LIKE :email")
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    public User findById(int userId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :passwordHash")
    public User findByEmailAndPassword(String email, String passwordHash);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User existsByEmail(String email);

    @Query("SELECT u FROM User u")
    public Page<User> findAllUsers(Pageable pageable);
  
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findByCreatedAtBetween(Date startDate, Date endDate);

    // Edit
    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.email = :email, u.title = :title, u.profilePicture = :profilePicture, " +
            "u.coverPicture = :coverPicture, u.description = :description, u.bio = :bio WHERE u.userId = :userId")
    void editUser(String name, String email, String title, String profilePicture, String coverPicture, String description, String bio, int userId);
}

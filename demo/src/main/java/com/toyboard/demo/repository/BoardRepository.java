package com.toyboard.demo.repository;

import com.toyboard.demo.dto.BoardUserDto;
import com.toyboard.demo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query( value = "SELECT b.id, u.username, b.title " +
            "FROM board_tb as b LEFT OUTER JOIN user_tb as u " +
            "ON b.user_id = u.id", nativeQuery = true)
    List<BoardUserDto> findAllByUser();


}
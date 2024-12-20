package com.example.demo.model.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능

    private final BoardRepository blogRepository; // 리포지토리 선언

    public List<Board> findAll() { // 게시판 전체 목록 조회
        return blogRepository.findAll();
    }

    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository.findById(id);
    }

    public void update(Long id, AddArticleRequest request) {
        Optional<Board> optionalBoard = blogRepository.findById(id);
        optionalBoard.ifPresent(boards -> {
        // 제목, 내용만 입력 받은걸로 교체
        boards.update(request.getTitle(), request.getContent(), boards.getUser(), boards.getNewdate(), boards.getCount(), boards.getLikec());
        blogRepository.save(boards);
        });
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    public Board save(AddArticleRequest request) {
        // DTO가 없는 경우 이곳에 직접 구현 가능
        return blogRepository.save(request.toEntity());
    }

    public Page<Board> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    } // LIKE 검색 제공(대소문자 무시)
}
package com.example.demo.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;

@Controller // 컨트롤러 어노테이션 명시

public class BlogController {
    @Autowired
    BlogService blogService;

        @GetMapping("/board_list") // 새로운 게시판 링크 지정
        public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword, HttpSession session) { // 세션 객체 전달
            String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
            String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인

            if (userId == null) {
                return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
            }
            System.out.println("세션 userID: " + userId); // 서버 IDE 터미널에 세션 값 출력

            PageRequest pageable = PageRequest.of(page, 3); // 한 페이지의 게시글 수
            Page<Board> list; // Page를 반환

            if (keyword.isEmpty()) {
                list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
            } else {
                list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
            }
        
            int startNum = (page * pageable.getPageSize()) + 1; // 1, 3, 7

            model.addAttribute("boards", list); // 모델에 추가
            model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
            model.addAttribute("currentPage", page); // 페이지 번호
            model.addAttribute("keyword", keyword); // 키워드
            model.addAttribute("startNum", startNum); // 시작 변수
            model.addAttribute("email", email); // 로그인 사용자(이메일)

            return "board_list"; // .HTML 연결
        }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id, HttpSession session) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        String email = (String) session.getAttribute("email");

        if (list.isPresent()) {
            model.addAttribute("boards", list.get());
            model.addAttribute("email", email);
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
        }

        return "board_view"; // .HTML 연결
    }

    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id){
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("boards", list.get());
        } else {
            return "/error_page/article_error";
        }
        return "board_edit";
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인
        request.setUser(email); // user를 확인한 이메일로 교체
        
        blogService.save(request);
        return "redirect:/board_list"; // .HTML 연결
    }
}
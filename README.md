**JPA Repository**  
* JPA는 Commons 를 제외한 기본 기능 (SpringDataCommons: Repositiry, CrudRepository, PaingAndSortingRepository)
* 순수 JPA 에 비해 코드 최소화, RDB 최적화
* CRUD 를 통해 표준 메소드를 미리 구현해 둠
* CRUD 및 JPA 세부 메서드
  * 메서드에 따라 상속 구분
    * JpaRepository가 일반적
  * 상속 예)
    * CRUD: 단순 작업
    * JPA: 페이징, 정렬 등 추가
  * PaingAndSortingRepository: 페이징(처리량, 정렬 조정 가능)
<br/>

**게시판 - 검색**
* Spring은 검색 최적화를 위해
  * Ehcache, Redis 등 캐싱 서비스 사용 가능
    * 자주 사용하는데이터를 저장하여 재활용
  * findByTitleContaining 메서드 제공
    * 단순 데이터 셋 검색에 적합
    * 기존 문자열 검색(equal, =)과 달리 like 검색 가능
  * 예) 검색 키워드에 관련된 다수 글을 리턴
    * findById 등은 단일 문자열 결과
  * 즉, 대규모 검색 엔진에는 적합하지 않음
    * 풀 텍스트 인덱싱 검색 방식  
<br/>

**게시판 - 검색**
* Spring은 검색 최적화를 위해
  * Ehcache, Redis 등 캐싱 서비스 사용 가능
    * 자주 사용하는데이터를 저장하여 재활용
  * findByTitleContaining 메서드 제공
    * 단순 데이터 셋 검색에 적합
    * 기존 문자열 검색(equal, =)과 달리 like 검색 가능
  * 예) 검색 키워드에 관련된 다수 글을 리턴
    * findById 등은 단일 문자열 결과
  * 즉, 대규모 검색 엔진에는 적합하지 않음
    * 풀 텍스트 인덱싱 검색 방식이 훨씬 빠름  
<br/>

**게시판 - 페이징**
* Spring은 pagination을 위한 인터페이스 제공
  * 데이터를 정렬기준, 페이지 크기와 번호에 따라 결과 제공
    * 참고 : 기본 개념은 나눠서 전달
  * 예) 단일 검색 요청에 게시글을 전부 리턴/출력?
    * 서버에 큰 부하
  * 주요 페이지 관련 기능 제공
    * 페이지 번호, 최대 수, 시작, 정렬, 이전 페이지 등
  * 내부 기본 값 설정
    * page 0, size 20, 정렬 x, @PageDefault로 설정
<br/>

**스프링 시큐리티**
* 웹 사이트 기본 보안
  * Security 정보를 전용 Context에 저장
    * 기본 AuthenticationManager 제어
  * 로그인 : 아이디/패스워드 기반
    * 내부 동작: 인증(누구), 인가(위임)
  * SecurityConfig 클래스 설정
    * 인증&인가 등 보안 관련 API 구현
* 사용자 로그인/로그아웃 동작
  * 예 : 사용자의 form 로그인 요청
    * 인증 관리자(Manager)를 통해 사용자 정보 처리(Service)
  * 세션 기반 로그인 구현
    * 인증 유지 : 서버 쪽에 사용자 정보 유지
    * HttpSession 클래스 사용
    * 기타 : 암호화 알고리즘
      * 기본 Bcrypt 제공(Blowfish)
    * 로그아웃 요청
      * 세션 삭제
<br/>

**@Bean과 @Autowired의 차이**
|구분|@Bean|@Autowired|
|---|---|---|
|역할|객체(Bean)를 수동으로 생성하여 Spring 컨테이너에 등록|Spring 컨테이너에 등록된 Bean을 자동으로 주입|
|사용 위치|메서드|필드, 생성자, 메서드|
|용도|명시적 Bean 등록(객체 생성 후 Spring에서 관리하도록 등록)|자동으로 의존성 주입(이미 컨테이너에 등록된 Bean 사용)
|생성 시점|@Configuration 초기화 시|Bean 주입 시점에서 자동 주입|

**스프링 세션**
* 스프링 부트 환경의 세션 관리
  * 사용자 상태 정보를 저장
    * 사용자 인증 정보(아이디, 패스워드 x, 일회성 해시 값 o)
    * 사용 예) 단일/다중 로그인
  * HttpSession을 통해 세션 관리 제공
    * 스프링 세션의 보안 설정과 연동 가능
      * 강제 https, 보안 flag, 세션 만료 등
    * 기본 세션 쿠키로 동작
      * 세션 ID를 쿠키로 저장
* 스프링 부트 내부 보안 필터
  * 다양한 보안 필터 존재
    * 사용자 인증을 위해 미리 메소드 수준 제공
    * 인증 과정 필터 동작
  * 스프링 세션은 인증 이후
    * 필터는 서버 요청의 시작, 마지막(양방향)에서 동작
  * 주요 메소드 동작
    * 시작 doFilter, 종료 doFilter
    * 마지막 commitSession
      * JSESSIONID 값 생성
<br/>

---
<br/>

**Controller - 사용자 요청을 처리하고 응답을 반환**
* 파라미터
  * Model
    * model.addAttribute("HTML에서읽을이름", 보낼값)
      * 보낼값을 model에 저장해 HTML로 보냄
  * @RequestParam(defaultValue = "0") int page
     * 전달된 데이터를 가지되, 기본값으로 0을 가짐
   * @ModelAttribbute AddMemberRequest request
     * 요청 파라미터를 자동으로 객체의 필드에 매핑
   * HttpSession
     * 클라이언트와 서버 간의 상태 유지를 위한 세션
   * HttpServletRequest
     * 클라이언트의 요청 정보를 담는 개체
   * HttpServletResponse
     * 서버의 응답 정보를 담는 개체
* .isPresent()
  * 존재한다면 true 리턴
* .isEmpty()
  * 비어있다면(배열 계열) true 리턴
* @PostMapping
  * @PostMapping("/api/..."), return "redirect:/...";
* String userId = (String) HttpSession파라미터.getAttribute("userId"); // 세션 아이디 존재 확인<br/>
  String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성 / UUID.randomUUID(): 고유한 식별자를 랜덤하게 생성
  * UUID.randomUUID(): 고유한 식별자를 랜덤하게 생성  <br/>
  session.setAttribute("userId", sessionId); // 아이디 이름 설정
<br/>

**@Entity(domain) - 데이터베이스 테이블**
* @Getter
  * 클래스의 모든 필드에 대해 Getter 메서드(getTitle(), getContent()...)를 자동 생성
  * 없으면 필드값에 접근할때 public String getTitle() {return this.title;}... 이런 식으로 매번 작성해야 함
* @Entity
  * JPA가 해당 클래스를 기반으로 데이터베이스를 관리
* @Table(name = "board")
  * 테이블 이름을 지정 / 안하면 클래스 이름으로 자동설정
* @@NoArgsConstructor(access = AccessLevel.PROTECTED)
  * 외부 생성자 접근 방지
* @id
  * 기본 키로 사용
* @Column(name = "user", nullable = false)
  * nullable = 널x, updatable = 수정x
<br/>

**@Repository - 데이터베이스를 CRUD**
* public interface MemberRepository extends JpaRepository<domain클래스, Long>
  * JpaRepository로 CRUD
 <br>
 
**service - 실제 동작을 설계 및 처리**
* public List findAll() {  return repository변수.findAll(); }
  * 도메인 데이터베이스에 입력된 모든 행을 가져옴
* public Optional findById(Long id) { return repository.findById(id); }
  * 데이터베이스의 id를 가져옴
* public domain클래스 save(AddArticleRequest request) { return repository변수.save(request.toEntity()); }
  * DTO 변수를 엔티티로 변환시키고 그것을 domain클래스 데이터베이스에 저장
* public Page<domain클래스> searchByKeyword(String keyword, Pageable pageable) { return repository변수.findByTitleContainingIgnoreCase(keyword, pageable); }
  * like 검색 제공(%문자열%)
  * 대소문자 무시(IgnoreCase 빼면 허용)
  * title 필드에 keyword가 포함된 게시물을 대소문자 구분 없이 검색하고, pageable 파라미터로 전달된 페이징 정보를 적용하여 결과를 페이징 처리하여 반환
  * 커스텀 쿼리 메서드라 따로 리포지토리에 선언해줘야 함
* validateDuplicateMember(DTO파라미터)
  * 중복 확인
* String encodedPassword = passwordEncoder.encode(DTO파라미터.getPassword());
  * getPassword(): 사용자가 입력한 비밀번호를 DTO파라미터 객체에서 가져옴
  * passwordEncoder.encode(): 비밀번호를 암호화
* if (!passwordEncoder.matches(rawPassword, member.getPassword()))
  * 입력된 비밀번호(rawPassword)와 저장된 암호화된비밀번호(member.getPassword())가 일치하는지 확인(matches)
 <br/>

**Config**
* SecurityConfig
  * 스프링에서 보안 관리 클래스
<br/>

**@(어노테이션)**
* @configuration
  * 스프링 설정 클래스임을 지정, 등록된 Bean 생성 시점
* @Bean
  * 명시적 의존성 주입
    * return http.build();    // http: HttpSecurity 파라미터
  * 암호화 설정
    * return new BCryptPasswordEncoder();    // 비밀번호 암호화 저장
  * @Transactional
    * 클래스 내 모든 메서드의 데이터베이스 작업을 하나의 트랜잭션(하나로 묶어)으로 처리
  * @RequiredArgsConstructor
    * final이나 @NonNull이 붙은 필드에 자동으로 생성자를 생성
  * @Value
    * 프로퍼티 파일의 값을 Bean에 주입
  * @RequestParam(“email”) String email
    * http 요청 파라미터에서 “email” 값을 추가
  * @NotBlank
    * null, 빈문자열, 공백 모두 허용하지 않음
  * @Pattern
    * 임의로 조건을 지정
    * regexp = "^[가-힣a-zA-Z]*$" // 한글, 영어 대소문자만 가능
<br/>

Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
* uploadFolder 경로를 Path 객체로 변환한 후, 이를 절대 경로로 변환하여 uploadPath에 저장
<br/>

Files.createDirectories(uploadPath);
* 지정된 경로(uploadPath)에 디렉토리가 존재하지 않으면 새로 생성
<br/>

server.servlet.session.timeout=300s: 세션의 유효 시간
<br/>
server.servlet.session.cookie.secure=true: HTTPS 연결에서만 세션 쿠키를 전송(보안 강화)

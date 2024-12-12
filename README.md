**JPA Repository**  
* JPA는 Commons 를 제외한 기본 기능 (SpringDataCommons: Repositiry, CrudRepository, PaingAndSortingRepository)
* 순수 JPA 에 비해 코드 최소화, RDB 최적화
* CRUD 를 통해 표준 메소드를 미리 구현해 둠
* 






**domain**
엔티티(Entity) / 데이터베이스 테이블

@Getter
* 클래스의 모든 필드에 대해 Getter 메서드(getTitle(), getContent()...)를 자동 생성
* 없으면 필드값에 접근할때 public String getTitle() {return this.title;}... 이런 식으로 매번 작성해야 함

@Entity
* JPA가 해당 클래스를 기반으로 데이터베이스를 관리

@Table(name = "board")
* 테이블 이름을 지정 / 안하면 클래스 이름으로 자동설정

@@NoArgsConstructor(access = AccessLevel.PROTECTED)
* 외부 생성자 접근 방지

@id
* 기본 키로 사용


---
**repository**
데이터베이스를 CRUD
---
**service**
실제 동작을 설계 및 처리

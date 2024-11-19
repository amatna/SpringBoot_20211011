**domain**
엔티티(Entity) / 데이터베이스 테이블

@Getter
* 클래스의 모든 필드에 대해 Getter 메서드(getTitle(), getContent()...)를 자동 생성
* 없으면 필드값에 접근할때 public String getTitle() {return this.title;}... 이런 식으로 매번 작성해야 함

@Entity
* JPA가 해당 클래스를 기반으로 데이터베이스를 관리


---
**repository**
데이터베이스를 CRUD
---
**service**
실제 동작을 설계 및 처리

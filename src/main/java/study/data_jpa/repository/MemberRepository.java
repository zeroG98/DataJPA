package study.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page; //추가 count 쿼리 결과를 포함하는 페이징
import org.springframework.data.domain.Pageable; //페이징 기능(내부에 Sort 포함)
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.Collection;
import java.util.List; //추가 count 쿼리 없이 결과만 반환
import java.util.Optional;

//spring data jpa
public interface MemberRepository extends JpaRepository<Member, Long> {

    //간단하게 만드는 상황일 때 사용
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

//    @Query(name = "Member.findByUsername") //없어도 실행가능
    List<Member> findByUsername(@Param("username") String username);

    //복잡하게 만들어질 때 사용
    @Query("select m from Member m where m.username = :username and m.age = :age")//이름기반(가독성과 유지보수를 위해 이름기반 파라미터바인딩을 사용하자)
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")//dto 조회 시 new 필수!
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names") //실무에서 꽤 많이 쓰임
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); //단건
    Optional<Member> findOptionalByUsername(String username); //단건 Optional

    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용
//    Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함

    @Modifying(clearAutomatically = true) //.executeUpdate()역할 update 쿼리가 아닌 다른 쿼리를 실행한다.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})//fetch join을 편리하게 할 수 있음
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.data_jpa.entity.Member;

import java.util.List;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{//만들어진 클래스명(ex.MemberRepository)
                                                                    // + Impl의 규칙으로 만들어야 JPA에서 찾아서 호출함

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}

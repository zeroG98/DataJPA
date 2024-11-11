package study.data_jpa.repository;

//Projections   엔티티 대신에 DTO를 편리하게 조회할 때 사용

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}

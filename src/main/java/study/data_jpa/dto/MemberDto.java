package study.data_jpa.dto;

import lombok.Data;
import study.data_jpa.entity.Member;

@Data
public class MemberDto {

    private Long id;//조회하고 싶은 대상
    private String username;//조회하고 싶은 대상
    private String teamName;//조회하고 싶은 대상

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}

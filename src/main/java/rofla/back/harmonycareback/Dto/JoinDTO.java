package rofla.back.harmonycareback.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JoinDTO {
    private String username;
    private String password;
    private String role;
    private String name;
    private LocalDate age;
    private Double star;
    private String regin;
    private String phoneNum;
}

package tutorialSecurity.Baocao.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String username;
    private String password;
    private String fullName;
    private String phone;

    private List<RoleDTO> roleDTOS;
}

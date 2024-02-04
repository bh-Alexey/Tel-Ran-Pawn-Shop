package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.ManagerStatus;

@Data
public class ManagerCreationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private ManagerStatus managerStatus;

}

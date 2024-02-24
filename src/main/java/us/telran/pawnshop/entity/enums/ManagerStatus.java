package us.telran.pawnshop.entity.enums;

import us.telran.pawnshop.security.RoleProvider;

public enum ManagerStatus implements RoleProvider {
    EXPERT_APPRAISER {
        @Override
        public String getRole() {
            return "ROLE_MANAGER";
        }
    },
    REGION_DIRECTOR {
        @Override
        public String getRole() {
            return "ROLE_DIRECTOR";
        }
    };

}

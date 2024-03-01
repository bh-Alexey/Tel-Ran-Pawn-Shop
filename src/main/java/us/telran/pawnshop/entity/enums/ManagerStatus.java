package us.telran.pawnshop.entity.enums;

import us.telran.pawnshop.security.RoleProvider;

/**
 * Enumerates the different statuses a manager can have within the system, each associated with a specific role.
 * This enumeration implements the {@link RoleProvider} interface, allowing each status to provide its associated security role.
 * This approach facilitates role-based access control (RBAC) by clearly defining the roles associated with different managerial positions.
 * <p>
 * The manager statuses and their corresponding roles are:
 * <ul>
 *     <li>{@code EXPERT_APPRAISER} - Represents a manager with expertise in appraising subjects of pledge. Associated with the "ROLE_MANAGER" security role.</li>
 *     <li>{@code REGION_DIRECTOR} - Represents a manager with responsibility over a region. Associated with the "ROLE_DIRECTOR" security role.</li>
 * </ul>
 *
 * Implementing the {@code RoleProvider} interface allows each status to directly provide the role string necessary for security decisions,
 * enabling a clean and modular approach to assigning roles based on manager status.
 *
 * @author bh-alexey
 *
 */
public enum ManagerStatus implements RoleProvider {

    EXPERT_APPRAISER {
        /**
         * Provides the security role associated with the EXPERT_APPRAISER status.
         *
         * @return A string representing the security role, "ROLE_MANAGER".
         *
         */
        @Override
        public String getRole() {
            return "ROLE_MANAGER";
        }
    },
    REGION_DIRECTOR {
        /**
         * Provides the security role associated with the REGION_DIRECTOR status.
         *
         * @return A string representing the security role, "ROLE_DIRECTOR".
         */
        @Override
        public String getRole() {
            return "ROLE_DIRECTOR";
        }
    };

}

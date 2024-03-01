package us.telran.pawnshop.entity.enums;

/**
 * Enumerates the different statuses that a client can have within the system.
 * Each status represents a level of relationship or benefits that the client has achieved.
 * The statuses are used to determine the level of service or discounts a client is eligible for.
 * <p>
 * The available statuses are:
 * <ul>
 *     <li>{@code REGULAR} - The default status for new clients, with no special benefits.</li>
 *     <li>{@code BRONZE_STATUS} - Represents a client who has reached the first tier of benefits.</li>
 *     <li>{@code SILVER_STATUS} - Represents a client who has reached the second tier of benefits, higher than Bronze.</li>
 *     <li>{@code GOLDEN_STATUS} - The highest status, representing a client who has reached the top tier of benefits available.</li>
 * </ul>
 *
 *@author bh-Alexey
 *
 */

public enum ClientStatus {

    REGULAR,

    BRONZE_STATUS,

    SILVER_STATUS,

    GOLDEN_STATUS
}

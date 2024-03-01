package us.telran.pawnshop.entity.enums;
/**
 * Enumerates the various statuses a pledge (or collateral) can have within the system.
 * This enumeration is crucial for tracking the lifecycle of a pledge from its initiation to conclusion,
 * providing clear and consistent status updates that are essential for both the management and the customer.
 * <p>
 * The defined statuses are:
 * <ul>
 *     <li>{@code PENDING} - Indicates that the pledge is under review or awaiting initial processing. Required for loan creation.</li>
 *     <li>{@code PLEDGED} - Signifies that the pledge has been accepted and is currently held as collateral. Loan has been borrowed.</li>
 *     <li>{@code COLLECTED} - Represents that the pledge has been collected by the institution, typically after a default or forfeiture.</li>
 *     <li>{@code READY_FOR_COLLECTION} - Indicates that the pledge is ready to be collected by institution, usually after all terms have been met.</li>
 *     <li>{@code RANSOMED} - Denotes that the pledge has been reclaimed by the owner, typically after fulfilling the contractual obligations.</li>
 * </ul>
 * This enumeration facilitates efficient and accurate tracking of pledges, contributing to the overall transparency and reliability of the pledging process.
 */
public enum PledgeStatus {

    PENDING,
    PLEDGED,
    COLLECTED,
    READY_FOR_COLLECTION,
    RANSOMED

}

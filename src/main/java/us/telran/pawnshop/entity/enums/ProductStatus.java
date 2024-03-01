package us.telran.pawnshop.entity.enums;
/**
 * Enumerates the possible states of a product's lifecycle within the system.
 * This enumeration is crucial for inventory management, allowing products to be easily categorized
 * based on their current availability or operational status. Such classification aids in the
 * effective management of product listings, eligibility for various operations.
 * <p>
 * The product statuses defined are:
 * <ul>
 *     <li>{@code ACTIVE} - Indicates that the product is currently available for use. Products marked as active can be transacted upon.</li>
 *     <li>{@code INACTIVE} - Denotes that the product is not available for use at the moment. Inactive products may be temporarily not offered for transaction.</li>
 * </ul>
 *
 * @author bh-alexey
 *
 */
public enum ProductStatus {
    ACTIVE,
    INACTIVE
}

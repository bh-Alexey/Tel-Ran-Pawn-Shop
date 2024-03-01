package us.telran.pawnshop.entity.enums;
/**
 * Represents the purity levels of metals, specifically gold and silver, used in items such as jewelry.
 * This enumeration provides a standardized way to refer to the purity of these metals, which is crucial
 * for valuation, manufacturing, and certification processes.
 * <p>
 * The purity levels are defined as follows:
 * <ul>
 *     <li>{@code GOLD_375} - Represents gold of 375 purity, commonly referred to as 9 karat (K) gold.</li>
 *     <li>{@code GOLD_585} - Represents gold of 585 purity, commonly referred to as 14 karat (K) gold.</li>
 *     <li>{@code GOLD_750} - Represents gold of 750 purity, commonly referred to as 18 karat (K) gold.</li>
 *     <li>{@code GOLD_916} - Represents gold of 916 purity, commonly referred to as 22 karat (K) gold.</li>
 *     <li>{@code GOLD_999} - Represents gold of 999 purity, the highest standard, commonly referred to as 24 karat (K) gold.</li>
 *     <li>{@code SILVER_800} - Represents silver of 800 purity, indicating a silver content of 80%.</li>
 *     <li>{@code SILVER_925} - Represents silver of 925 purity, also known as sterling silver, indicating a silver content of 92.5%.</li>
 * </ul>
 * This enumeration is essential for businesses and consumers alike to understand and communicate the quality and value of metal items accurately.
 *
 * @author bh-alexey
 *
 */
public enum MetalPurity {
    GOLD_375, //9K
    GOLD_585, //14K
    GOLD_750, //18K
    GOLD_916, //22K
    GOLD_999, //24K
    SILVER_800,
    SILVER_925
}

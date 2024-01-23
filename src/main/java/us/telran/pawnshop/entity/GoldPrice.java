package us.telran.pawnshop.entity;

import us.telran.pawnshop.entity.enums.GoldPurity;

import java.util.Map;

public class GoldPrice {

    public GoldPurity goldPurity;

    private Map<GoldPurity, Double> goldPrice;

}

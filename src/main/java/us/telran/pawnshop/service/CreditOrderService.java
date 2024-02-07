package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.CreditOrder;

import java.util.List;

public interface CreditOrderService {

    void createReceiptOrder();

    void makeExpenseOrder();

    List<CreditOrder> getOrders();


}

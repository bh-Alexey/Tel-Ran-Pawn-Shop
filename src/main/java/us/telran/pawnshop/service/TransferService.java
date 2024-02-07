package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.TransferRequest;


public interface TransferService {

    void cashTransfer(TransferRequest transferRequest);
}


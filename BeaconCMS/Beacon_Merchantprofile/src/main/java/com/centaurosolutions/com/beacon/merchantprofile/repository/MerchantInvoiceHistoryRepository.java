package com.centaurosolutions.com.beacon.merchantprofile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.centaurosolutions.com.beacon.merchantprofile.model.*;

import java.util.ArrayList;

public interface MerchantInvoiceHistoryRepository extends MongoRepository<MerchantInvoiceHistory, String> {

    ArrayList<MerchantInvoiceHistory> findByUserId(String userId);
    ArrayList<MerchantInvoiceHistory> findByMerchantId(String merchantId);


}

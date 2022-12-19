/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.endpoints.api;


import com.futurealgos.micro.credits.dto.CreditDto;
import com.futurealgos.micro.credits.dto.PartnerTransaction;
import com.futurealgos.micro.credits.dto.TransactionsHistory;
import com.futurealgos.micro.credits.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CreditsResource {

    @Autowired
    private CreditService creditService;

    @PostMapping("/add")
    public String addCredit(@RequestBody CreditDto payload, BindingResult result) throws Exception {
        creditService.create(payload);
        return "credit added successfully";
    }

    @PutMapping("/remove")
    public String removeCredit(@RequestBody CreditDto payload, BindingResult result) throws Exception {
        creditService.remove(payload);
        return "credit deleted successfully";
    }

    @GetMapping("/history")
    public List<TransactionsHistory> showBothTransaction() throws Exception {
        return creditService.showTransactionHistory();
    }

    @GetMapping("/partner/history")
    public List<PartnerTransaction> showPartnerTransaction(@RequestParam String id) {
        return creditService.partnerTransactionHistory(id);
    }


}

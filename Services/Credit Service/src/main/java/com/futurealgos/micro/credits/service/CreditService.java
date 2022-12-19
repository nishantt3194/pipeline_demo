/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.service;


import com.futurealgos.micro.credits.dto.CreditDto;
import com.futurealgos.micro.credits.dto.PartnerTransaction;
import com.futurealgos.micro.credits.dto.TransactionsHistory;
import com.futurealgos.micro.credits.model.User;
import com.futurealgos.micro.credits.model.UserTransactions;
import com.futurealgos.micro.credits.repos.UserRepository;
import com.futurealgos.micro.credits.repos.UserTransactionRepository;
import com.futurealgos.micro.credits.utils.Category;
import com.futurealgos.micro.credits.utils.Mode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;

    public void create(CreditDto payload) throws Exception {
        UserTransactions transactions = new UserTransactions();
        User user = userRepository.findById(new ObjectId(payload.userId())).orElseThrow(() -> new Exception("user does not exists"));

        Double userCredits = user.getCredits() + payload.credits();
        transactions.setCategory(Category.valueOf(payload.category()));
        transactions.setMode(Mode.valueOf(payload.mode()));
        transactions.setCredits(payload.credits());
        transactions.setInitialBalance(user.getCredits());
        transactions.setFinalBalance(userCredits);
        transactions.setAssociatedId(user.getId().toHexString());

        user.setCredits(userCredits);
        userRepository.save(user);
        userTransactionRepository.save(transactions);
    }

    public void remove(CreditDto payload) throws Exception {
        User user = userRepository.findById(new ObjectId(payload.userId())).orElseThrow(() -> new Exception("user does Not Exists"));

        UserTransactions transactions = new UserTransactions();

        Double userCredits = user.getCredits() - payload.credits();
        transactions.setCategory(Category.valueOf(payload.category()));
        transactions.setCredits(payload.credits());
        transactions.setMode(Mode.valueOf(payload.mode()));

        transactions.setInitialBalance(user.getCredits());
        transactions.setFinalBalance(userCredits);

        user.setCredits(userCredits);
        transactions.setAssociatedId(user.getId().toHexString());
        userRepository.save(user);
        userTransactionRepository.save(transactions);
    }

    public List<TransactionsHistory> showTransactionHistory() {
        List<UserTransactions> list = userTransactionRepository.findAll();
        return list.stream().map(userTransactions -> {
            TransactionsHistory transactionsHistory = TransactionsHistory.build(userTransactions);
            User user = userRepository.findById(new ObjectId(userTransactions.getAssociatedId())).orElse(null);
            assert user != null;
            transactionsHistory.setEmail(user.getUsername());
            transactionsHistory.setName(user.getFirstName() + " " + user.getLastName());
            return transactionsHistory;
        }).collect(Collectors.toList());
    }

    public List<PartnerTransaction> partnerTransactionHistory(String id) {
        List<UserTransactions> history = userTransactionRepository.findByAssociatedId(id);
        return history.stream().map(PartnerTransaction::build).collect(Collectors.toList());
    }

}

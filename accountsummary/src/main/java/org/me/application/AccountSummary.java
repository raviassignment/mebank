package org.me.application;

import com.opencsv.bean.CsvToBeanBuilder;
import org.me.model.Account;
import org.me.model.Transaction;
import org.me.model.TransactionType;
import org.me.utility.DateParse;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccountSummary {

    /**
     * @param fileName
     * @param accountNum
     * @param fromDate
     * @param toDate
     * @return AccountSummary
     * <p>
     * This method returns the AccountSummary
     * Used openCSV library to read transactions from CSV file to object
     * @Author : Ravi Kumar Enukonda
     */
    public float getAccountSummary(String fileName,
                                   String accountNum,
                                   LocalDateTime fromDate,
                                   LocalDateTime toDate) {
        Account accountObj = new Account(accountNum, 0f);
        try {
            List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(Transaction.class).build().parse();

            //Get valid transactions
            List<Transaction> accountTrans = filterValidPaymentTransactions(transactions,
                    accountNum, fromDate, toDate, accountObj);

            //Get valid transaction ID to check REVERSAL transactions
            List<String> validTransactionIds = getValidTransacationIds(accountTrans);

            //Get REVERSAL transactions count
            int reversalCounts = calculateReversalPayments(transactions, accountNum, validTransactionIds, accountObj);

            System.out.println("Relative balance for the period is: " + accountObj.getBalance());
            System.out.println("Number of transactions included is: " + (validTransactionIds.size() - reversalCounts));

        } catch (Exception e) {
            System.out.println("Exception While accessing/reading the file:: " + e.fillInStackTrace());
        }
        return accountObj.getBalance();
    }

    public List<String> getValidTransacationIds(List<Transaction> accountTrans) {
        //Get all transaction ids
        return accountTrans.stream()
                .map(transaction -> transaction.getTransactionId())
                .collect(Collectors.toList());

    }


    public List<Transaction> filterValidPaymentTransactions(List<Transaction> transactions, String accountNum,
                                                            LocalDateTime fromDate, LocalDateTime toDate, Account accountObj) {
        List<Transaction> accountTrans = new ArrayList<>();

        //Filter from account and to account with in given time.
        transactions.stream()
                .filter(transaction ->
                        (transaction.getFromAccountId().equals(accountNum)
                                || transaction.getToAccountId().equals(accountNum)) &&
                                transaction.getTransactionType().equals(TransactionType.PAYMENT.name()))
                .filter(transaction -> {
                    LocalDateTime d = DateParse.parseDate(transaction.getCreatedAt());
                    return d.compareTo(fromDate) >= 0 && d.compareTo(toDate) <= 0;
                }).forEach(transaction -> {
                    accountTrans.add(transaction);
                    if (transaction.getFromAccountId().equals(accountNum))
                        accountObj.setBalance(accountObj.getBalance() - transaction.getAmount());
                    else if (transaction.getToAccountId().equals(accountNum))
                        accountObj.setBalance(accountObj.getBalance() + transaction.getAmount());
            System.out.println("Git practice by Ravi");
            System.out.println("MergeThis to Branch2 ");
                }
        );
        return accountTrans;
    }

    public int calculateReversalPayments(List<Transaction> transactions, String accountNum,
                                         List<String> validTransactionIds, Account accountObj) {
        AtomicInteger count = new AtomicInteger();
        // Common method pass argument to  identify plus/minus  - shar
        // check for reversals and then process based on from acc or to acc
        transactions.stream().filter(transaction -> transaction.getTransactionType().
                equals(TransactionType.REVERSAL.name()))
                .filter(transaction -> transaction.getFromAccountId().equals(accountNum)
                        || transaction.getToAccountId().equals(accountNum)
                ).filter(transaction ->
                validTransactionIds.contains(transaction.getRelatedTransaction())
        ).forEach(transaction -> {
            if (transaction.getFromAccountId().equals(accountNum)) {
                accountObj.setBalance(accountObj.getBalance() + transaction.getAmount());
            }
            if (transaction.getToAccountId().equals(accountNum)) {
                accountObj.setBalance(accountObj.getBalance() - transaction.getAmount());
            }
            count.getAndIncrement();
        });
        return count.get();
    }

}
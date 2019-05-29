package org.me;

import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.Test;
import org.me.application.AccountSummary;
import org.me.model.Account;
import org.me.model.Transaction;
import org.me.utility.DateParse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple GetUserData.
 */
public class AccountSummaryTest
{

    /**
     *
     * **********   TESTING ACCOUNT SUMMARY **********
     *
     */

    @Test
    public void testAccountsummaryTestSet1()
    {

        String fileName = getClass().getResource("../../transactions_TestSet1.csv").getPath();

        AccountSummary accSum=new AccountSummary();
        String accountNumber = "ACC334455";
        String fromDateStr = "20/10/2018 12:00:00";
        String toDateStr = "20/10/2018 19:00:00";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);

        assertEquals(-25.0, accSum.getAccountSummary(fileName, accountNumber,fromDate,toDate),0.0);

        //assertEquals(2, accSum.);
    }


    @Test
    public void testAccountsummaryTestSet2()
    {

        String fileName = getClass().getResource("../../transactions_TestSet2.csv").getPath();
        AccountSummary accSum=new AccountSummary();
        String accountNumber = "ACC334455";
        String fromDateStr = "20/10/2018 12:47:55";
        String toDateStr = "21/10/2018 19:56:55";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);

        assertEquals(-70.0, accSum.getAccountSummary(fileName, accountNumber,fromDate,toDate),0.0);

    }

    @Test
    public void testAccountsummaryTestSet3()
    {

        String fileName = getClass().getResource("../../transactions_TestSet3.csv").getPath();

        AccountSummary accSum=new AccountSummary();
        String accountNumber = "ACC334465";
        String fromDateStr = "20/10/2018 12:47:55";
        String toDateStr = "21/10/2018 19:56:55";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);

        assertEquals(2.5, accSum.getAccountSummary(fileName, accountNumber,fromDate,toDate),0.0);

    }



    /**
     *
     * **********   TESTING NUMBER OF TRANSACTIONS **********
     *
     */


    @Test
    public void testNumOfTransactionsTestSet1() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet1.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:00:00";
        String toDateStr = "20/10/2018 19:00:00";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        List<String> validTransactionIds = accSum.getValidTransacationIds(accountTrans);

        int reversalCounts = accSum.calculateReversalPayments(transactions, accountNum, validTransactionIds, accountObj);

        assertEquals(1, validTransactionIds.size()-reversalCounts);

    }

    @Test
    public void testNumOfTransactionsTestSet2() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet2.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:47:55";
        String toDateStr = "21/10/2018 19:56:55";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        List<String> validTransactionIds = accSum.getValidTransacationIds(accountTrans);

        int reversalCounts = accSum.calculateReversalPayments(transactions, accountNum, validTransactionIds, accountObj);

        assertEquals(5, validTransactionIds.size()-reversalCounts);

    }


    @Test
    public void testNumOfTransactionsTestSet3() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet3.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334465";
        String fromDateStr = "20/10/2018 12:47:55";
        String toDateStr = "21/10/2018 19:56:55";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        List<String> validTransactionIds = accSum.getValidTransacationIds(accountTrans);

        int reversalCounts = accSum.calculateReversalPayments(transactions, accountNum, validTransactionIds, accountObj);

        assertEquals(4, validTransactionIds.size()-reversalCounts);

    }

    /**
     *
     * **********   TESTING filterValidPaymentTransactions() Method **********
     *
     */

    @Test
    public void testValidPaymentTransactionsTestSet1() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet1.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:00:00";
        String toDateStr = "20/10/2018 19:00:00";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        assertEquals(2, accountTrans.size());

    }

    @Test
    public void testValidPaymentTransactionsTestSet2() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet2.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:47:55";
        String toDateStr = "21/10/2018 19:56:55";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        assertEquals(6, accountTrans.size());

    }


    /**
     *
     * **********   TESTING getValidTransacationIds() Method **********
     *
     */

    @Test
    public void testValidTransactionsIDsTestSet1() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet1.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:00:00";
        String toDateStr = "20/10/2018 19:00:00";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        List<String> validTransactionIds = accSum.getValidTransacationIds(accountTrans);
        assertEquals(2, validTransactionIds.size());

    }

    /**
     *
     * **********   TESTING calculateReversalPayments() Method **********
     *
     */

    @Test
    public void testCalculateReversalPaymentsTestSet1() throws FileNotFoundException {


        String fileName = getClass().getResource("../../transactions_TestSet1.csv").getPath();
        List<Transaction> transactions = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Transaction.class).build().parse();

        AccountSummary accSum = new AccountSummary();
        String accountNum = "ACC334455";
        String fromDateStr = "20/10/2018 12:00:00";
        String toDateStr = "20/10/2018 19:00:00";
        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);
        Account accountObj = new Account();

        List<Transaction> accountTrans = accSum.filterValidPaymentTransactions(transactions,
                accountNum, fromDate, toDate, accountObj);

        List<String> validTransactionIds = accSum.getValidTransacationIds(accountTrans);

        int reversalCounts = accSum.calculateReversalPayments(transactions, accountNum, validTransactionIds, accountObj);

        assertEquals(1, reversalCounts);

    }


}

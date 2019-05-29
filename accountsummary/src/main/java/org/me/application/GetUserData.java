package org.me.application;

import org.me.utility.DateParse;

import java.time.LocalDateTime;

public class GetUserData {
    /**
     * @param args
     * @throws Exception Take command line arguments as mentioned below
     *                   1.CSV file with transactions, 2.accountID, 3.fromDate 4.today
     */
    public static void main(String[] args) throws Exception {

        String fileName = null, accountNum = null, fromDateStr = null, toDateStr = null;
        // Use iterative fashion for fetching args - Shar
        try {
            if (args.length > 0) {
                fileName = args[0];
                accountNum = args[1];
                fromDateStr = args[2];
                toDateStr = args[3];
            } else {
                System.out.println("No Arguments passed to program, exiting...");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            System.out.println(e.fillInStackTrace());
        }

        LocalDateTime fromDate = DateParse.parseDate(fromDateStr);
        LocalDateTime toDate = DateParse.parseDate(toDateStr);

        AccountSummary readCSVFile = new AccountSummary();
        readCSVFile.getAccountSummary(fileName, accountNum, fromDate, toDate);
    }
}

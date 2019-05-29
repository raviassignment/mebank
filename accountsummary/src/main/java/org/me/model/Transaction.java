package org.me.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {
    @CsvBindByName
    private String transactionId;
    @CsvBindByName
    private String fromAccountId;
    @CsvBindByName
    private String toAccountId;
    @CsvBindByName
    private String createdAt;
    @CsvBindByName
    private Float amount;
    @CsvBindByName
    private String transactionType;
    @CsvBindByName
    private String relatedTransaction;

}

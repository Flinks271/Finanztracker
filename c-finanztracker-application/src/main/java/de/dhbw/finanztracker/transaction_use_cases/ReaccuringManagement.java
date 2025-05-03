package de.dhbw.finanztracker.transaction_use_cases;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import de.dhbw.finanztracker.domain.IRepository;
import de.dhbw.finanztracker.domain.account.IAccount;
import de.dhbw.finanztracker.domain.account.transaction.counterparty.Counterparty;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.ReaccuringOneDayAMonth;

public class ReaccuringManagement {
    public static void deleteReaccuring(IRepository repository, IAccount account, IReaccuring reaccuring) {
        repository.deleteById(reaccuring.getReaccuringId());
        account.removeReaccuringTransaction(reaccuring);       
    } 

    public static IReaccuring createReaccuring(Map<String, Object> reaccuringData, IRepository repository, IAccount account, Counterparty counterparty, List<String> categories) {
        IReaccuring reaccuring = new ReaccuringOneDayAMonth(  
                                                (String)reaccuringData.get("name"), 
                                                (String)reaccuringData.get("description"), 
                                                categories, 
                                                Double.parseDouble((String) reaccuringData.get("amount")),
                                                (LocalDate)reaccuringData.get("start_date"), 
                                                (LocalDate)reaccuringData.get("last_modified_date"), 
                                                (LocalDate)reaccuringData.get("end_date"), 
                                                Integer.parseInt((String) reaccuringData.get("interval_in_days")), 
                                                Boolean.parseBoolean((String) reaccuringData.get("active")), 
                                                counterparty);
        
        String query = "INSERT INTO reaccuring (reaccuring_id, bank_account_id, reaccuring_name, reaccuring_description, amount, reaccuring_start_date, reaccuring_last_modified_date, reaccuring_end_date, interval_in_days, counterparty_id) VALUES " +
            "('" + reaccuring.getReaccuringId() + "', '" + account.getAccountId() + "', '" + reaccuring.getName() + "', '" + reaccuring.getDescription() + "', '" +
            reaccuring.getAmount() + "', '" + reaccuring.getStartDate() + "', '" + reaccuring.getLastModifiedDate() + "', " +
            (reaccuring.getEndDate() != null ? "'" + reaccuring.getEndDate() + "'" : "NULL") + ", '" + reaccuring.getIntervalInDays() + "', '" + 
            reaccuring.getCounterparty().getCounterpartyId() + "')";
        System.err.println(query);
        repository.save(query);           

        return reaccuring;
    }  

    public static void updateReaccuring(IRepository repository, IReaccuring reaccuring) {
        String query = "UPDATE reaccuring SET " +
                       "reaccuring_name = '" + reaccuring.getName() + "', " +
                       "reaccuring_description = '" + reaccuring.getDescription() + "', " +
                       "amount = '" + reaccuring.getAmount() + "', " +
                       "reaccuring_start_date = '" + reaccuring.getStartDate() + "', " +
                       "reaccuring_last_modified_date = '" + reaccuring.getLastModifiedDate() + "', " +
                       "reaccuring_end_date = " + (reaccuring.getEndDate() != null ? "'" + reaccuring.getEndDate() + "'" : "NULL") + ", " +
                       "interval_in_days = '" + reaccuring.getIntervalInDays() + "', " +
                       "counterparty_id = '" + reaccuring.getCounterparty().getCounterpartyId() + "' " +
                       "WHERE reaccuring_id = '" + reaccuring.getReaccuringId() + "'";
        
        System.err.println(query);
        repository.save(query);
    }
}


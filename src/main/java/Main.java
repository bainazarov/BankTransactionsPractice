import Data.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import static java.lang.Math.decrementExact;
import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) {
        String jsonFile = "bank_data.json";
        Consumer consumerData = readJsonFile(jsonFile);

        if (consumerData != null) {
            // 1
            int countNonZeroCreditLimitCount1 = countNonZeroCreditLimit(consumerData);
            System.out.println("Сделка с не нулевым кредитным лимитом " + countNonZeroCreditLimitCount1);
            // 2
            double findMaxBalance1 = findMaxBalance(consumerData);
            System.out.println("Максимальный баланс по всем сделками " + findMaxBalance1);
            // 3
            String findDealWithMaxCreditLimit1 = findDealWithMaxCreditLimit(consumerData);
            System.out.println("Тип сделки с самым большим кредитным лимитом за последние полтора года " + findDealWithMaxCreditLimit1);

            // 4


        }
    }

    private static Consumer readJsonFile(String jsonFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(jsonFile), Consumer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int countNonZeroCreditLimit(Consumer consumerData) {
        return  consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .filter(details -> details.getCreditLimit() != null && details.getCreditLimit().getAmount() > 0)
                .mapToInt(details -> 1)
                .sum();
    }

    private static double findMaxBalance(Consumer consumerData) {
         return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .flatMap(details -> details.getAccountBalances().stream())
                 .filter(AccountBalances -> AccountBalances.getAccountBalances() != null)
                .map(AccountBalances -> Double.parseDouble(AccountBalances.getAccountBalances()))
                 .max(Comparator.naturalOrder())
                 .orElse(0.0);
    }

    private static String findDealWithMaxCreditLimit(Consumer consumerData) {
        LocalDate yearAndHalf = LocalDate.now().minusYears(1).minusMonths(6);
        return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .filter(deal -> deal.getCaisAccStartDate().isAfter(yearAndHalf))
                .filter(deal -> deal.getCreditLimit() != null)
                .max(Comparator.comparing(deal ->  deal.getCreditLimit().getAmount()))
                .map(CAISDetails::getAccountType)
                .orElse("");
    }

    private static int countLastTwoYears(Consumer consumerData) {
        LocalDate startDate = LocalDate.now().minusYears(2);
        return consumerData.getConsumerData().getCais().stream()
                .flatMap(deal -> deal.getCaisDetails().stream())
                .filter(deal -> deal.getCaisAccStartDate().isAfter(startDate))
                .flatMap(details -> details.getAccountBalances().stream())
                .filter(balance -> balance.getStatus().compareTo(String.valueOf(BigDecimal.ZERO)) > 0)
                .mapToInt
    }
}

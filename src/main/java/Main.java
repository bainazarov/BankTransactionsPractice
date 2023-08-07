import data.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String jsonFile = "bank_data.json";
        Consumer consumerData = readJsonFile(jsonFile);

        if (consumerData != null) {
            // 1
            int countNonZeroCreditLimitCount1 = countNonZeroCreditLimit(consumerData);
            System.out.println("Сделка с не нулевым кредитным лимитом: " + countNonZeroCreditLimitCount1);
            // 2
            double findMaxBalance1 = findMaxBalance(consumerData);
            System.out.println("Максимальный баланс по всем сделками: " + findMaxBalance1);
            // 3
            String findDealWithMaxCreditLimit1 = findDealWithMaxCreditLimit(consumerData);
            System.out.println("Тип сделки с самым большим кредитным лимитом за последние полтора года: " + findDealWithMaxCreditLimit1);
            // 4
            int countLastTwoYears1 = countLastTwoYears(consumerData);
            System.out.println("Общее количество обновлений с просрочкой за последние 2 года: " + countLastTwoYears1);
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
        return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .filter(details -> details.getCreditLimit() != null && details.getCreditLimit().getAmount() > 0)
                .mapToInt(details -> 1)
                .sum();
    }

    private static double findMaxBalance(Consumer consumerData) {
        return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .map(CAISDetails::getBalance)
                .filter(Objects::nonNull)
                .mapToDouble(Balance::getAmount)
                .max()
                .orElse(0);

    }

    private static String findDealWithMaxCreditLimit(Consumer consumerData) {
        LocalDate yearAndHalf = LocalDate.now().minusYears(1).minusMonths(6);
        return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .filter(deal -> deal.getCaisAccStartDate().isAfter(yearAndHalf))
                .filter(deal -> deal.getCreditLimit() != null)
                .max(Comparator.comparing(deal -> deal.getCreditLimit().getAmount()))
                .map(CAISDetails::getAccountType)
                .orElse("");
    }

    private static int countLastTwoYears(Consumer consumerData) {
        LocalDate now = LocalDate.now();
        LocalDate yearAndHalfAgo = now.minusYears(1).minusMonths(6);
        LocalDate twoYearsAgo = now.minusYears(2);

        return consumerData.getConsumerData().getCais().stream()
                .flatMap(cais -> cais.getCaisDetails().stream())
                .filter(caisDetails -> caisDetails.getCaisAccStartDate().isAfter(twoYearsAgo))
                .flatMap(caisDetails -> {
                    int months = (int) ChronoUnit.MONTHS.between(caisDetails.getCaisAccStartDate(), caisDetails.getLastUpdatedDate());
                    int months1 = (int) ChronoUnit.MONTHS.between(yearAndHalfAgo, now);
                    long limit = Math.max(0, Math.min(months, months1));

                    return caisDetails.getAccountBalances().stream().limit(limit);
                })
                .mapToInt(balance -> 1)
                .sum();
    }
}

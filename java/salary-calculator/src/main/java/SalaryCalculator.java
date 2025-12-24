public class SalaryCalculator {
    final double PENALTY = 0.15;
    final int SALARY_MULTIPLIER = 1;
    final int BONUS_MULTIPLIER = 10;
    final int BIG_BONUS_MULTIPLIER = 13;
    final double BASE_SALARY = 1000;

    public double salaryMultiplier(int daysSkipped) {
        return daysSkipped >= 5 ? SALARY_MULTIPLIER - PENALTY : SALARY_MULTIPLIER;
    }

    public int bonusMultiplier(int productsSold) {
        return productsSold >= 20 ? BIG_BONUS_MULTIPLIER : BONUS_MULTIPLIER;
    }

    public double bonusForProductsSold(int productsSold) {
        return productsSold * bonusMultiplier(productsSold);
    }

    public double finalSalary(int daysSkipped, int productsSold) {
        double salary = BASE_SALARY * salaryMultiplier(daysSkipped) + bonusForProductsSold(productsSold);
        return salary > 2000 ? 2000 : salary;
    }
}

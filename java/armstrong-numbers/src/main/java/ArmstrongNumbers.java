class ArmstrongNumbers {

    boolean isArmstrongNumber(int numberToCheck) {
        String number = Integer.toString(numberToCheck);
        int numberOfDigits = number.length();
        int sum = 0;
        for (int i = 0; i < numberOfDigits; i++) {
            sum += (int) Math.pow(Character.getNumericValue(number.charAt(i)), numberOfDigits);
        }
        return sum == numberToCheck;
    }

}

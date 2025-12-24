class Greeter {

    String getGreeting() {
        return "Hello, World!";
    }

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        String message = greeter.getGreeting();
        System.out.println(message);
    }
}

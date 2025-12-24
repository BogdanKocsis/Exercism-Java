import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.util.stream.Collectors;

class RestApi {
    private final Map<String, User> users = new TreeMap<>();
    RestApi(User... users) {
        for (User user : users) {
            this.users.put(user.name(), user);
        }
    }
    public String get(String url) {
        if ("/users".equals(url)) {
            JSONArray userArray = new JSONArray();
            for (User user : users.values()) {
                userArray.put(toJson(user));
            }
            return new JSONObject().put("users", userArray).toString();
        }
        throw new IllegalArgumentException("Unsupported GET URL");
    }
    public String get(String url, JSONObject payload) {
        if ("/users".equals(url)) {
            JSONArray inputUsers = payload.getJSONArray("users");
            List<String> userList = new ArrayList<>();
            for (int i = 0; i < inputUsers.length(); i++) {
                userList.add(inputUsers.getString(i));
            }
            Collections.sort(userList);
            JSONArray result = new JSONArray();
            for (String name : userList) {
                result.put(toJson(users.get(name)));
            }
            return new JSONObject().put("users", result).toString();
        }
        throw new IllegalArgumentException("Unsupported GET URL");
    }
    public String post(String url, JSONObject payload) {
        switch (url) {
            case "/add":
                String name = payload.getString("user");
                User newUser = User.builder().setName(name).build();
                users.put(name, newUser);
                return toJson(newUser).toString();
            case "/iou":
                String lender = payload.getString("lender");
                String borrower = payload.getString("borrower");
                double amount = payload.getDouble("amount");
                User lenderUser = users.get(lender);
                User borrowerUser = users.get(borrower);
                Map<String, Double> lenderOwes = toMap(lenderUser.owes());
                Map<String, Double> lenderOwedBy = toMap(lenderUser.owedBy());
                Map<String, Double> borrowerOwes = toMap(borrowerUser.owes());
                Map<String, Double> borrowerOwedBy = toMap(borrowerUser.owedBy());
                // Handle existing reverse debts
                if (lenderOwes.containsKey(borrower)) {
                    double debt = lenderOwes.get(borrower);
                    if (amount == debt) {
                        lenderOwes.remove(borrower);
                        borrowerOwedBy.remove(lender);
                    } else if (amount < debt) {
                        lenderOwes.put(borrower, debt - amount);
                        borrowerOwedBy.put(lender, debt - amount);
                    } else {
                        lenderOwes.remove(borrower);
                        borrowerOwedBy.remove(lender);
                        double newAmount = amount - debt;
                        lenderOwedBy.put(borrower, lenderOwedBy.getOrDefault(borrower, 0.0) + newAmount);
                        borrowerOwes.put(lender, borrowerOwes.getOrDefault(lender, 0.0) + newAmount);
                    }
                } else {
                    lenderOwedBy.put(borrower, lenderOwedBy.getOrDefault(borrower, 0.0) + amount);
                    borrowerOwes.put(lender, borrowerOwes.getOrDefault(lender, 0.0) + amount);
                }
                users.put(lender, rebuildUser(lender, lenderOwes, lenderOwedBy));
                users.put(borrower, rebuildUser(borrower, borrowerOwes, borrowerOwedBy));
                JSONArray updatedUsers = new JSONArray();
                List<String> names = Arrays.asList(lender, borrower);
                Collections.sort(names);
                for (String n : names) {
                    updatedUsers.put(toJson(users.get(n)));
                }
                return new JSONObject().put("users", updatedUsers).toString();
            default:
                throw new IllegalArgumentException("Unsupported POST URL");
        }
    }
    private JSONObject toJson(User user) {
        JSONObject json = new JSONObject();
        json.put("name", user.name());
        JSONObject owesJson = new JSONObject();
        for (Iou iou : user.owes()) {
            owesJson.put(iou.name, round(iou.amount));
        }
        JSONObject owedByJson = new JSONObject();
        for (Iou iou : user.owedBy()) {
            owedByJson.put(iou.name, round(iou.amount));
        }
        double balance = user.owedBy().stream().mapToDouble(i -> i.amount).sum()
                - user.owes().stream().mapToDouble(i -> i.amount).sum();
        json.put("owes", owesJson);
        json.put("owedBy", owedByJson);
        json.put("balance", round(balance));
        return json;
    }
    private Map<String, Double> toMap(List<Iou> ious) {
        return ious.stream().collect(Collectors.toMap(i -> i.name, i -> i.amount));
    }
    private User rebuildUser(String name, Map<String, Double> owes, Map<String, Double> owedBy) {
        User.Builder builder = User.builder().setName(name);
        for (Map.Entry<String, Double> entry : owes.entrySet()) {
            if (entry.getValue() > 0)
                builder.owes(entry.getKey(), round(entry.getValue()));
        }
        for (Map.Entry<String, Double> entry : owedBy.entrySet()) {
            if (entry.getValue() > 0)
                builder.owedBy(entry.getKey(), round(entry.getValue()));
        }
        return builder.build();
    }
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
class Badge {
    public String print(Integer id, String name, String department) {
        String dep = (department == null) ? "Owner" : department;
        return (id != null) ? String.format("[%s] - %s - %s", id, name, dep.toUpperCase()) :
                String.format("%s - %s", name, dep.toUpperCase());
    }
}

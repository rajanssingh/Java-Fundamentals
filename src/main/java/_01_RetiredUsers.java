import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class _01_RetiredUsers {

    /**
     * Problem -
     * Find retired users
     * <p>
     * Input :
     * <p>
     * List<User> users = new ArrayList<>();
     * users. add(new User("Chris", 55, "M"));
     * users.add(new User("Kelly", 65, "F"));
     * users.add(new User("Paul", 70, "M"));
     * users.add(new User("Anthony", 52, "M"));
     * users.add(new User("Jenny", 25, "F"));
     * List<UserCriteria> userCriteriaList = new ArrayList<>();
     * userCriteriaList.add(new UserCriteria(60, "F"));
     * userCriteriaList.add(new UserCriteria(58, "M"));
     * <p>
     * Output:
     * [“Kelly”, “Paul”]
     * <p>
     * <p>
     * List<String> retiredUsers = findRetiredUsers(users, userCriteriaList);
     * <p>
     * private static List<String> findRetiredUsers(List<User> users, List<UserCriteria> userCriteriaList) {
     * List<String> retiredUsers = new ArrayList<>();
     * // Implement here
     * return retiredUsers;
     * }
     */

    public static class User {
        String name;
        Integer age;
        String gender;

        public User(String name, Integer age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class UserCriteria {
        Integer age;
        String gender;

        public UserCriteria(Integer age, String gender) {
            this.age = age;
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static List<String> findRetiredUsers(List<User> users, List<UserCriteria> userCriteriaList) {
        List<String> retiredUsers = new ArrayList<>();

        /*Map<String, Integer> criteria = userCriteriaList.stream().collect(Collectors.toMap(UserCriteria::getGender, UserCriteria::getAge));
        retiredUsers = users.stream().filter(user -> user.getAge() > criteria.get(user.getGender())).map(User::getName).collect(Collectors.toList());*/

        // Implement here
        // Optimized
        retiredUsers = users.parallelStream()
                .filter(user -> userCriteriaList.stream().anyMatch(userCriteria -> userCriteria.getGender().equals(user.getGender()) && userCriteria.getAge() < user.getAge()))
                .map(User::getName).collect(Collectors.toList());

        return retiredUsers;
    }

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("Chris", 55, "M"));
        users.add(new User("Kelly", 65, "F"));
        users.add(new User("Paul", 70, "M"));
        users.add(new User("Anthony", 52, "M"));
        users.add(new User("Jenny", 25, "F"));

        List<UserCriteria> userCriteriaList = new ArrayList<>();
        userCriteriaList.add(new UserCriteria(60, "F"));
        userCriteriaList.add(new UserCriteria(58, "M"));

        System.out.println(findRetiredUsers(users, userCriteriaList));

    }


}

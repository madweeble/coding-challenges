package rigup;

public class Palindrome {
    public static void main(String[] args) {
        final String s = "tacocat";
        final int result = countPalindromes(s);
        System.out.println(result);
    }

    public static int countPalindromes(String s) {
        String currString;
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length(); j > i; j--) {
                currString = s.substring(i, j);
                System.out.println(currString);
                if (currString.length() == 1 || isPalindrome(currString)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Check if this substring is a Palindrome
     * @param s the substring
     * @return boolean true if the string is a Palindrome, otherwise false
     */
    static boolean isPalindrome(String s) {
        for (int start = 0, end = s.length()-1; start < end; start++, end--) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
        }
        return true;
    }

}

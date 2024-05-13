using System;

namespace SampleDotNetProj
{
    // Takes a desired password length and the password itself as an input
    // Outputs the number of characters missing from the password for it to considered to be safe
    // A safe password should contain at least 1 number, 1 letter in both cases, and 1 special character
    // The output is a total number of missing chars, without specifics which criteria is violated
    public class PasswordComplexity {
        static void Main(string[] args) {
            // Read password length
            var length = 0;
            while (length <= 0) {
                Console.WriteLine("Enter the minimum password length:");
                int.TryParse(Console.ReadLine(), out length);
            }

            // Read password
            char[] userPassword = {};
            while (userPassword.Length == 0)
            {
                Console.WriteLine("Enter your password:");
                var enteredPassword = Console.ReadLine();
                if (enteredPassword != "")
                    userPassword = enteredPassword.ToCharArray();
            }

            int result = minCharNumber(length, userPassword);
            if (result > 0) Console.WriteLine("Your password misses " + result.ToString() + " characters");
            else Console.WriteLine("Your password is safe!");


            //Console.WriteLine("Enter the password\n");
            //char[] userPassword = Console.ReadLine().ToCharArray();
        }

        public static int minCharNumber(int reqPasswordLength, char[] password) {
            char[] numbers = "0123456789".ToCharArray();
            char[] lowCaseChars = "abcdefghijklmnopqrstuvwxyz".ToCharArray();
            char[] upCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".ToCharArray();
            char[] specialChars = "!@#$%^&*()-+".ToCharArray();

            int hasNoNumbers = 1;
            int hasNoLowerChars = 1;
            int hasNoUpperChars = 1;
            int hasNoSpecialChars = 1;

            foreach (char c in password) {
                if (numbers.Contains(c)) hasNoNumbers = 0;
                if (lowCaseChars.Contains(c)) hasNoLowerChars = 0;
                if (upCaseChars.Contains(c)) hasNoUpperChars = 0;
                if (specialChars.Contains(c)) hasNoSpecialChars = 0;
            }

            switch (password.Length < reqPasswordLength)
            {
                case true:
                        return reqPasswordLength - password.Length;
                    
                case false: {
                        int totalMissing = hasNoNumbers + hasNoLowerChars + hasNoUpperChars + hasNoSpecialChars;
                        return totalMissing > 0 ? totalMissing : 0;
                    }
            }
        }
    }
}

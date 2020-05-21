public class LeapYear {
    /** judge whether the input is leap year */
    public static boolean isLeapYear(int year){
        if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0)
            return true;
        return false;
    }

    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("please enter command line arguments!");
            System.out.println("e.g java LeapYear 2000");
        }

        for (int i = 0; i < args.length; i++){
            try{
                int year = Integer.parseInt(args[i]);
                if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0)
                    System.out.println(args[i] + " is a leap year.");
                else
                    System.out.println(args[i] + " is not a leap year.");
            }catch(NumberFormatException e){
                System.out.println(args[i] + " is not a valid number.");
            }
        }
    }
}

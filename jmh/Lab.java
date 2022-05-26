public class Lab
{
    private static boolean method1(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    private static boolean method2(String str)
    {
        char[] chars = str.toCharArray();
        for(char ch: chars)
        {
            if (!Character.isDigit(ch))
                return false;
        }
        return true;
    }

    private static boolean method3(String str)
    {
        return str.matches("^\\d+$");
    }

    public static void main(String[] args)
    {
        String str = "1o";
        System.out.println(method1(str));
        System.out.println(method2(str));
        System.out.println(method3(str));
    }
}
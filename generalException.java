import java.lang.Exception ;
import java.lang.String;
public class generalException extends Exception
{
   public generalException(String s)
   {
      System.out.println (s + " is throwing exception");
   }
};
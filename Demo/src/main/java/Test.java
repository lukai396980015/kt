import java.net.URLDecoder;

public class Test
{
    public static void main(String[] args) {
        int n=50;
        for(int x=0;x<n;x++)
        {
            for(int y=0;y<n/3;y++)
            {
                if(x+3*y==n)
                {
                    System.out.println("x="+x+",y="+y);
                }
            }
        }

    }
}

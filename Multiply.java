import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {
        int [] result = new int[2];
        int cost = 0;

        if(size==1)
        {
            result[0] = x*y;
            result[1] = 1;
            return result;
        }
        double m = Math.ceil((double)size/2);
        int poweroftwo = (int) (Math.pow(2.0, m));
        int a = x / poweroftwo; //a = x/(2^m)
        int b = x % poweroftwo; //b = x mod (2^m)
        int c = y / poweroftwo; //c = y/(2^m)
        int d = y % poweroftwo; //d = y mod (2^m)

        result = naive((int) m, a, c);
        int e = result[0];
        cost += result[1];
        result = naive((int) m, b, d);
        int f = result[0];
        cost += result[1];
        result = naive((int) m, b, c);
        int g = result[0];
        cost += result[1];
        result = naive((int) m, a, d);
        int h = result[0];
        cost += result[1];

        result[1] = cost + 3*(int)m; //3 arithmetic operations are about to be done
        result[0] = (int) (Math.pow(2.0, 2.0*m)) * e + poweroftwo * (g + h) + f; //2^2m*e + 2^2m(g+h) +f
        return result;

    }

    public static int[] karatsuba(int size, int x, int y) {

        int [] result = new int[2];
        int cost = 0;

        if(size==1)
        {
            result[0] = x*y;
            result[1] = 1;
            return result;
        }
        double m = Math.ceil((double)size/2);
        int poweroftwo = (int) (Math.pow(2.0, m));
        int a = x / poweroftwo; //a = x/(2^m)
        int b = x % poweroftwo; //b = x mod (2^m)
        int c = y / poweroftwo; //c = y/(2^m)
        int d = y % poweroftwo; //d = y mod (2^m)

        result = karatsuba((int) m, a, c);
        int e = result[0];
        cost += result[1];
        result = karatsuba((int) m, b, d);
        int f = result[0];
        cost += result[1];
        result = karatsuba((int) m, (a-b), (c-d));
        int g = result[0];
        cost += result[1];

        result[1] = cost + 6*(int)m; //3 arithmetic operations are about to be done
        result[0] = (int) (Math.pow(2.0, 2.0*m)) * e + poweroftwo * (e + f - g) + f; //2^2m*e + 2^2m(g+h) +f
        return result;
        
    }



    public static void main(String[] args){

        try{

            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}



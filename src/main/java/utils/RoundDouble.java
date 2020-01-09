package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundDouble {
    public static double roundDouble(double doubleToRound, int scale){
        //HALF_EVEN
        //Rounding mode to round towards the "nearest neighbor" unless both neighbors are equidistant, in which case, round towards the even neighbor.
        return new BigDecimal(doubleToRound).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
}

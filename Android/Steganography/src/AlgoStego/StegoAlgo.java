package AlgoStego;

import android.graphics.Bitmap;

/**
 * Created by Tamem on 5/23/13.
 */
public interface StegoAlgo {
    public Object stego(Bitmap im, String secret, Boolean whatDo);
}

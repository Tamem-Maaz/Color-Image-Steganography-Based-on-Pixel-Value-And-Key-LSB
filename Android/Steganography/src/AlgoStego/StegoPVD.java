package AlgoStego;

import java.util.LinkedList;

import android.graphics.Bitmap;

/**
 * Created by Tamem on 5/23/13.
 */
 abstract public class StegoPVD implements StegoAlgo {
	 static public LinkedList<int[]> theSkipColorPixels = new LinkedList<int[]>();
	Bitmap myBitmap = null; int myBitmap_Width = 0; int myBitmap_Height = 0; int[] all_Piexl_Color; int index_For_Partitional = 0 ;
    String Tsecret_Bits_All; int index_of_Tsecret_Bits = 0;
//    int[] all_NEW_Piexl_Stego;	
    int index_OF_NEW_Stego_Pixel = 0 ;

    // Function to get all pixel from the bitmap
    public void genaret_All_Pixel_Of_Bitmap(Bitmap bm){
    	myBitmap_Width = bm.getWidth();
    	myBitmap_Height = bm.getHeight();
    	myBitmap = bm.copy(bm.getConfig(), true);
    	all_Piexl_Color = new int[myBitmap_Width * myBitmap_Height];
    	myBitmap.getPixels(all_Piexl_Color, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
//    	 for (int i=0; i<myBitmap_Width;i++){
//        	for(int j=0; j<myBitmap_Height;j++){
//        		all_Piexl_Color[i] = bm.getPixel(i, j);
//        	}
//	      }
    }
    // Function to return Tow pixel form the array all_Piexl_Color
    public int[] get_next_Partition_Tow_Pixel(){
    	if(index_For_Partitional >= all_Piexl_Color.length) return null;
    	int [] Tow_Pixel = new int[2];
    	Tow_Pixel[0] = all_Piexl_Color[index_For_Partitional++];
    	Tow_Pixel[1] = all_Piexl_Color[index_For_Partitional++];
    	return Tow_Pixel;
    }
    // compute_difference value di for each block di=|pi-pi+1|
    public int compute_difference_Di(int Pi_1, int Pi_2){
        return Math.abs(Pi_1 - Pi_2);
    }
    // Function to choose the Range
    public int[] optimal_Range(int di){
        int[] optimal_range = new int[2];
        if(7 >= di){ optimal_range[0] = 0; optimal_range[1] = 7;}
        else if(15 >= di){ optimal_range[0] = 8; optimal_range[1] = 15;}
        else if(31 >= di){ optimal_range[0] = 16; optimal_range[1] = 31;}
        else if(63 >= di){ optimal_range[0] = 32; optimal_range[1] = 63;}
        else if(127 >= di){ optimal_range[0] = 64; optimal_range[1] = 127;}
        else if(255 >= di){ optimal_range[0] = 128; optimal_range[1] = 255;}
        return optimal_range;
    }
    // Wi =uk-lk+1.
    public int compute_Wi(int Ui, int Li){
        return Ui - Li + 1;
    }
    // Compute the amount of secret data bits t from the width wi of the optimum range, can be defined as t=⎿log2 wi⏌.
    public double compute_Number_Of_Bits(int Wi){
        return  Math.floor(Math.log10(Wi)/Math.log10(2));
    }
    // read the bits from secret to stego it
    public int[] read_T_Bit_From_Secret(int T_read){    // ,int[] Tsecret_Bits, int index_of_Tsecret_Bits
    	if(index_of_Tsecret_Bits >= Tsecret_Bits_All.length()) return null;
        // the first IF just to check if T_read can read it from Tsearet
        if(T_read >= Tsecret_Bits_All.length()) T_read = Tsecret_Bits_All.length();
        else{
            int check = Tsecret_Bits_All.length() - index_of_Tsecret_Bits;    // this to check if the bit what want read moor than Tsecret_Bits.length
            if( check < T_read )
                T_read = check;
        }

        int[] bits_readed  = new int[T_read];

        int startFrom = index_of_Tsecret_Bits;
        for (int i=startFrom, j=0; i < Tsecret_Bits_All.length() && j < T_read; i++,j++){
            bits_readed[j] = Integer.parseInt(String.valueOf(Tsecret_Bits_All.charAt(i)));
            index_of_Tsecret_Bits++;
        }
        return bits_readed;
    }
    // convert the b who readed from secret to Dec.
    public int convert_Secret_To_Dec(int[] Tsecret_bits){
        int b = 0;
        int bin = 1;
        for (int i= Tsecret_bits.length-1; i >= 0; i--){
            b+= Tsecret_bits[i] * bin;
            bin = bin * 2;
        }
        return b;
    }
    
    abstract public int[] embeding_Secret();
    abstract public String extracting_Secret(int pixel_num_used, int theLastStegoBitsSize, int theLastColorStego);
    @Override
    final public Object stego(Bitmap im, String secret, Boolean whatDo) {
    	Object returnObject = null;
        // [1] will contain the bitmap after embeded or extracted
        //        all_NEW_Piexl_Stego = new int[myBitmap_Width * myBitmap_Height];
        genaret_All_Pixel_Of_Bitmap(im);
        //************************************
        index_OF_NEW_Stego_Pixel = 0;
        if (whatDo)
        {	// if true do the Embeding
            Tsecret_Bits_All = toBinaryString(secret);
            int[] theInfo = embeding_Secret();
            //returnObject[0] = theInfo[0];			 the return number is mean how many pixel used
            //returnObject[1] = theInfo[1];			 the return number is mean how bit in the last stego
            //returnObject[2] = theInfo[2];			 the return number is mean where the stego stop color;
            int whathapend = theInfo[0];

            // ********************* LSB STEGO **************************
            String theSecretTOLSB = theInfo[0] + "," + theInfo[1] + "," + theInfo[2];
            for (int[] c : theSkipColorPixels)
            {
                theSecretTOLSB +=  "," + c[0] + c[1];
            }
            //MessageBox.Show("the LSB: " + theSecretTOLSB);
            theSecretTOLSB = toBinaryString(theSecretTOLSB);
            StegoLSB lsbStego = new StegoLSB();
            lsbStego.stego(all_Piexl_Color, theSecretTOLSB);
            // **********************************************************
            //MessageBox.Show("The Pixel Used For Stego is = " + ((int)returnObject[0]).ToString());

            if (whathapend > 0) // this mean all secret are embeded in the image
            {
            	myBitmap.setPixels(all_Piexl_Color, 0, im.getWidth(), 0, 0, im.getWidth(), im.getHeight());
            	returnObject = myBitmap;
            }
            else if (whathapend == -1)
            {
                // know will not do any thing because will return to the client
            }
        }
        //	******************************************** Extracting *************************************************************
        else
        {		// if false do the Extracting
            // ********************* Get The Secret Info From LSB For Get the Secret Stego In Image ********************* 
            String[] theSplit;
            int pixel_num_used = 0;
            int theLastStegoBitsSize = 0;
            int theLastColorStego = 0;
            try{
            	StegoLSB lsbStego = new StegoLSB();
                String theSecretLSB = lsbStego.extract(all_Piexl_Color);
            	theSplit = theSecretLSB.split(",");
            	pixel_num_used = Integer.parseInt(theSplit[0]);
            	theLastStegoBitsSize = Integer.parseInt(theSplit[1]);
                theLastColorStego = Integer.parseInt(theSplit[2]);
                for (int i = 3; i < theSplit.length; i++)
                {
                    int[] skip = new int[2];
                    skip[1] = Integer.parseInt(String.valueOf(theSplit[i].charAt(theSplit[i].length()-1)));
                    skip[0] = Integer.parseInt(theSplit[i].substring(0, theSplit[i].length() - 1));
                    theSkipColorPixels.add(skip);
                }
                Tsecret_Bits_All = "";
                returnObject = extracting_Secret(pixel_num_used, theLastStegoBitsSize, theLastColorStego);
            }
            catch (Exception e) {
            	returnObject = null;
			}
            // **********************************************************************************************************
        }
        // need to fill bitmap with all_NEW_Piexl_Stego		Function will write
        return returnObject;
    }
        
    public String toBinaryString(String s) {
        char[] cArray = s.toCharArray();
        
        StringBuilder sb = new StringBuilder();
        for (char c : cArray)
        {
        	int ch = (int)c;
            String cBinaryString = Integer.toBinaryString(ch);

            if (cBinaryString.length() != 8)
            {
                String tempStr = "00000000";
                char[] bin_Of_b = tempStr.toCharArray();

                for (int i = 8 - 1, j = cBinaryString.length() - 1; i >= 0; i--, j--)
                {
                    if (j >= 0)
                        bin_Of_b[i] = cBinaryString.charAt(j);
                }
                cBinaryString = "";
                for (int i = 0; i < bin_Of_b.length; i++)
                {
                    cBinaryString += bin_Of_b[i];
                }
            }

            sb.append(cBinaryString);
            //cBinaryString = Convert.ToString('#', 2);
            //sb.Append(cBinaryString);
            //cBinaryString = Convert.ToString('@', 2);
            //sb.Append(cBinaryString);
        }

        return sb.toString();
    }
}

using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Steganography.StegoAlgoCode
{
    abstract public class StegoPVD : StegoAlgoInt
    {
        protected List<int[]> theSkipColorPixels = new List<int[]>();
        protected Bitmap myBitmap = null;
        protected int myBitmap_Width = 0;
        protected int myBitmap_Height = 0;
        protected Color[] all_Piexl_Color;
        protected int index_For_Partitional = 0;
        protected int index_Of_All_Pixel_Color = 0;
        protected String Tsecret_Bits_All;
        protected int index_of_Tsecret_Bits = 0;
        protected int index_OF_NEW_Stego_Pixel = 0;

        // Function to get all pixel from the bitmap
        public void genaret_All_Pixel_Of_Bitmap(Bitmap bm)
        {
            myBitmap_Width = bm.Width;
            myBitmap_Height = bm.Height;
            myBitmap = bm;
            all_Piexl_Color = new Color[myBitmap_Width * myBitmap_Height];
            //mybitmap.getpixels(all_piexl_color, 0, bm.getwidth(), 0, 0, bm.getwidth(), bm.getheight());
            for (int i = 0; i < myBitmap_Height; i++)
            {
                for (int j = 0; j < myBitmap_Width; j++)
                {
                    all_Piexl_Color[index_Of_All_Pixel_Color++] = bm.GetPixel(j, i);
                }
            }
        }

        // Function to return Tow pixel form the array all_Piexl_Color
        public Color[] get_next_Partition_Tow_Pixel()
        {
            if (index_For_Partitional >= all_Piexl_Color.Length) return null;
            Color[] Tow_Pixel = new Color[2];
            Tow_Pixel[0] = all_Piexl_Color[index_For_Partitional++];
            Tow_Pixel[1] = all_Piexl_Color[index_For_Partitional++];
            return Tow_Pixel;
        }

        // compute_difference value di for each block di=|pi-pi+1|
        public int compute_difference_Di(int Pi_1, int Pi_2)
        {
            return Math.Abs(Pi_1 - Pi_2);
        }

        // Function to choose the Range
        public int[] optimal_Range(int di)
        {
            int[] optimal_range = new int[2];
            if (7 >= di) { optimal_range[0] = 0; optimal_range[1] = 7; }
            else if (15 >= di) { optimal_range[0] = 8; optimal_range[1] = 15; }
            else if (31 >= di) { optimal_range[0] = 16; optimal_range[1] = 31; }
            else if (63 >= di) { optimal_range[0] = 32; optimal_range[1] = 63; }
            else if (127 >= di) { optimal_range[0] = 64; optimal_range[1] = 127; }
            else if (255 >= di) { optimal_range[0] = 128; optimal_range[1] = 255; }
            return optimal_range;
        }

        // Wi =uk-lk+1.
        public int compute_Wi(int Ui, int Li)
        {
            return Ui - Li + 1;
        }

        // Compute the amount of secret data bits t from the width wi of the optimum range, can be defined as t=⎿log2 wi⏌.
        public double compute_Number_Of_Bits(int Wi)
        {
            return Math.Floor(Math.Log10(Wi) / Math.Log10(2));
        }

        // read the bits from secret to stego it
        public int[] read_T_Bit_From_Secret(int T_read)
        {    // ,int[] Tsecret_Bits, int index_of_Tsecret_Bits
            if (index_of_Tsecret_Bits >= Tsecret_Bits_All.Length) return null;
            // the first IF just to check if T_read can read it from Tsearet
            if (T_read >= Tsecret_Bits_All.Length) T_read = Tsecret_Bits_All.Length;
            else
            {
                int check = Tsecret_Bits_All.Length - index_of_Tsecret_Bits;    // this to check if the bit what want read moor than Tsecret_Bits.length
                if (check < T_read)
                    T_read = check;
            }

            int[] bits_readed = new int[T_read];

            int startFrom = index_of_Tsecret_Bits;
            for (int i = startFrom, j = 0; i < Tsecret_Bits_All.Length && j < T_read; i++, j++)
            {
                //MessageBox.Show(Tsecret_Bits_All[i].ToString());
                bits_readed[j] = int.Parse(Tsecret_Bits_All[i].ToString());
                index_of_Tsecret_Bits++;
            }
            return bits_readed;
        }

        // convert the b who readed from secret to Dec.
        public int convert_Secret_To_Dec(int[] Tsecret_bits)
        {
            int b = 0;
            int bin = 1;
            for (int i = Tsecret_bits.Length - 1; i >= 0; i--)
            {
                b += Tsecret_bits[i] * bin;
                bin = bin * 2;
            }
            return b;
        }

        abstract public int[] embeding_Secret();
        abstract public String extracting_Secret(int pixel_num_used, int theLastStegoBitsSize, int theLastColorStego);

        public Object stego(Bitmap im, String secret, Boolean whatDo)
        {
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
                foreach (int[] c in theSkipColorPixels)
                {
                    theSecretTOLSB += "," + c[0] + c[1];
                }
                //MessageBox.Show("the LSB: " + theSecretTOLSB);
                theSecretTOLSB = toBinaryString(theSecretTOLSB);
                StegoLSB lsbStego = new StegoLSB();
                lsbStego.stego(all_Piexl_Color, theSecretTOLSB);
                // **********************************************************
                //MessageBox.Show("The Pixel Used For Stego is = " + ((int)returnObject[0]).ToString());

                if (whathapend > 0) // this mean all secret are embeded in the image
                {
                    //myBitmap.setPixels(all_Piexl_Color, 0, im.getWidth(), 0, 0, im.getWidth(), im.getHeight());
                    index_Of_All_Pixel_Color = 0;
                    for (int i = 0; i < myBitmap_Height; i++)
                    {
                        for (int j = 0; j < myBitmap_Width; j++)
                        {
                            myBitmap.SetPixel(j, i, all_Piexl_Color[index_Of_All_Pixel_Color++]);
                        }
                    }
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

                try
                {
                    StegoLSB lsbStego = new StegoLSB();
                    String theSecretLSB = lsbStego.extract(all_Piexl_Color);
                    theSplit = theSecretLSB.Split(',');
                    pixel_num_used = int.Parse(theSplit[0]);
                    theLastStegoBitsSize = int.Parse(theSplit[1]);
                    theLastColorStego = int.Parse(theSplit[2]);
                    for (int i = 3; i < theSplit.Length; i++)
                    {
                        int[] skip = new int[2];
                        skip[1] = int.Parse(theSplit[i][theSplit[i].Length - 1].ToString());
                        skip[0] = int.Parse(theSplit[i].Substring(0, theSplit[i].Length - 1));
                        theSkipColorPixels.Add(skip);
                    }
                    Tsecret_Bits_All = "";
                    returnObject = extracting_Secret(pixel_num_used, theLastStegoBitsSize, theLastColorStego);
                }
                catch (Exception e)
                {
                    returnObject = null;
                }
                // **********************************************************************************************************


            }
            // need to fill bitmap with all_NEW_Piexl_Stego		Function will write
            return returnObject;
        }


        public String toBinaryString(String s)
        {
            char[] cArray = s.ToCharArray();

            StringBuilder sb = new StringBuilder();

            foreach (char c in cArray)
            {
                String cBinaryString = Convert.ToString(c, 2);

                if (cBinaryString.Length != 8)
                {
                    String tempStr = new String('0', 8);
                    char[] bin_Of_b = tempStr.ToCharArray();

                    for (int i = 8 - 1, j = cBinaryString.Length - 1; i >= 0; i--, j--)
                    {
                        if (j >= 0)
                            bin_Of_b[i] = cBinaryString[j];
                    }
                    cBinaryString = "";
                    for (int i = 0; i < bin_Of_b.Length; i++)
                    {
                        cBinaryString += bin_Of_b[i];
                    }

                    //MessageBox.Show(c + " = " +cBinaryString + " L= " + cBinaryString.Length);
                }

                sb.Append(cBinaryString);
                //cBinaryString = Convert.ToString('#', 2);
                //sb.Append(cBinaryString);
                //cBinaryString = Convert.ToString('@', 2);
                //sb.Append(cBinaryString);
            }

            return sb.ToString();
        }
    }
}

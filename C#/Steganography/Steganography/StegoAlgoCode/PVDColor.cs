using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Steganography.StegoAlgoCode
{
    class PVDColor : StegoPVD
    {
        public override int[] embeding_Secret()
        {
            int[] theReturnArrayInfo = new int[3];
            int how_many_piexl_used = 0;
            int theLastColorStego = 0;
            bool bit_discarded = false;
            Color[] Tow_Pixel_Original = get_next_Partition_Tow_Pixel();					// 1
            int[] pi_1 = new int[3]; int[] pi_2 = new int[3];
            int index_Of_RGB = 0;
            int theLastStegoBitsSize = 0;
            bool mustReturn = false;
            int[] Tow_new_Pixel = null;
            index_OF_NEW_Stego_Pixel = 0;
            while (Tow_Pixel_Original != null)
            {
                how_many_piexl_used++;
                //if (how_many_piexl_used == 57)
                //{
                //     MessageBox.Show("Tamem");
                //}
                bit_discarded = false;
                index_Of_RGB = 0;
                int colorRGB = 0;

                while (colorRGB < 3)
                {
                    bit_discarded = false;
                    if (colorRGB == 0) { index_Of_RGB = 0; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].R; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].R; }
                    else if (colorRGB == 1) { index_Of_RGB = 1; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].G; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].G; }
                    else if (colorRGB == 2) { index_Of_RGB = 2; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].B; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].B; }

                    int di = compute_difference_Di(pi_1[index_Of_RGB], pi_2[index_Of_RGB]);						// 2
                    int[] opt_Range = optimal_Range(di);								// 3
                    int wi = compute_Wi(opt_Range[1], opt_Range[0]);					// 4
                    int t = (int)compute_Number_Of_Bits(wi);							// 4
                    if (colorRGB == 0 && t > 5)
                    {
                        int[] tempSkip = { how_many_piexl_used, 0 };
                        theSkipColorPixels.Add(tempSkip);
                       // MessageBox.Show("Red: " + how_many_piexl_used.ToString());
                        colorRGB++; continue;
                    }									// 5
                    else if (colorRGB == 1 && t > 3)
                    {
                        int[] tempSkip = { how_many_piexl_used, 1 };
                        theSkipColorPixels.Add(tempSkip);
                        //MessageBox.Show("Green: " + how_many_piexl_used.ToString());
                        colorRGB++; continue;
                    }							// 5

                    int[] t_bits = read_T_Bit_From_Secret(t);							// 6
                    if (t_bits != null && t_bits.Length < 3)
                    {
                        theLastStegoBitsSize = t_bits.Length;
                    }
                    mustReturn = false;
                    if (t_bits == null)	// when return 1 this mean the secret finshed and all secret embeding
                    {
                        //	            	  theLastColorStego = index_Of_RGB;
                        if (index_Of_RGB == 0)
                        {
                            how_many_piexl_used--;
                            theLastColorStego = 2;
                        }
                        else if (index_Of_RGB == 1) theLastColorStego = 0;
                        else if (index_Of_RGB == 2) theLastColorStego = 1;

                        mustReturn = true;
                    }
                    if (!mustReturn)
                    {
                        int b = convert_Secret_To_Dec(t_bits);							// 6
                        int di_new = opt_Range[0] + b;				// 6
                        //***************************************** 7 ****************************************************
                        Tow_new_Pixel = original_PVD(di, di_new, pi_1[index_Of_RGB], pi_2[index_Of_RGB]);	// 7
                        if (Tow_new_Pixel[0] > 255 || Tow_new_Pixel[1] > 255 || Tow_new_Pixel[0] < 0 || Tow_new_Pixel[1] < 0)
                        {									// 8
                            if (t_bits[0] == 1)
                            {																// 8.1
                                t_bits[0] = 0;
                                bit_discarded = true;
                                b = convert_Secret_To_Dec(t_bits);
                                di_new = opt_Range[0] + b;
                            }
                            Tow_new_Pixel = original_PVD(di, di_new, pi_1[index_Of_RGB], pi_2[index_Of_RGB]);// 8.2
                            if (Tow_new_Pixel[0] > 255 || Tow_new_Pixel[1] > 255 || Tow_new_Pixel[0] < 0 || Tow_new_Pixel[1] < 0)
                            {								// 8.2
                                int m = Math.Abs(di_new - di);
                                if (Tow_new_Pixel[1] >= Tow_new_Pixel[0] && Tow_new_Pixel[1] > 255)
                                {
                                    Tow_new_Pixel[0] = pi_1[index_Of_RGB] - m;
                                    Tow_new_Pixel[1] = pi_2[index_Of_RGB];
                                }
                                else if (Tow_new_Pixel[1] < Tow_new_Pixel[0] && Tow_new_Pixel[0] > 255)
                                {
                                    Tow_new_Pixel[0] = pi_1[index_Of_RGB];
                                    Tow_new_Pixel[1] = pi_2[index_Of_RGB] - m;
                                }
                                else if (Tow_new_Pixel[1] >= Tow_new_Pixel[0] && Tow_new_Pixel[0] < 0)
                                {
                                    Tow_new_Pixel[0] = pi_1[index_Of_RGB];
                                    Tow_new_Pixel[1] = pi_2[index_Of_RGB] + m;
                                }
                                else if (Tow_new_Pixel[1] < Tow_new_Pixel[0] && Tow_new_Pixel[1] < 0)
                                {
                                    Tow_new_Pixel[0] = pi_1[index_Of_RGB] + m;
                                    Tow_new_Pixel[1] = pi_2[index_Of_RGB];
                                }
                            }
                        }
                        // Now, the pixel block (pi, pi+1) is replaced by (pi' , pi+1')				// 9
                        String temp_New_Piexl_1 = Convert.ToString(Tow_new_Pixel[0], 2);
                        String temp_New_Piexl_2 = Convert.ToString(Tow_new_Pixel[1], 2);
                        if (bit_discarded == false)
                        {													// 10
                            if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '0' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '0')		// i
                                Tow_new_Pixel[1] += 1;
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '0' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '1')	// ii
                            {
                                if (Tow_new_Pixel[1] < 255 && Tow_new_Pixel[0] >= 0) Tow_new_Pixel[1] += 1;		// a
                                else if (Tow_new_Pixel[0] > 0 && Tow_new_Pixel[1] == 255)
                                {						// b
                                    Tow_new_Pixel[0] -= 2;
                                    Tow_new_Pixel[1] -= 1;
                                }
                                else if (Tow_new_Pixel[0] == 0 && Tow_new_Pixel[1] == 255)							// c
                                    Tow_new_Pixel[1] = Tow_new_Pixel[1];
                            }
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '1' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '0')	// iii
                                Tow_new_Pixel[0] -= 1;
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '1' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '1')	// iv
                                Tow_new_Pixel[0] -= 1;

                        }
                        else
                        {
                            if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '0' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '0')
                            {		// i
                                Tow_new_Pixel[0] += 1;
                            }
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '0' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '1')	// ii
                                Tow_new_Pixel[0] += 1;
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '1' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '0')	// iii
                            {
                                if (Tow_new_Pixel[1] > 0 && Tow_new_Pixel[0] <= 255) Tow_new_Pixel[1] -= 1;		// a
                                else if (Tow_new_Pixel[0] < 255 && Tow_new_Pixel[1] == 0)
                                {						// b
                                    Tow_new_Pixel[0] += 2;
                                    Tow_new_Pixel[1] += 1;
                                }
                            }
                            else if (temp_New_Piexl_1[temp_New_Piexl_1.Length - 1] == '1' && temp_New_Piexl_2[temp_New_Piexl_2.Length - 1] == '1')	// iv
                                Tow_new_Pixel[1] -= 1;
                        }
                        // Now we get the stego blocks	11
                        //***************************************** 7 ****************************************************
                    }

                    pi_1[index_Of_RGB] = Tow_new_Pixel[0];	// fill with stego pixel color
                    pi_2[index_Of_RGB] = Tow_new_Pixel[1];	// fill with stego pixel color
                    if (mustReturn) break;
                    //index_Of_RGB++;
                    colorRGB++;
                }
                all_Piexl_Color[index_OF_NEW_Stego_Pixel++] = Color.FromArgb(pi_1[0], pi_1[1], pi_1[2]);
                all_Piexl_Color[index_OF_NEW_Stego_Pixel++] = Color.FromArgb(pi_2[0], pi_2[1], pi_2[2]);
                theReturnArrayInfo[0] = how_many_piexl_used;
                theReturnArrayInfo[1] = theLastStegoBitsSize;
                theReturnArrayInfo[2] = theLastColorStego;
                if (mustReturn) return theReturnArrayInfo;
                Tow_Pixel_Original = get_next_Partition_Tow_Pixel();
            }
            theReturnArrayInfo[0] = -1;
            theReturnArrayInfo[1] = theLastStegoBitsSize;
            theReturnArrayInfo[2] = theLastColorStego;
            return theReturnArrayInfo;		// when return -1 this mean the Photo is fineshed and some secret not embeding

        }

        private int[] original_PVD(int di, int di_new, int pi_1, int pi_2)
        {
            int m = Math.Abs(di_new - di);

            if (pi_1 >= pi_2 && di_new > di)
            {
                pi_1 = (int)(pi_1 + Math.Ceiling((double)m / 2));
                pi_2 = (int)(pi_2 - Math.Floor((double)m / 2));
            }
            else if (pi_1 < pi_2 && di_new > di)
            {
                pi_1 = (int)(pi_1 - Math.Floor((double)m / 2));
                pi_2 = (int)(pi_2 + Math.Ceiling((double)m / 2));
            }
            else if (pi_1 >= pi_2 && di_new <= di)
            {
                pi_1 = (int)(pi_1 - Math.Ceiling((double)m / 2));
                pi_2 = (int)(pi_2 + Math.Floor((double)m / 2));
            }
            else if (pi_1 < pi_2 && di_new <= di)
            {
                pi_1 = (int)(pi_1 + Math.Ceiling((double)m / 2));
                pi_2 = (int)(pi_2 - Math.Floor((double)m / 2));
            }


            int[] Tow_Stego_Pixel = { pi_1, pi_2 };
            return Tow_Stego_Pixel;
        }

        int index_pixel_used = 0;
        int colorRGB = 0;
        public override string extracting_Secret(int pixel_num_used, int theLastStegoBitsSize, int theLastColorStego)
        {
            index_pixel_used = 0;
            //List<int[]> theSkipColorPixels = theSkipColorPixels;
            Color[] Tow_Pixel_Original = get_next_Partition_Tow_Pixel();					// 1
            int[] pi_1 = new int[3]; int[] pi_2 = new int[3];
            int index_Of_RGB = 0;
            while ((index_pixel_used < pixel_num_used) && (Tow_Pixel_Original != null))
            {
                index_pixel_used++;
                //if (index_pixel_used == 57)
                //{
                //    MessageBox.Show("Tamem");
                //}
                index_Of_RGB = 0;
                colorRGB = 0;
                while (colorRGB < 3)
                {
                    if (colorRGB == 0) { index_Of_RGB = 0; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].R; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].R; }
                    else if (colorRGB == 1) { index_Of_RGB = 1; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].G; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].G; }
                    else if (colorRGB == 2) { index_Of_RGB = 2; pi_1[index_Of_RGB] = Tow_Pixel_Original[0].B; pi_2[index_Of_RGB] = Tow_Pixel_Original[1].B; }

                    // Step 2:	 Check the LSB positions of Pi (first pixel  of the block) for each block and do the following
                    String pi_1_LSB = Convert.ToString(pi_1[index_Of_RGB], 2);
                    int temp_Pi_1 = pi_1[index_Of_RGB];
                    if (pi_1_LSB[pi_1_LSB.Length - 1] == '0')
                        temp_Pi_1++;
                    else
                        temp_Pi_1--;
                    // Step 3: 	Now calculate the difference value di of two consecutive pixels of each block by using the formula di=|pi-pi+1| .
                    int di = compute_difference_Di(temp_Pi_1, pi_2[index_Of_RGB]);
                    // Step 4: 	Find the appropriate range Ri for the difference di.
                    int[] opt_Range = optimal_Range(di);
                    // Step 5
                    int wi = compute_Wi(opt_Range[1], opt_Range[0]);
                    int t = (int)compute_Number_Of_Bits(wi);
                    // Step 6
                    //if (index_pixel_used == 57)
                    //    MessageBox.Show("ddd");
                    int[] theMustSkip = {-1,-1};
                    if(theSkipColorPixels.Count != 0)
                        theMustSkip = theSkipColorPixels.First<int[]>();
                    if (colorRGB == 0 && t > 5) 
                    {
                        //int index = theSkipColorPixels.FindIndex(skipRedGreenError);
                        //if(index != -1)
                        //    theSkipColorPixels.RemoveAt(index);
                        theSkipColorPixels.RemoveAt(0);
                        //MessageBox.Show("Red: " + index_pixel_used.ToString());
                        colorRGB++; continue; 
                    }
                    else if (colorRGB == 1 && t > 3) 
                    {
                        //int index = theSkipColorPixels.FindIndex(skipRedGreenError);
                        //if(index != -1)
                        //    theSkipColorPixels.RemoveAt(index);
                        theSkipColorPixels.RemoveAt(0);
                        //MessageBox.Show("Green: " + index_pixel_used.ToString()); 
                        colorRGB++; continue;
                    }
                    else if (theMustSkip[0] == index_pixel_used && theMustSkip[1] == colorRGB)//theSkipColorPixels.Exists(skipRedGreenError))
                    {
                       // MessageBox.Show("the Skip Red: " + index_pixel_used.ToString());
                        //int index = theSkipColorPixels.FindIndex(skipRedGreenError);
                        //if(index != -1)
                        //theSkipColorPixels.RemoveAt(index);
                        theSkipColorPixels.RemoveAt(0);
                        colorRGB++; continue; 
                    }
                    // Step 7: 	Extract ‘t’ bits, by the extracting method of original PVD just STEP 5.
                    //  b = li – di  = 32 – 46 = 14
                    //	b = 01110
                    int b = Math.Abs(opt_Range[0] - di);

                    String tempStr = new String('0', t);
                    char[] bin_Of_b = tempStr.ToCharArray();
                    // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                    String newBin = Convert.ToString(b, 2);
                    for (int i = t - 1, j = newBin.Length - 1; i >= 0; i--, j--)
                    {
                        if (j >= 0)
                            bin_Of_b[i] = newBin[j];
                    }
                    newBin = "";
                    String withChange = "";
                    for (int i = 0; i < bin_Of_b.Length; i++)
                    {
                        newBin += bin_Of_b[i];
                        withChange += bin_Of_b[i];
                    }
                    // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&	            
                    // Step 8
                    if (pi_1_LSB[pi_1_LSB.Length - 1] == '1')
                    {
                        char MSB = '1';
                        withChange = newBin.Substring(1);
                        withChange = MSB + withChange;
                    }
                    if (index_pixel_used == pixel_num_used && theLastColorStego == index_Of_RGB)
                    {
                        if (theLastStegoBitsSize != 0)
                            withChange = withChange.Substring(withChange.Length - theLastStegoBitsSize);
                        Tsecret_Bits_All += withChange;
                        break;
                    }
                    else
                    {
                        Tsecret_Bits_All += withChange;
                    }
                    colorRGB++;
                }
                Tow_Pixel_Original = get_next_Partition_Tow_Pixel();
                //index_pixel_used+= 2;
            }
            return Tsecret_Bits_All;		// when return 2 this mean the Photo is fineshed and some secret not embeding

        }
    }
}

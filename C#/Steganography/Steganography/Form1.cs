using Steganography.StegoAlgoCode;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Steganography
{
    public partial class Form1 : Form
    {
        public Bitmap basic_image = null, modefide_image = null;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }



        private void openImage_Click(object sender, EventArgs e)
        {
            try
            {
                OpenFileDialog of = new OpenFileDialog();
                of.Filter = "Image Files(*.jpg,*.jpeg,*.bmp,*.png)|*.jpg;*.jpeg;*.bmp;*.png";
                if (of.ShowDialog() == DialogResult.OK)
                {
                    basic_image = new Bitmap(of.FileName,false);
                    image.Image = basic_image;
                }
            }
            catch (Exception)
            {
                throw new ApplicationException(" Faild to load image ");
            }

        }

        private void hideText_Click(object sender, EventArgs e)
        {
            StegoAlgoInt ss = new PVDColor();
            String str = textBox1.Text;
            Object obj = ss.stego(basic_image, str, true);
            modefide_image = (Bitmap)obj;

            image_mod.Image = modefide_image;

            MessageBox.Show("The Stego Done!");
        }

        private void showText_Click(object sender, EventArgs e)
        {
            if (basic_image != null)
            {
                StegoAlgoInt ss = new PVDColor();
                Object obj = ss.stego(basic_image, "", false);

                if (obj != null)
                {
                    String aa = (String)obj;

                    StringBuilder thelast = new StringBuilder();
                    StringBuilder strChar = new StringBuilder();
                    for (int i = 0; i < aa.Length; )
                    {
                        strChar.Clear();
                        for (int j = 0; j < 8; j++)
                        {
                            if (i < aa.Length)
                                strChar.Append(aa[i++]);
                        }
                        int b = Convert.ToInt32(strChar.ToString(), 2);
                        //textBox2.Text += b.ToString() + " : " + strChar.ToString() + " /n ";
                        thelast.Append(((char)b).ToString());
                    }
                    textBox1.Text = thelast.ToString(); //thelast.ToString();
                    MessageBox.Show("Done!");
                }
                else
                {
                    textBox1.Text = "No Stego In This Image !!";
                }
            }
            else
            {
                textBox1.Text = "Please Open Image !!";
            }
        }

        private void saveImage_Click(object sender, EventArgs e)
        {
            SaveFileDialog sf = new SaveFileDialog();
            sf.Filter = "PNG Files|*.png";
            if (sf.ShowDialog() == DialogResult.OK) 
            {
                modefide_image.Save(sf.FileName, System.Drawing.Imaging.ImageFormat.Png);
                // image_mod.Image.Save(sf.FileName, System.Drawing.Imaging.ImageFormat.Png);
            }
        }







    }
}

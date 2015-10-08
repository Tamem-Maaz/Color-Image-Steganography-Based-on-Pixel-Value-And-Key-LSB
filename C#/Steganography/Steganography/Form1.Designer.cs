namespace Steganography
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.openImage = new System.Windows.Forms.Button();
            this.image = new System.Windows.Forms.PictureBox();
            this.saveImage = new System.Windows.Forms.Button();
            this.hideText = new System.Windows.Forms.Button();
            this.showText = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.image_mod = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.image)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.image_mod)).BeginInit();
            this.SuspendLayout();
            // 
            // openImage
            // 
            this.openImage.Location = new System.Drawing.Point(141, 474);
            this.openImage.Name = "openImage";
            this.openImage.Size = new System.Drawing.Size(75, 23);
            this.openImage.TabIndex = 0;
            this.openImage.Text = "Open Image";
            this.openImage.UseVisualStyleBackColor = true;
            this.openImage.Click += new System.EventHandler(this.openImage_Click);
            // 
            // image
            // 
            this.image.Location = new System.Drawing.Point(2, 2);
            this.image.Name = "image";
            this.image.Size = new System.Drawing.Size(361, 296);
            this.image.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.image.TabIndex = 4;
            this.image.TabStop = false;
            // 
            // saveImage
            // 
            this.saveImage.Location = new System.Drawing.Point(530, 474);
            this.saveImage.Name = "saveImage";
            this.saveImage.Size = new System.Drawing.Size(75, 23);
            this.saveImage.TabIndex = 5;
            this.saveImage.Text = "Save Image";
            this.saveImage.UseVisualStyleBackColor = true;
            this.saveImage.Click += new System.EventHandler(this.saveImage_Click);
            // 
            // hideText
            // 
            this.hideText.Location = new System.Drawing.Point(449, 474);
            this.hideText.Name = "hideText";
            this.hideText.Size = new System.Drawing.Size(75, 23);
            this.hideText.TabIndex = 6;
            this.hideText.Text = "Hide Text";
            this.hideText.UseVisualStyleBackColor = true;
            this.hideText.Click += new System.EventHandler(this.hideText_Click);
            // 
            // showText
            // 
            this.showText.Location = new System.Drawing.Point(222, 474);
            this.showText.Name = "showText";
            this.showText.Size = new System.Drawing.Size(75, 23);
            this.showText.TabIndex = 7;
            this.showText.Text = "Show Text";
            this.showText.UseVisualStyleBackColor = true;
            this.showText.Click += new System.EventHandler(this.showText_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(2, 304);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(743, 164);
            this.textBox1.TabIndex = 8;
            // 
            // image_mod
            // 
            this.image_mod.Location = new System.Drawing.Point(381, 2);
            this.image_mod.Name = "image_mod";
            this.image_mod.Size = new System.Drawing.Size(364, 296);
            this.image_mod.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.image_mod.TabIndex = 9;
            this.image_mod.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(746, 509);
            this.Controls.Add(this.image_mod);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.showText);
            this.Controls.Add(this.hideText);
            this.Controls.Add(this.saveImage);
            this.Controls.Add(this.image);
            this.Controls.Add(this.openImage);
            this.Name = "Form1";
            this.Text = "Steganograghy";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.image)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.image_mod)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button openImage;
        private System.Windows.Forms.PictureBox image;
        private System.Windows.Forms.Button saveImage;
        private System.Windows.Forms.Button hideText;
        private System.Windows.Forms.Button showText;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.PictureBox image_mod;
    }
}


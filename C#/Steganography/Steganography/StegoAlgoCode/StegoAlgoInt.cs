using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Steganography.StegoAlgoCode
{
    public interface StegoAlgoInt
    {
        Object stego(Bitmap im, String secret, Boolean whatDo);
    }
}

/*
 * MIT License
 *
 * Copyright (c) 2017
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package newph.core.staticFunction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import newph.core.dib.COLOR;
import newph.core.dib.COLOR_MASK;
import newph.core.enumeration.ColorType;
import newph.core.dib.iDib;
import newph.core.memory.iBuffColor;

/**
 * Static RGB functions.
 * @author SerVB
 * @since "GitHub new sources"
 */
public final class RGB {

    /**
     * Returns int value of RGB16.
     * @param r Red color (0..255).
     * @param g Green color (0..255).
     * @param b Blue color (0..255).
     * @return Color code.
     */
    public static final int RGB16(
            final int r, final int g, final int b
    ) {
        return (r & 0xF8)<<8 | (g & 0xFC)<<3 | (b & 0xF8)>>3;
    }
    
    public static final int Darken50(final int pixel) {
        return (pixel & 0xf7de) >> 1;
    }

    public static final int Darken25(int pixel) {
        pixel = (pixel & 0xf7de) >> 1;
        pixel += (pixel & 0xf7de) >> 1;
        return pixel;
    }

    public static final int DarkenBWBlend(final int a, final int b) {
        return ( (a & 0xf7de) >> 1 ) + ( (b & 0xf7de) >> 1 );
    }

    public static final int DarkenBW(final int pixel) { 
//        static uint8 rpt = 31; // Useless line in sources???
        int chnl = (pixel >> 6) & 0x1f;
        return DarkenBWBlend(COLOR.BWPAL[chnl], 0x39e7 );
    } 
    
    public static final int BlendPixels(
            final int dst, final int src, final int a
    ) {
        final int inv_a = 255 - a;
        final int sr = a * ((src & (0x1F<<11)) >> 11);
        final int sg = a * ((src & (0x3F<<5)) >> 5);
        final int sb = a * ((src & 0x1F));
        final int dr = inv_a * ((dst & (0x1F<<11)) >> 11);
        final int dg = inv_a * ((dst & (0x3F<<5)) >> 5);
        final int db = inv_a * ((dst & 0x1F));
        return ((sr+dr)>>8)<<11 | ((sg+dg)>>8)<<5 | ((sb+db)>>8);
    }
    
    /**
     * Copies src buffer to dst buffer. (Blit function)
     * @param dst       Destination buffer of pixels.
     * @param dst_idx   Index of the first element to copy in dst.
     * @param src       Source buffer of pixels.
     * @param src_idx   Index of the first element to copy in src.
     * @param size      Elements to copy number.
     */
    public static final void BlitDibBlock_RGB(
            final iBuffColor dst,
            final int dst_idx,
            final iBuffColor src,
            final int src_idx,
            final int size
    ) {
        for (int ii = 0; ii < size; ii++) {
            dst.set(
                    dst_idx + ii,
                    src.at(src_idx + ii)
            );
        }
//        memcpy(dst,src,size*sizeof(iDib.pixel));
    }
    
    /**
     * Fills the specified Dib block {@code [0, size-1]}
     * with the specified pixel.
     * @param buff      Buffer.
     * @param color     Pixel color.
     * @param size      Number of pixels to fill.
     */
    public static final void FillDibBlock(
            final iBuffColor buff,
            final int color,
            final int size
    ) {
        FillDibBlock(buff, color, 0, size);
    }
    
    /**
     * Fills the specified Dib block with the specified pixel.
     * @param buff      Buffer.
     * @param color     Pixel color.
     * @param beginIdx  First pixel index to fill.
     * @param size      Number of pixels to fill.
     */
    public static final void FillDibBlock(
            final iBuffColor buff,
            final int color,
            final int beginIdx,
            final int size
    ) {
            for (int xx=0; xx < size; ++xx) {
                buff.set(beginIdx + xx, color);
            }
    }
    
    public static final void FillDibBlockAlpha(
            final iBuffColor buff,
            final int color,
            final int alpha,
            final int size
    ) {
        FillDibBlockAlpha(buff, color, alpha, 0, size);
    }
    
    public static final void FillDibBlockAlpha(
            final iBuffColor buff,
            final int color,
            final int alpha,
            final int beginIdx,
            final int size
    ) {
        if (true) {
            final int sb = color & 0x1f;
            final int sg = (color >> 5) & 0x3f;
            final int sr = (color >> 11) & 0x1f;

            for (int xx = 0; xx < size; ++xx) {
                    final int b = buff.at(beginIdx + xx);
                    
                    final int db = b & 0x1f;
                    final int dg = (b >> 5) & 0x3f;
                    final int dr = (b >> 11) & 0x1f;
                    
                    final int newColor =
                            (((alpha * (sb-db)) >> 8) + db) | 
                            (((alpha * (sg-dg)) >> 8) + dg) << 5 | 
                            (((alpha * (sr-dr)) >> 8) + dr) << 11;

                    buff.set(beginIdx + xx, newColor);
            }
        }
        /* Comment in sources (deprecated?): */
//        else {
//            uint8 inv_a = 255-a;
//            uint16 sr = a * ((src & RED_MASK[iDib::RGB]) >> 11);
//            uint16 sg = a * ((src & GREEN_MASK[iDib::RGB]) >> 5);
//            uint16 sb = a * ((src & BLUE_MASK[iDib::RGB]));
//            for (uint32 xx=0; xx<size; ++xx, ++dst) {
//                    uint16 dr = inv_a * ((*dst & RED_MASK[iDib::RGB]) >> 11);
//                    uint16 dg = inv_a * ((*dst & GREEN_MASK[iDib::RGB]) >> 5);
//                    uint16 db = inv_a * ((*dst & BLUE_MASK[iDib::RGB]));
//                    *dst = (((sr+dr)>>8)<<11 | ((sg+dg)>>8)<<5 | ((sb+db)>>8));
//            }
//        }
    }
    
    public static final int TintedShadow(final int color) { 
        //static uint8 rpt = 31;
        final int chnl = (color >> 6) & 0x1f;
        return ( (COLOR.BWPAL[chnl] & 0xf7de) >> 1 ) + ((0x39e7 & 0xf7de) >> 1);
    }
    
    public static final void SetDibPixelAlpha(
            final iDib dib,
            final int index,
            final int color,
            final int a
    ) {
        final int oldColor = dib.getPixelAt(index);
         
        final int inv_a = 255 - a;
        
        final int sr = a * ((color & COLOR_MASK.RED  [ColorType.RGB.getValue()]) >> 11);
        final int sg = a * ((color & COLOR_MASK.GREEN[ColorType.RGB.getValue()]) >> 5);
        final int sb = a * ((color & COLOR_MASK.BLUE [ColorType.RGB.getValue()]));
        
        final int dr = inv_a * ((oldColor & COLOR_MASK.RED  [ColorType.RGB.getValue()]) >> 11);
        final int dg = inv_a * ((oldColor & COLOR_MASK.GREEN[ColorType.RGB.getValue()]) >> 5);
        final int db = inv_a * ((oldColor & COLOR_MASK.BLUE [ColorType.RGB.getValue()]));
        
        dib.setPixelAt(
                index,
                (((sr+dr)>>8) << 11) | (((sg+dg)>>8) << 5) | ((sb+db)>>8)
        );
    }
     
    public static final void BlitDibBlockAlpha(
            final iBuffColor dst,
            final int dst_idx,
            final iBuffColor src,
            final int src_idx,
            final int alpha,
            final int size
    ) {
        if (true) {
            for (int xx = 0; xx < size; ++xx) {
                    final int a = src.at(src_idx + xx);
                    final int b = dst.at(dst_idx + xx);
                    
                    final int sb = a & 0x1f;
                    final int sg = (a >> 5) & 0x3f;
                    final int sr = (a >> 11) & 0x1f;
                    
                    final int db = b & 0x1f;
                    final int dg = (b >> 5) & 0x3f;
                    final int dr = (b >> 11) & 0x1f;

                    dst.set(dst_idx + xx, 
                        (((alpha * (sb-db)) >> 8) + db) |
                        (((alpha * (sg-dg)) >> 8) + dg) << 5 |
                        (((alpha * (sr-dr)) >> 8) + dr) << 11
                    );
            }
        }
        /* Comment in sources: (deprecated?) */
//        else {
//                uint8 inv_a = 256-alpha;
//                for (uint32 xx=0; xx<size; ++xx, ++dst, ++color) {
//                        uint16 sr = alpha * ((*color & RED_MASK[iDib::RGB]) >> 11);
//                        uint16 sg = alpha * ((*color & GREEN_MASK[iDib::RGB]) >> 5);
//                        uint16 sb = alpha * ((*color & BLUE_MASK[iDib::RGB]));
//                        uint16 dr = inv_a * ((*dst & RED_MASK[iDib::RGB]) >> 11);
//                        uint16 dg = inv_a * ((*dst & GREEN_MASK[iDib::RGB]) >> 5);
//                        uint16 db = inv_a * ((*dst & BLUE_MASK[iDib::RGB]));
//                        *dst = (((sr+dr)>>8)<<11 | ((sg+dg)>>8)<<5 | ((sb+db)>>8));
//                }
//        }
    }
    
    public static final void BlitDibBlock_RGBA(
            final iBuffColor dst,
            final int dst_idx,
            final iBuffColor src,
            final int src_idx,
            final int size
    ) {
        for (int xx = 0; xx < size; ++xx) {
            final int spix = src.at(src_idx + xx);
            
            if ((spix & COLOR_MASK.ALPHA[ColorType.RGBA.getValue()]) == 0xF) {
                dst.set(dst_idx + xx,
                        (spix & COLOR_MASK.RED  [ColorType.RGBA.getValue()])    |
                        (spix & COLOR_MASK.GREEN[ColorType.RGBA.getValue()])>>1 |
                        (spix & COLOR_MASK.BLUE [ColorType.RGBA.getValue()])>>3
                );
            } else if ((spix & COLOR_MASK.ALPHA[ColorType.RGBA.getValue()]) > 0) {
                final int dpix = dst.at(dst_idx + xx);
                
                final int aa = (spix &  COLOR_MASK.ALPHA[ColorType.RGBA.getValue()]) << 2;
                
                final int RB1 = dpix & (COLOR_MASK.RED  [ColorType.RGB.getValue()] | COLOR_MASK.BLUE[ColorType.RGB.getValue()]); 
                final int G1  = dpix & (COLOR_MASK.GREEN[ColorType.RGB.getValue()]); 
                
                final int RB2 = 
                        (spix & COLOR_MASK.RED  [ColorType.RGBA.getValue()]) |
                        (spix & COLOR_MASK.BLUE [ColorType.RGBA.getValue()]) >> 3; 
                final int G2  = (spix & (COLOR_MASK.GREEN[ColorType.RGBA.getValue()])) >> 1; 
                
                int RB = RB1 + (((RB2 - RB1) * aa) >> 6); 
                int G  = G1  + ((( G2 - G1 ) * aa) >> 6);
                
                RB &= (COLOR_MASK.RED  [ColorType.RGB.getValue()] | COLOR_MASK.BLUE[ColorType.RGB.getValue()]); 
                G  &=  COLOR_MASK.GREEN[ColorType.RGB.getValue()]; 
                
                dst.set(dst_idx + xx, RB | G);
            }
        }
    }
    
    public static final void BlitDibBlock_RGBCK(
            final iBuffColor dst,
            final int dst_idx,
            final iBuffColor src,
            final int src_idx,
            final int size
    ) {
        for (int xx = 0; xx < size; ++xx) {
            final int spix = src.at(src_idx + xx);
            if ((spix & COLOR_MASK.ALPHA[ColorType.RGBCK.getValue()]) > 0) {
                dst.set(dst_idx + xx,
                        (spix & COLOR_MASK.RED  [ColorType.RGBCK.getValue()]) |
                        (spix & COLOR_MASK.GREEN[ColorType.RGBCK.getValue()]) |
                        (spix & COLOR_MASK.BLUE [ColorType.RGBCK.getValue()]) >> 1
                );
            }
    }
    }
    
    /**
     * Saves not bitmap but png.
     * @param dib   Dib to save.
     * @param fname Path to the resulting file.
     * @return      {@code True} if the saving was successful, {@code false} if not.
     */
    public static final boolean SaveDibBitmap(
            final iDib dib,
            final String fname
    ) {
        BufferedImage bi = ImageInOut.imageFromDib(dib);
        
        try {
            ImageIO.write(bi, "png",new File(fname));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(RGB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}

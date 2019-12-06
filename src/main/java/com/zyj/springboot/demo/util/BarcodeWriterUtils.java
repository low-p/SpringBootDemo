/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @since 0.0.1
 */

package com.zyj.springboot.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Map;

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class BarcodeWriterUtils implements Writer {
    /**
     * @param contents 。
     * @param format 。
     * @param width 。
     * @param height 。
     * @return  BitMatrix
     * @throws WriterException 。
     */
    @Override
    public BitMatrix encode(final String contents, final BarcodeFormat format, final int width, final int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    /**
     * @param contents 。
     * @param format 。
     * @param width 。
     * @param height 。
     * @param hints 。
     * @return BitMatrix
     * @throws WriterException 。
     */
    @Override
    public BitMatrix encode(final String contents, final BarcodeFormat format, final int width, final int height, final Map<EncodeHintType, ?> hints) throws WriterException {
        Writer writer;
        switch (format) {
            case EAN_8:
                writer = new EAN8Writer();
                break;
            case UPC_E:
                writer = new UPCEWriter();
                break;
            case EAN_13:
                writer = new EAN13Writer();
                break;
            case UPC_A:
                writer = new UPCAWriter();
                break;
            case QR_CODE:
                writer = new QRCodeWriter();
                break;
            case CODE_39:
                writer = new Code39Writer();
                break;
            case CODE_93:
                writer = new Code93Writer();
                break;
            case CODE_128:
                writer = new Code128Writer();
                break;
            case ITF:
                writer = new ITFWriter();
                break;
            case PDF_417:
                writer = new PDF417Writer();
                break;
            case CODABAR:
                writer = new CodaBarWriter();
                break;
            case DATA_MATRIX:
                writer = new DataMatrixWriter();
                break;
            case AZTEC:
                writer = new AztecWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + format);
        }
        return writer.encode(contents, format, width, height, hints);
    }

    /**
     * 获取内容长度
     * @param contents 。
     * @param format 。
     * @return int
     */
    public static int getContentsInputWidth(final String contents, final BarcodeFormat format){
        int inputWidth;
        switch (format) {
            case EAN_8:
                inputWidth = new EAN8Writer().encode(contents).length;
                break;
            case UPC_E:
                inputWidth = new UPCEWriter().encode(contents).length;
                break;
            case EAN_13:
                inputWidth = new EAN13Writer().encode(contents).length;
                break;
            case CODE_39:
                inputWidth = new Code39Writer().encode(contents).length;
                break;
            case CODE_93:
                inputWidth = new Code93Writer().encode(contents).length;
                break;
            case CODE_128:
                inputWidth = new Code128Writer().encode(contents).length;
                break;
            case ITF:
                inputWidth = new ITFWriter().encode(contents).length;
                break;
            case CODABAR:
                inputWidth = new CodaBarWriter().encode(contents).length;
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + format);
        }
        return inputWidth;
    }
}

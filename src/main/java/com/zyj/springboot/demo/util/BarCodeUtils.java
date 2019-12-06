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

import com.github.pagehelper.util.StringUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 条形码工具类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class BarCodeUtils {
    private static final Logger log = LoggerFactory.getLogger(QRCodeUtils.class);
    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 450;

    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 99;

    /**
     * 条形码-编码
     * @param contents 。
     * @param codeType 。
     * @return String
     */
    private static String createBarCodeBase64(final String contents, final String codeType) {
        try {
            if (StringUtil.isNotEmpty(contents)) {
                Map<EncodeHintType, java.io.Serializable> hints = new HashMap<>();
                //设置字符集编码类型
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                // 容错级别 这里选择最高H级别
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                hints.put(EncodeHintType.MARGIN, 0);
                BarcodeFormat codeFormat;
                if ("CODE_39".equals(codeType)) {
                    codeFormat = BarcodeFormat.CODE_39;
                } else {
                    codeFormat = BarcodeFormat.CODE_128;
                }
                int realInputWidth = BarCodeUtils.getRealInputWidth(BarcodeWriterUtils.getContentsInputWidth(contents, codeFormat), DEFAULT_WIDTH);
                BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, codeFormat, realInputWidth, DEFAULT_HEIGHT, hints);
                BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(image, "png", outputStream);
                    return Base64.encodeBase64String(outputStream.toByteArray());
                } catch (final IOException ie) {
                    log.error("输出条形码流异常", ie);
                }
            }
        } catch (final WriterException we) {
            log.error("条形码编码异常", we);
        }
        return null;
    }

    /**
     * 获取条形码Base64的图片编码
     * @param contents 。
     * @param codeType 。
     * @return String
     */
    public static String getBarCodeImgSrc(final String contents, final String codeType){
        String barCode = createBarCodeBase64(contents, codeType);
        if (StringUtil.isNotEmpty(barCode)) {
            return "data:image/png;base64," + barCode;
        }
        return null;
    }

    /**
     * 获取边框大小
     * @param inputWidth 。
     * @param width 。
     * @return int
     */
    public static int getRealInputWidth(final int inputWidth, final int width){
        int multiple = 1;
        if (inputWidth < width) {
            multiple = width/inputWidth;
        }
        return (inputWidth*multiple);
    }
}

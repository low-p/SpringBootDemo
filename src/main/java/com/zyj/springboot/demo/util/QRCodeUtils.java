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
 * 二维码工具类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class QRCodeUtils {
    private static final Logger log = LoggerFactory.getLogger(QRCodeUtils.class);
    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 80;
    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 80;

    /**
     * 二维码-编码
     * @param contents 。
     * @return String
     */
    private static String createQRCodeBase64(final String contents) {
        try {
            if (StringUtil.isNotEmpty(contents)) {
                Map<EncodeHintType, java.io.Serializable> hints = new HashMap<>();
                //设置字符集编码类型
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.MARGIN, 0);
                BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, DEFAULT_WIDTH, DEFAULT_HEIGHT, hints);
                BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(image, "png", outputStream);
                    return Base64.encodeBase64String(outputStream.toByteArray());
                } catch (final IOException ie) {
                    log.error("输出二维码流异常", ie);
                }
            }
        } catch (final WriterException we) {
           log.error("二维码编码异常", we);
        }
        return null;
    }

    /**
     * 获取二维码base64的图片编码
     * @param contents 。
     * @return String
     */
    public static String getQRCodeImgSrc(final String contents) {
        String qrCode = createQRCodeBase64(contents);
        if (StringUtil.isNotEmpty(qrCode)) {
            return "data:image/png;base64," + qrCode;
        }
        return null;
    }
}

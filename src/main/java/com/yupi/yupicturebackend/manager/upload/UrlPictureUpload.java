package com.yupi.yupicturebackend.manager.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.yupi.yupicturebackend.exception.BusinessException;
import com.yupi.yupicturebackend.exception.ErrorCode;
import com.yupi.yupicturebackend.exception.ThrowUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 图片Url上传
 */
@Service
public class UrlPictureUpload extends PictureUploadTemplate {
    @Override
    protected void validPicture(Object inputSource) {
        String fileUrl = (String) inputSource;
        // 校验非空
        ThrowUtils.throwIf(StrUtil.isBlank(fileUrl),ErrorCode.PARAMS_ERROR,"文件地址为空");

        // 校验URL格式
        try {
            new URL(fileUrl);
        } catch (MalformedURLException e)  {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"文件地址格式不对");
        }

        // 校验URL协议
        ThrowUtils.throwIf(!fileUrl.startsWith("http://")&& !fileUrl.startsWith("https://"),
                ErrorCode.PARAMS_ERROR,"仅支持HTTP或HTTPS的协议的文件地址"
        );
        // 发送HEAD请求
        HttpResponse httpResponse = null;

        try {
            httpResponse = HttpUtil.createRequest(Method.HEAD, fileUrl).execute();
            //未正常返回，无需执行其他判断
            if (httpResponse.getStatus()!= HttpStatus.HTTP_OK){
                return;
            }
            //文件存在，文件类型校验
            String contentType = httpResponse.header("Content-Type");
            //不为空校验是否合法
            if (StrUtil.isNotBlank(contentType)){
                //允许的图片类型
                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/png","image/jpg","image/jpeg","image/webp");
                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(contentType.toLowerCase()),
                        ErrorCode.PARAMS_ERROR,"文件类型错误");
            }

            //文件存在，文件大小校验
            String contentLengthStr = httpResponse.header("Content-Length");
            if (StrUtil.isNotBlank(contentLengthStr)) {
                try {
                    long contentLength = Long.parseLong(contentLengthStr);
                    final long ONE_M = 1024 * 1024L;
                    ThrowUtils.throwIf(contentLength > 2 * ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过 2M");
                } catch (NumberFormatException e) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR,"文件大小格式异常");
                }
            }

        } finally {
            // 记得释放资源
            if (httpResponse!=null) {
                httpResponse.close();
            }
        }
    }

    @Override
    protected String getOriginFilename(Object inputSource) {
        String fileUrl = (String) inputSource;
        // 从URL中提取文件名
        return FileUtil.mainName(fileUrl);
    }

    @Override
    protected void processFile(Object inputSource, File file) throws Exception {
        String fileUrl = (String) inputSource;
        // 下载文件到临时目录
        HttpUtil.downloadFile(fileUrl,file);
    }
}

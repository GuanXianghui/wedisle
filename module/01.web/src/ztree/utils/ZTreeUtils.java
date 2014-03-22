package ztree.utils;

import org.apache.commons.lang.StringUtils;
import ztree.entities.Article;

import java.io.*;
import java.util.List;

/**
 *  ztree������
 */
public class ZTreeUtils
{
    /**
     * ��Article������Json����
     * @param article
     * @return
     */
    public static String getJsonFromArticle(Article article)
    {
        String json = "{id: " + article.getId() + ", " +
                "pId: " + article.getPid() + ", " +
                "name:'" + formatOutputSpecialString(article.getName()) + "', " +
                "isParent: " + article.isArticleType() + "}";
        return json;
    }

    /**
     * ��List<Article>������Json����
     * @param articles
     * @return
     */
    public static String getJsonFromArticles(List<Article> articles)
    {
        String json = "[";
        for(int i=0;i<articles.size();i++)
        {
            if(i > 0)
            {
                json += ",";
            }
            json += getJsonFromArticle(articles.get(i));
        }
        json += "]";
        return json;
    }

    /**
     * ��ʽ�����������ַ�
     * @param string
     * @return
     */
    public static String formatInputSpecialString(String string)
    {
        string = string.replaceAll("'", "\\\\'")
                .replaceAll("\"", "\\\\\"")
                .replaceAll("&", "\\\\&");
        return string;
    }

    /**
     * ��ʽ�����������ַ�
     * @param string
     * @return
     */
    public static String formatOutputSpecialString(String string)
    {
        string = string.replaceAll("'", "\\\\'");
        return string;
    }

    /**
     * �����ļ�
     * @param src
     * @param dst
     */
    public static void copy(File src, File dst) throws Exception
    {
        int BUFFER_SIZE = 16 * 1024;
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0)
                {
                    out.write(buffer);
                }
            } finally
            {
                if (null != in)
                {
                    in.close();
                }
                if (null != out)
                {
                    out.close();
                }
            }
    }

    /**
     * ������ ɾ��ͼƬ
     * @param article
     * @param delImgId
     * @return
     */
    public static String delImage(Article article, String delImgId)
    {
        String imageIds = article.getImageIds();
        if(StringUtils.isBlank(imageIds))
        {
            return StringUtils.EMPTY;
        }
        String[] imageArray = imageIds.split(",");
        String newImageIds = StringUtils.EMPTY;
        for(int i=0;i<imageArray.length;i++)
        {
            if(StringUtils.equals(imageArray[i], delImgId))
            {
                continue;
            }
            if(StringUtils.isNotBlank(newImageIds))
            {
                newImageIds += ",";
            }
            newImageIds += imageArray[i];
        }
        return newImageIds;
    }

    /**
     * ������ ǰ��ͼƬ
     * @param article
     * @param preImgId
     * @return
     */
    public static String preImage(Article article, String preImgId)
    {
        String imageIds = article.getImageIds();
        if(StringUtils.isBlank(imageIds))
        {
            return StringUtils.EMPTY;
        }
        String[] imageArray = imageIds.split(",");
        int index = -1;
        for(int i=0;i<imageArray.length;i++)
        {
            if(StringUtils.equals(imageArray[i], preImgId))
            {
                index = i;
                break;
            }
        }
        if((-1 == index) || (0 == index))
        {
            return imageIds;
        }
        String preValue = imageArray[index - 1];
        imageArray[index - 1] = preImgId;
        imageArray[index] = preValue;

        String newImageIds = StringUtils.EMPTY;
        for(int i=0;i<imageArray.length;i++)
        {
            if(StringUtils.isNotBlank(newImageIds))
            {
                newImageIds += ",";
            }
            newImageIds += imageArray[i];
        }
        return newImageIds;
    }

    /**
     * ������ ����ͼƬ
     * @param article
     * @param nextImgId
     * @return
     */
    public static String nextImage(Article article, String nextImgId)
    {
        String imageIds = article.getImageIds();
        if(StringUtils.isBlank(imageIds))
        {
            return StringUtils.EMPTY;
        }
        String[] imageArray = imageIds.split(",");
        int index = -1;
        for(int i=0;i<imageArray.length;i++)
        {
            if(StringUtils.equals(imageArray[i], nextImgId))
            {
                index = i;
                break;
            }
        }
        if((-1 == index) || (imageArray.length-1 == index))
        {
            return imageIds;
        }
        String preValue = imageArray[index + 1];
        imageArray[index] = preValue;
        imageArray[index + 1] = nextImgId;

        String newImageIds = StringUtils.EMPTY;
        for(int i=0;i<imageArray.length;i++)
        {
            if(StringUtils.isNotBlank(newImageIds))
            {
                newImageIds += ",";
            }
            newImageIds += imageArray[i];
        }
        return newImageIds;
    }
}

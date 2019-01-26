package com.kq.common.util.jwt;



import com.kq.common.constants.CommonConstants;
import com.kq.common.util.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtil {

    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();


    /**
     * 快速的生成Token
     *
     * @return
     */
    public static String genToken(IJWTInfo ijwtInfo, String pubkeyPath, int exprieTime) throws Exception {
        String compactJws = Jwts.builder()
                //签名人
                .setSubject(ijwtInfo.getUniqueName())
                //增加载荷信息
                .claim(CommonConstants.JWT_KEY_USER_ID, ijwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, ijwtInfo.getName())
                .claim(CommonConstants.JWT_KEY_USERTYPE,ijwtInfo.getUserType())
                //不能放权限,base64解码了，不安全
               // .claim(CommonConstants.JWT_KEY_PERIMISSION,ijwtInfo.getPerimissionList())
                //设置失效时间 DateTime是个工具类，挺有意思，可以看看
                .setExpiration(DateTime.now().plusSeconds(exprieTime).toDate())
                //设置加密算法 SHA512  SignatureAlgorithm.RS512 需要公钥私钥
                //设置SHA512的算法不需要公钥私钥,直接给个值即可
                //私钥加密
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(pubkeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String genToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
               // .claim(CommonConstants.JWT_KEY_PERIMISSION,jwtInfo.getPerimissionList())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }


    /**
     * 公钥解密，获取token中的信息
     * @param token
     * @param pubkeyPath
     * @return
     */
    public static Jws<Claims> parserToken(String token, String pubkeyPath) throws Exception {
            //解析token
            //公钥解密,返回的这个对象
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubkeyPath)).parseClaimsJws(token);
            return claimsJws;

    }

    /**
     * 公钥解析token,从流文件当中
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     *
     * @param token
     * @param pubKeyPath
     * @return userid,user
     * @throws Exception
     */
    public  static IJWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)),StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USERTYPE)));
    }


    /**
     * 获取token中的用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token,byte[] pubKeyByte) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyByte);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), StringHelper.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }


    public static void main(String[] args) throws Exception {

    }
}

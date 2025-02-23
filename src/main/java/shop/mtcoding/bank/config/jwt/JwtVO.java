package shop.mtcoding.bank.config.jwt;

/*
 * SECRET 노출되면 안된다 (클라우드 AWS, - 환경변수, 파일에 있는 것을 읽을 수도 있고!)
 * 리플래시 토큰 ( 여기서 구현 X)
 */
public interface JwtVO {
    //HS256 대칭키  -- 이거는 절대 노출 X 환경 변수나 DB값을 쓸것
    // 이해를 위해 여기에 기입
    public static final String SECRET = "id";

    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;  //만료시간 1주일

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";
}

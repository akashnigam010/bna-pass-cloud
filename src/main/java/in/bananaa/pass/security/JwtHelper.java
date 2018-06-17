package in.bananaa.pass.security;

import static in.bananaa.pass.security.TokenInfo.*;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.dto.type.RoleType;
import in.bananaa.pass.helper.exception.BusinessException;
import net.oauth.jsontoken.Checker;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class JwtHelper {
	private static final Logger LOG = Logger.getLogger(JwtHelper.class);
	private static String SIGNING_KEY;
	private static String tokenExpiry;
	private static String audience;
	private static String issuer;
	
	@Value("${jwt.token.expiry}")
	public void setTokenExpiry(String expiry) {
		JwtHelper.tokenExpiry = expiry;
	}
	
	@Value("${jwt.token.audience}")
	public void setAudience(String audience) {
		JwtHelper.audience = audience;
	}
	
	@Value("${jwt.token.issuer}")
	public void setIssuer(String issuer) {
		JwtHelper.issuer = issuer;
	}
	
	@Value("${jwt.token.issuer}")
	public void setS(String signingKey) {
		JwtHelper.SIGNING_KEY = signingKey;
	}

	/**
	 * Creates a JSON Web Token which is digitally signed token and contains a
	 * payload (e.g. userId to identify the user). The signing key is secret
	 * which ensures that the token is authentic and has not been modified.
	 * Using a JWT eliminates the need to store authentication session
	 * information in a database.
	 * 
	 * @param userId
	 * @param durationInDays
	 * @return
	 * @throws BusinessException 
	 */
	public static String createJsonWebTokenForUser(String id, String name) throws BusinessException {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(issuer, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured while creating JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(audience);
		token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + 1000L * Long.valueOf(tokenExpiry)));
		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty(ID, id);
		request.addProperty(NAME, name);
		request.addProperty(ROLE, RoleType.USER.getRole());
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add(INFO, request);
		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			LOG.error("Exception occured while serializing and signing JWT token ", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	/**
	 * Verifies a JSON Web Token's validity and extracts the userId and other
	 * information from it.
	 * 
	 * @param token
	 * @return
	 * @throws BusinessException 
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static TokenInfo verifyToken(String token) {
		try {
			final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());
			VerifierProvider hmacLocator = new VerifierProvider() {
				@Override
				public List<Verifier> findVerifier(String id, String key) {
					return Lists.newArrayList(hmacVerifier);
				}
			};
			VerifierProviders locators = new VerifierProviders();
			locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
			Checker checker = new Checker() {
				@Override
				public void check(JsonObject payload) throws SignatureException {
				}
			};
			// Ignore Audience does not mean that the Signature is ignored
			JsonTokenParser parser = new JsonTokenParser(locators, checker);
			JsonToken jt;
			try {
				jt = parser.verifyAndDeserialize(token);
			} catch (SignatureException e) {
				LOG.error("Exception occured while verifying and deserializing JWT token ", e);
				throw new AuthenticationCredentialsNotFoundException("Token expired! Please login again");
			}
			
			JsonObject payload = jt.getPayloadAsJsonObject();
			TokenInfo tokenInfo = new TokenInfo();
			String issuer = payload.getAsJsonPrimitive("iss").getAsString();
			String role = payload.getAsJsonObject(INFO).getAsJsonPrimitive(ROLE).getAsString();
			if (JwtHelper.issuer.equals(issuer) && StringUtils.isNotBlank(role)) {
				if (RoleType.USER == RoleType.getRole(role)) {
					String id = payload.getAsJsonObject(INFO).getAsJsonPrimitive(ID).getAsString();
					String name = payload.getAsJsonObject(INFO).getAsJsonPrimitive(NAME).getAsString();
					tokenInfo.setId(id);
					tokenInfo.setName(name);
					tokenInfo.setRole(role);
					tokenInfo.setIssuedAt(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong()));
					tokenInfo.setExpiresAt(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong()));
					return tokenInfo;
				}
			}
			return null;
		} catch (InvalidKeyException e) {
			LOG.error("Exception occured for invalid key while verifying JWT token ", e);
			throw new AuthenticationCredentialsNotFoundException("Token expired! Kindly login again");
		}
	}
}

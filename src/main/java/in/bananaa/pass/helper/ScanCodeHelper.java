package in.bananaa.pass.helper;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = { "classpath:application.properties" })
public class ScanCodeHelper {
	private static String SIGNING_KEY;

	@Value("${scan.code.signer}")
	public void setS(String signingKey) {
		ScanCodeHelper.SIGNING_KEY = signingKey;
	}

	public String generateCode(Integer memberId, Integer userId) {
		Calendar calendar = Calendar.getInstance();
		String date = DateTimeUtil.formatDate(calendar, DateFormatType.DATE_FORMAT_DD_MM_YYYY);
		return memberId.toString() + userId.toString() + SIGNING_KEY + date;
	}
}

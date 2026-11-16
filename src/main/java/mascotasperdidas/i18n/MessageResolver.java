package mascotasperdidas.i18n;

import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {
    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String resolve(String code, Locale locale, Object... arguments) {
        Objects.requireNonNull(code, "Message code is required");
        Objects.requireNonNull(locale, "Locale is required");
        return messageSource.getMessage(code, arguments, locale);
    }
}

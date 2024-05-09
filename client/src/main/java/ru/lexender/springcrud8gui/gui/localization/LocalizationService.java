package ru.lexender.springcrud8gui.gui.localization;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class LocalizationService {
    MessageSource messageSource;

    public String get(String src) {
        return messageSource.getMessage(src, null, LocaleContextHolder.getLocale());
    }
}

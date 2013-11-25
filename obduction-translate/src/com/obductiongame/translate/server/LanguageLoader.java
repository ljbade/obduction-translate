package com.obductiongame.translate.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.icu.util.ULocale;
import com.obductiongame.translate.server.service.MainServiceImpl;
import com.obductiongame.translate.shared.Language;
import com.obductiongame.translate.shared.LanguageImpl;

public class LanguageLoader {

	private static final Logger LOG = Logger.getLogger(MainServiceImpl.class.getName());

	private static LanguageImpl[] languagesArray;

	static public Language[] getLanguages() {
		if (languagesArray == null) {
			LOG.log(Level.INFO, "languagesArray is null, creating");
			ArrayList<LanguageImpl> languages = new ArrayList<LanguageImpl>();

			String[] languageCodes = ULocale.getISOLanguages();
			for (String languageCode : languageCodes) {
				String languageName = new ULocale(languageCode).getDisplayLanguage(ULocale.ENGLISH);
				languages.add(new LanguageImpl(languageCode, languageName));
			}

			languagesArray = languages.toArray(new LanguageImpl[0]);
			Arrays.sort(languagesArray, new LanguageImpl.NameComparator());
		}

		return languagesArray;
	}
}

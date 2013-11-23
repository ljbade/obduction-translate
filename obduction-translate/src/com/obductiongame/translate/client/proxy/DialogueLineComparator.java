package com.obductiongame.translate.client.proxy;

import java.util.Comparator;

public class DialogueLineComparator implements Comparator<DialogueLineProxy> {

	@Override
	public int compare(DialogueLineProxy o1, DialogueLineProxy o2) {
		if (o1.getId() == o2.getId()) {
			return o1.getLanguage().compareToIgnoreCase(o2.getLanguage());
		}
		return o1.getId() - o2.getId();
	}

}

package com.rdk.crime.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testName() throws Exception {

		String source = "The,\"quick,brown\",fox,\"jumps,over\",the,lazy,dog";


		String normalizedString = StringUtils.normalizeSubstring(source);
		assertThat(normalizedString,
				equalTo("The,\"quick;brown\",fox,\"jumps;over\",the,lazy,dog"));
	}

}

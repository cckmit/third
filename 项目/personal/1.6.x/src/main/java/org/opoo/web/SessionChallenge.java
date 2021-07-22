/*
 * $Id: SessionChallenge.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.opoo.lang.DefaultRandomStringGenerator;
import org.opoo.lang.RandomStringGenerator;

/**
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public class SessionChallenge {
	private static class Token implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5494388225310625734L;
		private String value;
		private long time;

		public Token(String value, long time) {
			this.value = value;
			this.time = time;
		}
	}

	private RandomStringGenerator gen = new DefaultRandomStringGenerator(23);
	private String key = "__s_k_challenge";
	private String parameterName = ".challenge";
	private int millis = -1;

	public SessionChallenge() {
	}

	public SessionChallenge(String key) {
		this.key = key;
	}

	public SessionChallenge(String key, int minutes) {
		this.key = key;
		millis = minutes * 60 * 1000;
	}

	public SessionChallenge(String key, String parameterName, int minutes) {
		this.key = key;
		this.parameterName = parameterName;
		millis = minutes * 60 * 1000;
	}

	private String generateChallenge() {
		return gen.getNewString();
	}

	private String getKey(Serializable id) {
		if (id == null) {
			return key;
		}
		return key + "." + id;
	}

	public void saveChallenge(HttpServletRequest request) {
		saveChallenge(request, null);
	}

	public void saveChallenge(HttpServletRequest request, Serializable id) {
		String c = generateChallenge();
		Token token = new Token(c, System.currentTimeMillis());
		request.getSession().setAttribute(getKey(id), token);
	}

	public boolean isChallengeValid(HttpServletRequest request, Serializable id, boolean reset) {
		HttpSession session = request.getSession();
		String s0 = request.getParameter(parameterName);
		String k = getKey(id);
		Token s1 = (Token) session.getAttribute(k);
		if (reset) {
			session.removeAttribute(k);
		}
		boolean b = s0 != null && s1 != null && s0.equals(s1.value);
		if (b && millis > 0) {
			b = ((System.currentTimeMillis() - s1.time) < millis);
		}
		return b;
	}

	public boolean isChallengeValid(HttpServletRequest request, boolean reset) {
		return isChallengeValid(request, null, reset);
	}

	public boolean isChallengeValid(HttpServletRequest request) {
		return isChallengeValid(request, true);
	}

	public void resetChallenge(HttpServletRequest request) {
		resetChallenge(request, null);
	}

	public void resetChallenge(HttpServletRequest request, Serializable id) {
		request.getSession().removeAttribute(getKey(id));
	}
}
